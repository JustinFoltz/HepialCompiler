public interface SemantiqueAbstraitVisitor {

    //String visit(ArbreAbstrait tree);

    String visit(Identifiant i);
    String visit(Addition a);
    String visit(Soustraction s);
    String visit(Division d);
    String visit(Produit p);
    String visit(Egal e);
    String visit(Different d);
    String visit(Superieur s);
    String visit(SuperieurEgal s);
    String visit(Inferieur i); 
    String visit(InferieurEgal i);
    String visit(Et e);
    String visit(Ou o);
    String visit(Parenthese p);
    String visit(Booleen b);
    String visit(Nombre n);
    String visit(Affectation a);
    String visit(Condition c);
    String visit(Corps c);
    String visit(DeclarationProgramme dp);
    String visit(Pour p);
    String visit(Read r);
    String visit(TantQue tq);
    String visit(Write w);
    String visit(Unaire u);
    String visit(Not n);
    String visit(Inverse i);

}