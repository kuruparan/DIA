package org.yarlithub.dia.repo.object;

/**
 * Project YIT DIA
 * Created by jaykrish on 5/25/14.
 */
public class Garden {
    private int id;
    private String garden_name;
    private String password;

    public Garden() {
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGarden_name() {
        return garden_name;
    }

    public void setGarden_name(String garden_name) {
        this.garden_name = garden_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
