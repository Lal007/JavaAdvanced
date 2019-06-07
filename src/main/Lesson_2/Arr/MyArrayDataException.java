package main.Lesson_2.Arr;

public class MyArrayDataException extends Exception {
    public MyArrayDataException(int x, int y) {
        super("Преобразование не удалось!, проверьте значение в ячейке: " + x + "x" + y);
    }
}
