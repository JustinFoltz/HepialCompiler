import java.util.List;

import javax.swing.text.html.ListView;

public class Condition extends Instruction {
/*   private Expression instruction;
    private List<Expression> alors;
    private List<Expression> sinon;

     public Condition(Expression instruction, List<Expression> alors, List<Expression> sinon){
        this.instruction = instruction;
        this.alors = alors;
        this.sinon = sinon;
    } */

    private Expression condition;
    private Corps alors;
    private Corps sinon;

    public Condition(Expression condition, Corps alors, List<Instruction> sinon, int line, int col){
        super(line, col);
        this.condition = condition;
        this.alors = alors;
        this.sinon = new Corps(sinon, line, col);
    }

    public Expression getCondition() {
        return condition;
    }

    public Corps getAlors() {
        return alors;
    }

    public Corps getSinon() {
        return sinon;
    }
    public String toString() {
        String other = sinon.getlstInstr().isEmpty()?("sinon{" + sinon.toString()+"}") : "";
        return "if("+condition.toString() + ")" + "alors{"+alors.toString() + "}" + other;
    }

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }
    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }

}
