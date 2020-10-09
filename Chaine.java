public class Chaine extends Expression {
    
    private String str;

    public Chaine(String str, int line, int col) {
        super(line, col);
        this.str = str;
    }

    public String toString() {
        return str;
    }
    
    public String accept(SemantiqueAbstraitVisitor v) {return "";}
    public void accept(ProductionCodeAbstraitVisitor v) {}

}