package entity;

import exception.DatabaseException;
import facade.DatabaseFacade;
import facade.PostgresDatabaseFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.CatalogManager;
import service.UserManager;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
@DiscriminatorValue("Administrator")
public class Administrator extends RegisteredUser implements CatalogManager, UserManager {
    @Transient
    private final Logger logger = LogManager.getLogger(Administrator.class); //логгер

    public Administrator() {
    }

    public Administrator(int id, String name, String email, String password, boolean acclaimedCritic, String website) {
        super(id, name, email, password, acclaimedCritic, website);
    }

    @Override
    public void addMediaProduct(Catalog catalog, String title, int release, String creator, String rating, String runtime) { //реализация метода добавления медиапродукта в каталог
        int id = MediaProductId.getInstance().getId(); //получение id для нового медиапродукта
        MediaProduct newMediaProduct = new MediaProduct(id, title, release, creator, rating, runtime, catalog); //создаем новый медиапродукт
        catalog.getMediaProducts().put(id, newMediaProduct); //помещаем его в каталог
        MediaProductId.getInstance().setNewId(); //обновление id на следующее использование
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        //обновление БД от имени администратора вызвавшего метод
        String databaseLogin = this.getName().toLowerCase();
        String databasePassword = this.getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM MEDIAPRODUCT CREATION"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM MEDIAPRODUCT CREATION"); //информация об успешно установленном соединении
                //запись медиапродукта в таблицу///
                String catalogNameForDB = catalog.getName().replace(" ", "");
                catalogNameForDB = catalogNameForDB.replace("'", "");
                String sql = "INSERT INTO mediaproductsfrom" + catalogNameForDB + "(id, title, release, creator, rating, runtime, metascore, userscore, catalog) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, newMediaProduct.getId());
                preparedStatement.setString(2, newMediaProduct.getTitle());
                preparedStatement.setInt(3, newMediaProduct.getRelease());
                preparedStatement.setString(4, newMediaProduct.getCreator());
                preparedStatement.setString(5, newMediaProduct.getRating());
                preparedStatement.setString(6, newMediaProduct.getRuntime());
                preparedStatement.setDouble(7, newMediaProduct.getMetascore());
                preparedStatement.setDouble(8, newMediaProduct.getUserscore());
                preparedStatement.setString(9, catalog.getName());
                preparedStatement.executeUpdate();
            }
        }
        catch (DatabaseException de) {
            System.out.println(de.getMessage());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        finally {
            databaseFacade.disconnect(); //закрытие соединения с БД
        }
        //////HIBERNATE//////
        MediaProductDao mediaProductDao = new MediaProductDao();
        mediaProductDao.save(newMediaProduct); //запись медиапродукта в БД с помощью hibernate

    }

    @Override
    public void addMediaProductFromFile(Catalog catalog, String fileName) { //реализация метода добавления медиапродукта в каталог используя данные из файла
        //List<Object> fields = new ArrayList<Object>();
        ArrayList<String> lines = FileReader.readFromFile(fileName); //записываем реузльтат метода чтения строк из файла в список
        for (String objectFields : lines) { //идем по каждой строке файла
            //данные, которые мы берем не из файла
            int id = MediaProductId.getInstance().getId(); //получение id для нового медиапродукта

            //данные, которые мы берем из файла
            String[] splitted = objectFields.split(", ");
            String titleFromFile = (splitted[0]);
            int releaseFromFile = Integer.parseInt(splitted[1]);
            String creatorFromFile = splitted[2];
            String ratingFromFile = splitted[3];
            String runtimeFromFile = splitted[4];
            MediaProduct newMediaProduct = new MediaProduct(id, titleFromFile, releaseFromFile, creatorFromFile, ratingFromFile, runtimeFromFile, catalog); //создаем медиапродукт на основе прочитанных данных
            catalog.getMediaProducts().put(id, newMediaProduct); //помещаем его в каталог
            MediaProductId.getInstance().setNewId(); //обновление id на следующее использование

            ///DATABASE///
            DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
            //обновление БД от имени администратора вызвавшего метод
            String databaseLogin = this.getName().toLowerCase();
            String databasePassword = this.getPassword();

            try {
                databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

                Connection connection = databaseFacade.getConnection();
                if (connection != null) {
                    //System.out.println("CONNECTED FROM MEDIAPRODUCT CREATION"); //информация об успешно установленном соединении
                    logger.info("CONNECTED FROM MEDIAPRODUCT CREATION"); //информация об успешно установленном соединении
                    //запись медиапродукта в таблицу///
                    String catalogNameForDB = catalog.getName().replace(" ", "");
                    catalogNameForDB = catalogNameForDB.replace("'", "");
                    String sql = "INSERT INTO mediaproductsfrom" + catalogNameForDB + "(id, title, release, creator, rating, runtime, metascore, userscore, catalog) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setInt(1, newMediaProduct.getId());
                    preparedStatement.setString(2, newMediaProduct.getTitle());
                    preparedStatement.setInt(3, newMediaProduct.getRelease());
                    preparedStatement.setString(4, newMediaProduct.getCreator());
                    preparedStatement.setString(5, newMediaProduct.getRating());
                    preparedStatement.setString(6, newMediaProduct.getRuntime());
                    preparedStatement.setDouble(7, newMediaProduct.getMetascore());
                    preparedStatement.setDouble(8, newMediaProduct.getUserscore());
                    preparedStatement.setString(9, catalog.getName());
                    preparedStatement.executeUpdate();
                }
            }
            catch (DatabaseException de) {
                System.out.println(de.getMessage());
            } catch (SQLException sqle) {
                System.out.println(sqle.getMessage());
            }
            finally {
                databaseFacade.disconnect(); //закрытие соединения с БД
            }
            //////HIBERNATE//////
            MediaProductDao mediaProductDao = new MediaProductDao();
            mediaProductDao.save(newMediaProduct); //запись медиапродукта в БД с помощью hibernate
        }
    }

    public void writeMediaProductsToFile(HashMap<Integer, MediaProduct> mediaProducts, String fileName){ //метод записи медиародуктов в файл
        //String line;
        ArrayList<String> lines = new ArrayList<String>(); //список для хранения медиапродуктов в текстовой форме
        for(MediaProduct mediaProduct : mediaProducts.values()){ //проходимся по отображению медиародуктов
            lines.add(mediaProduct.toString()); //записываем каждый медиапродукт в текстовой форме
        }
        FileWriter.writeToFile(lines, fileName); //записываем строки в файл
    }

    @Override
    public void addCriticReviewsFromFile(String fileName) { //реализация метода добавления рецензий критиков используя данные из файла
        ArrayList<String> lines = FileReader.readFromFile(fileName); //записываем реузльтат метода чтения строк из файла в список
        for (String objectFields : lines) { //идем по каждой строке файла
            //данные, которые мы берем не из файла
            int id = CriticReviewId.getInstance().getId(); //получение id для новой рецензии критика

            //данные, которые мы берем из файла
            String[] splitted = objectFields.split(", ");
            String mediaProductFromFile = (splitted[0]);
            String reviewText = splitted[1];
            String author = splitted[2];
            Double metascore = Double.parseDouble(splitted[3]);
            String fullPublication = splitted[4];
            CriticReview criticReview = new CriticReview(id, reviewText, UserCatalog.getInstance().requestByName(author).getResult().get(0), metascore, fullPublication); //создаем рецензию критика на основе прочитанных данных
            MediaProduct mediaProduct = GeneralCatalog.getInstance().requestByTitle(mediaProductFromFile).getResult().get(0); //получение медиапродукта по имени из файла
            mediaProduct.getCriticReviews().put(id, criticReview); //помещаем рецензию критика в каталог рецензий соответствующего медиапродукта
            CriticReviewId.getInstance().setNewId(); //обновление id на следующее использование
            mediaProduct.updateMetascore(metascore); //обновление metascore последобавления новой рецензии
            ///DATABASE///
            DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
            //обновление БД от имени администратора вызвавшего метод
            String databaseLogin = this.getName().toLowerCase();
            String databasePassword = this.getPassword();

            try {
                databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

                Connection connection = databaseFacade.getConnection();
                if (connection != null) {
                    //System.out.println("CONNECTED FROM CRITIC REVIEW ADDITION FROM FILE"); //информация об успешно установленном соединении
                    logger.info("CONNECTED FROM CRITIC REVIEW ADDITION FROM FILE"); //информация об успешно установленном соединении
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
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, mediaProductFromFile);
                    preparedStatement.setString(3, reviewText);
                    preparedStatement.setString(4, author);
                    preparedStatement.setDouble(5, metascore);
                    preparedStatement.setString(6, fullPublication);
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
    }
    @Override
    public void addUserReviewsFromFile(String fileName) { //реализация метода добавления рецензий обычных пользователей используя данные из файла
        ArrayList<String> lines = FileReader.readFromFile(fileName); //записываем реузльтат метода чтения строк из файла в список
        for (String objectFields : lines) { //идем по каждой строке файла
            //данные, которые мы берем не из файла
            int id = UserReviewId.getInstance().getId(); //получение id для новой рецензии критика

            //данные, которые мы берем из файла
            String[] splitted = objectFields.split(", ");
            String mediaProductFromFile = (splitted[0]);
            String reviewText = splitted[1];
            String author = splitted[2];
            Double userscore = Double.parseDouble(splitted[3]);
            int [] helpful = new int [2];
            UserReview userReview = new UserReview(id, reviewText, UserCatalog.getInstance().requestByName(author).getResult().get(0), userscore, helpful); //создаем рецензию обычного пользователя на основе прочитанных данных
            MediaProduct mediaProduct = GeneralCatalog.getInstance().requestByTitle(mediaProductFromFile).getResult().get(0); //получение медиапродукта по имени из файла
            mediaProduct.getUserReviews().put(id, userReview); //помещаем рецензию обычного пользователя в каталог рецензий соответствующего медиапродукта
            UserReviewId.getInstance().setNewId(); //обновление id на следующее использование
            mediaProduct.updateUserscore(userscore); //обновление userscore последобавления новой рецензии

            ///DATABASE///
            DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
            //обновление БД от имени администратора вызвавшего метод
            String databaseLogin = this.getName().toLowerCase();
            String databasePassword = this.getPassword();

            try {
                databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

                Connection connection = databaseFacade.getConnection();
                if (connection != null) {
                    //System.out.println("CONNECTED FROM USER REVIEW ADDITION FROM FILE"); //информация об успешно установленном соединении
                    logger.info("CONNECTED FROM USER REVIEW ADDITION FROM FILE"); //информация об успешно установленном соединении
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
                    preparedStatement.setInt(1, id);
                    preparedStatement.setString(2, mediaProductFromFile);
                    preparedStatement.setString(3, reviewText);
                    preparedStatement.setString(4, author);
                    preparedStatement.setDouble(5, userscore);
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
    }

    @Override
    public void addCatalog(String catalogName) { //реализация метода добавления каталога
        HashMap<Integer, MediaProduct> newCatalogMap = new HashMap<Integer, MediaProduct>(); //структура данных для хранения медиапродуктов
        int catalogId = CatalogId.getInstance().getId(); //получение id для нового каталога
        Catalog newCatalog = new Catalog(catalogId, catalogName, newCatalogMap); //создание тестового каталога для хранения медиапродуктов
        GeneralCatalog.getInstance().getCatalogs().put(catalogId, newCatalog); //записываем новый каталог в главный каталог
        CatalogId.getInstance().setNewId(); //обновление id на следующее использование
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        //обновление БД от имени администратора вызвавшего метод
        String databaseLogin = this.getName().toLowerCase();
        String databasePassword = this.getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM CATALOG CREATION"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM CATALOG CREATION"); //информация об успешно установленном соединении
                Statement statement = connection.createStatement();
                ///создание таблицы для хранения каталогов///
                String sql = "create table if not exists catalogs(id integer primary key,\n" + //если таблица с указанным именем не сушествует, создать ее
                        "name varchar(30) not null \n);";
                statement.execute(sql);

                //запись каталога в таблицу///
                String sql2 = "INSERT INTO catalogs (id, name) VALUES (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setInt(1, newCatalog.getId());
                preparedStatement.setString(2, newCatalog.getName());
                preparedStatement.executeUpdate();

                ///создание таблицы для хранения медиапродуктов из каталога///
                String catalogNameForDB = catalogName.replace(" ", "");
                catalogNameForDB = catalogNameForDB.replace("'", "");
                String tableName = "mediaproductsfrom" + catalogNameForDB; //удобоваримое название таблицы в БД
                String sql3 = "create table if not exists " + tableName + "(id integer primary key,\n" + //если таблица с указанным именем не сушествует, создать ее
                        "title varchar(50) not null,\n" +
                        "release integer not null,\n" +
                        "creator varchar(50) not null,\n" +
                        "rating varchar(30) not null,\n" +
                        "runtime varchar(30) not null,\n" +
                        "metascore double precision not null,\n" +
                        "userscore double precision not null,\n" +
                        //"catalog varchar(50) not null,\n" +
                        //"mediaproducts" + catalogNameForDB + "_fk FOREIGN KEY (catalog) REFERENCES catalogs (name) ON DELETE CASCADE OR UPDATE\n);";
                        "catalog varchar(50) not null \n);";
                statement.execute(sql3);

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
        CatalogDao catalogDao = new CatalogDao();
        catalogDao.save(newCatalog); //запись каталога в БД с помощью hibernate
    }

    @Override
    public void deleteMediaProduct(MediaProduct targetMediaProduct) { //реализации метода удаления медиапродукта
        int targetId = -1; //id медиапродукта, который нужно удалить
        String targetCatalog = null; //каталог в котором находится удаляемый медиапродукт
        for (Catalog catalog : GeneralCatalog.getInstance().getCatalogs().values()) { //проходимся по всем каталогам
            for (MediaProduct mediaProduct : catalog.getMediaProducts().values()) { //проходимся по текущему каталогу
                if (mediaProduct.getId() == targetMediaProduct.getId()){ //если нашли совпадение по id
                    targetId = mediaProduct.getId(); //обновляем id удаляемого медиапродукта
                    targetCatalog = catalog.getName(); //обновляем каталог в котором находится удаляемый медиапродукт
                    break; //заканчиваем поиск
                }
            }
        }
        //////HIBERNATE//////
        MediaProductDao mediaProductDao = new MediaProductDao();
        mediaProductDao.delete(GeneralCatalog.getInstance().requestById(targetId).getResult().get(0)); //удаление медиапродукта из БД с помощью hibernate

        //  if (targetId >= 0 && targetCatalog != null){ //если медиапродукт для удаления был найден
            GeneralCatalog.getInstance().requestCatalog(targetCatalog).getMediaProducts().remove(targetId); //удаляем его из каталога
       // } else {
         //   logger.info("CAN'T FIND MEDIAPRODUCT FOR REMOVING"); //иначе сообщаем о невозможности удаления несуществующего медиапродукта
       // }

        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        //обновление БД от имени администратора вызвавшего метод
        String databaseLogin = this.getName().toLowerCase();
        String databasePassword = this.getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM MEDIAPRODUCT REMOVING"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM MEDIAPRODUCT REMOVING"); //информация об успешно установленном соединении
                //запись каталога в таблицу///
                String catalogNameForDB = targetCatalog.replace(" ", "");
                catalogNameForDB = catalogNameForDB.replace("'", "");
                String sql = "DELETE FROM mediaproductsfrom" + catalogNameForDB + " WHERE id = (?)"; //удаление медиапродукта из каталога
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, targetId);
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
    }

    @Override
    public void login() {

    }

    @Override
    public void authentification() {

    }

    @Override
    public void validInputData() {

    }

    @Override
    public void createNewUser(RegisteredUser registeredUser) { //реализация метода по добавлению новых пользователей
        UserCatalog.getInstance().getUsers().put(registeredUser.getId(), registeredUser); //записываем нового пользователя в каталог пользователей
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        //обновление БД от имени администратора вызвавшего метод
        String databaseLogin = this.getName().toLowerCase();
        String databasePassword = this.getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM USER CREATION"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM USER CREATION"); //информация об успешно установленном соединении
                Statement statement = connection.createStatement();
                //создание таблицы для хранения пользователей///
                String sql = "create table if not exists users(id integer primary key,\n" + //если таблица с указанным именем не сушествует, создать ее
                        "name varchar(30) not null,\n" +
                        "email varchar(30) not null,\n" +
                        "password varchar(30) not null,\n" +
                        "acclaimedCritic boolean not null,\n" +
                        "website varchar(30) not null \n);";
                statement.execute(sql);

                //запись пользователя в таблицу///
                String sql2 = "INSERT INTO users (id, name, email, password, acclaimedCritic, website) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql2);
                preparedStatement.setInt(1, registeredUser.getId());
                preparedStatement.setString(2, registeredUser.getName());
                preparedStatement.setString(3, registeredUser.getEmail());
                preparedStatement.setString(4, registeredUser.getPassword());
                preparedStatement.setBoolean(5, registeredUser.isAcclaimedCritic());
                preparedStatement.setString(6, registeredUser.getWebsite());
                preparedStatement.executeUpdate();
            }
        }
        catch (DatabaseException de) {
            System.out.println(de.getMessage());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        finally {
            databaseFacade.disconnect(); //закрытие соединения с БД
        }
        //////HIBERNATE//////
        RegisteredUserDao registeredUserDao = new RegisteredUserDao();
        registeredUserDao.save(registeredUser); //запись пользователя в БД с помощью hibernate
    }

    public void assignDBRole(Administrator assigned, String dbName){ //создание роли в БД
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        //создание роли от имени администратора вызвавшего метод
        String databaseLogin = this.getName().toLowerCase();
        String databasePassword = this.getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM DB ROLE ASSIGNMENT"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM FROM DB ROLE ASSIGNMENT"); //информация об успешно установленном соединении
                Statement statement = connection.createStatement();
                //создание таблицы для хранения пользователей///
                String sql = "create user "+ assigned.getName() + " with password '" + assigned.getPassword() + "';"; //создание роли в БД
                statement.execute(sql);
                String sql2 = "grant all on database " + dbName + " to " + assigned.getName() + ";"; //назначение прав на БД
            }
        }
        catch (DatabaseException de) {
            System.out.println(de.getMessage());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        finally {
            databaseFacade.disconnect(); //закрытие соединения с БД
        }
    }
    public void dropDBAssignment(Administrator target){ //удаление роли в БД
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        //удаление роли от имени суперадминистратора вызвавшего метод
        String databaseLogin = this.getName().toLowerCase();
        String databasePassword = this.getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM DROP DB ASSIGNMENT"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM DROP DB ROLE ASSIGNMENT"); //информация об успешно установленном соединении
                Statement statement = connection.createStatement();
                //создание таблицы для хранения пользователей///
                String sql = "REASSIGN OWNED BY " + target.getName() + " TO postgres;"; //переназначение прав на БД
                statement.execute(sql);
                String sql2 = "DROP OWNED BY " + target.getName() + ";"; //удаление назначения на БД
                statement.execute(sql2);
                String sql3 = "DROP USER IF EXISTS " + target.getName() + ";"; //удаление роли
                statement.execute(sql3);
            }
        }
        catch (DatabaseException de) {
            System.out.println(de.getMessage());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
        finally {
            databaseFacade.disconnect(); //закрытие соединения с БД
        }
    }
}
