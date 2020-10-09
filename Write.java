public class Write extends Instruction {
    private Expression e;
    private boolean ln;

    public Write(Expression e, boolean ln, int line, int col) {
        super(line, col);
        this.ln = ln;
        this.e = e;
    }

    public Expression getE() {
        return e;
    }

    public boolean getLn() {
        return this.ln;
    }

    public void display( ) {
        System.out.println(e.toString());
    }

    public String toString() {
        return "Ecrire(" + e.toString() + ")";
    }

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }

}