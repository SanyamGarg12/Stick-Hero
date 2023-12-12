package com.example.stick_hero;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayControllerTest {

    @Test
    public void checkHeroX(){
        Hero h = Hero.getInstance();
        assertEquals(0, h.getX());
    }

    @Test
    public void checkHeroY(){
        Hero h = Hero.getInstance();
        assertEquals(0, h.getY());
    }

    @Test
    public void singletonVerify(){
        Hero h = Hero.getInstance();
        Hero h2 = Hero.getInstance();
        assertEquals(h, h2);
    }

}