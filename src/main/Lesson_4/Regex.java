package main.Lesson_4;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        System.out.println(s);

        Pattern p = Pattern.compile("^(?!.*[А-Я])(?!.*[а-я])(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^\\w\\s]).{8,}");
        Matcher matcher = p.matcher(s);
        System.out.println(matcher.find());


    }
}
