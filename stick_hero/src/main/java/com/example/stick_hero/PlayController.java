package com.example.stick_hero;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.io.InputStream;
import java.util.Random;



public class PlayController {
    private Cherry cherry;
    private Hero hero;
    private int score;
    @FXML
    private AnchorPane rootPane;
    @FXML
    Rectangle pillar;
    @FXML
    Rectangle pillar2;
    int swap = 0;
    private static final int SCENE_WIDTH = 600;
    private static final int SCENE_HEIGHT = 400;
    private static final int PILLAR_WIDTH = 30;
    private static final int PILLAR_HEIGHT = 150;
    private static final int PILLAR_GAP = 100;
    private Random random;
    private Pillar newPillar;
    @FXML
    ImageView heroImg;
    private int currentFrame = 0;
    private Image[] frames;
    private long lastUpdateTime = 0;  // To track the last time the frame was updated
    private long frameDelay = 100_000_000;  // Delay in nanoseconds (adjust as needed)

    private long pressStartTime = 0;
    private double pressDuration = 0;
    private Rectangle stick;

    @FXML
    public void initialize() {
//        Image newimg = new Image(getClass().getResourceAsStream("/Assets/chr0.png"));
        loadFrames();

        rootPane.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
        rootPane.setOnMousePressed(this::handleMousePress);
        rootPane.setOnMouseReleased(this::handleMouseRelease);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdateTime >= frameDelay) {
                    updateAnimation();
                    lastUpdateTime = now;
                }
            }
        };
        timer.start();

//        rootPane.setOnKeyPressed(event -> handleKeyPress(event.getCode()));

//        this.heroImg.setImage(newimg);
        random = new Random();
//        newPillar = createPillar();
//        moveLeft();
    }

    private void handleMouseRelease(MouseEvent mouseEvent) {
        long pressTime = System.currentTimeMillis() - pressStartTime;
        pressDuration = pressTime; // Update pressDuration
        double stickLength = Math.max(pressDuration / 100.0, 50.0); // Use Math.max to ensure a minimum length
        if (stick != null) {
            rootPane.getChildren().remove(stick);
        }

        stick = new Rectangle(5, stickLength, Color.RED);
//        stick.setTranslateX(heroImg.getTranslateX() + heroImg.getFitWidth() / 2 - stick.getWidth() / 2); // Set the position to the center of the hero character
//        stick.setTranslateY(heroImg.getTranslateY() - stick.getHeight());
        stick.setTranslateX(100);
        stick.setTranslateY(100);
        rootPane.getChildren().add(stick);
        System.out.println("Stick added to the scene");
        rotateStickAnimation();
    }
    private void handleMousePress(MouseEvent mouseEvent) {
        pressStartTime = System.currentTimeMillis();
    }

//    public void stickIncreaseLength() {
//        this.stick.set_length(this.stick.get_length() + 1);
//    }

    private void HeroSuccess(ActionEvent e) {
//        newPillar = createPillar();
        if(swap == 0) {
//            pillar = createPillar();
            swap = 1;
        }
        else {
//            pillar2 = createPillar();
            swap = 0;
        }
    }

    private Pillar createPillar() {
        double pillarY = random.nextDouble() * (SCENE_HEIGHT - PILLAR_GAP - PILLAR_HEIGHT);
        Pillar pillarr = new Pillar(random.nextDouble() * 50 + PILLAR_WIDTH, PILLAR_HEIGHT);
        pillarr.setLayoutX(SCENE_WIDTH);
        pillarr.setLayoutY(pillarY);
        return pillarr;
    }

    public void moveLeft1() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), pillar);
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), heroImg);

        transition.setToX(pillar.getTranslateX() + 100); // Adjust the distance as needed
        transition2.setToX(heroImg.getTranslateX() + 100); // Adjust the distance as needed
        transition.play();
        transition2.play();
    }
    public void moveLeft2() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), pillar2);
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), heroImg);

        transition.setToX(pillar2.getTranslateX() + 100); // Adjust the distance as needed
        transition2.setToX(heroImg.getTranslateX() + 100); // Adjust the distance as needed
        transition.play();
        transition2.play();
    }
    public void tpPillar() {
        if (swap == 0) {
//            moveLeft1();
            pillar.setLayoutX(500);
            pillar.setLayoutY(100);
        }
        else {
//            moveLeft2();
            pillar2.setLayoutX(500);
            pillar2.setLayoutY(100);
        }

    }
    public void heromove(){

    }
    private void loadFrames() {
        frames = new Image[13];
        for (int i = 0; i <= 12; i++) {
            String imagePath = "/Assets/chr" + i + ".png";
            try (InputStream stream = getClass().getResourceAsStream(imagePath)) {
                if (stream != null) {
                    frames[i] = new Image(stream);
                } else {
                    System.out.println("Failed to load image: " + imagePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void updateAnimation() {
        currentFrame = (currentFrame + 1) % frames.length;
        heroImg.setImage(frames[currentFrame]);
    }
    private void rotateStickAnimation() {
        // Rotate the stick from vertical to horizontal
        stick.setRotate(90);

        // Move the character along the stick
        double characterStartX = heroImg.getTranslateX();
        double characterEndX = stick.getTranslateX() + stick.getWidth() / 2 - heroImg.getFitWidth() / 2;

        double characterStartY = heroImg.getTranslateY();
        double characterEndY = stick.getTranslateY() - heroImg.getFitHeight();

        Duration duration = Duration.seconds(2); // Adjust the duration as needed

        KeyValue keyValueX = new KeyValue(heroImg.translateXProperty(), characterEndX);
        KeyValue keyValueY = new KeyValue(heroImg.translateYProperty(), characterEndY);

        KeyFrame keyFrame = new KeyFrame(duration, keyValueX, keyValueY);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }
    private void handleKeyPress(KeyCode code) {
        switch (code) {
            case RIGHT:
                heroImg.setTranslateX(heroImg.getTranslateX() + 10);
                break;
        }


    }
}
