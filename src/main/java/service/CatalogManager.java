package service;

import entity.Catalog;
import entity.MediaProduct;

public interface CatalogManager {
    public void addCatalog(String catalogName); //добавить каталог
    public void addMediaProduct(Catalog catalog, String title, int release, String creator, String rating, String runtime); //добавить медиапродукт в каталог
    public void addMediaProductFromFile(Catalog catalog, String fileName); //добавить медиапродукт в каталог используя данные из файла
    public void deleteMediaProduct(MediaProduct targetMediaProduct); //удалить медиапродукт
    public void addCriticReviewsFromFile(String fileName);
    public void addUserReviewsFromFile(String fileName);
}
