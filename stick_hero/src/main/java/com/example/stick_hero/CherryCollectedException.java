package com.example.stick_hero;

public class CherryCollectedException extends Exception {
    @Override
    public String getMessage(){
        return "Cherry Collected";
    }
}
