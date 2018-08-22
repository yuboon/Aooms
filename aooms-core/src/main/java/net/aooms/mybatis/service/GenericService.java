package net.aooms.mybatis.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.aooms.core.configuration.Vars;
import net.aooms.mybatis.SqlPara;
import net.aooms.mybatis.dao.GenericDaoSupport;
import net.aooms.mybatis.entity.User;
import net.aooms.mybatis.mapper.UserMapper;
import net.aooms.mybatis.record.Record;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class GenericService {

	/**
	 * 以DataBoss.DataPara为参数创建SqlPara对象
	 * @return
	 */
	public SqlPara fromDataBoss() {
		return SqlPara.fromDataBoss();
	}
}