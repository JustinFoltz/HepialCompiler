import java.util.Scanner;

public class Read extends Instruction {
    private Scanner sc = new Scanner(System.in);
    private Type t;
    private Identifiant id;

    public Read(Identifiant id, Type t, int line, int col) {
        super(line, col);
        this.id = id;
    }

    public Identifiant getId() {
        return id;
    }

    public String toString() {
        return "Lire(" + id.toString() + ")";
    }

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }
}
