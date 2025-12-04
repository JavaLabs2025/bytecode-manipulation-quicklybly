package org.example.visitor;

import org.example.model.AbcMetric;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import static org.objectweb.asm.Opcodes.ASM9;

// init blocks are inlined in constructor
public class AbcMethodVisitor extends MethodVisitor {

    private final AbcMetric resultMetric;
    private long assignment;
    private long branch;
    private long condition;

    public AbcMethodVisitor(MethodVisitor delegate, AbcMetric resultMetric) {
        super(ASM9, delegate);
        this.resultMetric = resultMetric;
    }

    @Override
    public void visitVarInsn(int opcode, int varIndex) {
        if (opcode >= Opcodes.ISTORE && opcode <= Opcodes.ASTORE) {
            assignment++;
        }

        super.visitVarInsn(opcode, varIndex);
    }

    @Override
    public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
        if (opcode == Opcodes.PUTSTATIC || opcode == Opcodes.PUTFIELD) {
            assignment++;
        }

        super.visitFieldInsn(opcode, owner, name, descriptor);
    }

    @Override
    public void visitIincInsn(int varIndex, int increment) {
        assignment++;
        super.visitIincInsn(varIndex, increment);
    }

    @Override
    public void visitInsn(int opcode) {
        // array assignment
        if (opcode >= Opcodes.IASTORE && opcode <= Opcodes.SASTORE) {
            assignment++;
        }

        super.visitInsn(opcode);
    }

    @Override
    public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
        branch++;
        super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
    }

    @Override
    public void visitInvokeDynamicInsn(String name, String descriptor, Handle bootstrapMethodHandle, Object... bootstrapMethodArguments) {
        branch++;
        super.visitInvokeDynamicInsn(name, descriptor, bootstrapMethodHandle, bootstrapMethodArguments);
    }

    @Override
    public void visitJumpInsn(int opcode, Label label) {
        if (opcode != Opcodes.GOTO && opcode != Opcodes.JSR) {
            condition++;
        }
        super.visitJumpInsn(opcode, label);
    }

    @Override
    public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
        // dflt = default branch
        // labels.length = number of cases
        condition += labels.length; // cases
        if (dflt != null) {
            condition++;
        }
        super.visitTableSwitchInsn(min, max, dflt, labels);
    }

    @Override
    public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
        condition += labels.length; // cases
        if (dflt != null) {
            condition++;
        }
        super.visitLookupSwitchInsn(dflt, keys, labels);
    }

    @Override
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        condition++;
        super.visitTryCatchBlock(start, end, handler, type);
    }

    @Override
    public void visitEnd() {
        resultMetric.add(new AbcMetric(assignment, branch, condition));
        super.visitEnd();
    }
}
