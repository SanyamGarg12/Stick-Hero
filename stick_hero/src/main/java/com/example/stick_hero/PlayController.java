package com.example.stick_hero;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;


import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class PlayController {
    private Timeline stickGrowTimeline;
    private Cherry cherry;
    private Hero hero;
    private int score;
    private Image bkg1;
    private Image bkg2;
    private Image cherryImg;

    @FXML
    ImageView cherryImgView;
    @FXML
    private ImageView background;
    private boolean motionStarted = false;
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
    private boolean upsideDown = false;
    private long lastUpdateTime = 0;  // To track the last time the frame was updated
    private long frameDelay = 100_000_000;  // Delay in nanoseconds (adjust as needed)

    private long pressStartTime = 0;
    private double pressDuration = 0;
    private Rectangle stick;
    private double distanceToPillar2;
    private boolean isSceneShifted = false;
    private int cherryCount = 0;
    private boolean isMousePressed = false;
    private boolean isFirstTime = true;
    private boolean isFirstTime2 = true;
    int test = -1;
    private boolean isFirstTime3 = true;
    private double totaldistance = 0;
    private int i = 0;
    private double distancMoved = 0;
    private double totalWidth = 0;


    @FXML
    public void initialize() {
        loadFrames(false);
        rootPane.setOnKeyPressed(event -> handleKeyPress(event.getCode()));
        rootPane.setOnMousePressed(this::handleMousePress);
        rootPane.setOnMouseReleased(this::handleMouseRelease);
        rootPane.setOnMouseClicked(this::handleMouseClicked);
        bkg1 = new Image(getClass().getResourceAsStream("/Assets/bkg01.jpg"));
        bkg2 = new Image(getClass().getResourceAsStream("/Assets/bkg02.jpg"));
        cherryImg = new Image(getClass().getResourceAsStream("/Assets/cherry.png"));
        cherryImgView.setImage(cherryImg);
        Random rand = new Random();
        double x = rand.nextDouble();
        if (x > 0.5) {
            x = 1;
            background.setImage(bkg1);
        } else {
            x = 0;
            background.setImage(bkg2);
        }
        background.fitWidthProperty().bind(rootPane.widthProperty());
        cherryImgView.setVisible(false);
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
        random = new Random();

    }

    private void moveHeroAcrossStick() throws HeroFallException, InterruptedException {
        motionStarted = true;
        loadFrames(true);
        // Calculate the distance between the heroImg and pillar2
        if (swap == 0) {
//            heroImg.setTranslateX(pillar.getTranslateX() + pillar.getWidth());
            distanceToPillar2 = pillar2.getLayoutX() - (pillar.getLayoutX() + pillar.getWidth());
        } else {
            distanceToPillar2 = pillar.getLayoutX() - (pillar2.getLayoutX() + pillar2.getWidth());
        }
        totaldistance = totaldistance + distanceToPillar2;
//        distanceToPillar2 = pillar2.getLayoutX() - (heroImg.getLayoutX() + heroImg.getFitWidth());
        // If the stick length is sufficient to reach pillar2
        if (stick.getHeight() + 5 >= distanceToPillar2 && stick.getHeight() + 5 <= distanceToPillar2 + pillar.getWidth()) {
            double endX;
            double endY;
            if (isFirstTime2) {
                endX = pillar2.getLayoutX();
                endY = heroImg.getTranslateY();
                isFirstTime2 = false;
                // Calculate the end position of the heroImg
            } else {
                if (swap == 0) {
                    // Calculate the end position of the heroImg which is the point off generation of stick + stick length
                    endX = pillar2.getWidth() + distanceToPillar2;
                    endY = heroImg.getTranslateY();
                } else {
                    // Calculate the end position of the heroImg which is the point off generation of stick + stick length
                    endX = pillar.getWidth() + distanceToPillar2;
                    endY = heroImg.getTranslateY();
                }
            }//add distance moved by hero to distance moved
            distancMoved = distancMoved + (endX - heroImg.getTranslateX());
//            System.out.println("endX: " + endX);
            // Create a TranslateTransition for the heroImg
            TranslateTransition transition = new TranslateTransition(Duration.seconds(2), heroImg);
            transition.setByX(endX);
            transition.setToY(endY);
            heroImg.translateXProperty().addListener((observable) -> {
                // Check if the hero is upside down and if it has stumbled upon the pillar
                if (swap == 0) {
                    if (upsideDown && pillar.getLayoutX() + pillar.getWidth() + heroImg.getTranslateX() >= pillar2.getLayoutX() + heroImg.getFitWidth() / 2) {
                        // Stop the transition
                        transition.stop();
                        loadFrames(false);
                        // Make the hero fall
                        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), heroImg);
                        fallTransition.setToY(SCENE_HEIGHT - heroImg.getFitHeight());
                        fallTransition.play();
                    }
                    if (!motionStarted && pillar.getLayoutX() + heroImg.getTranslateX() + pillar.getWidth() > pillar2.getLayoutX() + heroImg.getFitWidth() / 2) {
                        motionStarted = false;
                    }

                } else {
                    if (upsideDown && pillar2.getLayoutX() + pillar2.getWidth() + heroImg.getTranslateX() >= pillar.getLayoutX() + heroImg.getFitWidth() / 2) {
                        // Stop the transition
                        transition.stop();
                        loadFrames(false);
                        // Make the hero fall
                        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), heroImg);
                        fallTransition.setToY(SCENE_HEIGHT - heroImg.getFitHeight());
                        fallTransition.play();
                    }
                    if (!motionStarted && pillar2.getLayoutX() + heroImg.getTranslateX() + pillar2.getWidth() > pillar.getLayoutX() + heroImg.getFitWidth() / 2) {
                        motionStarted = false;
                    }
                }
            });
            // Play the transition
            transition.play();

            transition.setOnFinished(event -> {


                             // Load the standing frames after the hero has moved
                loadFrames(false);
                HeroSuccess();
                ShiftScene();
            });
            //create a trashProcess without sleep
