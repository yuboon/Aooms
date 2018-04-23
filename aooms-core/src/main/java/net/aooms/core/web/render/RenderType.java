package net.aooms.core.web.render;

/**
 * 响应渲染类型
 * Created by cccyb on 2018-04-20
 */
public enum RenderType {

    JSON("text/plain"),
    HTML("text/html"),
    JAVASCRIPT("text/html"),
    TEXT("text/plain"),
    PAGE("text/html"),
    FILE("text/html"),
    IMAGE("image/jpeg")

    ;

    // 响应类型
    private String contentType;

    RenderType(String contentType) {
        this.contentType = contentType;
    }

    public String getContentType() {
        return contentType;
    }
}