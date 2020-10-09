public abstract class Expression extends ArbreAbstrait{

     public Expression(int line, int col) {
        super(line, col);
     }

     public abstract String toString();


}