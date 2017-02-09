package com.testtask.haulmont.model;

/**
 *
 * @author Сергей
 */
public class Group {

    private long id;
    private int number;
    private String facultyName;

    public Group() {
    }

    public Group(long id, int number, String facultyName) {
        this.id = id;
        this.number = number;
        this.facultyName = facultyName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    @Override
    public String toString() {
        return "Group{" + "id=" + id + ", number=" + number + ", facultyName=" + facultyName + '}';
    }
    
    

}
