package service;

import entity.CriticReview;
import entity.RegisteredUser;
import entity.UserReview;

public interface Reviewing {
    public void createCriticReview(String criticReviewText, RegisteredUser author, double metascore, String fullPublication); //создать рецензию критика
    public void createUserReview(String userReviewText, RegisteredUser author, double userscore); //создать рецензию обычного пользователя
    public void addCriticReview(CriticReview criticReview); //добавить к медиапродукту готовую рецензию критика
    public void addUserReview(UserReview userReview); //добавить к медиапродукту готовую рецензию обычного пользователя
    public void rateMediaProduct(int rate);
}
