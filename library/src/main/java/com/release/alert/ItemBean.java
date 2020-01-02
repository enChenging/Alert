package com.release.alert;

/**
 * @author Mr.release
 * @create 2019-12-30
 * @Describe
 */
public class ItemBean {
    String name;
    int color;

    public ItemBean(String name, int color) {
        this.name = name;
        this.color = color;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
