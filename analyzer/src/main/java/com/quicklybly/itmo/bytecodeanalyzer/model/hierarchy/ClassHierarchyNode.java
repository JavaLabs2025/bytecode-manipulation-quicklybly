package com.quicklybly.itmo.bytecodeanalyzer.model.hierarchy;

import java.util.Collections;
import java.util.Set;

public class ClassHierarchyNode {

    private final String name;
    private final String superName;
    private final Set<String> methods; // name + descriptor

    private Integer cachedDepth = null;

    public ClassHierarchyNode(String name, String superName, Set<String> methods) {
        if (name == null || superName == null) {
            throw new IllegalArgumentException("Name and superName must not be null");
        }
        this.name = name;
        this.superName = superName;
        this.methods = methods != null ? methods : Collections.emptySet();
    }

    public String getName() {
        return name;
    }

    public String getSuperName() {
        return superName;
    }

    public boolean hasMethod(String signature) {
        return methods.contains(signature);
    }

    public void setCachedDepth(int depth) {
        this.cachedDepth = depth;
    }

    public Integer getCachedDepth() {
        return cachedDepth;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        ClassHierarchyNode that = (ClassHierarchyNode) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}