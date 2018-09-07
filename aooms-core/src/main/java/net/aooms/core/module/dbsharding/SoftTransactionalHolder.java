package net.aooms.core.module.dbsharding;

import io.shardingsphere.transaction.api.AbstractSoftTransaction;

/**
 * 柔性事务持有
 * Created by cccyb on 2018/9/7
 */
public class SoftTransactionalHolder {

    private static ThreadLocal<AbstractSoftTransaction> abstractSoftTransactionThreadLocal = new ThreadLocal<>();

    public static void setSoftTransaction(AbstractSoftTransaction abstractSoftTransaction){
        abstractSoftTransactionThreadLocal.set(abstractSoftTransaction);
    }

    public static AbstractSoftTransaction getSoftTransaction(){
        return abstractSoftTransactionThreadLocal.get();
    }

    public static void removeSoftTransaction(){
        abstractSoftTransactionThreadLocal.remove();
    }

}
