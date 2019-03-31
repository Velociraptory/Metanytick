package entity;

public class CatalogId {
    private static int id;
    private static CatalogId instance;

    private CatalogId() {
    }

    public static int getId() {
        return id;
    }

    public static CatalogId getInstance(){
        if(instance == null){
            instance = new CatalogId();
        }
        return instance;
    }

    public static void setNewId() { //обновление id на следующее использование
        CatalogId.id = ++id;
    }
}
