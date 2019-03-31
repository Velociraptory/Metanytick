package entity;

public class MediaProductId {
    private static int id;
    private static MediaProductId instance;

    private MediaProductId() {
    }

    public static int getId() {
        return id;
    }

    public static MediaProductId getInstance(){
        if(instance == null){
            instance = new MediaProductId();
        }
        return instance;
    }

    public static void setNewId() { //обновление id на следующее использование
        MediaProductId.id = ++id;
    }
}
