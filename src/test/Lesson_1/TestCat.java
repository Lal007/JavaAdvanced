package test.Lesson_1;


import main.Lesson_1.Competitors.Cat;
import main.Lesson_1.Competitors.Competitor;
import main.Lesson_1.Competitors.Dog;
import main.Lesson_1.Competitors.Human;
import main.Lesson_1.Course;
import main.Lesson_1.Obstacles.Cross;
import main.Lesson_1.Obstacles.Wall;
import main.Lesson_1.Obstacles.Water;
import main.Lesson_1.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

public class TestCat {

    private static Team team;

    @BeforeAll
    static void init(){
        Course c = new Course(new Cross(80), new Wall(2), new Water(15), new Cross(120));
        team = new Team("1", new Human("Боб"), new Cat("Барсик"), new Dog("Бобик"));
        c.doIt(team);
    }

    @Test
    void test(){
        List<Competitor> finishers = team.getCompetitorsList()
                .stream()
                .filter(Competitor::isOnDistance)
                .collect(Collectors.toList());
        System.out.println(finishers.size());
        Assertions.assertEquals(3, finishers.size());
    }

}
