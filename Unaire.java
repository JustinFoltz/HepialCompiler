public abstract class Unaire extends Expression {
    protected Expression exp;

    public Unaire(int line, int col) {
        super(line, col);
     }

    public void setOperande(Expression exp){
        this.exp = exp;
    }

    public Expression getExp() {
        return exp;
    }
    public abstract String toString();

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }
 
}