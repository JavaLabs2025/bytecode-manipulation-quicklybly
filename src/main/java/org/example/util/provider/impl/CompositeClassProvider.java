package org.example.util.provider.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.example.util.provider.ClassProvider;

public class CompositeClassProvider implements ClassProvider {

    private final List<ClassProvider> providers;

    public CompositeClassProvider(List<ClassProvider> providers) {
        this.providers = providers;
    }

    @Override
    public InputStream getClassInputStream(String internalName) throws IOException {
        for (ClassProvider provider : providers) {
            InputStream is = provider.getClassInputStream(internalName);

            if (is != null) {
                return is;
            }
        }
        return null;
    }
}
