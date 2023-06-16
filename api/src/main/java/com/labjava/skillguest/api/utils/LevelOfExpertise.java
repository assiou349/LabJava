package com.labjava.skillguest.api.utils;

 public enum LevelOfExpertise {
    A("Junior"),
    B("Intermédiaire"),
    C("Senior"),
    D("Senior"),
    E("Senior+");

    private String name;

    LevelOfExpertise(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
