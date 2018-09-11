package net.aooms.core.module.dbsharding;

import io.shardingsphere.core.api.algorithm.sharding.PreciseShardingValue;
import io.shardingsphere.core.api.algorithm.sharding.standard.PreciseShardingAlgorithm;

import java.util.Collection;

/**
 * 分表策略
 * Created by 风象南(cheereebo) on 2018/9/7
 */
public final class PreciseModuloTableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {
    
    @Override
    public String doSharding(final Collection<String> availableTargetNames, final PreciseShardingValue<Long> shardingValue) {
        for (String each : availableTargetNames) {
            if (each.endsWith(shardingValue.getValue() % 2 + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }
}