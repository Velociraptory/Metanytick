package entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public class Review {
    @Id
    @Column(name = "id")
    private int id; //id рецензии
    @Column(name = "reviewText")
    private String reviewText; //текст рецензии
    @Transient
    private RegisteredUser author; //автор рецензии
    @Column(name = "author")
    String authorName; //имя автора рецензии

    public Review() {
    }

    public Review(int id, String reviewText, RegisteredUser author) {
        this.id = id;
        this.reviewText = reviewText;
        this.author = author;
        authorName = author.getName();
    }

    public int getId() {
        return id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public RegisteredUser getAuthor() {
        return author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        Review that = (Review) o;
        return (id == that.id && author.equals(that.author));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + author.hashCode();
        result = prime * result + id;
        return result;
    }

}
