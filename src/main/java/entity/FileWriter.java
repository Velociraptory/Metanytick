package entity;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class FileWriter {
    public static void writeToFile(ArrayList<String> lines, String fileName){ //метод записи строк в файл
        try(BufferedWriter writer = new BufferedWriter( new java.io.FileWriter(fileName) )){ //записываем данные в файл
            for(String line : lines){ //проходимся по списку
                writer.write(line + "\r\n"); //записываем строку в файл
            }
        } catch (FileNotFoundException e) { //если файл не найден, кидаем исключение
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