//            loadFrames(false);
        } else {
            // If the stick length is insufficient to reach pillar2
            // Create a TranslateTransition for the heroImg
            // Calculate the end position of the heroImg which is the point off generation of stick + stick length
            double endX = heroImg.getTranslateX() + stick.getHeight() + heroImg.getFitWidth();
            double endY = heroImg.getTranslateY();

            TranslateTransition transition = new TranslateTransition(Duration.seconds(2), heroImg);
            transition.setToX(endX);
            transition.setToY(endY);
            if (stick.getHeight() >= distanceToPillar2 + pillar.getWidth()) {
                heroImg.translateXProperty().addListener((observable) -> {
                    // Check if the hero is upside down and if it has stumbled upon the pillar
                    if (swap == 0) {
                        if (upsideDown && pillar.getLayoutX() + pillar.getWidth() + heroImg.getTranslateX() >= pillar2.getLayoutX() + heroImg.getFitWidth() / 2) {
                            // Stop the transition
                            transition.stop();
                            loadFrames(false);
                            // Make the hero fall
                            TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), heroImg);
                            fallTransition.setToY(SCENE_HEIGHT - heroImg.getFitHeight());
                            fallTransition.play();
                        }
                    } else {
                        if (upsideDown && pillar2.getLayoutX() + pillar2.getWidth() + heroImg.getTranslateX() >= pillar.getLayoutX() + heroImg.getFitWidth() / 2) {
                            // Stop the transition
                            transition.stop();
                            loadFrames(false);

                            // Make the hero fall
                            TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), heroImg);
                            fallTransition.setToY(SCENE_HEIGHT - heroImg.getFitHeight());
                            fallTransition.play();
                        }
                    }
                });
            }
            transition.play();
            transition.setOnFinished(event -> {
                loadFrames(false);
//                try {
//                    throw new HeroFallException();
//                } catch (HeroFallException e) {
//                    throw new RuntimeException(e);
                // Fall the heroImg
                TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), heroImg);
                fallTransition.setToY(SCENE_HEIGHT - heroImg.getFitHeight());
                fallTransition.play();


            });


        }
    }

    public void handleMouseClicked(MouseEvent mouseEvent) {
        if (!motionStarted) {
            return;
        }
        Rotate rotate = new Rotate(180, heroImg.getFitWidth() / 2, heroImg.getFitHeight() * 2 / 3);
        heroImg.getTransforms().add(rotate);
        if (!upsideDown) {
            upsideDown = true;
        } else {
            upsideDown = false;
        }
    }

    private void ShiftScene() {
        moveLeft();
//        pillar2.setTranslateX(pillar2.getLayoutX());
//        pillar.setTranslateX(pillar.getLayoutX());
//        tpPillar();
        isSceneShifted = false;
        i++;
        //remove the stick
        rootPane.getChildren().remove(stick);
        //remove the cherry
        cherryImgView.setVisible(false);
    }

    private void handleMouseRelease(MouseEvent mouseEvent) {
        if (!isMousePressed) {
            return;
        }
        isMousePressed = false;
        isSceneShifted = true;
        long pressTime = System.currentTimeMillis() - pressStartTime;
        pressDuration = pressTime; // Update pressDuration

        // Stop the stick from growing
        stickGrowTimeline.stop();

        // Set the pivot point to the lower end of the stick
        double pivotX = stick.getX() + stick.getWidth() / 2;
        double pivotY = stick.getY() + stick.getHeight();

        // Create a Rotate transformation for the stick
        Rotate rotate = new Rotate(0, pivotX, pivotY);
        stick.getTransforms().add(rotate);

        // Create a Timeline to gradually rotate the stick
        Timeline rotateTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), new KeyValue(rotate.angleProperty(), 90))
        );
        rotateTimeline.play();
        rotateTimeline.setOnFinished(event -> {
            // Move the character across the stick
            try {
                moveHeroAcrossStick();

//                sleep(2000);
//                loadFrames(false);
            } catch (HeroFallException | InterruptedException e) {
                e.printStackTrace();
            }
        });


    }

    private void handleMousePress(MouseEvent mouseEvent) {
        if (isSceneShifted) {
            return;
        }
//        isSceneShifted = true;
        isMousePressed = true;
        pressStartTime = System.currentTimeMillis();

        // Create a new stick at the start of the press
        stick = new Rectangle(5, 0, Color.BLACK);
        if (true) {
            if (swap == 0) {
                totalWidth = totalWidth + pillar.getWidth();

            } else {
                totalWidth = totalWidth + pillar2.getWidth();
            }
        }
        if (isFirstTime) {
            stick.setTranslateX(pillar.getLayoutX() + pillar.getWidth() - 1); // Set the base of the stick at the right edge of the character
            isFirstTime = false;
        } else {
            if (swap == 0) {
                // Set the base of the stick at the right edge of the character
                stick.setTranslateX(pillar.getLayoutX() - totaldistance - totalWidth + 2 * pillar.getWidth());

            } else {
                stick.setTranslateX(pillar2.getLayoutX() - totaldistance - totalWidth + 2 * pillar2.getWidth()); // Set the base of the stick at the right edge of the character
            }
        }
//        stick.setTranslateX(pillar.getTranslateX() + pillar.getWidth()); // Set the base of the stick at the right edge of the character
        stick.setTranslateY(heroImg.getLayoutY() + heroImg.getFitHeight() - 35); // Set the base of the stick at the bottom of the character
        rootPane.getChildren().add(stick);

        // Create a timeline that increases the stick's height every frame
        stickGrowTimeline = new Timeline(new KeyFrame(Duration.millis(5), event -> {
            stick.setHeight(stick.getHeight() + 1);
            stick.setTranslateY(stick.getTranslateY() - 1); // Move the stick up as it grows
        }));
        stickGrowTimeline.setCycleCount(Timeline.INDEFINITE);
        stickGrowTimeline.play();
    }

