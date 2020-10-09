public class InverseBits extends Unaire {
    
    public InverseBits(int line, int col) {
        super(line, col);
    }

    public String operateur() {
        return "~";
    }

    public String toString() {
        return "~" + this.exp.toString();
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
        v.visit(this);
    }
}