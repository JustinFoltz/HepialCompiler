
public class Booleen extends Expression {
    private boolean valeur;

    public Booleen(boolean val, int line, int col) {
        super(line, col);
        this.valeur = val;
    }

    public int getValeur() {
        return this.valeur ? 1 : 0;
    }

     public String toString() {
        return valeur?"true":"false";
    }

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }
    
}

