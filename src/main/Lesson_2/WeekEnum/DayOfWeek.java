package main.Lesson_2.WeekEnum;

enum  DayOfWeek {
    MONDAY(1),
    TUESDAY(2),
    WEDNSDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);


    private int dayOfWeek;

    DayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }
}
