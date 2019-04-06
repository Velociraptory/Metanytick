package service;


import entity.MediaProductRequestResult;

public interface Requesting {
    public MediaProductRequestResult requestById(int id); //запросить по id
    public MediaProductRequestResult requestByTitle(String title); //запросить по навзанию
    public MediaProductRequestResult requestByRelease(int release); //запросить по дате выпуска
    public MediaProductRequestResult requestByMetascore(double metascore); //запросить по оценке критика
    public MediaProductRequestResult requestByUserscore(double userscore); //запросить по оценке обычного пользователя
    public MediaProductRequestResult requestByCreator(String creator); //запросить по создателю
    public MediaProductRequestResult requestByRating(String rating); //запросить по возрастному рейтингу
    public MediaProductRequestResult requestAll(); //запросить все медиапродукты из каталога
}
