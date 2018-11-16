package ohtu;

import java.util.ArrayList;

public class Submission {
    private int week;
    private int hours;
    private int[] exercises;
    private String course;
    
    public void setWeek(int week) {
        this.week = week;
    }

    public int getWeek() {
        return week;
    }
    
    public void setHours(int hours) {
        this.hours = hours;
    }
    
    public int getHours() {
        return this.hours;
    }
    
    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }
    
    public int[] getExercises() {
        return this.exercises;
    }
    
    public void setCourse(String course) {
        this.course = course;
    }
    
    public String getCourse() {
        return this.course;
    }
    
    @Override
    public String toString() {
        return ""+week;
    }
    
}
