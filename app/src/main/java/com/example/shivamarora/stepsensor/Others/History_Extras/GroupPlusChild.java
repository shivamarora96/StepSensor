/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor.Others.History_Extras;

public class GroupPlusChild{

    GroupObject groupObject ;
    ChildObject childObject ;

    public GroupPlusChild(GroupObject groupObject, ChildObject childObject) {
        this.groupObject = groupObject;
        this.childObject = childObject;
    }


    public GroupObject getGroupObject() {
        return groupObject;
    }

    public void setGroupObject(GroupObject groupObject) {
        this.groupObject = groupObject;
    }

    public ChildObject getChildObject() {
        return childObject;
    }

    public void setChildObject(ChildObject childObject) {
        this.childObject = childObject;
    }
}
