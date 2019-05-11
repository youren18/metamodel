package entity;



public class User
{
    private int ids;

    private String name;
    public User(){

    }

    public User(int id, String name){
        this.ids = id;
        this.name = name;
    }

    public String toString(){
        return "User:{ id = " + ids + " , name = " + name + " }";
    }

    public int getIds() {
        return ids;
    }

    public String getName() {
        return name;
    }

    public void setIds(int ids) {
        this.ids = ids;
    }

    public void setName(String name) {
        this.name = name;
    }
}
