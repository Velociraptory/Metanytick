package entity;

import javax.persistence.*;

@Entity
@DiscriminatorValue("RegisteredUser")
public class RegisteredUser extends Visitor{
    @Column(name = "name")
    private String name; //имя пользователя
    @Column(name = "email")
    private String email; //электронная почта пользователя
    @Column(name = "password")
    private String password; //пароль пользователя
    @Column(name = "acclaimedCritic")
    private boolean acclaimedCritic; //флаг сообщающий является ли пользователь критиком
    @Column(name = "website")
    private String website; //личный сайт

    public RegisteredUser() {
    }

    public RegisteredUser(int id, String name, String email, String password, boolean acclaimedCritic, String website) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.acclaimedCritic = acclaimedCritic;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAcclaimedCritic() {
        return acclaimedCritic;
    }

    public String getWebsite() {
        return website;
    }

    public void share(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        RegisteredUser that = (RegisteredUser) o;
        return (super.getId() == that.getId() && name.equals(that.name));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + super.getId();
        return result;
    }

    @Override
    public String toString() {
        return "RegisteredUser{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", acclaimedCritic=" + acclaimedCritic +
                ", website=" + website +
                '}';
    }

}
