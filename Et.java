public class Et extends Relation {
    
    public Et(int line, int col) {
        super(line, col);
    }

    public String operateur() {
        return "&&";
    }

    public String toString() {
        return this.gauche + "&&" + this.droite;
    }

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }
}
