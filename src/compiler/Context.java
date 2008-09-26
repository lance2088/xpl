import java.util.*;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

public class Context {
  private String        className;
  private Scope         scope;
  private ClassWriter   classWriter;
  public  ClassWriter   getClassWriter() { return classWriter; }

  private MethodVisitor initMethodVisitor;
  private MethodVisitor mainMethodVisitor;
  private MethodVisitor runMethodVisitor;
  private MethodVisitor currentMethodVisitor;

  public Context(String className, ClassWriter classWriter, MethodVisitor initMethodVisitor, MethodVisitor mainMethodVisitor, MethodVisitor runMethodVisitor) {
    this.className            = className;
    this.classWriter          = classWriter;
    this.initMethodVisitor    = initMethodVisitor;
    this.mainMethodVisitor    = mainMethodVisitor;
    this.runMethodVisitor     = runMethodVisitor;
    this.currentMethodVisitor = runMethodVisitor;
  }

  private ArrayList<CodeGeneratorModule> observers = new ArrayList<CodeGeneratorModule>();

  public void subscribe(CodeGeneratorModule observer) {
    observers.add(observer);
    observer.classChanged(className, classWriter);
    observer.currentMethodVisitorChanged(currentMethodVisitor);
  }

  public void switchMethodVisitor(MethodVisitor methodVisitor) {
    this.currentMethodVisitor = methodVisitor;
    for(CodeGeneratorModule observer : observers)
      observer.currentMethodVisitorChanged(currentMethodVisitor);
  }

  public void switchScope(Scope scope) {
    this.scope = scope;
    for(CodeGeneratorModule observer : observers)
      observer.scopeChanged(scope);
  }

  public void leaveMethod() {
    switchMethodVisitor(runMethodVisitor);
  }
}