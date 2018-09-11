package net.aooms.core.module.dbsharding;


import com.google.common.base.Optional;
import io.shardingsphere.transaction.api.AbstractSoftTransaction;
import io.shardingsphere.transaction.api.SoftTransactionManager;
import io.shardingsphere.transaction.api.config.NestedBestEffortsDeliveryJobConfiguration;
import io.shardingsphere.transaction.api.config.SoftTransactionConfiguration;
import io.shardingsphere.transaction.bed.BEDSoftTransaction;
import io.shardingsphere.transaction.constants.SoftTransactionType;
import io.shardingsphere.transaction.constants.TransactionLogDataSourceType;
import net.aooms.core.Aooms;
import net.aooms.core.util.LogUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.sql.SQLException;


/**
 * 柔性事务控制
 * Created by 风象南(cheereebo) on 2018/9/7
 */
@Order(1)
@Component
@Aspect
public class SoftTransactionalAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Pointcut("@annotation(net.aooms.core.module.dbsharding.SoftTransactional)")
    public void softTransaction() {

    }

    @Before("softTransaction()")
    public void startTransaction(JoinPoint point){
        try{
            Signature signature = point.getSignature();
            if (signature instanceof MethodSignature) {
                MethodSignature msig = (MethodSignature) signature;
                Object target = point.getTarget();
                Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
                SoftTransactional softTransaction = currentMethod.getAnnotation(SoftTransactional.class);
                if(softTransaction != null){
                    SoftTransactionType softTransactionType = softTransaction.value();
                    DataSource dataSource = Aooms.getInstance().getDynamicDataSource().getDefaultDataSource();
                    SoftTransactionConfiguration transactionConfig = new SoftTransactionConfiguration(dataSource);
                    //transactionConfig.setStorageType(TransactionLogDataSourceType.MEMORY);
                    transactionConfig.setBestEffortsDeliveryJobConfiguration(Optional.of(new NestedBestEffortsDeliveryJobConfiguration()));
                    transactionConfig.setTransactionLogDataSource(Aooms.getInstance().getDynamicDataSource().getDataSource("slave"));

                    // 2. 初始化SoftTransactionManager
                    SoftTransactionManager transactionManager = new SoftTransactionManager(transactionConfig);
                    transactionManager.init();

                    // 3. 获取SoftTransaction
                    if(softTransactionType == null){
                        softTransactionType = SoftTransactionType.BestEffortsDelivery;
                    }
                    AbstractSoftTransaction transaction = transactionManager.getTransaction(softTransactionType);
                    SoftTransactionalHolder.setSoftTransaction(transaction);

                    if(softTransactionType == SoftTransactionType.BestEffortsDelivery){
                        BEDSoftTransaction bedSoftTransaction = (BEDSoftTransaction) SoftTransactionalHolder.getSoftTransaction();
                        bedSoftTransaction.begin(dataSource.getConnection()); // 开启事务
                        LogUtils.logFormatPrint("will be managed by BEDSoftTransaction");
                    }else{
                        throw new RuntimeException("TCC SoftTransactional Do not implement !");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @After("softTransaction()")
    public void endTransaction(JoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature msig = (MethodSignature) signature;
            Object target = point.getTarget();
            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

            SoftTransactional softTransaction = currentMethod.getAnnotation(SoftTransactional.class);
            AbstractSoftTransaction transaction = SoftTransactionalHolder.getSoftTransaction();
            if(softTransaction != null){
                if(transaction == null){
                    throw new RuntimeException("SoftTransactionalHolder.getSoftTransaction() is null");
                }
                transaction.end(); // 关闭事务
                transaction.getConnection().close();
                SoftTransactionalHolder.removeSoftTransaction();
                LogUtils.logFormatPrint(transaction.getTransactionType() + " SoftTransaction End");
            }
        }
    }

    @AfterThrowing("softTransaction()")
    public void exTransaction(JoinPoint point) throws SQLException {
        AbstractSoftTransaction transaction = SoftTransactionalHolder.getSoftTransaction();
        if(transaction != null){
            transaction.getConnection().close();
            SoftTransactionalHolder.removeSoftTransaction();
        }
    }

}