package main.Lesson_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Words {

    public static void main(String[] args) {
        ArrayList<String> wordsList = new ArrayList<>();
        Map<String, Integer> countWords = new HashMap<>();

        wordsList.add("Hello");
        wordsList.add("world");
        wordsList.add("Geekbrains");
        wordsList.add("List");
        wordsList.add("Massive");
        wordsList.add("Cat");
        wordsList.add("Dog");
        wordsList.add("Window");
        wordsList.add("Hello");
        wordsList.add("Hel7lo");
        wordsList.add("Dog");
        wordsList.add("Windo1w");
        wordsList.add("Hel2lo");
        wordsList.add("Hello");
        wordsList.add("Do3g");
        wordsList.add("Do6g");
        wordsList.add("Window");
        wordsList.add("Hel4lo");
        wordsList.add("Hello");



        for (String word: wordsList) {
            countWords.merge(word, 1, Integer::sum);
        }

        System.out.println(countWords);
    }
}
