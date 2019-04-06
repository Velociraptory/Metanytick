package entity;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class RegisteredUserBeansConfiguration { //здесь лежат бины пользователей

    @Bean (name = "Super Administrator - Jamal") //бин суперадминистратора
    @Description("Super Jamal Bean")
    @Qualifier("Super Jamal")
    Administrator SuperJamal(){
        int id = UserId.getInstance().getId(); //получение id для нового пользователя
        UserId.getInstance().setNewId(); //обновление id на следующее использование
        return new Administrator(id, "SuperJamal", "superjamal.gmail.com", "jamal69", false, "doesn't have");

    }

    @Bean (name = "Administrator - Jamal") //бин администратора
    @Description("Jamal Bean")
    @Qualifier("Jamal")
    Administrator Jamal(){
        int id = UserId.getInstance().getId(); //получение id для нового пользователя
        UserId.getInstance().setNewId(); //обновление id на следующее использование
        return new Administrator(id, "Jamal", "jamal69.gmail.com", "jamal69", false, "doesn't have");

    }

    @Bean (name = "Critic - Roger Ebert") //бин критика
    @Description("Roger Ebert Bean")
    @Qualifier("RogerEbert")
    RegisteredUser RogerEbert(){
        int id = UserId.getInstance().getId(); //получение id для нового пользователя
        UserId.getInstance().setNewId(); //обновление id на следующее использование
        return new RegisteredUser(id, "Roger Ebert", "roger.ebert.gmail.com", "****", true, "rogerebert.com");
    }

    @Bean (name = "Critic - Gene Siskel") //бин критика
    @Description("Gene Siskel Bean")
    @Qualifier("GeneSiskel")
    RegisteredUser GeneSiskel(){
        int id = UserId.getInstance().getId(); //получение id для нового пользователя
        UserId.getInstance().setNewId(); //обновление id на следующее использование
        return new RegisteredUser(id, "Gene Siskel", "gene.siskel.gmail.com", "****", true, "genesiskel.com");
    }

    @Bean (name = "User - John Doe") //бин обычного пользователя
    @Description("John Doe Bean")
    @Qualifier("JohnDoe")
    RegisteredUser JohnDoe(){
        int id = UserId.getInstance().getId(); //получение id для нового пользователя
        UserId.getInstance().setNewId(); //обновление id на следующее использование
        return new RegisteredUser(id, "John Doe", "john.doe.mail.ru", "****", false, "doesn't have");
    }
}
