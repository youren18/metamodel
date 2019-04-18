package entity;

import annotation.Column;

public class User
{
    @Column("ids")
    private int id;
    @Column("name")
    private String name;
    public User(){

    }

    public String toString(){
        return "User:{ id = " + id + " , name = " + name + " }";
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
