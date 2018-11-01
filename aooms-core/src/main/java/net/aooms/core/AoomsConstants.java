package net.aooms.core;

/**
 * Framework Const
 * Created by 风象南(yuboon) on 2018-04-20
 */
public interface AoomsConstants {

    public static final String ENCODE = "UTF-8";
    public static final String ID = "id";
    public static final String TREE_ROOT = "ROOT";
    public static final String ADMIN = "admin";
    public static final String DEFAULT_DATASOURCE = "master";

    public static interface Para{

        public static final String PAGE = "page";
        public static final String LIMIT = "limit";

    }

    public static interface Result{

        public static final String META = "$";
        public static final String DATA = "$data";
        public static final String TREE = "$tree";
        public static final String TOTAL = "$total";
        public static final String RECORD = "$vo";
        public static final String Authentication = "$authentication";

    }

    public static interface Render{

        public static final String IS_RENDER = "is_render";

    }

    public static interface Status{
        public static final int YES_FOR_INT = 1;
        public static final int NO_FOR_INT = -1;

        public static final int YES_FOR_CHAR = 'Y';
        public static final int NO_FOR_CHAR = 'N';

        public static final int AUTH_NO_LOGIN = -110;
        public static final int AUTH_NOT_ACCESS = -120;
    }

}