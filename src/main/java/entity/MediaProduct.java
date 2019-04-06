package entity;

import exception.DatabaseException;
import facade.DatabaseFacade;
import facade.PostgresDatabaseFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.Reviewing;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
@Table(name = "mediaproducts_hibernate")
public class MediaProduct implements Reviewing {
    @Transient
    private final Logger logger = LogManager.getLogger(MediaProduct.class); //логгер
    @Id
    @Column(name = "id")
    private int id; //id медиапродукта
    @Column(name = "title")
    private String title; //название медиапродукта
    @Column(name = "release")
    private int release; //год выпуска медиапродукта
    @Column(name = "creator")
    private String creator; //создатель(режиссер снявший фильм, группа записавшая альбом, студия разработавшая игру)
    @Column(name = "rating")
    private String rating; //возрастной рейтинг
    @Column(name = "runtime")
    private String runtime; //длительность
    @Column(name = "metascore")
    private double metascore; //рейтинг от критиков
    @Column(name = "userscore")
    private double userscore; //рейтинг от пользователей
    @Transient
    private ArrayList<Double> allGivenMetascores; //все оценки от критиков
    @Transient
    private ArrayList<Double> allGivenUserscores; //все оценки от обычных пользователей
    @Transient
    private HashMap<Integer, CriticReview> criticReviews; //каталог рецензий от критиков на медиапродукт
    @Transient
    private HashMap<Integer, UserReview> userReviews; //каталог рецензий от обычных пользователей на медиапродукт
    @Transient
    private Catalog catalog; //каталог, в котором находится медиапродукт
    @Column(name = "catalog")
    private String catalogName; //имя каталога, в котором находится медиапродукт

