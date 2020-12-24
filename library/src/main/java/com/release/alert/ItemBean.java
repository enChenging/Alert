package com.release.alert;

import android.support.annotation.ColorInt;

/**
 * @author Mr.release
 * @create 2019-12-30
 * @Describe
 */
public class ItemBean {
    String name;

    @ColorInt
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
