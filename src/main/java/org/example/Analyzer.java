package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;
import java.util.jar.JarFile;
import org.example.model.Statistic;
import org.example.util.ClassRepository;
import org.example.util.provider.ClassProvider;
import org.example.util.provider.impl.CompositeClassProvider;
import org.example.util.provider.impl.JarClassProvider;
import org.example.util.provider.impl.SystemClassProvider;
import org.example.visitor.ClassStatisticVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;

public class Analyzer {

    public static void main(String[] args) throws IOException {
        var path = Path.of("src/main/resources/sample.jar");
        var analyzer = new Analyzer();

        var statistic = analyzer.analyzeJar(path);
        statistic.printStatistic();
    }

    public Statistic analyzeJar(Path path) throws IOException {
        Statistic statistic = new Statistic();
        try (JarFile jar = new JarFile(path.toFile())) {

            ClassProvider jarProvider = new JarClassProvider(jar);
            ClassProvider systemProvider = new SystemClassProvider();
            ClassProvider compositeProvider = new CompositeClassProvider(List.of(jarProvider, systemProvider));

            ClassRepository repository = new ClassRepository(compositeProvider);


            jar.stream()
                    .filter(entry -> entry.getName().endsWith(".class"))
                    .forEach(entry -> {
                        String internalName = entry.getName().replace(".class", "");

                        try (InputStream is = jar.getInputStream(entry)) {
                            ClassReader cr = new ClassReader(is);

                            ClassVisitor visitor = new ClassStatisticVisitor(statistic, repository);

                            cr.accept(visitor, 0);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        return statistic;
    }
}
