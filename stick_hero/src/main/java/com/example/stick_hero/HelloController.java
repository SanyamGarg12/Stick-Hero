package com.example.stick_hero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader; // Import the correct FXMLLoader class

import java.io.IOException;

public class HelloController {
    @FXML
    AnchorPane root;
    @FXML
    ImageView img;
    @FXML
    Button play;
    @FXML
    ImageView intro;
    private Stage stage; // Declare Stage
    private Scene scene; // Declare Scene

    @FXML
    public void initialize() {
        Image newimg = new Image(getClass().getResourceAsStream("/Assets/MON.png"));
        Image introimg = new Image(getClass().getResourceAsStream("/Assets/Intro.jpg"));
        intro.setImage(introimg);
        img.setImage(newimg);
        System.out.println("Hello");
    }

    @FXML
    public void switchToScene1(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("play-view.fxml"));
        stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void play(ActionEvent e) {
        System.out.println("Play");
    }
}
