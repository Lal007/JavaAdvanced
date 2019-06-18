package test.Lesson_5;

import main.Lesson_5.ThreadTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestLesson5 {

    @Test
    void test(){
        ThreadTest.calculationOne();
        ThreadTest.calculationTwo();
        Assertions.assertEquals(10000000, ThreadTest.single.length);
        Assertions.assertEquals(10000000, ThreadTest.multi.length);
        Assertions.assertArrayEquals(ThreadTest.single, ThreadTest.multi);
    }
}
