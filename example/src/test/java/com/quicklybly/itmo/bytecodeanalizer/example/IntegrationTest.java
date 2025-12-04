package com.quicklybly.itmo.bytecodeanalizer.example;

import com.quicklybly.itmo.bytecodeanalyzer.Analyzer;
import com.quicklybly.itmo.bytecodeanalyzer.api.Statistic;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class IntegrationTest {

    @Test
    public void analyzeComplexClass() throws IOException {
        String jarPathStr = System.getProperty("test.jar.path");

        Assertions.assertNotNull(jarPathStr, "System property 'test.jar.path' is not set");

        Path jarPath = Path.of(jarPathStr);
        File jarFile = jarPath.toFile();

        System.out.println("Testing artifact: " + jarFile.getAbsolutePath());
        Assertions.assertTrue(jarFile.exists(), "JAR file does not exist at path: " + jarPath);

        Analyzer analyzer = new Analyzer();

        Statistic stats = analyzer.analyzeJar(jarPath);

        assertThat(stats.getMaxInheritanceDepth()).isEqualTo(4);
        assertThat(stats.getAverageInheritanceDepth()).isEqualTo(4.0);
        assertThat(stats.getAverageFieldCount()).isEqualTo(2.0);
        assertThat(stats.getAverageOverriddenCount()).isEqualTo(1.0);
        assertThat(stats.getAbcMetric().getAbc()).isBetween(9.0, 10.0);
    }
}
