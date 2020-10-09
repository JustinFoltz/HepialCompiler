//import java.util.Optional;

public class Identifiant extends Expression {
    private String nom;

    public Identifiant(String nom, int line, int col) {
        super(line, col);
        this.nom = nom;
     }

    public String getNom() { 
        return nom; 
    }

    public String toString() {
        return nom;
    }

    public String accept(SemantiqueAbstraitVisitor v){
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }
}
