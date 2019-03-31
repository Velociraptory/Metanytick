package entity;

import java.util.ArrayList;

public class UserRequestResult { //результат запроса по пользователям
    private ArrayList<RegisteredUser> result;

    public UserRequestResult(ArrayList<RegisteredUser> result) {
        this.result = result;
    }

    public ArrayList<RegisteredUser> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "RequestResult{" +
                "result=" + result +
                '}';
    }

    public void sort(){ //метод сортировки результата запроса

    }
}
