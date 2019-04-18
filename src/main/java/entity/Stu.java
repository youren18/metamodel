package entity;

import annotation.Column;
import annotation.Table;

@Table("stu")
public class Stu {

    @Column("id")
    private int id;

    @Column("name")
    private String name;

    @Column("age")
    private int age;

    @Override
    public String toString(){
        return "stu { " +
                "id = " + id +
                " name = " + name +
                " age = " + age +
                " }";
    }

    public Stu(int i, String s, int a){
        id = i;
        name = s;
        age = a;

    }
    public Stu(){

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
