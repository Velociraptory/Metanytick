package entity;

public class UserReviewId {
    private static int id;
    private static UserReviewId instance;

    private UserReviewId() {
    }

    public static int getId() {
        return id;
    }

    public static UserReviewId getInstance(){
        if(instance == null){
            instance = new UserReviewId();
        }
        return instance;
    }
    public static void setNewId() { //обновление id на следующее использование
        UserReviewId.id = ++id;
    }
}
