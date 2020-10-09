import java.util.List;

public class TantQue extends Instruction{

    private Expression condition;
    private Corps process;

    public TantQue(Expression condition, Corps process, int line, int col) {
        super(line, col);
        this.condition = condition;
        this.process = process;
    }

    public Corps getProcess() {
        return process;
    }

    public Expression getCondition() {
        return condition;
    }

    public String toString() {
        return "\nTantque(" + condition.toString() + ") {" + process.toString() + "}";
    }

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }
}
