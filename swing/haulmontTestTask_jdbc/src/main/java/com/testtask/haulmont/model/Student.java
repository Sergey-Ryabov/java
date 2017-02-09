package com.testtask.haulmont.model;

import java.sql.Date;

/**
 *
 * @author Сергей
 */
public class Student {

    private long id;
    private String name;
    private String surname;
    private String patronymic;
    private Date birthday;
    private Group group;

    public Student() {
        this.group = new Group();
    }

    public Student(long id, String name, String surname, String patronymic, Date birthday, Group group) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.group = group;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name=" + name + ", surname=" + surname + ", patronymic=" + patronymic + ", birthday=" + birthday + ", group=" + group.toString() + '}';
    }

}
