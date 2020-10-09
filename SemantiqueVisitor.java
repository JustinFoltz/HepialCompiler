import java.util.List;

public class SemantiqueVisitor implements SemantiqueAbstraitVisitor {
    private boolean errors = false;

    /**
     * Gere les erreurs d'identifiant non déclaré
     * @param i : Identifiant avec une erreur
     */
    public void syntaxErrorIdNotFound(Identifiant i) {
        System.out.println("syntaxErrorIdNotFound [" + (i.getLine() + 1) + ": " + i.getCol() + "] -> Identifiant " + i.toString() + " has not been declared");
        errors = true;
    }

    /**
     * Gere les erreurs d'identifiant non déclaré
     * @param i : Identifiant avec une erreur
     */
    public void syntaxErrorAffectConst(Identifiant i, int line){
        System.out.println("syntaxErrorAffectConst [" + (i.getLine()+1) +": "+i.getCol()+"] -> Constante " +i.toString()+ " is immuable");
        errors = true;
    }

    /**
     * Gere les erreurs ou des éléments ne sont pas du même type
     * @param e : Element de l'arbre abstrait avec une erreur
     */
    public void syntaxErrorType(ArbreAbstrait e) {
        System.out.println("syntaxErrorType [" + (e.getLine()+1)+": "+e.getCol()+"] -> left and right of expression " +e.toString()+ " are not of the same type");
        errors = true;
    }

    /**
     * Gere les erreurs dans le cas ou le type attendu n'est pas le bon
     * @param e : Element de l'arbre abstrait avec une erreur
     * @param typeObserved : type de l'élément 
     * @param typeExpected : type attendu
     */
    public void syntaxErrorTypeMismatch(ArbreAbstrait e, String typeObserved, String typeExpected) {
        System.out.println("syntaxErrorTypeMismatch [" + e.getLine()+":"+e.getCol()+"] -> elements of expression " +e.toString()+ " are type " +typeObserved+ " instead of " + typeExpected);
        errors = true;
    }

    /**
     * Vérifie si les valeurs gauche et droite d'un élement de l'arbre abstrait sont de même type 
     * @param a : élement de l'arbre abstrait
     * @param typeGauche : sous-élement de gauche
     * @param typeDroite : sous-élement de droite
     */
    private void checkSyntaxErrorType(ArbreAbstrait a, String typeGauche, String typeDroite) {
        if(!typeGauche.equals(typeDroite)) {
            syntaxErrorType(a);
        }
    }

    /**
     * Vérifie si le type d'un élément correspond à celui attendu 
     * @param a : élement de l'arbre abstrait
     * @param type : type de l'élément
     * @param typeExpected : type attendu
     */
    private void checkSyntaxErrorMismatch(ArbreAbstrait a, String type, String typeExpected) {
        if(!type.equals(typeExpected)) {
            syntaxErrorTypeMismatch(a, type, typeExpected);
        }
    }

    /**
     * Visite de l'arbre abstrait
     * @return : errors si il y a des erreurs dans le programme, le nom du programme sinon
     */
    public String visit(DeclarationProgramme dp){
        //gere le cas des constantes séparement. Permet de verifier qu'une constante n'est pas ré-affectée dans visit(Affectation)
        dp.getDeclarations().stream().filter(d -> d != null).forEach(d -> affectConstante((Affectation)d)); 
        dp.getInstructions().stream().filter(i -> i != null).forEach(i -> i.accept(this));
        if(errors){
            return "errors";
        }
        return dp.getNom();
    }

    /**
     * Visite d'un identifiant
     * @param i : l'identifiant visité
     * @return : le type de l'élément au format String
     */
    public String visit(Identifiant i) {
        DeclarationsTable d = DeclarationsTable.getInstance();
        if(!d.getConst().containsKey(i.getNom()) && !d.getVar().containsKey(i.getNom())){
            syntaxErrorIdNotFound(i);
            return "null";
        }
        else if(d.getConst().get(i.getNom()) instanceof TypeBoolean || d.getVar().get(i.getNom()) instanceof TypeBoolean){
            return "bool";
        } else {
            return "nombre";
        }
    }

    /**
     * Visite d'un élément Booleen
     * @return : String "bool"
     */
    public String visit(Booleen b){
        return "bool";
    }
    
    /**
     * Visite d'un élément Nombre
     * @return : String "nombre"
     */
    public String visit(Nombre n){
        return "nombre";
    }

    /**
     * Vérification de la sémantique d'une expression de type Booleen
     * @param b : élément de type Binaire (Booleen attendu)
     * @return : si pas d'erreurs retourne le type de l'élément ("bool")
     */
    private String visitExpressionBoooleen(Binaire b){
        String typeGauche = b.gauche.accept(this);
        String typeDroite = b.droite.accept(this);
        checkSyntaxErrorType(b, typeGauche, typeDroite);
        checkSyntaxErrorMismatch(b, typeGauche, "bool");
        return b.gauche.accept(this);
    }

    /**
     * Vérification de la sémantique d'une expression de type Nombre
     * @param b : élement de type Binaire (Nombre attendu)
     * @return : si pas d'erreurs retourne le type de l'élément ("nombre")
     */
    private String visitExpressionNombre(Binaire b){
        String typeGauche = b.gauche.accept(this);
        String typeDroite = b.droite.accept(this);
        checkSyntaxErrorType(b, typeGauche, typeDroite);
        checkSyntaxErrorMismatch(b, typeGauche, "nombre");
        return b.gauche.accept(this);
    }

