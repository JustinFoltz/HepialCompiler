import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DeclarationProgramme extends ArbreAbstrait {
    private String nom;
    private List<Instruction> declarations;
    private List<Instruction> instructions;
    private Map<String, Type> tableVar;
    private Map<String, Type> tableConst;

    public DeclarationProgramme(String nom, int line, int col) {
        super(line, col);
        this.nom = nom;
    } 

    public String getNom() {
        return this.nom;
    }

    public void setDeclarations(List<Instruction> declarations) {
        this.declarations = new ArrayList<>(declarations);
    }

    public void setInstructions(List<Instruction>  instructions) {
        this.instructions = new ArrayList<>(instructions);
    }

    public List<Instruction> getDeclarations() {
        return new ArrayList<>(declarations) ;
    }

    public List<Instruction> getInstructions() {
        return new ArrayList<>(instructions);
    }

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }
}