package entity;

import javax.persistence.*;

@Entity
@Table(name = "users_hibernate")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
public class Visitor {
    @Id
    @Column(name = "id")
    private int id; //id посетителя

    public Visitor() {
    }

    public Visitor(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void getRegistered(){ //отсутствие реализации метода регистрации пользователя

    }
}
