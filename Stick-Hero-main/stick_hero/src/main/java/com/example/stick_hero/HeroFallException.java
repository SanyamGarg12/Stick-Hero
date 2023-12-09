package com.example.stick_hero;

import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class HeroFallException extends Exception {
    static int SCENE_HEIGHT = 400;

    @Override
    public String getMessage() {
        return "Hero Fall";
    }

//    public static void FallTransitionShow() {
//
//        TranslateTransition fallTransition = new TranslateTransition(Duration.seconds(1), PlayController.heroImg);
//        fallTransition.setToY(SCENE_HEIGHT - PlayController.heroImg.getFitHeight());
//        fallTransition.play();
//    }
}
