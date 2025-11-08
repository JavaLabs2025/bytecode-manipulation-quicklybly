package org.example.visitor;

import org.example.Statistic;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import static org.objectweb.asm.Opcodes.ASM8;

public class ClassStatisticVisitor extends ClassVisitor {

    private final Statistic statistic;

    private Integer fieldCount = 0;

    public ClassStatisticVisitor(Statistic statistic) {
        super(ASM8);
        this.statistic = statistic;
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    }

    public void visitSource(String source, String debug) {
    }

    public void visitOuterClass(String owner, String name, String desc) {
    }

    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return super.visitAnnotation(desc, visible);
    }

    public void visitAttribute(Attribute attr) {
    }

    // todo also visit inner classes
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        fieldCount++;
        return super.visitField(access, name, desc, signature, value);
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        return super.visitMethod(access, name, desc, signature, exceptions);
    }

    public void visitEnd() {
        statistic.updateFieldStatistic(fieldCount);
    }
}

