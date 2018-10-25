package net.aooms.core.module.mybatis;

/**
 * Mybatis常量
 * Created by 风象南(cheereebo) on 2018/9/7
 */
public interface MyBatisConst {

    String MS_RECORD_INSERT = "MS_RECORD_INSERT";
    String MS_RECORD_UPDATE = "MS_RECORD_UPDATE";
    String MS_RECORD_DELETE = "MS_RECORD_DELETE";
    String MS_RECORD_FIND_BY_PK = "MS_RECORD_FIND_BY_PK";

    String TABLE_NAME_PLACEHOLDER = "_table_name_";
    String TABLE_PK_NAME_PLACEHOLDER = "_table_pk_name_";
    String TABLE_PK_VALUE_PLACEHOLDER = "_table_pk_value_";

    String CRUD_QUERY_COUNT_PLACEHOLDER = "_crud_query_count_";
    String CRUD_QUERY_PAGING_PLACEHOLDER = "_crud_query_paging_";
    String CRUD_QUERY_PK_PLACEHOLDER = "_crud_query_pk_";


}