    public MediaProduct(int id, String title, int release, String creator, String rating, String runtime, Catalog catalog) {
        this.id = id;
        this.title = title;
        this.release = release;
        this.creator = creator;
        this.rating = rating;
        this.runtime = runtime;
        metascore = 0;
        userscore = 0;
        this.catalog = catalog;
        criticReviews = new HashMap<Integer, CriticReview>();
        userReviews = new HashMap<Integer, UserReview>();
        allGivenMetascores = new ArrayList<Double>();
        allGivenUserscores = new ArrayList<Double>();
        catalogName = catalog.getName();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getRelease() {
        return release;
    }

    public String getCreator() {
        return creator;
    }

    public String getRating() {
        return rating;
    }

    public String getRuntime() {
        return runtime;
    }

    public double getMetascore() {
        return metascore;
    }

    public double getUserscore() {
        return userscore;
    }

    public HashMap<Integer, CriticReview> getCriticReviews() {
        return criticReviews;
    }

    public HashMap<Integer, UserReview> getUserReviews() {
        return userReviews;
    }

    public Catalog getCatalog() {
        return catalog;
    }

    @Override
    public void createCriticReview(String criticReviewText, RegisteredUser author, double metascore, String fullPublication) { //реализации метода создания рецензии критика
        int criticReviewId = CriticReviewId.getInstance().getId(); //получение id для новой рецензии критика
        CriticReview criticReview = new CriticReview(criticReviewId, criticReviewText, author, metascore, fullPublication); //инициализация объекта рецензии
        criticReviews.put(criticReviewId, criticReview); //запись рецензии в каталог рецензий на данный медиапродукт
        CriticReviewId.getInstance().setNewId(); //обновление id на следующее использование
        updateMetascore(metascore); //обновление текущего metascore
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        String databaseLogin = Metauser.getInstance().getName();
        String databasePassword = Metauser.getInstance().getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM CRITIC REVIEW CREATION"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM CRITIC REVIEW CREATION"); //информация об успешно установленном соединении
                Statement statement = connection.createStatement();
                ///создание таблицы для хранения рецензий критиков///
                String sql = "create table if not exists criticreviews(id integer primary key,\n" + //если таблица с указанным именем не сушествует, создать ее
                        "mediaproduct varchar(50) not null,\n" +
                        "reviewtext varchar(1000) not null,\n" +
                        "author varchar(30) not null,\n" +
                        "metascore double precision not null,\n" +
                        "fullpublication varchar(100) \n);";
                statement.execute(sql);
                //запись рецензии критика в таблицу///
                String sql2 = "INSERT INTO criticreviews (id, mediaproduct, reviewtext, author, metascore, fullpublication) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setInt(1, criticReview.getId());
                preparedStatement.setString(2, this.getTitle());
                preparedStatement.setString(3, criticReview.getReviewText());
                preparedStatement.setString(4, criticReview.getAuthor().getName());
                preparedStatement.setDouble(5, criticReview.getMetascore());
                preparedStatement.setString(6, criticReview.getFullPublication());
                preparedStatement.executeUpdate();
            }
        } catch (DatabaseException de) {
            System.out.println(de.getMessage());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        finally {
            databaseFacade.disconnect(); //закрытие соединения с БД
        }
        //////HIBERNATE//////
        CriticReviewDao criticReviewDao = new CriticReviewDao();
        criticReviewDao.save(criticReview); //запись рецензии критика в БД с помощью hibernate
    }

    @Override
    public void createUserReview(String userReviewText, RegisteredUser author, double userscore) { //реализации метода создания рецензии обычного пользователя
        int userReviewId = UserReviewId.getInstance().getId(); //получение id для новой рецензии обычного пользователя
        int helpful [] = new int [2]; //структура данных для хранение коэффициента полезности рецензии
        //helpful [0] = 0; //кол-во оценок "полезна"
        //helpful [1] = 0; //кол-во оценок "не полезна"
        UserReview userReview = new UserReview(userReviewId, userReviewText, author, userscore, helpful); //инициализация объекта рецензии
        userReviews.put(userReviewId, userReview); //запись рецензии в каталог рецензий на данный медиапродукт
        UserReviewId.getInstance().setNewId(); //обновление id на следующее использование
        updateUserscore(userscore); //обновление текущего userscore
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        String databaseLogin = Metauser.getInstance().getName();
        String databasePassword = Metauser.getInstance().getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM USER REVIEW CREATION"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM USER REVIEW CREATION"); //информация об успешно установленном соединении
                Statement statement = connection.createStatement();
                ///создание таблицы для хранения рецензий обычных пользователей///
                String sql = "create table if not exists userreviews(id integer primary key,\n" + //если таблица с указанным именем не сушествует, создать ее
                        "mediaproduct varchar(50) not null,\n" +
                        "reviewtext varchar(1000) not null,\n" +
                        "author varchar(30) not null,\n" +
                        "userscore double precision not null,\n" +
                        "helpfulyes int,\n" +
                        "helpfulno int \n);";
                statement.execute(sql);
                //запись рецензии обычного пользователя в таблицу///
                String sql2 = "INSERT INTO userreviews (id, mediaproduct, reviewtext, author, userscore, helpfulyes, helpfulno) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setInt(1, userReview.getId());
                preparedStatement.setString(2, this.getTitle());
                preparedStatement.setString(3, userReview.getReviewText());
                preparedStatement.setString(4, userReview.getAuthor().getName());
                preparedStatement.setDouble(5, userReview.getUserscore());
                int helpfulYes = helpful[0];
                int helpfulNo = helpful[1];
                preparedStatement.setInt(6, helpfulYes);
                preparedStatement.setInt(7, helpfulNo);
                preparedStatement.executeUpdate();
            }
        } catch (DatabaseException de) {
            System.out.println(de.getMessage());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        finally {
            databaseFacade.disconnect(); //закрытие соединения с БД
        }
        //////HIBERNATE//////
        UserReviewDao userReviewDao = new UserReviewDao();
        userReviewDao.save(userReview); //запись рецензии обычного пользователя в БД с помощью hibernate
    }

    @Override
    public void addCriticReview(CriticReview criticReview) { //реализации метода добавления к медиапродукту готовой рецензии критика из бина
        criticReviews.put(criticReview.getId(), criticReview); //запись рецензии в каталог рецензий на данный медиапродукт
        updateMetascore(criticReview.getMetascore()); //обновление текущего metascore
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        String databaseLogin = Metauser.getInstance().getName();
        String databasePassword = Metauser.getInstance().getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM CRITIC REVIEW ADDITION"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM CRITIC REVIEW ADDITION"); //информация об успешно установленном соединении

                Statement statement = connection.createStatement();
                ///создание таблицы для хранения рецензий критиков///
                String sql = "create table if not exists criticreviews(id integer primary key,\n" + //если таблица с указанным именем не сушествует, создать ее
                        "mediaproduct varchar(50) not null,\n" +
                        "reviewtext varchar(1000) not null,\n" +
                        "author varchar(30) not null,\n" +
                        "metascore double precision not null,\n" +
                        "fullpublication varchar(100) \n);";
                statement.execute(sql);
                //запись рецензии критика в таблицу///
                String sql2 = "INSERT INTO criticreviews (id, mediaproduct, reviewtext, author, metascore, fullpublication) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setInt(1, criticReview.getId());
                preparedStatement.setString(2, this.getTitle());
                preparedStatement.setString(3, criticReview.getReviewText());
                preparedStatement.setString(4, criticReview.getAuthor().getName());
                preparedStatement.setDouble(5, criticReview.getMetascore());
                preparedStatement.setString(6, criticReview.getFullPublication());
                preparedStatement.executeUpdate();
            }
        } catch (DatabaseException de) {
            System.out.println(de.getMessage());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        finally {
            databaseFacade.disconnect(); //закрытие соединения с БД
        }
        //////HIBERNATE//////
        CriticReviewDao criticReviewDao = new CriticReviewDao();
        criticReviewDao.save(criticReview); //запись рецензии критика в БД с помощью hibernate
    }

    @Override
    public void addUserReview(UserReview userReview) { //реализации метода добавления к медиапродукту готовой рецензии обычного пользователя из бина
        userReviews.put(userReview.getId(), userReview); //запись рецензии в каталог рецензий на данный медиапродукт
        updateUserscore(userReview.getUserscore()); //обновление текущего userscore
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        String databaseLogin = Metauser.getInstance().getName();
        String databasePassword = Metauser.getInstance().getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM USER REVIEW ADDITION"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM USER REVIEW ADDITION"); //информация об успешно установленном соединении
                Statement statement = connection.createStatement();
                ///создание таблицы для хранения рецензий обычных пользователей///
                String sql = "create table if not exists userreviews(id integer primary key,\n" + //если таблица с указанным именем не сушествует, создать ее
                        "mediaproduct varchar(50) not null,\n" +
                        "reviewtext varchar(1000) not null,\n" +
                        "author varchar(30) not null,\n" +
                        "userscore double precision not null,\n" +
                        "helpfulyes int,\n" +
                        "helpfulno int \n);";
                statement.execute(sql);
                //запись рецензии обычного пользователя в таблицу///
                String sql2 = "INSERT INTO userreviews (id, mediaproduct, reviewtext, author, userscore, helpfulyes, helpfulno) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setInt(1, userReview.getId());
                preparedStatement.setString(2, this.getTitle());
                preparedStatement.setString(3, userReview.getReviewText());
                preparedStatement.setString(4, userReview.getAuthor().getName());
                preparedStatement.setDouble(5, userReview.getUserscore());
                int [] helpful = userReview.getHelpful();
                int helpfulYes = helpful[0];
                int helpfulNo = helpful[1];
                preparedStatement.setInt(6, helpfulYes);
                preparedStatement.setInt(7, helpfulNo);
                preparedStatement.executeUpdate();
            }
        } catch (DatabaseException de) {
            System.out.println(de.getMessage());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        finally {
            databaseFacade.disconnect(); //закрытие соединения с БД
        }
        //////HIBERNATE//////
        UserReviewDao userReviewDao = new UserReviewDao();
        userReviewDao.save(userReview); //запись рецензии обычного пользователя в БД с помощью hibernate

    }

    @Override
    public void rateMediaProduct(int rate) { //отсутствие реализации метода оценки медиапродукта

    }

    public void updateMetascore(double metascore) { //метод обновления текущего metascore
        allGivenMetascores.add(metascore); //добавление новой оценки в список всех оценок
        int sum = 0; //сумма всех оценок
        int count = 0; //счетчик оценок
        for(double score : allGivenMetascores){ //проходимся по списку всех оценок
            sum += score; //считаем их сумму
            count++; //увеличиваем счетчик
        }
        this.metascore = sum / count; //обновляем metascore средним арифметическим от всех поставленных оценок на данный момент
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        String databaseLogin = Metauser.getInstance().getName();
        String databasePassword = Metauser.getInstance().getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM METASCORE UPDATE"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM METASCORE UPDATE"); //информация об успешно установленном соединении
                Statement statement = connection.createStatement();
                ///обновление metascore в таблице медиапродукта///
                String catalogNameForDB = catalog.getName().replace(" ", "");
                catalogNameForDB = catalogNameForDB.replace("'", "");
                String sql = "UPDATE " + "mediaproductsfrom" + catalogNameForDB + "\n" + //обновление metascore в таблице продукта
                        "SET metascore = '" + this.metascore + "'\n" +
                        "WHERE title = '" + this.getTitle() + "';";
                statement.executeUpdate(sql);
            }
        } catch (DatabaseException de) {
            System.out.println(de.getMessage());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        finally {
            databaseFacade.disconnect(); //закрытие соединения с БД
        }
        //////HIBERNATE//////
        MediaProductDao mediaProductDao = new MediaProductDao();
        mediaProductDao.update(this); //обновление медиапродукта в БД с помощью hibernate
    }
    public void updateUserscore(double usercore) { //метод обновления текущего userscore
        allGivenUserscores.add(usercore); //добавление новой оценки в список всех оценок
        int sum = 0; //сумма всех оценок
        int count = 0; //счетчик оценок
        for(double score : allGivenUserscores){ //проходимся по списку всех оценок
            sum += score; //считаем их сумму
            count++; //увеличиваем счетчик
        }
        this.userscore = sum / count; //обновляем userscore средним арифметическим от всех поставленных оценок на данный момент
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        String databaseLogin = Metauser.getInstance().getName();
        String databasePassword = Metauser.getInstance().getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM USERSCORE UPDATE"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM USERSCORE UPDATE"); //информация об успешно установленном соединении
                Statement statement = connection.createStatement();
                ///обновление userscore в таблице медиапродукта///
                String catalogNameForDB = catalog.getName().replace(" ", "");
                catalogNameForDB = catalogNameForDB.replace("'", "");
                String sql = "UPDATE " + "mediaproductsfrom" + catalogNameForDB +"\n" + //обновление userscore в таблице продукта
                        "SET userscore = '" + this.userscore + "'\n" +
                        "WHERE title = '" + this.getTitle() + "';";
                statement.executeUpdate(sql);
            }
        } catch (DatabaseException de) {
            System.out.println(de.getMessage());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        finally {
            databaseFacade.disconnect(); //закрытие соединения с БД
        }
        //////HIBERNATE//////
        MediaProductDao mediaProductDao = new MediaProductDao();
        mediaProductDao.update(this); //обновление медиапродукта в БД с помощью hibernate

    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        MediaProduct that = (MediaProduct) o;
        return (id == that.id && title.equals(that.title));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + title.hashCode();
        result = prime * result + id;
        return result;
    }

    @Override
    public String toString() {
        return "\n" +
                "MediaProduct{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", release=" + release +
                ", creator='" + creator + '\'' +
                ", rating='" + rating + '\'' +
                ", runtime='" + runtime + '\'' +
                ", metascore=" + metascore +
                ", userscore=" + userscore +
                ", criticReviews=" + criticReviews +
                ", userReviews=" + userReviews +
                '}';
    }
}
