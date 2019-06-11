package main.Lesson_1;


import main.Lesson_1.Competitors.Cat;
import main.Lesson_1.Competitors.Dog;
import main.Lesson_1.Competitors.Human;
import main.Lesson_1.Obstacles.Cross;
import main.Lesson_1.Obstacles.Wall;
import main.Lesson_1.Obstacles.Water;

public class Main {
    public static void main(String[] args) {

        Course c = new Course(new Cross(80), new Wall(2), new Water(15), new Cross(120));
        Team team = new Team("1", new Human("Боб"), new Cat("Барсик"), new Dog("Бобик"));
        c.doIt(team);
        team.teamInfo();
        team.showResults();
    }
}