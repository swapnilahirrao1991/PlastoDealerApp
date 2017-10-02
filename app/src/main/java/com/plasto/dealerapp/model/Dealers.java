package com.plasto.dealerapp.model;

/**
 * Created by Pat-Win 10 on 26-01-2017.
 */

public class Dealers {
    String name;
    int id;

    public Dealers(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
