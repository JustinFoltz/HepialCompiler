public class Inverse extends Unaire {
    
    public Inverse(int line, int col) {
        super(line, col);
    }

    public String operateur() {
        return "-";
    }

    public String toString() {
        return "-" + this.exp.toString();
    }

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }
}
