package main.Lesson_2.Arr;

public class MyArraySizeException extends Exception {

    public MyArraySizeException(int x, int y) {
        super("Размер массива должен быть 4х4!");

    }
}
