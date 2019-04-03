package sx;

public class User
{
    private int id;
    private String name;
    public User(){
        id = 0;
        name = "xiaoming";
        System.out.println("user");
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
