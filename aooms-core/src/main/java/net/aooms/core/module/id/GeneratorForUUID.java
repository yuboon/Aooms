package net.aooms.core.module.id;

import org.springframework.util.IdGenerator;

import java.util.UUID;

/**
 * uuid 生成
 * Created by cccyb on 2018/9/7
 */
public class GeneratorForUUID implements IDGenerator {

    @Override
    public ID createID() {
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        return new ID(uuid);
    }
}
