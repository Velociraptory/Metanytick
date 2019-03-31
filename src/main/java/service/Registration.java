package service;

import entity.RegisteredUser;

public interface Registration {
    public void validInputData();
    public void createNewUser(RegisteredUser registeredUser); //метод добавления новых пользователей
}
