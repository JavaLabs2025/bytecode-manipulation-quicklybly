package com.quicklybly.itmo.bytecodeanalyzer.util.provider;

import java.io.IOException;
import java.io.InputStream;

@FunctionalInterface
public interface ClassProvider {

    /**
     * @param internalName class name in ASM format, for example "java/lang/String"
     * @return InputStream or null
     */
    InputStream getClassInputStream(String internalName) throws IOException;
}
