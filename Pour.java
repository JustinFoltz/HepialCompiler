import java.util.ArrayList;
import java.util.List;

public class Pour extends Instruction { //TantQue{

    //regle le probleme mais Pour étant directement Instruction
    private Identifiant id;
    private Expression lowerBound;
    private Expression upperBound;
    private Corps process;

    public Pour(Identifiant id, Expression lowerBound, Expression upperBound, Corps process, int line, int col) {
        super(line, col);
        this.id = id;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.process = process;
    }

    public Identifiant getId() {
        return id;
    }

    public Corps getProcess() {
        return process;
    }

    public Expression getLowerBound() {
        return lowerBound;
    }

    public Expression getUpperBound() {
        return upperBound;
    }


    public String toString() {
        return "Pour(" + id.toString() + " allantde " + lowerBound.toString() + 
            " à " + upperBound.toString() + ")" + "{" + process.toString() + "}";
    }

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }

}
