package entity;

import java.util.ArrayList;
import java.util.HashMap;

public class UserCatalog {
    private static UserCatalog instance;
    private HashMap<Integer, RegisteredUser> users;

    private UserCatalog(HashMap<Integer, RegisteredUser> users) {
        this.users = users;
    }

    public HashMap<Integer, RegisteredUser> getUsers() {
        return users;
    }

    public static UserCatalog getInstance() {
        if(instance == null){
            HashMap<Integer, RegisteredUser> users = new HashMap<Integer, RegisteredUser>();
            instance = new UserCatalog(users);
        }
        return instance;
    }

    public UserRequestResult requestById(int id) { //запрос пользователя по id
        ArrayList<RegisteredUser> result = new ArrayList<RegisteredUser>(); //лист для хранения результата запроса
        UserRequestResult userRequestResult = new UserRequestResult(result); //создаем объект результата запроса
        for (RegisteredUser user : users.values()) { //проходимся по каталогу пользователей
            if(user.getId() == id){ //если нашли совпадение по id
                result.add(user); //добавляем пользователя в результат запроса
            }
        }
        return userRequestResult; //возвращаем результат запроса
    }
    public UserRequestResult requestByName(String name) { //запрос пользователя по имени
        ArrayList<RegisteredUser> result = new ArrayList<RegisteredUser>(); //лист для хранения результата запроса
        UserRequestResult userRequestResult = new UserRequestResult(result); //создаем объект результата запроса
        for (RegisteredUser user : users.values()) { //проходимся по каталогу пользователей
            if(user.getName().equals(name)){ //если нашли совпадение по имени
                result.add(user); //добавляем пользователя в результат запроса
            }
        }
        return userRequestResult; //возвращаем результат запроса
    }

    @Override
    public String toString() {
        return "UserCatalog{" +
                "users=" + users +
                '}';
    }
}
