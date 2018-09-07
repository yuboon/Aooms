package net.aooms.core.module.id;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;

import java.util.UUID;

/**
 * snowflake id 算法生成
 * Created by cccyb on 2018/9/7
 */
public class GeneratorForSnowflake implements IDGenerator {

    private static DefaultKeyGenerator defaultKeyGenerator = new DefaultKeyGenerator();
    static {
        DefaultKeyGenerator.setWorkerId(1L);
    }

    @Override
    public ID createID() {
        Object value = defaultKeyGenerator.generateKey();
        return new ID(value);
    }
}
