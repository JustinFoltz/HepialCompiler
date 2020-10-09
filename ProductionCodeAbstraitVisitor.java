public interface ProductionCodeAbstraitVisitor {

        //String visit(ArbreAbstrait tree);
        void visit(DeclarationProgramme dp);
        void visit(Affectation a);
        
        void visit(Nombre n);
        void visit(Booleen b);

        void visit(Addition a);
        void visit(Soustraction s);
        void visit(Division d);
        void visit(Produit p);
        void visit(Egal e);
        void visit(Different d);
        void visit(Superieur s);
        void visit(SuperieurEgal s);
        void visit(Inferieur i);
        void visit(InferieurEgal i);
        void visit(Not n); 
        void visit(Identifiant i);
        void visit(Write w);
        void visit(Condition c);
        void visit(Corps c);
        void visit(TantQue tq);
        void visit(Pour p);
        void visit(Et e);
        void visit(Ou o);
        void visit(Inverse i);
        void visit(InverseBits ib);
        void visit(Parenthese p);
        void visit(Read r);

}
