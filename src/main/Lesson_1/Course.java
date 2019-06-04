package main.Lesson_1;

import main.Lesson_1.Competitors.Competitor;
import main.Lesson_1.Obstacles.Obstacle;

import java.util.ArrayList;
import java.util.Arrays;

 class Course {

    private ArrayList<Obstacle> obstacleCourse;


    Course(Obstacle... obstacles) {
        obstacleCourse = new ArrayList<>(Arrays.asList(obstacles));
    }

    void doIt(Team team){
        for (Competitor c:team.getCompetitorsList()) {
            for (Obstacle o:obstacleCourse) {
                o.doIt(c);
            }
        }
    }
}
