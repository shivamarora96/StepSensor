package com.example.shivamarora.stepsensor;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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

@Table(name = "General")
class DbGeneral extends Model{




    @Column(name = Constant.DBG_COLUMN_Senstivity)
    int dbSenstivity ;

    @Column( name = Constant.DBG_COLUMN_StepSize)
    int dbStepSize ;

    @Column(name = Constant.DBG_COLUMN_StepGoal)
    long dbStepGoal ;

    @Column( name = Constant.DBG_COLUMN_VERSION)
    int dbVersion ;
    @Column( name = Constant.DBG_COLUMN_ID)
    int dbID = Constant.DB_GENERAL_ID ;


    public DbGeneral() {
        super();
    }


    public DbGeneral(int dbSenstivity, int dbStepSize, long dbStepGoal, int dbVersion) {
        this.dbSenstivity = dbSenstivity;
        this.dbStepSize = dbStepSize;
        this.dbStepGoal = dbStepGoal;
        this.dbVersion = dbVersion;
        this.dbID = 0 ;

    }

    public int getDbSenstivity() {
        return dbSenstivity;
    }

    public void setDbSenstivity(int dbSenstivity) {
        this.dbSenstivity = dbSenstivity;
    }

    public int getDbStepSize() {
        return dbStepSize;
    }

    public void setDbStepSize(int dbStepSize) {
        this.dbStepSize = dbStepSize;
    }

    public long getDbStepGoal() {
        return dbStepGoal;
    }

    public void setDbStepGoal(long dbStepGoal) {
        this.dbStepGoal = dbStepGoal;
    }

    public int getDbVersion() {
        return dbVersion;
    }

    public void setDbVersion(int dbVersion) {
        this.dbVersion = dbVersion;
    }

    public int getDbID() {
        return dbID;
    }

    public void setDbID(int dbID) {
        this.dbID = dbID;
    }
}

