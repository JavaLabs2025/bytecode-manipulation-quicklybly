package org.example.util.provider.impl;

import java.io.InputStream;
import org.example.util.provider.ClassProvider;

public class SystemClassProvider implements ClassProvider {

    @Override
    public InputStream getClassInputStream(String internalName) {
        String resourceName = internalName + ".class";
        return ClassLoader.getSystemResourceAsStream(resourceName);
    }
}
