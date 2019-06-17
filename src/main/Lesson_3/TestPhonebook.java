package main.Lesson_3;

public class TestPhonebook {
    public static void main(String[] args) {
        Phonebook myPhonebook = new Phonebook();

        myPhonebook.add("Сидоров", "+7(999)867-34-21");
        myPhonebook.add("Сидоров", "+7(812)834-36-28");
        myPhonebook.add("Иванов", "+7(931)832-75-76");

        myPhonebook.get("Иванов");
        myPhonebook.get("Сидоров");
    }
}
