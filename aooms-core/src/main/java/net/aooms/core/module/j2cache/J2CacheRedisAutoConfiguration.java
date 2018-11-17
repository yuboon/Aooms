package net.aooms.core.module.j2cache;

import com.alibaba.fastjson.JSON;
import net.oschina.j2cache.Cache;
import net.oschina.j2cache.J2Cache;
import net.oschina.j2cache.autoconfigure.J2CacheAutoConfiguration;
import net.oschina.j2cache.cache.support.redis.SpringRedisProvider;
import net.oschina.j2cache.cache.support.util.J2CacheSerializer;
import net.oschina.j2cache.redis.RedisUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 对spring redis支持的配置入口
 *
 * 修复本地调试时，不启用redis时的问题
 * @author zhangsaizz
 *
 */
@Configuration
@AutoConfigureAfter({ RedisAutoConfiguration.class })
@AutoConfigureBefore({ J2CacheAutoConfiguration.class })
public class J2CacheRedisAutoConfiguration {

	private final static int MAX_ATTEMPTS = 3;

	private final static int CONNECT_TIMEOUT = 5000;

	private static final Logger log = LoggerFactory.getLogger(J2CacheRedisAutoConfiguration.class);

	@Deprecated
	@Bean("j2CahceRedisConnectionFactory")
	@ConditionalOnProperty(name = "j2cache.redis-client", havingValue = "jedis")
	public JedisConnectionFactory jedisConnectionFactory(net.oschina.j2cache.J2CacheConfig j2CacheConfig) {
        // 解决二级缓存为none时加载redis问题
        String l2CacheName = j2CacheConfig.getL2CacheName();
        if("none".equalsIgnoreCase(l2CacheName)){
            return new JedisConnectionFactory();
        }

		Properties l2CacheProperties = j2CacheConfig.getL2CacheProperties();
		String hosts = l2CacheProperties.getProperty("hosts");
		String mode = l2CacheProperties.getProperty("mode");
		String clusterName = l2CacheProperties.getProperty("cluster_name");
		String password = l2CacheProperties.getProperty("password");
		int database = l2CacheProperties.getProperty("database") == null ? 0
				: Integer.parseInt(l2CacheProperties.getProperty("database"));
		JedisConnectionFactory connectionFactory = null;
		JedisPoolConfig config = RedisUtils.newPoolConfig(l2CacheProperties, null);
		List<RedisNode> nodes = new ArrayList<>();
		if (hosts != null && !"".equals(hosts)) {
			for (String node : hosts.split(",")) {
				String[] s = node.split(":");
				String host = s[0];
				int port = (s.length > 1) ? Integer.parseInt(s[1]) : 6379;
				RedisNode n = new RedisNode(host, port);
				nodes.add(n);
			}
		} else {
			log.error("Aooms.J2CacheRedisAutoConfiguration j2cache中的redis配置缺少hosts！！");
			throw new IllegalArgumentException();
		}

		RedisPassword paw = RedisPassword.none();
		if (!StringUtils.isEmpty(password)) {
			paw = RedisPassword.of(password);
		}

		switch (mode) {
		case "sentinel":
			RedisSentinelConfiguration sentinel = new RedisSentinelConfiguration();
			sentinel.setDatabase(database);
			sentinel.setPassword(paw);
			sentinel.setMaster(clusterName);
			sentinel.setSentinels(nodes);
			connectionFactory = new JedisConnectionFactory(sentinel, config);
			break;
		case "cluster":
			RedisClusterConfiguration cluster = new RedisClusterConfiguration();
			cluster.setClusterNodes(nodes);
			cluster.setMaxRedirects(MAX_ATTEMPTS);
			cluster.setPassword(paw);
			connectionFactory = new JedisConnectionFactory(cluster, config);
			break;
		case "sharded":
			try {
				for (String node : hosts.split(",")) {
					connectionFactory = new JedisConnectionFactory(new JedisShardInfo(new URI(node)));
					connectionFactory.setPoolConfig(config);
					log.warn("Jedis mode [sharded] not recommended for use!!");
					break;
				}
			} catch (URISyntaxException e) {
				throw new JedisConnectionException(e);
			}
			break;
		default:
			for (RedisNode node : nodes) {
				String host = node.getHost();
				int port = node.getPort();
				RedisStandaloneConfiguration single = new RedisStandaloneConfiguration(host, port);
				single.setDatabase(database);
				single.setPassword(paw);
				JedisClientConfiguration.JedisClientConfigurationBuilder clientConfiguration = JedisClientConfiguration.builder();
				clientConfiguration.usePooling().poolConfig(config);
				clientConfiguration.connectTimeout(Duration.ofMillis(CONNECT_TIMEOUT));
				connectionFactory = new JedisConnectionFactory(single, clientConfiguration.build());
				break;
			}
			if (!"single".equalsIgnoreCase(mode))
				log.warn("Redis mode [" + mode + "] not defined. Using 'single'.");
			break;
		}
		return connectionFactory;
	}

