package net.aooms.core.id;

import io.shardingsphere.core.keygen.DefaultKeyGenerator;

/**
 * snowflake id 算法生成
 * Created by 风象南(cheereebo) on 2018/9/7
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
