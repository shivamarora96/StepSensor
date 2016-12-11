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
import com.example.shivamarora.stepsensor.Others.Constant;

@Table(name = "General")
public class DbGeneral extends Model {




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
