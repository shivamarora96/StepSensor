/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor.Database_Models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.example.shivamarora.stepsensor.Others.Constant ;
import com.example.shivamarora.stepsensor.Others.Constant;

@Table(name = "Records")
public class DbData extends Model{

    @Column(name = Constant.DB_COLUMN_STEPSCOUNT)
    int dbStepCount;
    @Column(name = Constant.DB_COLUMN_DATE)
    int dbDate;
    @Column(name = Constant.DB_COLUMN_MONTH)
    int dbMonth;
    @Column(name = Constant.DB_COLUMN_YEAR)
    int dbYear;
    @Column(name = Constant.DB_COLUMN_WEEKDAYS)
    int dbWeekDays ;
    @Column(name = Constant.DB_COLUMN_TIMEINMILLISEC)
    long dbActiveTimeInMilli;



    public DbData() {
        super();
    }


    public DbData(int dbStepCount, int dbDate, int dbMonth, int dbYear, int dbWeekDays, long dbActiveTimeInMilli) {
        this.dbStepCount = dbStepCount;
        this.dbDate = dbDate;
        this.dbMonth = dbMonth;
        this.dbYear = dbYear;
        this.dbWeekDays = dbWeekDays;
        this.dbActiveTimeInMilli = dbActiveTimeInMilli;
    }

    public int getDbStepCount() {
        return dbStepCount;
    }

    public void setDbStepCount(int dbStepCount) {
        this.dbStepCount = dbStepCount;
    }

    public int getDbDate() {
        return dbDate;
    }

    public void setDbDate(int dbDate) {
        this.dbDate = dbDate;
    }

    public int getDbMonth() {
        return dbMonth;
    }

    public int getDbWeekDays() {
        return dbWeekDays;
    }

    public void setDbWeekDays(int dbWeekDays) {
        this.dbWeekDays = dbWeekDays;
    }

    public void setDbMonth(int dbMonth) {
        this.dbMonth = dbMonth;
    }

    public int getDbYear() {
        return dbYear;
    }

    public void setDbYear(int dbYear) {
        this.dbYear = dbYear;
    }


    public long getDbActiveTimeInMilli() {
        return dbActiveTimeInMilli;
    }

    public void setDbActiveTimeInMilli(long dbActiveTimeInMilli) {
        this.dbActiveTimeInMilli = dbActiveTimeInMilli;
    }
}

