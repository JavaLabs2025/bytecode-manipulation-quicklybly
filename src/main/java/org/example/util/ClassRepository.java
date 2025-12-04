package org.example.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.example.model.hierarchy.ClassHierarchyNode;
import org.example.util.provider.ClassProvider;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ClassRepository {

    private static final String OBJECT_CLASS_NAME = "java/lang/Object";

    private final Map<String, ClassHierarchyNode> cache = new HashMap<>();
    private final ClassProvider classProvider;

    public ClassRepository(ClassProvider classProvider) {
        this.classProvider = classProvider;
    }

    public ClassHierarchyNode getNode(String className) {
        if (className == null) {
            throw new IllegalArgumentException("Class name must not be null");
        }

        if (cache.containsKey(className)) {
            return cache.get(className);
        }

        try {
            ClassHierarchyNode node = loadNode(className);
            cache.put(className, node);
            return node;
        } catch (IOException e) {
            System.err.println("WARN: Unable to load class " + className);
            cache.put(className, null); // remember failed attempt
            return null;
        }
    }

    public int getDepth(String className) {
        if (OBJECT_CLASS_NAME.equals(className) || className == null) {
            return 0;
        }

        ClassHierarchyNode node = getNode(className);

        if (node == null) {
            return 0;
        }

        if (node.getCachedDepth() != null) {
            return node.getCachedDepth();
        }

        int depth = 1 + getDepth(node.getSuperName());

        node.setCachedDepth(depth);
        return depth;
    }

    public boolean isOverridden(String className, String methodSignature) {
        ClassHierarchyNode node = getNode(className);

        if (node == null) {
            return false;
        }

        return checkParentHierarchy(node.getSuperName(), methodSignature);
    }

    private boolean checkParentHierarchy(String currentClassName, String methodSignature) {
        if (currentClassName == null || OBJECT_CLASS_NAME.equals(currentClassName)) {
            return false;
        }

        ClassHierarchyNode node = getNode(currentClassName);
        if (node == null) {
            return false;
        }

        if (node.hasMethod(methodSignature)) {
            return true;
        }

        return checkParentHierarchy(node.getSuperName(), methodSignature);
    }

    private ClassHierarchyNode loadNode(String className) throws IOException {
        try (InputStream in = classProvider.getClassInputStream(className)) {
            if (in == null) return null;

            ClassReader cr = new ClassReader(in);
            Set<String> methods = new HashSet<>();
            final String[] superNameHolder = new String[1];

            // only reading superclass and method signature
            // flags needed for performance
            cr.accept(new ClassVisitor(Opcodes.ASM9) {

                @Override
                public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                    superNameHolder[0] = superName;
                }

                @Override
                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                    boolean isPrivateOrStatic = (access & (Opcodes.ACC_PRIVATE | Opcodes.ACC_STATIC)) != 0;
                    boolean isConstructor = name.startsWith("<");

                    if (!isPrivateOrStatic && !isConstructor) {
                        methods.add(name + descriptor);
                    }
                    return null; // not entering method body
                }
            }, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);

            return new ClassHierarchyNode(className, superNameHolder[0], methods);
        }
    }
}
