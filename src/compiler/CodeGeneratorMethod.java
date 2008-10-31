import java.util.*;

import org.objectweb.asm.MethodVisitor;

public class CodeGeneratorMethod extends CodeGeneratorModule {
  public CodeGeneratorMethod(Context context) {
    super(context);
  }

  public void definition(MethodNode definition) {
    Method method = definition.getMethod();
    String name   = method.getName();
    MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, name, method.getSignature(), null, null);
    context.switchMethodVisitor(methodVisitor);
  }

  public void ret() {
    currentMethod.visitInsn(IRETURN);
  }

  public void finish(int argumentsCount) {
    currentMethod.visitInsn(IRETURN);
    currentMethod.visitMaxs(10, argumentsCount);
    context.leaveMethod();
  }

  public void prepareCall() {
    currentMethod.visitVarInsn(ALOAD, 0);
  }

  public void call(MethodNode call) {
    Method method    = call.getMethod();
    String name      = method.getName();

    if(method.isBuiltin()) {
      currentMethod.visitMethodInsn(INVOKESTATIC, "Runtime", name, method.getSignature());
      currentMethod.visitInsn(POP);
    }
    else {
      currentMethod.visitMethodInsn(INVOKEVIRTUAL, className, name, method.getSignature());
    }
  }
}
