package main;

import main.Lesson_2.Arr.ArraySum;
import main.Lesson_2.Arr.MyArrayDataException;
import main.Lesson_2.Arr.MyArraySizeException;
import org.junit.jupiter.api.Test;

public class TestLesson_2 {
    static String[][] arr = {{"1", "2", "3", "4"}, {"5", "6", "7", "8"}, {"9", "10", "11", "12"}, {"13", "14", "15", "16"}};
    static ArraySum sum = new ArraySum();
    @Test
    static void test() throws MyArrayDataException, MyArraySizeException{

        //Assertions.assertEquals(136, sum.arraySum(arr));
    }

}
