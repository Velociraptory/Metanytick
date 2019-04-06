package entity;

public class CatalogId extends GeneralId{
    private static CatalogId instance;

    private CatalogId() {
    }

    public static CatalogId getInstance(){
        if(instance == null){
            instance = new CatalogId();
        }
        return instance;
    }
}
