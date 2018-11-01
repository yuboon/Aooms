package net.aooms.rbac.mapper;

import net.aooms.core.module.mybatis.MappedStatementId;
import net.aooms.core.module.mybatis.MapperPackage;

/**
 * Mapper包路径定义
 * Created by 风象南(yuboon) on 2018/9/27
 */
public class RbacMapper implements MapperPackage {

    public static final MapperPackage PKG = new RbacMapper();

    public String by(String id) {
        return MappedStatementId.getStatementId(this.getClass(),id);
    }
}
