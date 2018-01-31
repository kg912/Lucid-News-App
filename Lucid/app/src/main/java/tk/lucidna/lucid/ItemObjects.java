package tk.lucidna.lucid;

/**
 * Created by user on 4/12/2016.
 */
public class ItemObjects {
    private String name;
    private int res;
    private int id;
    public ItemObjects(int id, int res) {
        this.id = id;
        this.res = res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
