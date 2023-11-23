package com.example.stick_hero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayController {
    @FXML
    private Stick stick;
    private Cherry cherry;
    private Hero hero;
    private int score;
    private Pillar pillar;

    @FXML
    ImageView heroImg;

    @FXML
    public void initialize() {
        Image newimg = new Image(getClass().getResourceAsStream("/Assets/chr0.png"));
        this.heroImg.setImage(newimg);
    }
}
