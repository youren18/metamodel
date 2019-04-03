package sx;

public class Stu {
    private int id;
    private int classes;
    private String name;

    public Stu(){
        id = 1;
        classes = 10;
        name = "hello";
        System.out.println("stu");
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

    public int getClasses() {
        return classes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClasses(int classes) {
        this.classes = classes;
    }
}
