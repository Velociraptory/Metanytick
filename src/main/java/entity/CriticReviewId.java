package entity;

public class CriticReviewId {
    private static int id;
    private static CriticReviewId instance;

    private CriticReviewId() {
    }

    public static int getId() {
        return id;
    }

    public static CriticReviewId getInstance(){
        if(instance == null){
            instance = new CriticReviewId();
        }
        return instance;
    }

    public static void setNewId() { //обновление id на следующее использование
        CriticReviewId.id = ++id;
    }
}
