package entity;

import exception.DatabaseException;
import facade.DatabaseFacade;
import facade.PostgresDatabaseFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Metauser {
    private final Logger logger = LogManager.getLogger(Metauser.class); //логгер

    private static Metauser instance;
    private String name;
    private String password;


    private Metauser(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public static Metauser getInstance(){
        if(instance == null){
           String name = "";
           String password = "";
            instance = new Metauser(name, password);
        }
        return instance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void assignSuperRole(Administrator assigned, String dbName){ //создание роли суперпользователя в БД
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        //создать суперадминистратора может только метаадминистратор(создатель API)
        String databaseLogin = getName();
        String databasePassword = getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM SUPER ROLE ASSIGNMENT"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM SUPER ROLE ASSIGNMENT"); //информация об успешно установленном соединении
                Statement statement = connection.createStatement();
                //создание таблицы для хранения пользователей///
                String sql = "create user "+ assigned.getName() + " with password '" + assigned.getPassword() + "';"; //создание роли в БД
                statement.execute(sql);
                String sql2 = "ALTER USER " + assigned.getName() + " WITH SUPERUSER;"; //назначение прав суперпользователя
                statement.execute(sql2);
                String sql3 = "grant all on database " + dbName + " to " + assigned.getName() + ";"; //назначение прав на БД
                statement.execute(sql3);
                String sql4 = "ALTER USER " + assigned.getName() + " WITH CREATEROLE;"; //назначение прав на создание ролей
                statement.execute(sql4);
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

    public void dropSuperRoleAssignment(Administrator target) { //удаление роли в БД
        ///DATABASE///
        DatabaseFacade databaseFacade = new PostgresDatabaseFacade();
        //снять полномочия с суперадминистратора может только метаадминистратор(создатель API)
        String databaseLogin = getName();
        String databasePassword = getPassword();

        try {
            databaseFacade.connect(databaseLogin, databasePassword); //сеодинение с БД

            Connection connection = databaseFacade.getConnection();
            if (connection != null) {
                //System.out.println("CONNECTED FROM DROP SUPER DB ASSIGNMENT"); //информация об успешно установленном соединении
                logger.info("CONNECTED FROM DROP SUPER ROLE ASSIGNMENT"); //информация об успешно установленном соединении
                Statement statement = connection.createStatement();
                //создание таблицы для хранения пользователей///
                String sql = "REASSIGN OWNED BY " + target.getName() + " TO postgres;"; //переназначение прав на БД
                statement.execute(sql);
                String sql2 = "DROP OWNED BY " + target.getName() + ";"; //удаление назначения на БД
                statement.execute(sql2);
                String sql3 = "DROP USER IF EXISTS " + target.getName() + ";"; //удаление роли
                statement.execute(sql3);
            }
        } catch (DatabaseException de) {
            System.out.println(de.getMessage());
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        } finally {
            databaseFacade.disconnect(); //закрытие соединения с БД
        }
    }
}
