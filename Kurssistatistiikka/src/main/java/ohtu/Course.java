/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import java.util.ArrayList;

/**
 *
 * @author sami
 */
public class Course {
    private String name;
    private String fullName;
    private int[] exercises;
    private ArrayList<Submission> submissions;
    
    public Course() {
        this.submissions = new ArrayList<>();
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setFullName(String name) {
        this.fullName = name;
    }
    
    public String getFullName() {
        return this.fullName;
    }
    
    public void setExercises(int[] exercises) {
        this.exercises = exercises;
    }
    
    public int[] getExercises() {
        return this.exercises;
    }
    
    public int getMaxExercises() {
        int sum = 0;
        
        for (int ex : exercises) {
            sum += ex;
        }
        
        return sum;
    }
    
    public void addSubmission(Submission sub) {
        this.submissions.add(sub);
    }
    
    public ArrayList<Submission> getSubmissions() {
        return this.submissions;
    }
}
