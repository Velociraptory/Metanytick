package entity;

public class UserId extends GeneralId{
    private static UserId instance;

    private UserId() {
    }

    public static UserId getInstance() {
        if(instance == null){
            instance = new UserId();
        }
        return instance;
    }

}
