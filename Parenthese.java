public class Parenthese extends Expression {
    private Expression expr;

    public Parenthese(Expression expr, int line, int col) {
        super(line, col);
        this.expr = expr;
    }

    public Expression getExpr(){
        return expr;
    }

    public String toString() {
        return "(" + expr.toString() + ")";
    }

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }
    
}