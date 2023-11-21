package com.example.stick_hero;

public class HeroFallException extends Exception{
    @Override
    public String getMessage(){
        return "Hero Fall";
    }
}