	@Primary
	@Bean("j2CahceRedisConnectionFactory")
	@ConditionalOnProperty(name = "j2cache.redis-client", havingValue = "lettuce", matchIfMissing = true)
	public LettuceConnectionFactory lettuceConnectionFactory(net.oschina.j2cache.J2CacheConfig j2CacheConfig) {

	    // 解决二级缓存为none时加载redis问题
	    String l2CacheName = j2CacheConfig.getL2CacheName();
        if("none".equalsIgnoreCase(l2CacheName)){
            return new LettuceConnectionFactory();
        }

		Properties l2CacheProperties = j2CacheConfig.getL2CacheProperties();
		String hosts = l2CacheProperties.getProperty("hosts");
		String mode = l2CacheProperties.getProperty("mode");
		String clusterName = l2CacheProperties.getProperty("cluster_name");
		String password = l2CacheProperties.getProperty("password");
		int database = l2CacheProperties.getProperty("database") == null ? 0
				: Integer.parseInt(l2CacheProperties.getProperty("database"));
		LettuceConnectionFactory connectionFactory = null;
		LettucePoolingClientConfiguration.LettucePoolingClientConfigurationBuilder config = LettucePoolingClientConfiguration.builder();
		config.commandTimeout(Duration.ofMillis(CONNECT_TIMEOUT));
		config.poolConfig(getGenericRedisPool(l2CacheProperties, null));
		List<RedisNode> nodes = new ArrayList<>();
		if (hosts != null && !"".equals(hosts)) {
			for (String node : hosts.split(",")) {
				String[] s = node.split(":");
				String host = s[0];
				int port = (s.length > 1) ? Integer.parseInt(s[1]) : 6379;
				RedisNode n = new RedisNode(host, port);
				nodes.add(n);
			}
		} else {
			log.error("Aooms.J2CacheRedisAutoConfiguration j2cache中的redis配置缺少hosts！！");
			throw new IllegalArgumentException();
		}
		RedisPassword paw = RedisPassword.none();
		if (!StringUtils.isEmpty(password)) {
			paw = RedisPassword.of(password);
		}
		switch (mode) {
		case "sentinel":
			RedisSentinelConfiguration sentinel = new RedisSentinelConfiguration();
			sentinel.setDatabase(database);
			sentinel.setPassword(paw);
			sentinel.setMaster(clusterName);
			sentinel.setSentinels(nodes);
			connectionFactory = new LettuceConnectionFactory(sentinel, config.build());
			break;
		case "cluster":
			RedisClusterConfiguration cluster = new RedisClusterConfiguration();
			cluster.setClusterNodes(nodes);
			cluster.setMaxRedirects(MAX_ATTEMPTS);
			cluster.setPassword(paw);
			connectionFactory = new LettuceConnectionFactory(cluster, config.build());
			break;
		case "sharded":
			throw new IllegalArgumentException("Lettuce not support use mode [sharded]!!");
		default:
			for (RedisNode node : nodes) {
				String host = node.getHost();
				int port = node.getPort();
				RedisStandaloneConfiguration single = new RedisStandaloneConfiguration(host, port);
				single.setDatabase(database);
				single.setPassword(paw);
				connectionFactory = new LettuceConnectionFactory(single, config.build());
				break;
			}
			if (!"single".equalsIgnoreCase(mode))
				log.warn("Redis mode [" + mode + "] not defined. Using 'single'.");
			break;
		}
		return connectionFactory;
	}

