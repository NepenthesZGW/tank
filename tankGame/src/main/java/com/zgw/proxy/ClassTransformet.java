package com.zgw.proxy;

import com.sun.org.apache.bcel.internal.generic.INVOKEINTERFACE;
import com.sun.org.apache.bcel.internal.generic.INVOKESPECIAL;
import com.sun.org.apache.bcel.internal.generic.INVOKESTATIC;
import org.springframework.asm.ClassReader;
import org.springframework.asm.ClassVisitor;
import org.springframework.asm.ClassWriter;
import org.springframework.asm.MethodVisitor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ClassTransformet {

    public static void main(String[] args) throws IOException {

        ClassReader cr=new ClassReader(ClassTransformet.class.getClassLoader().getResourceAsStream("com/zgw/proxy/A.class"));
        ClassWriter cw=new ClassWriter(0);


        /**
         * 这里取动态改变读取类的结构，   用cw存储改变后的结构
         * 定制cw
         */
        ClassVisitor cv=new ClassVisitor(458752,cw) {
            @Override
            public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
                super.visit(version, access, name, signature, superName, interfaces);
            }

            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {

                MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);

                return new MethodVisitor(458752,methodVisitor) {
                    @Override
                    public void visitCode() {
                        visitMethodInsn(184,"TimeProxy","before","()V",false);
                        super.visitCode();
                    }
                };
            }

            @Override
            public void visitEnd() {
                super.visitEnd();
            }
        };

        /**
         *
         * 因为ClassVisitor cv加入ClassWriter这个Visitor之后
         * ClassReader会把读出来的类结构放入ClassWriter
         */
        cr.accept(cv,0);
        byte[] buf=cw.toByteArray();
        FileOutputStream fo=new FileOutputStream("A.class");
        fo.write(buf);
        fo.flush();
        fo.close();
    }
}
