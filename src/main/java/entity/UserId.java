package entity;

public class UserId {
    private static int id;
    private static UserId instance;

    private UserId() {
    }

    public static int getId() {
        return id;
    }

    public static UserId getInstance() {
        if(instance == null){
            instance = new UserId();
        }
        return instance;
    }

    public static void setNewId() { //обновление id на следующее использование
        UserId.id = ++id;
    }
}
