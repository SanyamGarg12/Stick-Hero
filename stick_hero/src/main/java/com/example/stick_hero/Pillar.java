package com.example.stick_hero;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Pillar extends Rectangle {
    public Pillar(double width, double height) {
        super(width, height);
        setFill(Color.GREEN); // You can set the color as per your requirement
    }

    public void moveLeft() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), this);
        transition.setToX(getTranslateX() - 100); // Adjust the distance as needed
        transition.play();
    }
}
