import java.util.Map;
import java.util.Set;


public class ProductionCodeVisitor implements ProductionCodeAbstraitVisitor {

    public void visit(DeclarationProgramme dp) {

        //Pour toutes les déclarations
        dp.getDeclarations().stream().forEach(i -> {
            if( i != null){
                Affectation a = (Affectation)i;
                DeclarationsTable t = DeclarationsTable.getInstance();
                //assignation des constantes
                Set<String> ctes = DeclarationsTable.getInstance().getConst().keySet();
                for(String cte : ctes){
                        int index =  t.indexOf(cte);
                        JasminWriter.getInstance().declarer(cte, DeclarationsTable.getInstance().getConst().get(cte).toString(), index);
                        a.getDst().accept(this);
                        JasminWriter.getInstance().assigner(index);
                    }
                }
        });
        //création des variables
        Set<Map.Entry<String, Type>> setVar = DeclarationsTable.getInstance().getVar().entrySet();
        DeclarationsTable t = DeclarationsTable.getInstance();
        for(Map.Entry<String, Type> e : setVar){
            int index = t.indexOf(e.getKey());
            JasminWriter.getInstance().declarer(e.getKey(), e.getValue().toString(), index);
        }

        //Pour toutes les instructions
        dp.getInstructions().stream().forEach(i->i.accept(this));
    }

    public void visit(Affectation a) {
        a.getDst().accept(this);
        int index = DeclarationsTable.getInstance().indexOf(a.getSrc().getNom());
        JasminWriter.getInstance().ajouterLigne("istore " + index);
    }

    public void visit(Nombre n) { JasminWriter.getInstance().ajouterValeur(n.getValeur()); }
    public void visit(Booleen b) {
        JasminWriter.getInstance().ajouterValeur(b.getValeur()); 
    }

    public void visitBinaire(Binaire a, String operation) {
        a.gauche.accept(this);
        a.droite.accept(this);
        JasminWriter.getInstance().ajouterLigne(operation);
    }



    public void visitRelation(Relation r, String operation) {
        r.gauche.accept(this);
        r.droite.accept(this);
        JasminWriter.getInstance().ajouterRelation(operation);
    }

    public void visit(Identifiant i) {
        int index = DeclarationsTable.getInstance().indexOf(i.getNom());
        JasminWriter.getInstance().ajouterLigne("iload " + index);

    }
    public void visit(Addition a) { visitBinaire(a, "iadd"); }
    public void visit(Soustraction s) { visitBinaire(s, "isub"); }
    public void visit(Division d) { visitBinaire(d, "idiv"); }
    public void visit(Produit p) { visitBinaire(p, "imul"); }
    public void visit(Egal e) { visitRelation(e, "if_icmpeq"); }
    public void visit(Different e) { visitRelation(e, "if_icmpne"); }
    public void visit(Superieur s) { visitRelation(s, "if_icmpgt"); }
    public void visit(SuperieurEgal se) { visitRelation(se, "if_icmpge"); }
    public void visit(Inferieur i) { visitRelation(i, "if_icmplt"); }
    public void visit(InferieurEgal ie) { visitRelation(ie, "if_icmple"); }


    public void visit(Not n) { 
        n.getExp().accept(this);
        JasminWriter.getInstance().ajouterRelation("ifeq");
    }

    public void visit(Et e){
        visitBinaire(e, "iand");
    }

    public void visit(Ou o){
        visitBinaire(o, "ior");
    }

    public void visit(Inverse i){
        i.getExp().accept(this);
        JasminWriter.getInstance().ajouterLigne("ineg");
    }

    public void visit(InverseBits ib){
        ib.getExp().accept(this);
        JasminWriter.getInstance().ajouterValeur(-1);
        JasminWriter.getInstance().ajouterLigne("ixor");
    }



    public void visit(Write w){
        Expression e =  w.getE();
        if(e instanceof Identifiant) {
            JasminWriter.getInstance().afficher((Identifiant)e, w.getLn());
        } else if(e instanceof Chaine) {
            JasminWriter.getInstance().afficher((Chaine)e, w.getLn());
        } else if(e instanceof Expression) {
            JasminWriter.getInstance().afficher(e, w.getLn());
        }
    }

    public void visit(Condition c){
        c.getCondition().accept(this);
        Corps alors = c.getAlors();
        Corps sinon = c.getSinon();
        JasminWriter.getInstance().ajouterSi(alors, sinon);

    }

    public void visit(Corps c){
        c.getlstInstr().forEach(i -> i.accept(this));
    }

    public void visit(TantQue t){
        JasminWriter.getInstance().ajouterTantQue(t.getCondition(), t.getProcess());
    }

    public void visit(Pour p){
        JasminWriter.getInstance().ajouterPour(p);
    }

    public void visit(Parenthese p){
        p.getExpr().accept(this);
    }

    public void visit(Read r){
        JasminWriter.getInstance().ajouterRead(r.getId());
    }
}
