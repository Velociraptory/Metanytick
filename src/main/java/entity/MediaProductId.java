package entity;

public class MediaProductId extends GeneralId{
    private static MediaProductId instance;

    private MediaProductId() {
    }

    public static MediaProductId getInstance(){
        if(instance == null){
            instance = new MediaProductId();
        }
        return instance;
    }
}
