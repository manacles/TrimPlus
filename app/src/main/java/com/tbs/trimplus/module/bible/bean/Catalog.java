package com.tbs.trimplus.module.bible.bean;

public class Catalog {

    /**
     * id : 1
     * name : 设计
     * selected : true
     */

    private String id;
    private String name;
    private boolean selected = true;

    public Catalog() {
    }

    public Catalog(String id, String name, boolean selected) {
        this.id = id;
        this.name = name;
        this.selected = selected;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
