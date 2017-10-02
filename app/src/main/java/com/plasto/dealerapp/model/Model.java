package com.plasto.dealerapp.model;

/**
 * Created by Pat-Win 10 on 31-01-2017.
 */

public class Model {
    String id, name;

    public Model(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