    /**
     * Vérifie de la sémantique d'une Expression logique
     * @param r : élément de type Relation
     * @return : si pas d'erreurs retourne le type de l'élément ("bool")
     */
    private String visitLogicalExpression(Relation r){
        String check = visitExpressionNombre(r);
        return "bool";
    }

    /**
     * Visite d'un élément Addition
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Addition a){
       return visitExpressionNombre(a);
    }

    /**
     * Visite d'un élément Soustraction
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Soustraction s){
        return visitExpressionNombre(s);
    }

    /**
     * Visite d'un élément Division
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Division d){
        return visitExpressionNombre(d);
    }

    /**
     * Visite d'un élément produit
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Produit p){
        return visitExpressionNombre(p);
    }

    /**
     * Visite d'un élément Egal
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Egal e){
        if(!e.gauche.accept(this).equals(e.droite.accept(this))){
            syntaxErrorType(e);
        }
        return "bool";
    }

    /**
     * Visite d'un élément Different
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Different d){
        if(!d.gauche.accept(this).equals(d.droite.accept(this))){
            syntaxErrorType(d);
        }
        return "bool";
    }

    /**
     * Visite d'un élément Superieur
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Superieur s){
        return visitLogicalExpression(s);
    }

    /**
     * Visite d'un élément SuperieurEgal
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(SuperieurEgal s){
        return visitLogicalExpression(s);
    }

    /**
     * Visite d'un élément Inferieur
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Inferieur i){
        return visitLogicalExpression(i);
    }

    /**
     * Visite d'un élément InferieurEgal
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(InferieurEgal i){
        return visitLogicalExpression(i);
    }

    /**
     * Visite d'un élément Et
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Et e){
        return visitExpressionBoooleen(e);
    }

    /**
     * Visite d'un élément Ou
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Ou o){
        return visitExpressionBoooleen(o);
    }

    /**
     * Visite d'un élément Parenthese
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Parenthese p){
        return p.getExpr().accept(this);
    }

    /**
     * Visit d'un élément de type Affectation
     * @return : "affectation" pour satisfaire la signature
     */
    public String visit(Affectation a){
      if(DeclarationsTable.getInstance().existConst(a.getSrc())) {
            syntaxErrorAffectConst(a.getSrc(), a.getLine());
        } 
        if(!a.getSrc().accept(this).equals(a.getDst().accept(this))){
            syntaxErrorType(a);
        }
        return "affectation";
    }
//-----------------------------------------------------------------------------
    /**
     * Visit d'un élément de type Affectation
     * @return : "affectation" pour satisfaire la signature
     */
    public String affectConstante(Affectation a){
                if(!a.getSrc().accept(this).equals(a.getDst().accept(this))){
                    syntaxErrorType(a);
                }
                return "affectation";
            }

    /**
    * Visit d'un élément de type Condition
    * @return : "condition" pour satisfaire la signature
    */   
    public String visit(Condition c){
        if(!c.getCondition().accept(this).equals("bool")){
            System.out.println("[" + c.getLine() + ":" + c.getCol() + "] -> error : Condition can only be of type boolean, not "+c.getCondition().accept(this));
            errors = true;
        }
        String alors = c.getAlors().accept(this);
        String sinon = c.getSinon().accept(this);
        return "condition";
    }

    /**
    * Visit d'un élément de type Condition
    * @return : "corps" pour satisfaire la signature
    */      
    public String visit(Corps c){
        c.getlstInstr().stream().forEach(i -> i.accept(this));
        return "corps";
    }

    /**
    * Visit d'un élément de type Pour
    * @return : "pour" pour satisfaire la signature
    */    
    public String visit(Pour p){
        if(!p.getId().accept(this).equals("nombre")) {
            System.out.println("[" + p.getLine() + ":" + p.getCol() + "] -> error : could only iterate on \"number\", not on \"" + 
            p.getId().accept(this) + "\"");
            errors = true;
        }
        p.getLowerBound().accept(this);
        p.getUpperBound().accept(this);
        p.getProcess().accept(this);
        return "pour";
    }

    /**
    * Visit d'un élément de type Read
    * @return : "read" pour satisfaire la signature
    */   
    public String visit(Read r){
        //bizarre ici car on fais la recherche de id dans le cup, mais sinon on ne peut pas avoir le type....
        //je suppose que c'est la même chose dans Affectation
        r.getId().accept(this);
        return "read";
    }

    /**
    * Visit d'un élément de type TantQue
    * @return : "tant que" pour satisfaire la signature
    */   
    public String visit(TantQue tq){
        if(!tq.getCondition().accept(this).equals("bool")){
            syntaxErrorTypeMismatch(tq.getCondition(), "nombre", "booleen");
        }
        tq.getProcess().accept(this);
        return "tant que";
    }

    /**
    * Visit d'un élément de type Write
    * @return : la chaine ce caractères
    */   
    public String visit(Write w){
        String s = w.getE().accept(this);
        return s;
    }

    /**
    * Visit d'un élément de type Not
    * @return : si pas d'erreurs retourne le type de l'élément (String)
    */   
    public String visit(Not n){
        if(!n.getExp().accept(this).equals("bool")){
            syntaxErrorTypeMismatch(n.getExp(), "nombre", "bool");
        }
        return n.getExp().accept(this);
    }

    /**
     * Visit d'un élément de type Inverse
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Inverse i){
        if(!i.getExp().accept(this).equals("bool")){
            syntaxErrorTypeMismatch(i.getExp(), "nombre", "bool");
        }
        return i.getExp().accept(this);
    }

    /**
     * Visit d'un élément de type Unaire
     * @return : si pas d'erreurs retourne le type de l'élément (String)
     */
    public String visit(Unaire u){
        return u.getExp().accept(this);
    }
}
