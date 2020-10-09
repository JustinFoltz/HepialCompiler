import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class JasminWriter {
    private FileWriter fw;
    private BufferedWriter output;
    public static JasminWriter instance = null;
    public int labelCounter;

    private JasminWriter(String nomFichier) {
        try {
            this.fw = new FileWriter(nomFichier + ".j", false); 
            this.output = new BufferedWriter(fw);
            labelCounter = 0;
        } catch (IOException e) { e.printStackTrace(); }     
    }

    public static void ouvrirFichier(String nomFichier) {
        if(instance == null) { 
            instance = new JasminWriter(nomFichier);
            instance.ajouterLigne(".class public "+ nomFichier);
            instance.ajouterLigne(".super java/lang/Object");
            instance.ajouterLigne(".method public static main([Ljava/lang/String;)V");
            instance.ajouterLigne(".limit stack 20000");
            instance.ajouterLigne(".limit locals 10");
            //cte pour afficher les boooléens et stocker les valeurs de booléens
        }
    }

    public static JasminWriter getInstance() {
        return instance;
    }

    public void fermerFichier() {
        try {
            //pas sur que ca aille là, à voir
            this.ajouterLigne("return");
            this.ajouterLigne(".end method");
            this.output.close();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public void ajouterLigne(String ligne) {
        try {
            this.output.write(ligne + "\n");
            this.output.flush();
        } catch(IOException e) { e.printStackTrace(); }
    }

    public void ajouterValeur(int valeur) {
        ajouterLigne("ldc " + valeur);
    }

    public void ajouterRelation(String operation) {
        ajouterLigne(operation+ " label_"+labelCounter);
        ajouterLigne("ldc 0");
        ajouterLigne("goto label_"+(labelCounter+1));
        ajouterLigne("label_"+labelCounter+":");
        ajouterLigne("ldc 1");
        ajouterLigne("label_"+(labelCounter+1)+":");
        labelCounter += 2;
    }

    public void declarer(String id, String t, int index){
        StringBuilder statement = new StringBuilder();
        switch(t) {
            case "nombre":
                statement.append(".var ").append(index).append(" is ").append(id).append(" I");
                break;
            case "booleen":
                statement.append(".var ").append(index).append(" is ").append(id).append(" Z");
                break;
            default: break;
        }
        ajouterLigne(statement.toString());
    }

    public void assigner(int index){
        ajouterLigne("istore "+index);
    }

    public Type getTypeId(Identifiant id) {
        if(DeclarationsTable.getInstance().existVar(id)) {
            return DeclarationsTable.getInstance().getVar().get(id.getNom());
        }
        return DeclarationsTable.getInstance().getConst().get(id.getNom());
    }

    public void afficher(Identifiant i, boolean ln) {
        int index = DeclarationsTable.getInstance().indexOf(i.toString());
        Type t = getTypeId(i);
        System.out.println(t.getClass());
        ajouterLigne("getstatic java/lang/System/out Ljava/io/PrintStream;");
        ajouterLigne( "iload "+index);
        if(t instanceof TypeInt) {
            if(ln) { ajouterLigne("invokevirtual java/io/PrintStream/println(I)V");
            } else { ajouterLigne("invokevirtual java/io/PrintStream/print(I)V"); }
        } else {
            ajouterLigne("ifeq label_"+labelCounter);
            ajouterLigne("ldc \"vrai\"");
            ajouterLigne("goto label_"+(labelCounter+1));
            ajouterLigne("label_"+labelCounter+":");
            ajouterLigne("ldc \"faux\"");
            ajouterLigne("label_"+(labelCounter+1)+":");
            labelCounter += 2;
            if(ln) { ajouterLigne("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V"); } 
            else { ajouterLigne("invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V"); }
        }
    }

    public void afficher(Expression e, boolean ln) {
        String type = e.accept(new SemantiqueVisitor());
        ajouterLigne("getstatic java/lang/System/out Ljava/io/PrintStream;");
        e.accept(new ProductionCodeVisitor());
        if(type == "nombre") {
            if(ln) { ajouterLigne("invokevirtual java/io/PrintStream/println(I)V");
            } else { ajouterLigne("invokevirtual java/io/PrintStream/print(I)V"); }
        } else {
            ajouterLigne("ifeq label_"+labelCounter);
            ajouterLigne("ldc \"vrai\"");
            ajouterLigne("goto label_"+(labelCounter+1));
            ajouterLigne("label_"+labelCounter+":");
            ajouterLigne("ldc \"faux\"");
            ajouterLigne("label_"+(labelCounter+1)+":");
            labelCounter += 2;
            if(ln) { ajouterLigne("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V"); } 
            else { ajouterLigne("invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V"); }
        }
    }

    public void afficher(Chaine c, boolean ln) {
        ajouterLigne("getstatic java/lang/System/out Ljava/io/PrintStream;");
        ajouterLigne("ldc " + c.toString());
        if(ln) { ajouterLigne("invokevirtual java/io/PrintStream/println(Ljava/lang/String;)V"); } 
        else { ajouterLigne("invokevirtual java/io/PrintStream/print(Ljava/lang/String;)V"); }
        
    }

    public void ajouterValeurAIdentifiant(int index) {
        ajouterLigne("istore " + index);
    }

    public void ajouterSi(Corps alors, Corps sinon){
        ajouterLigne("ifeq label_"+labelCounter);
        alors.accept(new ProductionCodeVisitor());
        ajouterLigne("goto label_"+(labelCounter+1));
        ajouterLigne("label_" + labelCounter + ":");
        sinon.accept(new ProductionCodeVisitor());
        ajouterLigne("label_"+(labelCounter+1)+":");
        labelCounter+=2;
    }

    public void ajouterTantQue(Expression condition, Corps corps){
        int start = labelCounter;
        int stop = start+1;
        labelCounter+=2;
        ajouterLigne("label_"+start+":");
        condition.accept(new ProductionCodeVisitor());
        ajouterLigne("ifeq label_"+stop);
        corps.accept(new ProductionCodeVisitor());
        ajouterLigne("goto label_"+start);
        ajouterLigne("label_"+stop+":");
    }

    public void ajouterPour(Pour p){
        int id = DeclarationsTable.getInstance().indexOf(p.getId().getNom());
        p.getLowerBound().accept(new ProductionCodeVisitor());
        ajouterValeurAIdentifiant(id);
        Inferieur i = new Inferieur(0, 0);
        i.setOperandeGauche(p.getId());
        i.setOperandeDroite(p.getUpperBound());
        int start = labelCounter;
        int stop = start+1;
        labelCounter+=2;
        Addition add = new Addition(0, 0);
        add.setOperandeGauche(p.getId());
        add.setOperandeDroite(new Nombre(1, 0, 0));
        Affectation aff = new Affectation(p.getId(), add, 0, 0);

        ajouterLigne("label_"+start+":");
        i.accept(new ProductionCodeVisitor());
        ajouterLigne("ifeq label_"+stop);
        p.getProcess().accept(new ProductionCodeVisitor());

        aff.accept(new ProductionCodeVisitor());
        ajouterLigne("goto label_"+start);
        ajouterLigne("label_"+stop+":");
    }

    public void ajouterRead(Identifiant i){
        int index = DeclarationsTable.getInstance().indexOf(i.getNom());
        Type t = DeclarationsTable.getInstance().getVar().get(i.getNom());

        ajouterLigne("new java/util/Scanner");
        ajouterLigne("dup");
        ajouterLigne("getstatic java/lang/System/in Ljava/io/InputStream;");
        ajouterLigne("invokespecial java/util/Scanner/<init>(Ljava/io/InputStream;)V");
        if(t.getClass().equals(TypeBoolean.class)){
            ajouterLigne("invokevirtual java/util/Scanner/nextBoolean()Z");
        }else {
            ajouterLigne("invokevirtual java/util/Scanner/nextInt()I");
        }
        ajouterLigne("istore "+index);
    }

}

