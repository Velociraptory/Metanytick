package entity;

public class GeneralId {
    private int id;

    public int getId() {
        return id;
    }

    public void setNewId() { //обновление id на следующее использование
        id++;
    }

}
