import java.util.Optional;

public class Nombre extends Expression {
    private int valeur;

    public Nombre(int val, int line, int col) {
        super(line, col);
        this.valeur = val;
     }

    public int getValeur() {
        return valeur;
    }

    public String toString() {
        return String.valueOf(valeur) ;
    }


    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }
     
}