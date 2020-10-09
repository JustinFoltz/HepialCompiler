import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Corps extends Instruction {
    private List<Instruction> lstInstr;

    public Corps(List<Instruction> lstInstr, int line, int col) {
        super(line, col);
        this.lstInstr = lstInstr;
    }

    public List<Instruction> getlstInstr() {
        return new ArrayList<>(this.lstInstr);
    }

    public String toString() {
        String str = "";
        for(Instruction i : lstInstr) {
            str += i.toString() + " ";
        }
        return str;
    }

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }
}