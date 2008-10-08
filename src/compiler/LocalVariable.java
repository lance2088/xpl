public class LocalVariable extends Variable {
  private static String type = "local-variable";
  public boolean isOfType(String type) { return super.isOfType(type) || this.type.equals(type); }

  private String variableId;

  public LocalVariable(String name, int id) {
    this.name       = name;
    this.variableId = "variable" + id;
  }

  public String getVariableId()  {
    return variableId;
  }
}
