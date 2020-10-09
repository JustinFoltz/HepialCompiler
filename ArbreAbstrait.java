import java.util.List;

public abstract class ArbreAbstrait {
    private String nom;
    private int line;
    private int col;
    private List<Instruction> declarations;
    private List<Instruction> instructions;

    public ArbreAbstrait(int line, int col) {
        this.line = line;
        this.col = col;
    } 

    public int getLine() { return this.line; }
    public int getCol() { return this.col; }
    public void setNom(String nom) { this.nom = nom; }

    public void setDeclarations(List<Instruction> declarations) {
        this.declarations = declarations;
    }

    public void setInstructions(List<Instruction>  instructions) {
        this.instructions = instructions;
    }

    public abstract String accept(SemantiqueAbstraitVisitor v);
    public abstract void accept(ProductionCodeAbstraitVisitor v);
}