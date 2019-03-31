package entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Component;

@Configuration
public class ReviewBeansConfiguration { //здесь лежат бины рецензий
    @Autowired
    @Qualifier("RogerEbert")
    RegisteredUser RogerEbert; //берем критика из другого бина
    @Autowired
    @Qualifier("GeneSiskel")
    RegisteredUser GeneSiskel; //берем критика из другого бина
    @Autowired
    @Qualifier("JohnDoe")
    RegisteredUser JohnDoe; //берем пользователя из другого бина

    @Bean(name = "Roger Ebert Review on Toxic Avenger") //бин рецензии критика
    @Description("Review on Toxic Avenger")
    CriticReview reviewByRogerEbertOnToxicAvenger(){
        int id = CriticReviewId.getId(); //получение id для новой рецензии критика
        CriticReviewId.setNewId(); //обновление id на следующее использование
        return new CriticReview(id, "Awesome Blazing Modern Classic", RogerEbert, 50, RogerEbert.getWebsite());
    }
    @Bean(name = "Gene Siskel Review on Toxic Avenger") //бин рецензии критика
    @Description("Review on Toxic Avenger")
    CriticReview reviewByGeneSiskelOnToxicAvenger(){
        int id = CriticReviewId.getId(); //получение id для новой рецензии критика
        CriticReviewId.setNewId(); //обновление id на следующее использование
        return new CriticReview(id, "Great! Like it!", GeneSiskel, 0, GeneSiskel.getWebsite());
    }
    @Bean(name = "John Doe Review on Toxic Avenger") //бин рецензии обычного пользователя
    @Description("Review on Toxic Avenger")
    UserReview reviewByJohnDoelOnToxicAvenger(){
        int id = UserReviewId.getId(); //получение id для новой рецензии критика
        UserReviewId.setNewId(); //обновление id на следующее использование
        int [] helpful = new int [2]; //структура данных для хранение коэффициента полезности рецензии
        return new UserReview(id, "Not So Bad", JohnDoe, 50, helpful);
    }


}