//    public void stickIncreaseLength() {
//        this.stick.set_length(this.stick.get_length() + 1);
//    }

    private void HeroSuccess() {
        this.motionStarted = false;
//        newPillar = createPillar();
        if (swap == 0) {
//            pillar = createPillar();
            swap = 1;
        } else {
//            pillar2 = createPillar();
            swap = 0;
        }
    }

    public void moveLeft() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1), pillar2);
        TranslateTransition transition3 = new TranslateTransition(Duration.seconds(1), pillar);
        TranslateTransition transition4 = new TranslateTransition(Duration.seconds(1), stick);
        TranslateTransition transition2 = new TranslateTransition(Duration.seconds(1), heroImg);
        if (swap == 0) {
            transition.setToX(pillar2.getTranslateX() - distanceToPillar2 - pillar2.getWidth()); // Adjust the distance as needed
            // also move the stick and heroImg
            transition4.setToX(stick.getTranslateX() - distanceToPillar2 - pillar2.getWidth());
            transition2.setToX(heroImg.getTranslateX() - distanceToPillar2 - pillar2.getWidth());
            transition3.setToX(pillar.getTranslateX() - distanceToPillar2 - pillar2.getWidth());
        } else {
            transition.setToX(pillar.getTranslateX() - distanceToPillar2 - pillar.getWidth()); // Adjust the distance as needed
            // also move the stick and heroImg
            transition4.setToX(stick.getTranslateX() - distanceToPillar2 - pillar.getWidth());
            transition2.setToX(heroImg.getTranslateX() - distanceToPillar2 - pillar.getWidth());
            transition3.setToX(pillar2.getTranslateX() - distanceToPillar2 - pillar.getWidth());
        }
        transition.play();
        transition3.play();
        transition4.play();
        transition2.play();
        transition.setOnFinished(event -> {
            tpPillar();
        });
    }

    public void tpPillar() {
        Random random = new Random();
        if (swap != 0) {
            double gap = SCENE_WIDTH - (pillar2.getWidth());
            // Generate a random value within the gap
            double randomX = pillar2.getLayoutX() + pillar2.getWidth() + random.nextDouble() * gap;
            // Set the X position of the pillar
            pillar.setLayoutX(randomX);
            pillar.setLayoutY(336);
            // set random width
            pillar.setWidth(random.nextDouble() * 200 + PILLAR_WIDTH);
            cherryImgView.setVisible(true);
            double val = (pillar.getLayoutX() - pillar2.getLayoutX() - pillar2.getWidth());
            if (val < 2 * cherryImgView.getFitWidth()) {
                cherryImgView.setVisible(false);
            }
            cherryImgView.setLayoutX((pillar2.getWidth() + random.nextDouble() * (val)) - cherryImgView.getFitWidth());
            cherryImgView.setLayoutY(336);
        } else {
            double gap = SCENE_WIDTH - (pillar.getWidth());
//            System.out.println();
            // Generate a random value within the gap
            double randomX = pillar.getLayoutX() + pillar.getWidth() + random.nextDouble() * gap;
            // Set the X position of the pillar
            pillar2.setLayoutX(randomX);
            pillar2.setLayoutY(336);
            // set random width
            pillar2.setWidth(random.nextDouble() * 200 + PILLAR_WIDTH);
            cherryImgView.setVisible(true);
            cherryImgView.setLayoutY(336);
            double val = pillar2.getLayoutX() - (pillar.getLayoutX() + pillar.getWidth());
            if (val < 2 * cherryImgView.getFitWidth()) {
                cherryImgView.setVisible(false);
            }
            cherryImgView.setLayoutX(pillar.getWidth() + ((random.nextDouble() * (val))) - cherryImgView.getFitWidth());
        }

    }

    public void heromove() {

    }

    private void loadFrames(boolean isMoving) {
        frames = new Image[13];
        if (isMoving) {
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
        } else {
            for (int i = 0; i <= 12; i++) {
                String imagePath = "/Assets/chr" + 0 + ".png";
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