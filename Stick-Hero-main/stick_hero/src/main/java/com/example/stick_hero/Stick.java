package com.example.stick_hero;

import javafx.scene.shape.Rectangle;

public class Stick extends Rectangle {
    private double x;
    private double y;
    private int length;

    public Stick() {
        this.x = 0;
        this.y = 0;
        this.length = 0;
    }
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
