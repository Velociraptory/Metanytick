package entity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileReader {
    public static ArrayList<String> readFromFile(String fileName){ //метод чтения строк из файла
        BufferedReader reader = null;
        try {
            reader = new BufferedReader( new java.io.FileReader(fileName) ); //читаем данные из файла
        } catch (
                FileNotFoundException e) { //если файл не найден, кидаем исключение
            e.printStackTrace();
        }
        String line;
        ArrayList<String> lines = new ArrayList<String>(); //лист для хранения прочитанных строк
        try {
            while ((line = reader.readLine()) != null) { //читаем строки пока есть что читать
                lines.add(line); //добавляем прочитанную строку в лист
            }
        }   catch (
                IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
