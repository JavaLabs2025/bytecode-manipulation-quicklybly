package org.example.visitor;

import org.example.model.Statistic;
import org.example.util.ClassRepository;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import static org.objectweb.asm.Opcodes.ASM9;

public class ClassStatisticVisitor extends ClassVisitor {

    private final Statistic statistic;
    private final ClassRepository repository;

    private String className;
    private int fieldCount = 0;
    private int overriddenCount = 0;

    public ClassStatisticVisitor(Statistic statistic, ClassRepository repository) {
        super(ASM9);
        this.statistic = statistic;
        this.repository = repository;
    }

    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        this.className = name;

        int depth = repository.getDepth(name);
        statistic.writeDepth(depth);

        super.visit(version, access, name, signature, superName, interfaces);
    }

    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        fieldCount++;
        return super.visitField(access, name, desc, signature, value);
    }

    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        boolean isPrivate = (access & Opcodes.ACC_PRIVATE) != 0;
        boolean isStatic = (access & Opcodes.ACC_STATIC) != 0;

        if (!isPrivate && !isStatic && !name.startsWith("<")) {
            String methodSig = name + desc;
            if (repository.isOverridden(className, methodSig)) {
                overriddenCount++;
            }
        }

        boolean hasCode = (access & Opcodes.ACC_ABSTRACT) == 0 && (access & Opcodes.ACC_NATIVE) == 0;

        if (!hasCode) {
            return mv;
        }

        return new AbcMethodVisitor(mv, statistic.getAbcMetric());
    }

    public void visitEnd() {
        statistic.updateFieldStatistic(fieldCount);
        statistic.writeOverriddenCount(overriddenCount);
    }
}
