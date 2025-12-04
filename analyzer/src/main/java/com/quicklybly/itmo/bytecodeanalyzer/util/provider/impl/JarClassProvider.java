package com.quicklybly.itmo.bytecodeanalyzer.util.provider.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import com.quicklybly.itmo.bytecodeanalyzer.util.provider.ClassProvider;

public class JarClassProvider implements ClassProvider {

    private final JarFile jarFile;

    public JarClassProvider(JarFile jarFile) {
        this.jarFile = jarFile;
    }

    @Override
    public InputStream getClassInputStream(String internalName) throws IOException {
        String entryName = internalName + ".class";
        ZipEntry entry = jarFile.getEntry(entryName);

        if (entry == null) {
            return null;
        }

        return jarFile.getInputStream(entry);
    }
}
