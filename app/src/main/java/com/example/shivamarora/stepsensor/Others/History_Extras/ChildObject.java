/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor.Others.History_Extras;

public class ChildObject{

    String dateChild ;
    String stepCountInChild ;
    String distanceCountInChild ;
    String caloriesCountInChild ;

    public ChildObject(String dateChild, String stepCountInChild, String distanceCountInChild, String caloriesCountInChild) {
        this.dateChild = dateChild;
        this.stepCountInChild = stepCountInChild;
        this.distanceCountInChild = distanceCountInChild;
        this.caloriesCountInChild = caloriesCountInChild;
    }

    public String getDateChild() {
        return dateChild;
    }

    public void setDateChild(String dateChild) {
        this.dateChild = dateChild;
    }

    public String getStepCountInChild() {
        return stepCountInChild;
    }

    public void setStepCountInChild(String stepCountInChild) {
        this.stepCountInChild = stepCountInChild;
    }

    public String getDistanceCountInChild() {
        return distanceCountInChild;
    }

    public void setDistanceCountInChild(String distanceCountInChild) {
        this.distanceCountInChild = distanceCountInChild;
    }

    public String getCaloriesCountInChild() {
        return caloriesCountInChild;
    }

    public void setCaloriesCountInChild(String caloriesCountInChild) {
        this.caloriesCountInChild = caloriesCountInChild;
    }
}
