package net.aooms.core.authentication;

/**
 * 类描述
 * Created by 风象南(cheereebo) on 2018/11/13
 */

import javax.security.auth.callback.CallbackHandler;
import java.security.Principal;
import java.util.List;

public final class SamplePrincipal implements Principal {

    private String name;

    public SamplePrincipal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean equals(Object o) {
        return (o instanceof SamplePrincipal)
                && this.name.equalsIgnoreCase(((SamplePrincipal) o).name);
    }

    public int hashCode() {
        return name.toUpperCase().hashCode();
    }

}
