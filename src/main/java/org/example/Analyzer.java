package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.jar.JarFile;
import org.example.visitor.ClassStatisticVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

public class Analyzer {

    public static void main(String[] args) throws IOException {
        var path = Path.of("src/main/resources/sample.jar");
        var analyzer = new Analyzer();

        analyzer.analyzeJar(path);
    }

    public void analyzeJar(Path path) throws IOException {
        try (JarFile jar = new JarFile(path.toFile())) {

            Statistic statistic = new Statistic();

            jar.stream()
                    .filter(entry -> entry.getName().endsWith(".class"))
                    .forEach(entry -> {
                                // Should be safe for Java classes, since they always in the class with same name
                                // unlike kotlin classes
                                String className = entry.getName().replace(".class", "");

                                try (InputStream is = jar.getInputStream(entry)) {
                                    ClassReader cr = new ClassReader(is);
                                    ClassVisitor visitor = new ClassStatisticVisitor(statistic);
                                    cr.accept(visitor, 0);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                    );

            statistic.printStatistic();
        }
    }
}
