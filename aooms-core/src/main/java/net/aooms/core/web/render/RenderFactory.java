package net.aooms.core.web.render;

/**
 * 渲染工厂
 * Created by cccyb on 2018-04-20
 */
public class RenderFactory {

    private static final RenderFactory renderFactory = new RenderFactory();

    public static RenderFactory me(){
        return renderFactory;
    }

    /**
     * 获取render
     * @param renderType
     * @return
     */
    public IRender getRender(RenderType renderType){
        switch (renderType){
            case JSON:
                return new JSONRender();

            default:
                return new JSONRender();
        }
    }

}