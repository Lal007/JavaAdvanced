package main.Lesson_3;

import java.util.HashMap;
import java.util.HashSet;

public class Phonebook {
    private static HashMap<String, HashSet<String>> phonebook = new HashMap<>();

    public void add(String surname, String number) {
        HashSet<String> numbers = phonebook.getOrDefault(surname, new HashSet<>());
        numbers.add(number);
        phonebook.put(surname, numbers);

    }

    public void get(String surname){
        HashSet<String> numbers = phonebook.get(surname);
        System.out.println("Номер пользователя " + surname + ": ");
        for (String number:numbers) {
            System.out.println(number);
        }
    }
}
