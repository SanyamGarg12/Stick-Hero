package com.example.stick_hero;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
// Design Pattern: Singleton
// This class is a singleton class
// The instance of this class is created only once
// The instance of this class is used to store the score, cherry, high score of the game
public class Hero {
    private Text score ;
    private Text cherry ;
    private Text HighScore ;
    private int x = 0;
    private int y = 0;

    private static Hero instance = null;

    private Hero() {
        this.score = new Text("0");
        this.cherry = new Text("0");
        this.HighScore = new Text("0");
        this.score.setStyle("-fx-font-size: 30px;");
        this.cherry.setStyle("-fx-font-size: 30px;");
        this.HighScore.setStyle("-fx-font-size: 30px;");
        this.score.setX(100);
        this.score.setY(50);
        this.cherry.setX(100);
        this.cherry.setY(100);
        this.HighScore.setX(100);
        this.HighScore.setY(150);
    }
    public static Hero getInstance() {
        if (instance == null) {
            System.out.println("Hero created");
            instance = new Hero();
        }
        return instance;
    }
    public void increaseScore(int score) {
        this.score.setText(Integer.toString(Integer.parseInt(this.score.getText()) + score));
    }

    public void increaseCherry() {
        this.cherry.setText(Integer.toString(Integer.parseInt(this.cherry.getText()) + 1));
    }
    public void setHighScore(int score) {
        this.HighScore.setText(Integer.toString(score));
    }
    public Text getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score.setText(Integer.toString(score));
    }
    public Text getCherry() {
        return this.cherry;
    }
    public Text getHighScore() {
        return this.HighScore;
    }
    public void decreaseCherry() {
        this.cherry.setText(Integer.toString(Integer.parseInt(this.cherry.getText()) - 1));
    }
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
}
