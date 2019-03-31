package entity;

import service.Requesting;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

@Entity
@Table (name = "catalogs_hibernate")
public class Catalog implements Requesting { //каталог для хранения медиапродуктов

    @Id
    @Column(name = "id")
    private int id; //id каталога
    @Column(name = "name")
    private String name; //имя каталога
    @Transient
    private HashMap<Integer, MediaProduct> mediaProducts; //медиапродукты хранящиеся в каталоге

    public Catalog() {
    }

    public Catalog(int id, String name, HashMap<Integer, MediaProduct> mediaProducts) {
        this.id = id;
        this.name = name;
        this.mediaProducts = mediaProducts;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public HashMap<Integer, MediaProduct> getMediaProducts() {
        return mediaProducts;
    }


    @Override
    public String toString() {
        return "\n" +
                "Catalog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", mediaProducts=" + mediaProducts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Catalog that = (Catalog) o;
        return (id == that.id && name.equals(that.name));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode();
        result = prime * result + id;
        return result;
    }

    public MediaProductRequestResult requestAll() { //запрос всех медиапродуктов из каталога
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (MediaProduct mediaProduct : mediaProducts.values()) { //проходимся по каталогу
                result.add(mediaProduct); //добавляем каждый медиапродукт в результат запроса
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestById(int id) { //запрос медиапродукта по id
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (MediaProduct mediaProduct : mediaProducts.values()) { //проходимся по каталогу
            if(mediaProduct.getId() == id){ //если нашли совпадение по id
                result.add(mediaProduct); //добавляем медиапродукт в результат запроса
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestByTitle(String title) { //запрос медиапродукта по названию
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (MediaProduct mediaProduct : mediaProducts.values()) { //проходимся по каталогу
            if(mediaProduct.getTitle().equals(title)){ //если нашли совпадение по названию
                result.add(mediaProduct); //добавляем медиапродукт в результат запроса
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestByRelease(int release) { //запроса медиапродукта по дате выпуска
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (MediaProduct mediaProduct : mediaProducts.values()) { //проходимся по каталогу
            if(mediaProduct.getRelease() == release){ //если нашли совпадение по дате выпуска
                result.add(mediaProduct); //добавляем медиапродукт в результат запроса
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestByMetascore(double metascore) { //запрос медиапродукта по metascore
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (MediaProduct mediaProduct : mediaProducts.values()) { //проходимся по каталогу
            if(mediaProduct.getMetascore() == metascore){ //если нашли совпадение по metascore
                result.add(mediaProduct); //добавляем медиапродукт в результат запроса
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestByUserscore(double userscore) { //запрос медиапродукта по userscore
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (MediaProduct mediaProduct : mediaProducts.values()) { //проходимся по каталогу
            if(mediaProduct.getUserscore() == userscore){ //если нашли совпадение по userscore
                result.add(mediaProduct); //добавляем медиапродукт в результат запроса
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestByCreator(String creator) { //запрос медиапродукта по создателю
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (MediaProduct mediaProduct : mediaProducts.values()) { //проходимся по каталогу
            if(mediaProduct.getCreator().equals(creator)){ //если нашли совпадение по создателю
                result.add(mediaProduct); //добавляем медиапродукт в результат запроса
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestByRating(String rating) { //запрос медиапродукта по возрастному рейтингу
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (MediaProduct mediaProduct : mediaProducts.values()) { //проходимся по каталогу
            if(mediaProduct.getRating().equals(rating)){ //если нашли совпадение по возрастному рейтингу
                result.add(mediaProduct); //добавляем медиапродукт в результат запроса
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }


}
