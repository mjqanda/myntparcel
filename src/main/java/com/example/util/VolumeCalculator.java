package com.example.util;

public class VolumeCalculator {

    private VolumeCalculator() {
    }

    public static double calculateVolume(double height, double width, double length) {
        return height * width * length;
    }
}
