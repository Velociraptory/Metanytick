package entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.Requesting;

import java.util.ArrayList;
import java.util.HashMap;

public class GeneralCatalog implements Requesting {
    private final Logger logger = LogManager.getLogger(Administrator.class); //логгер
    private static GeneralCatalog instance;
    private HashMap<Integer, Catalog> catalogs;

    private GeneralCatalog(HashMap<Integer, Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    public HashMap<Integer, Catalog> getCatalogs() {
        return catalogs;
    }

    public static GeneralCatalog getInstance(){
        if(instance == null){
            HashMap<Integer, Catalog> catalogs = new HashMap<Integer, Catalog>();
            instance = new GeneralCatalog(catalogs);
        }
        return instance;
    }

    public Catalog requestCatalog(String catalogName) { //метод запроса каталога по его имени
        for (Catalog catalog : catalogs.values()) { //проходимся по каталогам
            if(catalog.getName().equals(catalogName)){ //если нашли совпадение по названию
                return catalog; //возвращаем найденный каталог
            }
        }
        //logger.info("CAN'T FIND CATALOG"); //сообщение о невозможности найти указанный каталог
        return null;
    }
    public MediaProductRequestResult requestAll() { //метод запроса всех медиапродуктов из всех каталогов
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (Catalog catalog : catalogs.values()) { //проходимся по всем каталогам
            for (MediaProduct mediaProduct : catalog.getMediaProducts().values()) { //проходимся по текущему каталогу
                    result.add(mediaProduct); //добавляем каждый медиапродукт в результат запроса
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }



    @Override
    public String toString() {
        return "GeneralCatalog{" +
                "catalogs=" + catalogs +
                '}';
    }

    public MediaProductRequestResult requestById(int id) { //запрос медиапродуктов по id
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (Catalog catalog : catalogs.values()) { //проходимся по всем каталогам
            for (MediaProduct mediaProduct : catalog.getMediaProducts().values()) { //проходимся по текущему каталогу
                if(mediaProduct.getId() == id){ //если нашли совпадение по id
                    result.add(mediaProduct); //добавляем медиапродукт в результат запроса
                }
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestByTitle(String title) { //запрос медиародуктов по названию
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (Catalog catalog : catalogs.values()) { //проходимся по всем каталогам
            for (MediaProduct mediaProduct : catalog.getMediaProducts().values()) { //проходимся по текущему каталогу
                if(mediaProduct.getTitle().equals(title)){ //если нашли совпадение по названию
                    result.add(mediaProduct); //добавляем медиапродукт в результат запроса
                }
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestByRelease(int release) { //запрос медиапродуктов по дате выпуска
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (Catalog catalog : catalogs.values()) { //проходимся по всем каталогам
            for (MediaProduct mediaProduct : catalog.getMediaProducts().values()) { //проходимся по текущему каталогу
                if(mediaProduct.getRelease() == release){ //если нашли совпадение по дате выпуска
                    result.add(mediaProduct); //добавляем медиапродукт в результат запроса
                }
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestByMetascore(double metascore) { //запрос медиапродуктов по metascore
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (Catalog catalog : catalogs.values()) { //проходимся по всем каталогам
            for (MediaProduct mediaProduct : catalog.getMediaProducts().values()) { //проходимся по текущему каталогу
                if(mediaProduct.getMetascore() == metascore){ //если нашли совпадение по metascore
                    result.add(mediaProduct); //добавляем медиапродукт в результат запроса
                }
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestByUserscore(double userscore) { //запрос медиапродуктов по userscore
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (Catalog catalog : catalogs.values()) { //проходимся по всем каталогам
            for (MediaProduct mediaProduct : catalog.getMediaProducts().values()) { //проходимся по текущему каталогу
                if(mediaProduct.getUserscore() == userscore){ //если нашли совпадение по userscore
                    result.add(mediaProduct); //добавляем медиапродукт в результат запроса
                }
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestByCreator(String creator) { //запрос медиапродуктов по создателю
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (Catalog catalog : catalogs.values()) { //проходимся по всем каталогам
            for (MediaProduct mediaProduct : catalog.getMediaProducts().values()) { //проходимся по текущему каталогу
                if(mediaProduct.getCreator().equals(creator)){ //если нашли совпадение по создателю
                    result.add(mediaProduct); //добавляем медиапродукт в результат запроса
                }
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }

    public MediaProductRequestResult requestByRating(String rating) { //запрос медиапродуктов по возрастному рейтингу
        ArrayList<MediaProduct> result = new ArrayList<MediaProduct>(); //лист для хранения результата запроса
        MediaProductRequestResult mediaProductRequestResult = new MediaProductRequestResult(result); //создаем объект результата запроса
        for (Catalog catalog : catalogs.values()) { //проходимся по всем каталогам
            for (MediaProduct mediaProduct : catalog.getMediaProducts().values()) { //проходимся по текущему каталогу
                if(mediaProduct.getRating().equals(rating)){ //если нашли совпадение по возрастному рейтингу
                    result.add(mediaProduct); //добавляем медиапродукт в результат запроса
                }
            }
        }
        return mediaProductRequestResult; //возвращаем результат запроса
    }
}