	@Bean("j2CacheRedisTemplate")
    @ConditionalOnBean(RedisConnectionFactory.class)
	public RedisTemplate<String, Serializable> j2CacheRedisTemplate(
			@Qualifier("j2CahceRedisConnectionFactory") RedisConnectionFactory j2CahceRedisConnectionFactory,
			@Qualifier("j2CacheValueSerializer") RedisSerializer<Object> j2CacheSerializer) {
		RedisTemplate<String, Serializable> template = new RedisTemplate<String, Serializable>();
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setDefaultSerializer(j2CacheSerializer);
		template.setConnectionFactory(j2CahceRedisConnectionFactory);
		return template;
	}

	@Bean("j2CacheValueSerializer")
	public RedisSerializer<Object> j2CacheValueSerializer() {
		return new J2CacheSerializer();
	}

	@Bean("j2CacheRedisMessageListenerContainer")
    @ConditionalOnBean(RedisConnectionFactory.class)
    RedisMessageListenerContainer container(
			@Qualifier("j2CahceRedisConnectionFactory") RedisConnectionFactory j2CahceRedisConnectionFactory) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(j2CahceRedisConnectionFactory);
		return container;
	}

	private GenericObjectPoolConfig getGenericRedisPool(Properties props, String prefix) {
		GenericObjectPoolConfig cfg = new GenericObjectPoolConfig();
		cfg.setMaxTotal(Integer.valueOf((String) props.getOrDefault(key(prefix, "maxTotal"), "-1")));
		cfg.setMaxIdle(Integer.valueOf((String) props.getOrDefault(key(prefix, "maxIdle"), "100")));
		cfg.setMaxWaitMillis(Integer.valueOf((String) props.getOrDefault(key(prefix, "maxWaitMillis"), "100")));
		cfg.setMinEvictableIdleTimeMillis(
				Integer.valueOf((String) props.getOrDefault(key(prefix, "minEvictableIdleTimeMillis"), "864000000")));
		cfg.setMinIdle(Integer.valueOf((String) props.getOrDefault(key(prefix, "minIdle"), "10")));
		cfg.setNumTestsPerEvictionRun(
				Integer.valueOf((String) props.getOrDefault(key(prefix, "numTestsPerEvictionRun"), "10")));
		cfg.setLifo(Boolean.valueOf(props.getProperty(key(prefix, "lifo"), "false")));
		cfg.setSoftMinEvictableIdleTimeMillis(
				Integer.valueOf((String) props.getOrDefault(key(prefix, "softMinEvictableIdleTimeMillis"), "10")));
		cfg.setTestOnBorrow(Boolean.valueOf(props.getProperty(key(prefix, "testOnBorrow"), "true")));
		cfg.setTestOnReturn(Boolean.valueOf(props.getProperty(key(prefix, "testOnReturn"), "false")));
		cfg.setTestWhileIdle(Boolean.valueOf(props.getProperty(key(prefix, "testWhileIdle"), "true")));
		cfg.setTimeBetweenEvictionRunsMillis(
				Integer.valueOf((String) props.getOrDefault(key(prefix, "timeBetweenEvictionRunsMillis"), "300000")));
		cfg.setBlockWhenExhausted(Boolean.valueOf(props.getProperty(key(prefix, "blockWhenExhausted"), "false")));
		return cfg;
	}

	private String key(String prefix, String key) {
		return (prefix == null) ? key : prefix + "." + key;
	}
}