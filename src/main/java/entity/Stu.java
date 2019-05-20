package entity;

import annotation.Column;
import annotation.Table;

@Table("stu")
public class Stu {

    @Column("id")
    private int ids;

    @Column("name")
    private String name;

    @Column("age")
    private int age;

    @Override
    public String toString(){
        return "stu { " +
                "id = " + ids +
                " name = " + name +
                " age = " + age +
                " }";
    }

    public Stu(int i, String s, int a){
        ids = i;
        name = s;
        age = a;

    }
    public Stu(){

    }

    public void setId(int id) {
        this.ids = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return ids;
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
