package main.Lesson_2.Arr;

public class ArraySum {


    public static void main(String[] args) {

        //String[][] arr = {{"1", "2", "3", "4"}, {"5", "6", "7", "8"}, {"9", "10", "11", "12"}, {"13", "14", "15", "16"}};
        String[][] arr = {{"1", "2", "3"}, {"5", "6", "7", "8"}, {"9", "10", "11", "12"}};
        //String[][] arr = {{"1", "2", "3", "4"}, {"5", "6", "7", "*"}, {"9", "10", "11", "12"}, {"13", "14", "15", "16"}};
        try {
            new ArraySum().arraySum(arr);
        } catch (MyArraySizeException | MyArrayDataException e) {
            e.printStackTrace();
        }
    }

    public void arraySum(String[][] arr) throws MyArraySizeException, MyArrayDataException {
        int x = arr.length;
        //if (!(x == 4 && y == 4)) throw new MyArraySizeException(x, y);
        for (int i = 0; i < arr.length; i++){
            if (!(x == 4 && arr[i].length == 4)) throw new MyArraySizeException(x, i);
        }
        int sum = 0;

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                try {
                    sum += Integer.parseInt(arr[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j);
                }
            }
        }

        System.out.println("Сумма всех элементов массива = " + sum);
    }
}