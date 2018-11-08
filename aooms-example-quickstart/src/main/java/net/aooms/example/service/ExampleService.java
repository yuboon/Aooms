package net.aooms.example.service;

import net.aooms.core.datasource.DS;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.record.RecordGroup;
import net.aooms.core.service.GenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * more example service
 * Created by 风象南(yuboon) on 2018-09-18
 */
@Service
public class ExampleService extends GenericService {

    @Autowired
    private Db db;

    @DS
    public void example6() {
        // 使用主数据源
        RecordGroup recordGroup = db.use("master").findRecords("UserMapper.findList", SqlPara.SINGLETON);
        setResultValue("pg1", recordGroup);

        // 调用从数据源
        this.proxy(this.getClass()).slave();
    }

    @DS("slave")
    public void slave() {
        // 使用从数据源
        RecordGroup recordGroup2 = db.use("slave").findRecords("UserMapper.findList", SqlPara.SINGLETON);
        setResultValue("pg2", recordGroup2);
    }
}