package run;

import entity.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;

public class Runner {
    public static void main(String[] args) {
        //данные метапользователя, которые используются для тестирования работы с БД (создание/удаление суперадминистратора, действия совершаемые без непосредственного контроля администраторов: запись рецензий, обновление оценок)
        Metauser metauser = Metauser.getInstance(); //роль вне контекста предметной области (метароль)
        metauser.setName("George"); //логин в БД
        metauser.setPassword("schrodingerscat"); //пароль в БД
        //ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("file:src\\main\\resources\\applicationContext.xml"); //загрузка контекста из xml
        //Administrator admin = context.getBean("testAdministratorBean", Administrator.class); //загрузка бина администратора из xml
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(); //загрузка контекста
        context.register(RegisteredUserBeansConfiguration.class); //регистрация контекста из файла конфигурации
        context.register(ReviewBeansConfiguration.class); //регистрация контекста из файла конфигурации
        context.refresh();

        Administrator superAdmin = context.getBean("Super Administrator - Jamal", Administrator.class); //загрузка бина суперадминистратора из файла конфигурации
        metauser.assignSuperRole(superAdmin, "metanytick"); //создание для суперадминистратора роли суперпользователя в БД


        //добавление новых пользователей в каталог пользователей
        superAdmin.createNewUser(superAdmin);
        superAdmin.createNewUser(context.getBean("Critic - Roger Ebert", RegisteredUser.class));
        superAdmin.createNewUser(context.getBean("Critic - Gene Siskel", RegisteredUser.class));
        superAdmin.createNewUser(context.getBean("User - John Doe", RegisteredUser.class));

        Administrator admin = context.getBean("Administrator - Jamal", Administrator.class); //загрузка бина администратора из файла конфигурации
        superAdmin.createNewUser(admin);
        superAdmin.assignDBRole(admin, "metanytick"); //создание суперадминистратором для младшего администратора роли в БД с назначением

        admin.addCatalog("80'S CINEMA"); //добавление нового каталога с помощью интерфейса администратора
        admin.addCatalog("MODERN CINEMA"); //добавление нового каталога с помощью интерфейса администратора
        admin.addCatalog("WES ANDERSON'S FILMOGRAPHY"); //добавление нового каталога с помощью интерфейса администратора

        admin.addMediaProductFromFile(GeneralCatalog.getInstance().requestCatalog("80'S CINEMA"), "src\\\\main\\\\resources\\\\80'S CINEMA mediaProducts.txt"); //добавление медиапродуктов в каталог используя данные из файла
        admin.addMediaProductFromFile(GeneralCatalog.getInstance().requestCatalog("MODERN CINEMA"), "src\\\\main\\\\resources\\\\MODERN CINEMA mediaProducts.txt"); //добавление медиапродуктов в каталог используя данные из файла
        admin.addMediaProductFromFile(GeneralCatalog.getInstance().requestCatalog("WES ANDERSON'S FILMOGRAPHY"), "src\\\\main\\\\resources\\\\WES ANDERSONS'S FILMOGRAPHY mediaProducts.txt"); //добавление медиапродуктов в каталог используя данные из файла
        System.out.println(GeneralCatalog.getInstance()); //Вывод содержимого главного каталога
        //процесс добавления рецензии критика
        Catalog targetCatalog = GeneralCatalog.getInstance().requestCatalog("80'S CINEMA"); //получение нужного каталога по имени
        MediaProductRequestResult targetResult = targetCatalog.requestByTitle("The Toxic Avenger"); //получение результата выюорки по названию фильма
        ArrayList<MediaProduct> targetMediaProducts = targetResult.getResult(); //преобразование результата выборки в список
        MediaProduct targetMediaProduct = targetMediaProducts.get(0); //получение из списка нужного медиапродукта
        //targetMediaProduct.createCriticReview("Awesome Blazing Modern Classic", rogerEbert, 50, "www.rogerebert.com"); //создание рецензии на целевой медиапродукт
        targetMediaProduct.addCriticReview(context.getBean("Roger Ebert Review on Toxic Avenger", CriticReview.class)); //добавление рецензии критика из бина

        // процесс добавления еще одной рецензии критика
        Catalog targetCatalog3 = GeneralCatalog.getInstance().requestCatalog("80'S CINEMA"); //получение нужного каталога по имени
        MediaProductRequestResult targetResult3 = targetCatalog3.requestByTitle("The Toxic Avenger"); //получение результата выюорки по названию фильма
        ArrayList<MediaProduct> targetMediaProducts3 = targetResult3.getResult(); //преобразование результата выборки в список
        MediaProduct targetMediaProduct3 = targetMediaProducts3.get(0); //получение из списка нужного медиапродукта
        //targetMediaProduct3.createCriticReview("Great! Like it!", geneSiskel, 0, "www.genesiskel.com"); //создание рецензии на целевой медиапродукт
        targetMediaProduct3.addCriticReview(context.getBean("Gene Siskel Review on Toxic Avenger", CriticReview.class)); //добавление рецензии критика из бина

        // процесс добавления рецензии обычного пользователя
        Catalog targetCatalog2 = GeneralCatalog.getInstance().requestCatalog("80'S CINEMA"); //получение нужного каталога по имени
        MediaProductRequestResult targetResult2 = targetCatalog2.requestByTitle("The Toxic Avenger"); //получение результата выюорки по названию фильма
        ArrayList<MediaProduct> targetMediaProducts2 = targetResult2.getResult(); //преобразование результата выборки в список
        MediaProduct targetMediaProduct2 = targetMediaProducts2.get(0); //получение из списка нужного медиапродукта
        //targetMediaProduct2.createUserReview("Not So Bad", testUser, 50); //создание рецензии на целевой медиапродукт
        targetMediaProduct2.addUserReview(context.getBean("John Doe Review on Toxic Avenger", UserReview.class)); //добавление рецензии обычного пользователя из бина

        //System.out.println();
        //System.out.println();
        //System.out.println();
        //System.out.println();
        //System.out.println(GeneralCatalog.getInstance()); //Вывод содержимого главного каталога
        //запросы из конкретного каталога
        //System.out.println(GeneralCatalog.getInstance().requestCatalog("MODERN CINEMA").requestById(6)); //получение медиапродукта по id
        //System.out.println(GeneralCatalog.getInstance().requestCatalog("80'S CINEMA").requestByTitle("Blade Runner")); //получение медиапродукта по названию
        //System.out.println(GeneralCatalog.getInstance().requestCatalog("80'S CINEMA").requestByRelease(1981)); //получение медиапродукта по дате выпуска
        //System.out.println(GeneralCatalog.getInstance().requestCatalog("80'S CINEMA").requestByMetascore(25)); //получение медиапродукта по metascore
        //System.out.println(GeneralCatalog.getInstance().requestCatalog("80'S CINEMA").requestByUserscore(50)); //получение медиапродукта по userscore
        //System.out.println(GeneralCatalog.getInstance().requestCatalog("80'S CINEMA").requestByCreator("Ridley Scott")); //получение медиапродукта по создателю
        //System.out.println(GeneralCatalog.getInstance().requestCatalog("80'S CINEMA").requestByRating("R")); //получение медиапродукта по возрастному рейтингу

        //запросы по всему каталогу
        //System.out.println(GeneralCatalog.getInstance().requestById(0)); //получение медиапродукта по id
        //System.out.println(GeneralCatalog.getInstance().requestByTitle("Rushmore")); //получение медиапродукта по названию
        //System.out.println(GeneralCatalog.getInstance().requestByRelease(2005)); //получение медиапродукта по дате выпуска
        //System.out.println(GeneralCatalog.getInstance().requestByMetascore(25)); //получение медиапродукта по metascore
        //System.out.println(GeneralCatalog.getInstance().requestByUserscore(50)); //получение медиапродукта по Userscore
        //System.out.println(GeneralCatalog.getInstance().requestByCreator("Wes Anderson")); //получение медиапродукта по создателю
        //System.out.println(GeneralCatalog.getInstance().requestByRating("PG-13")); //получение медиапродукта по возрастному рейтингу
        //System.out.println();
        //System.out.println();
        //System.out.println();
        //System.out.println(UserCatalog.getInstance()); //Вывод содержимого каталога пользователей
        //System.out.println();
        //System.out.println(UserCatalog.getInstance().requestById(3)); //получение пользователя по его id
        //System.out.println(UserCatalog.getInstance().requestByName("Roger Ebert")); //получение пользователя по имени

        //System.out.println("\nвыбрать все фильмы из определенного каталога и отсортировать их по дате выпуска");
        //System.out.println(GeneralCatalog.getInstance().requestCatalog("WES ANDERSON'S FILMOGRAPHY").requestAll().sortByRelease());

        superAdmin.addCriticReviewsFromFile("src\\\\main\\\\resources\\\\CriticReviews.txt"); //добавление рецензий критиков из файла
        superAdmin.addUserReviewsFromFile("src\\\\main\\\\resources\\\\UserReviews.txt"); //добавление рецензий обычных пользователей из файла

        //System.out.println("\nвыбрать все фильмы из всех каталогов и отсортировать их по дате выпуска");
        //System.out.println(GeneralCatalog.getInstance().requestAll().sortByRelease());

        //System.out.println(GeneralCatalog.getInstance().requestAll().sortByMetascore()); //сортировка по metsscore
        //System.out.println(GeneralCatalog.getInstance().requestAll().sortByUserscore()); //сортировка по userscore;


        //admin.deleteMediaProduct(GeneralCatalog.getInstance().requestByTitle("Rushmore").getResult().get(0)); //удаление медиапродукта
        //System.out.println(GeneralCatalog.getInstance().requestCatalog("WES ANDERSON'S FILMOGRAPHY")); //каталог после удаление медиапродукта


        //admin.writeMediaProductsToFile(GeneralCatalog.getInstance().requestCatalog("80'S CINEMA").getMediaProducts(), "c://80'S CINEMA.txt"); //запись медиапродуктов в файл

        //superAdmin.dropDBAssignment(admin); //сложение суперадминистратором с младшего администратора полномочий на БД
        //metauser.dropSuperRoleAssignment(superAdmin); //сложение метапользователем полномочий с суперадминистратора на БД
    }
}