package com.example.stick_hero;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cherry {
    private float x;
    private float y;

    public static Circle generate_cherry(Circle redDot) {
        redDot.setFill(Color.RED);
        redDot.setRadius(5);
        return redDot;
    }

    void remove_cherry() {

    }

    float getX() {
        return x;
    }

    float getY() {
        return y;
    }

    void setX(float x) {
        this.x = x;
    }

    void setY(float y) {
        this.y = y;
    }

}
