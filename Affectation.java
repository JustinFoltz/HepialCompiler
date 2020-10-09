public class Affectation extends Instruction {
    private Identifiant src;
    private Expression dst;

    public Affectation(Identifiant src, Expression dst, int line, int col){
        super(line, col);
        this.src = src;
        this.dst = dst;
    }

    public Expression getDst() {
        return dst;
    }

    public Identifiant getSrc() {
        return this.src;
    }

    public String toString() {
        return src.toString()+"="+dst.toString();
    }

    public String accept(SemantiqueAbstraitVisitor v) {
        return v.visit(this);
    }

    public void accept(ProductionCodeAbstraitVisitor v) {
       v.visit(this);
    }

    
}
