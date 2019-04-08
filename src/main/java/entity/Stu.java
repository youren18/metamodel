package entity;

import annotation.Column;
import annotation.Table;

@Table(tableName = "stu")
public class Stu {

    @Column(columnValue = "id")
    private int id;

    @Column(columnValue = "name")
    private String name;

    @Column(columnValue = "age")
    private int age;

    @Override
    public String toString(){
        return "stu { " +
                "id = " + id +
                " name = " + name +
                " age = " + age +
                " }";
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
