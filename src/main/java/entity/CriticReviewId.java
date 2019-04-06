package entity;

public class CriticReviewId extends GeneralId{
    private static CriticReviewId instance;

    private CriticReviewId() {
    }

    public static CriticReviewId getInstance(){
        if(instance == null){
            instance = new CriticReviewId();
        }
        return instance;
    }
}
