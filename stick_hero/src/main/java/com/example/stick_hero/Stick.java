package com.example.stick_hero;

import javafx.scene.shape.Rectangle;

public class Stick extends Rectangle {
    private int x;
    private int y;
    private int length;

    public Stick(int x, int y, int length) {
        this.x = x;
        this.y = y;
        this.length = length;
    }
    public void set_length(int length) {
        // TODO implement here
        this.length = length;
    }
//    getX() {
//        // TODO implement here
//    }
//    getY() {
//        // TODO implement here
//    }
//    setX() {
//        // TODO implement here
//    }
//    setY() {
//        // TODO implement here
//    }


    public int get_length() {
// TODO implement here
        return this.length;

    }
}
