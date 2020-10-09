public abstract class Binaire extends Expression {
    protected Expression gauche;
    protected Expression droite;

    public Binaire(int line, int col) {
        super(line, col);
     }

    public void setOperandeGauche(Expression gauche){
        this.gauche = gauche;
    }

    public void setOperandeDroite(Expression droite){
        this.droite = droite;
    }

    public abstract String toString();


}
