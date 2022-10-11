package com.sparta.week01_homework;

import java.util.Arrays;

public enum Category {
    Study(0, "공부"),
    Game(1,"게임"),
    Play(2,"놀기"),
    UNKNOWN(-1,"알수없음");

    private int id;
    private String koreanName;
//    private String name;

    Category(int id, String koreanName){

        this.id = id;
        this.koreanName = koreanName;
    }
    public static Category find(int id) {
        return Arrays.stream(values())
                .filter(category -> category.id==id)
                .findAny()
                .orElse(UNKNOWN);
    }
}
