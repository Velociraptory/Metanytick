package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "criticreviews_hibernate")
public class CriticReview  extends Review{
    @Column(name = "metascore")
    private double metascore; //оценка критика
    @Column(name = "fullPublication")
    private String fullPublication; //ссылка на полную публикацию рецензии

    public CriticReview() {
    }

    public CriticReview(int id, String reviewText, RegisteredUser author, double metascore, String fullPublication) {
        super(id, reviewText, author);
        this.metascore = metascore;
        this.fullPublication = fullPublication;
    }

    public double getMetascore() {
        return metascore;
    }

    public String getFullPublication() {
        return fullPublication;
    }

    @Override
    public String toString() {
        return "CriticReview{" +
                "id=" + getId() +
                ", reviewText='" + getReviewText() + '\'' +
                ", author=" + getAuthor() +
                " metascore=" + metascore +
                ", fullPublication='" + fullPublication + '\'' +
                '}';
    }
}
