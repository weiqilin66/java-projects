package com.stream.entity;

/**
 * @Description:
 * @author: lwq
 */
public class Student {
    private int id;
    private String name;
    private int age;

    public Student(){

    }
    public Student(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getAge() {
        return age;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        final Student std = (Student) obj;
        if (this == std) {
            return true;
        } else {
            return (this.name.equals(std.name) && (this.age == std.age));
        }
    }
    @Override
    public int hashCode() {
        int hashno = 7;
        hashno = 13 * hashno + (name == null ? 0 : name.hashCode());
        return hashno;
    }

    public void setName(String name) {
        this.name = name;
    }
}
