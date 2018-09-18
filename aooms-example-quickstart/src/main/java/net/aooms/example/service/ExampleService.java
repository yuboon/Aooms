package net.aooms.example.service;

import net.aooms.core.AoomsConstants;
import net.aooms.core.datasource.DS;
import net.aooms.core.id.IDGenerator;
import net.aooms.core.module.mybatis.Db;
import net.aooms.core.module.mybatis.SqlPara;
import net.aooms.core.module.mybatis.record.PagingRecord;
import net.aooms.core.module.mybatis.record.Record;
import net.aooms.core.service.GenericService;
import net.aooms.example.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * more example service
 * Created by 风象南(cheereebo) on 2018-09-18
 */
@Service
public class ExampleService extends GenericService {

    @Autowired
    private Db db;

    @DS
    public void example6() {
        // 使用主数据源
        PagingRecord pagingRecord = db.use("master").findList("UserMapper.findList", SqlPara.SINGLETON);
        setResultValue("pg1",pagingRecord);

        // 调用从数据源
        this.proxy(this.getClass()).slave();
    }

    @DS("slave")
    public void slave() {
        // 使用从数据源
        PagingRecord pagingRecord2 = db.use("slave").findList("UserMapper.findList", SqlPara.SINGLETON);
        setResultValue("pg2",pagingRecord2);
    }
}