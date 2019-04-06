package entity;

public class UserReviewId extends GeneralId{
    private static UserReviewId instance;

    private UserReviewId() {
    }

    public static UserReviewId getInstance(){
        if(instance == null){
            instance = new UserReviewId();
        }
        return instance;
    }
}
