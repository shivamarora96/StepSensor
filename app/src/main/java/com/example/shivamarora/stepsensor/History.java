package com.example.shivamarora.stepsensor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class History extends AppCompatActivity {

    ExpandableListView listView ;
    CustomExpandableAdapter adapter ;
ArrayList<GroupPlusChild> groupPlusChildArrayList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ActiveAndroid.initialize(this);



        Toolbar toolbar = (Toolbar)findViewById(R.id.Historytoolbar) ;
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);//            getSupportActionBar().set
        }

    }


    @Override
    protected void onResume() {
        super.onResume();


        groupPlusChildArrayList = new ArrayList<GroupPlusChild>();
        List<DbData> l = new Select().from(DbData.class).execute() ;
        Calendar c = Calendar.getInstance() ;
        for(int i = 0 ; i <l.size() ; i++){

            String currentDate = l.get(i).getDbDate() + " / " + l.get(i).getDbMonth() + " / " + l.get(i).getDbYear() ;
            c.set(Calendar.DAY_OF_WEEK , l.get(i).getDbWeekDays()) ;
            String currentWeek = StepsGraphFragment.returnDayOfWeekInString(c) ;
            GroupObject currentGroupObject = new GroupObject(currentDate , currentWeek ) ;


            String stepsCurrent =  l.get(i).getDbStepCount() + "" ;
            String distanceCurrent = Math.round(l.get(i).getDbStepCount() / 2.82)  +  "" ;
            String caloriesCurrent = Math.round((((0.57 * 2.21 * 85) / 4540) * l.get(i).getDbStepCount())) + "" ;
            ChildObject currentChildObject = new ChildObject(currentDate ,stepsCurrent , distanceCurrent , caloriesCurrent) ;

            groupPlusChildArrayList.add(new GroupPlusChild(currentGroupObject , currentChildObject)) ;


        }


        listView =(ExpandableListView)findViewById(R.id.HistoryListView);
        adapter = new CustomExpandableAdapter(History.this , groupPlusChildArrayList) ;
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            History.this.finish();
        return super.onOptionsItemSelected(item);
    }

}


class CustomExpandableAdapter extends BaseExpandableListAdapter{

    Context context ;
    ArrayList<GroupPlusChild> groupPlusChildArrayList ;

    public CustomExpandableAdapter(Context context, ArrayList<GroupPlusChild> groupPlusChildArrayList) {
        this.context = context;
        this.groupPlusChildArrayList = groupPlusChildArrayList;
    }

    @Override
    public int getGroupCount() {
        return groupPlusChildArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupPlusChildArrayList.get(groupPosition).getGroupObject();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupPlusChildArrayList.get(groupPosition).getChildObject();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition*100;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return groupPosition*100 + childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        View v = convertView ;

        if(v == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.context) ;
            v = layoutInflater.inflate(R.layout.custom_history_layout_child_listview , parent , false) ;

        }


        TextView dateTextView = (TextView)v.findViewById(R.id.HistoryDate) ;
        TextView weekDay = (TextView)v.findViewById(R.id.HistoryWeekDayTextView) ;

        dateTextView.setText(groupPlusChildArrayList.get(groupPosition).getGroupObject().getDate());
        weekDay.setText(groupPlusChildArrayList.get(groupPosition).getGroupObject().getWeekDay());

        return  v ;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View v = convertView ;

        if(v == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.context) ;
            v = layoutInflater.inflate(R.layout.custom_history_layout_expandable_child_layout , parent , false) ;

        }


        TextView dateTextView = (TextView)v.findViewById(R.id.childDatePreview) ;
        TextView steps = (TextView)v.findViewById(R.id.ChildStepsPreview) ;
        TextView calories = (TextView)v.findViewById(R.id.ChildCaloriesPreview) ;
        TextView distance = (TextView)v.findViewById(R.id.ChildDistancePreview) ;


        steps.setText(groupPlusChildArrayList.get(groupPosition).getChildObject().getStepCountInChild());
        dateTextView.setText(groupPlusChildArrayList.get(groupPosition).getChildObject().getDateChild()) ;
        calories.setText(groupPlusChildArrayList.get(groupPosition).getChildObject().getCaloriesCountInChild() + " cal" ) ;
        distance.setText(groupPlusChildArrayList.get(groupPosition).getChildObject().getDistanceCountInChild() + " m ");

        return  v ;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}



class GroupObject {

    String Date;
    String WeekDay ;


    public GroupObject(String date, String weekDay) {
        Date = date;
        WeekDay = weekDay;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getWeekDay() {
        return WeekDay;
    }

    public void setWeekDay(String weekDay) {
        WeekDay = weekDay;
    }
}

class ChildObject{

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


class GroupPlusChild{

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







/*

 class CustomAdapter extends ArrayAdapter<DbData>{

    Context context ;
    ArrayList<DbData> dbDataArrayList ;

    public CustomAdapter(Context context,  ArrayList<DbData> arrayList) {
        super(context,0 , arrayList);
        this.context = context ;
        this.dbDataArrayList = arrayList ;
    }


    @Override
    public int getCount() {
        return this.dbDataArrayList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

    DbData currentDbdata = dbDataArrayList.get(position) ;
        View v  = convertView;

        if(v == null){
             v = LayoutInflater.from(this.context).inflate(R.layout.custom_history_layout_child_listview , parent , false) ;
        }

        TextView date = (TextView) v.findViewById(R.id.HistoryDate);
        TextView steps = (TextView) v.findViewById(R.id.HistoryWeekDayTextView);

        String dateshow = currentDbdata.getDbDate() + " / "  + (currentDbdata.getDbMonth() + 1 ) + " / " + currentDbdata.getDbYear() ;
        date.setText(dateshow );
        steps.setText(currentDbdata.dbStepCount + "");

return  v;
    }
}*/
