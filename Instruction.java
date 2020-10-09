public abstract class Instruction extends ArbreAbstrait {

    public Instruction(int line, int col) {
        super(line, col);
    }

    public abstract String toString();
}
