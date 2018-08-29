package net.aooms.core.property.loader;

import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.DefaultPropertySourceFactory;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.List;

/**
 * yaml资源加载类
 */
public class YamlPropertyLoaderFactory extends DefaultPropertySourceFactory {

    public PropertySource<?> createPropertySource(@Nullable String name, EncodedResource resource) throws IOException {
        /*if (resource == null) {
            super.createPropertySource(name, resource);
        }*/

        List<PropertySource<?>> propertySourceList = new YamlPropertySourceLoader().load(resource.getResource().getFilename(),resource.getResource());
        return propertySourceList.get(0);
    }

}