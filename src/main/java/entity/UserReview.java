package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Arrays;

@Entity
@Table(name = "userreviews_hibernate")
public class UserReview extends Review{
    @Column(name = "userscore")
    private double userscore; //оценка обычного пользователя
    @Transient
    private int [] helpful; //коэффициент полезности рецензии

    public UserReview() {
    }

    public UserReview(int id, String reviewText, RegisteredUser author, double userscore, int[] helpful) {
        super(id, reviewText, author);
        this.userscore = userscore;
        this.helpful = helpful;
    }

    public double getUserscore() {
        return userscore;
    }

    public int[] getHelpful() {
        return helpful;
    }

    @Override
    public String toString() {
        return "UserReview{" +
                "id=" + getId() +
                ", reviewText='" + getReviewText() + '\'' +
                ", author=" + getAuthor() +
                " userscore=" + userscore +
                ", helpful=" + Arrays.toString(helpful) +
                '}';
    }
}
