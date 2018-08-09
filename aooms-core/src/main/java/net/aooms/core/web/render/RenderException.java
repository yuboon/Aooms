package net.aooms.core.web.render;

import net.aooms.core.exception.AoomsException;

/**
 * 渲染异常
 *
 * Created by cccyb on 2018-04-18
 */
public class RenderException extends AoomsException {


    public RenderException(String message) {
        super(message);
    }

    public RenderException(String message, int code) {
        super(message,code);
    }

    public RenderException(String message, Throwable cause) {
        super(message,cause);
    }

    public RenderException(String message, int code, Throwable cause) {
        super(message, code ,cause);
    }

}