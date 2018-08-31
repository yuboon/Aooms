package net.aooms.core.datasource;


import cn.hutool.core.util.StrUtil;
import net.aooms.core.Constants;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 切换数据源Advice
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */

@Order(-1)// 保证该AOP在@Transactional之前执行
@Component
@Aspect
public class DynamicDataSourceAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    /*@Pointcut("@annotation(net.aooms.core.datasource.UseDataSource)")
    public void useDataSource() {

    }*/

    //@Pointcut("execution(* net.aooms.core.module.mybatis.dao.GenericDaoSupport.*(..))")
    @Pointcut("@annotation(net.aooms.core.datasource.UseDataSource)")
    public void dataSource() {

    }

    /*
     * @Before("@annotation(ds)")
     * 的意思是：
     *
     * @Before：在方法执行之前进行执行：
     * @annotation(targetDataSource)：
     * 会拦截注解targetDataSource的方法，否则不拦截;
     */

    /*@Before("useDataSource()")
    public void changeDataSource(JoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature msig = (MethodSignature) signature;
            Object target = point.getTarget();
            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

            UseDataSource dataSource = currentMethod.getAnnotation(UseDataSource.class);
            if(dataSource != null){
                // 获取当前的指定的数据源;
                String dsId = dataSource.value();

                // 如果不在我们注入的所有的数据源范围之内，那么输出警告信息，系统自动使用默认的数据源。
                if (!DynamicDataSourceHolder.containsDataSource(dsId)) {
                    System.err.println("数据源[{}]不存在，使用默认数据源 > {}" + dataSource.value() + point.getSignature());
                } else {
                    System.out.println("Use DataSource : {} > {}" + dataSource.value() + point.getSignature());
                    // 找到的话，那么设置到动态数据源上下文中。
                    DynamicDataSourceHolder.setDataSourceType(dataSource.value());
                }
            }
        }
    }*/

    @Before("dataSource()")
    public void changeDataSource(JoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature msig = (MethodSignature) signature;
            Object target = point.getTarget();
            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

            if("use".equals(currentMethod.getName()) || "onUse".equals(currentMethod.getName()) || "uesOff".equals(currentMethod.getName())){
                return;
            }

            UseDataSource dataSource = currentMethod.getAnnotation(UseDataSource.class);
            if(dataSource != null){
                // 获取当前的指定的数据源;
                String name = dataSource.value();
                if(StrUtil.isBlank(name)){
                    name = Constants.DEFAULT_DATASOURCE;
                }

                if (!DynamicDataSourceHolder.containsDataSource(name)) {
                    logger.error("datasource [{}] not found !",name);
                } else {
                    logger.info("use datasource [{}]",name);
                    DynamicDataSourceHolder.setDataSource(dataSource.value());
                }
            }
        }
    }

    @After("dataSource()")
    public void restoreDataSource(JoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature msig = (MethodSignature) signature;
            Object target = point.getTarget();
            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

            if ("use".equals(currentMethod.getName()) || "onUse".equals(currentMethod.getName()) || "uesOff".equals(currentMethod.getName())) {
                //DynamicDataSourceHolder.setDataSource();
            } else {
                DynamicDataSourceHolder.clearDataSource();
            }
        }
    }

    @AfterThrowing("dataSource()")
    public void releaseDataSource(JoinPoint point) {
        DynamicDataSourceHolder.clearDataSource();
    }

}