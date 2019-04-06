package entity;

import java.util.ArrayList;
import java.util.Comparator;

public class MediaProductRequestResult { //результат запроса по медиапродуктам
    private ArrayList<MediaProduct> result;

    public MediaProductRequestResult(ArrayList<MediaProduct> result) {
        this.result = result;
    }

    public ArrayList<MediaProduct> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "RequestResult{" +
                "result=" + result +
                '}';
    }

    public ArrayList<MediaProduct> sortByRelease(){ //метод сортировки результата запроса по году выпуска
        ArrayList<MediaProduct> sortedResult = result;
        sortedResult.sort(Comparator.comparing(MediaProduct::getRelease));
        return sortedResult;
    }
    public ArrayList<MediaProduct> sortByMetascore(){ //метод сортировки результата запроса по metascore
        ArrayList<MediaProduct> sortedResult = result;
        sortedResult.sort(Comparator.comparing(MediaProduct::getMetascore));
        return sortedResult;
    }
    public ArrayList<MediaProduct> sortByUserscore(){ //метод сортировки результата запроса по userscore
        ArrayList<MediaProduct> sortedResult = result;
        sortedResult.sort(Comparator.comparing(MediaProduct::getUserscore));
        return sortedResult;
    }
}
