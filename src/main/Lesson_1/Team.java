package main.Lesson_1;

import main.Lesson_1.Competitors.Competitor;
import java.util.ArrayList;
import java.util.Arrays;

public class Team {
   private ArrayList<Competitor> competitorsList;
   private String name;

    public Team(String name, Competitor... competitors) {
        this.name = name;
        competitorsList = new ArrayList<>(Arrays.asList(competitors));
    }


    public ArrayList<Competitor> getCompetitorsList() {
        return new ArrayList<>(competitorsList);
    }

    public void teamInfo(){
        System.out.println("Список команды " + name + ":");
        for (Competitor c:competitorsList) {
            c.info();
        }
    }

    public void showResults(){
        System.out.println("Список участников команды " + name + ", успешно прошедших дистанцию:");
        for (Competitor c:competitorsList) {
            if (c.isOnDistance()){
                c.info();
            }
        }
    }
}
