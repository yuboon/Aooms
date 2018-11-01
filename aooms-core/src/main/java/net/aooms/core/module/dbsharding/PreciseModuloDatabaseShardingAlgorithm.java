package net.aooms.core.module.dbsharding;

import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * 分库策略
 * Created by 风象南(yuboon) on 2018/9/7
 */
public final class PreciseModuloDatabaseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Integer> preciseShardingValue) {
        for (String each : collection) {
            if (each.endsWith(preciseShardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}