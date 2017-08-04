package com.keruis.cilent.test;


import java.sql.Timestamp;

/**
 * Created by Administrator on 2017/4/7.
 */

public class User implements Comparable<User> {

    private String name;
    private int score;

    private int age;

    private Timestamp time;

    public User(String name, int score, int age) {
        this.name = name;
        this.score = score;
        this.age = age;
    }

    public User(String name, int score, int age, Timestamp time) {
        this.name = name;
        this.score = score;
        this.age = age;
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int compareTo(User o) {

//         int i =this.getName().length()-o.getName().length();
//        if(i==0){
//            i= this.getName().compareTo(o.getName());
//        }
//
//        return i;
        return (int) this.getTime().getTime() - (int) o.getTime().getTime();

    }


}
