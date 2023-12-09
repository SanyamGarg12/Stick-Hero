package com.example.stick_hero;

import javafx.animation.TranslateTransition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Pillar extends Rectangle {
    private double x;
    private double y;
    private int length;
    private int breadth;

    public Pillar() {
        this.x = 0;
        this.y = 0;
        this.length = 0;
        this.breadth = 0;
    }
//
//    private void ShiftScene() {
//        if (!perfect) {
//            hero.increaseScore(1);
//
//            ScoreBoard.setText(String.valueOf(Integer.parseInt(hero.getScore().getText())));
//        } else {
//            hero.increaseScore(2);
//
//            ScoreBoard.setText(String.valueOf(Integer.parseInt(hero.getScore().getText())));
//            perfect = false;
//        }
//        moveLeft();
//        isSceneShifted = false;
//        i++;
//
//        cherryImgView.setVisible(false);
//    }

    public void setLength(int length) {
        this.length = length;
    }

    public void setBreadth(int width) {
        this.breadth = width;
    }

    public int getLength() {
        return length;
    }

    public int getBreadth() {
        return breadth;
    }


}
