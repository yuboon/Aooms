package net.aooms.core;

import org.springframework.http.ResponseEntity;

/**
 * DataKey Const
 * Created by 风象南(yuboon) on 2018-04-20
 */
public interface K {

    public static final String P_PAGE = "page";
    public static final String P_LIMIT = "limit";

    public static final String RS_META = "$";
    public static final String RS_DATA = "$data";
    public static final String RS_TREE = "$tree";
    public static final String RS_TOTAL = "$total";
    public static final String RS_VO = "$vo";
    public static final String RS_Authentication = "$authentication";

    public static final String IS_RENDER = "is_render";


}