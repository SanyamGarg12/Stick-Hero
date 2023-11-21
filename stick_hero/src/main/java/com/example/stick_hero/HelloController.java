package com.example.stick_hero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class HelloController {
    @FXML
    AnchorPane root;
    @FXML
    ImageView img;
    @FXML
    Button play;
    @FXML
    ImageView intro;

    @FXML
    public void initialize() {
        Image newimg = new Image(getClass().getResourceAsStream("/Assets/MON.png"));
        Image introimg = new Image(getClass().getResourceAsStream("/Assets/Intro.jpg"));
        intro.setImage(introimg);
        img.setImage(newimg);
        System.out.println("Heo");
    }

    public void play(ActionEvent e) {
        System.out.println("Play");
    }
}