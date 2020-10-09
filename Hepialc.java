import java.util.Vector;
import java.io.FileReader;
import java_cup.runtime.Symbol;

public class Hepialc {
    public static void main(String[] arg) {
            try { FileReader myFile = new FileReader(arg[0]);
                HepialLexer myLex = new HepialLexer(myFile);
                parser myP = new parser(myLex);


                try {
                    DeclarationProgramme program = (DeclarationProgramme)myP.parse().value;
                    DeclarationsTable.getInstance().populateIndexes();
                    if(program.accept(new SemantiqueVisitor()).equals("errors")){
                        System.out.println("Quitting Compilation due to errors in the code");
                        System.exit(0);
                    }

                    JasminWriter.ouvrirFichier(arg[1]);
                    program.accept(new ProductionCodeVisitor());
                    JasminWriter.getInstance().fermerFichier();

                }
                catch (Exception e) {
                    e.printStackTrace();

                }
            }
            catch (Exception e){
                System.out.println("invalid file");
            }
    }
}
