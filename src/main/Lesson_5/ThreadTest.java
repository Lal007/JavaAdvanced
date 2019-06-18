package main.Lesson_5;

import java.util.ArrayList;
import java.util.Arrays;

public class ThreadTest {
    static final int COUNT_THREAD = 2;
    static final int ARR_SIZE = 10000000;
    static final int THREAD_ARR_SIZE = ARR_SIZE / COUNT_THREAD;


    public static void main(String[] args) {
        calculationOne();
        calculationTwo();
    }

    public static void calculate(float[] arr, int iteration) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + iteration / 5) * Math.cos(0.2f + iteration / 5) * Math.cos(0.4f + iteration / 2));
            iteration++;
        }
    }

    public static void calculationOne(){
        float[] arr = new float[ARR_SIZE];

        Arrays.fill(arr, 1);

        long a = System.currentTimeMillis();

        calculate(arr, 0);

        long b = System.currentTimeMillis();

        //System.out.println(Arrays.toString(arr));
        System.out.println("Первый метод завершил выполнение за: " + (b - a)/1000.0 + " сек");
    }

    public static void calculationTwo(){
        float[] arr = new float[ARR_SIZE];
        Arrays.fill(arr, 1);

        ArrayList<float[]> arrays = new ArrayList<>();
        ArrayList<CalcThread> treads = new ArrayList<>();

        for (int i = 0; i < COUNT_THREAD; i++) {
            arrays.add(new float[THREAD_ARR_SIZE]);
        }

        long a = System.currentTimeMillis();

        for (int i = 0; i < arrays.size(); i++) {
            System.arraycopy(arr, (i * THREAD_ARR_SIZE), arrays.get(i),0, THREAD_ARR_SIZE);
        }

        for (int i = 0; i <arrays.size(); i++) {
            treads.add(new CalcThread(arrays.get(i), i));
        }

        for (CalcThread ct:treads) {
            ct.start();
        }

        for (CalcThread ct:treads) {
            try {
                ct.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int i = 0; i < arrays.size(); i++) {
            System.arraycopy(arrays.get(i),0, arr,(i * THREAD_ARR_SIZE), THREAD_ARR_SIZE);
        }

        //System.out.println(Arrays.toString(arr));

        long b = System.currentTimeMillis();
        System.out.println("Второй метод завершил выполнение за: " + (b - a)/1000.0 + " сек");

    }


    public static class CalcThread extends Thread{

        float[] arr;
        int iteration;

        public CalcThread(float[] arr, int number) {
            this.arr = arr;
            this.iteration = number * arr.length;
        }

        @Override
        public void run() {
            calculate(arr, iteration);
        }
    }
}


