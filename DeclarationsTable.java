import java.util.*;

public class DeclarationsTable {
    private Map<String, Type> declarationsVar;
    private Map<String, Type> declarationsConst;
    private List<String> declarationsIndex;
    private static DeclarationsTable singletonDeclaration = null;


    private DeclarationsTable() {
        declarationsVar = new HashMap<>();
        declarationsConst = new HashMap<>();
        declarationsIndex = new ArrayList<>();
    }

    public boolean existConst(Identifiant id) {
        return this.declarationsConst.containsKey(id.getNom());
    }

    public boolean existVar(Identifiant id) {
        return this.declarationsVar.containsKey(id.getNom());
    }

    private boolean exist(Identifiant id) {
        return this.existVar(id) || this.existConst(id);
    }

    public Boolean putVar( Identifiant id, Type t ) {
        if(exist(id)) { return false; }
        this.declarationsVar.put(id.getNom(), t);
        return true;
    }

    public Boolean putConst( Identifiant id, Type t ) {
        if(exist(id)) { return false; }
        this.declarationsConst.put(id.getNom(), t);
        return true;
    }

    public static DeclarationsTable getInstance(){
        if(singletonDeclaration == null){
            singletonDeclaration = new DeclarationsTable();
        }
        return singletonDeclaration;
    }

    public void populateIndexes(){
        Set<Map.Entry<String, Type>> consts = declarationsConst.entrySet();
        for(Map.Entry<String, Type> e : consts){
            declarationsIndex.add(e.getKey());
        }

        Set<Map.Entry<String, Type>> vars = declarationsVar.entrySet();
        for(Map.Entry<String, Type> e : vars){
            declarationsIndex.add(e.getKey());
        }
    }

    public Map<String, Type> getVar() { return declarationsVar; }
    public Map<String, Type> getConst() { return declarationsConst; }

    public int indexOf(String id){
        return this.declarationsIndex.indexOf(id);
    }
}