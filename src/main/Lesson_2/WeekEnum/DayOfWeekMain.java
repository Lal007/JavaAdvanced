package main.Lesson_2.WeekEnum;

public class DayOfWeekMain {

   public static int getWorkingHours(DayOfWeek dof) {
        switch (dof.getDayOfWeek()){
            case(1): return 40;
            case (2): return 32;
            case(3): return 24;
            case(4): return 16;
            case(5): return 8;
            default: return 0;
        }
   }

    public static void main(String[] args) {
        System.out.println(getWorkingHours(DayOfWeek.MONDAY));
    }


}
