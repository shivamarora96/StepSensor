/*
 * Copyright (c) 2016. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.example.shivamarora.stepsensor.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.shivamarora.stepsensor.Adapters_Customs.History_Adapter;
import com.example.shivamarora.stepsensor.Database_Models.DbData;
import com.example.shivamarora.stepsensor.Fragments.StepsGraphFragment;
import com.example.shivamarora.stepsensor.Others.History_Extras.ChildObject;
import com.example.shivamarora.stepsensor.Others.History_Extras.GroupObject;
import com.example.shivamarora.stepsensor.Others.History_Extras.GroupPlusChild;
import com.example.shivamarora.stepsensor.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class History extends AppCompatActivity {

    ExpandableListView listView ;
    History_Adapter adapter ;
ArrayList<GroupPlusChild> groupPlusChildArrayList ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        ActiveAndroid.initialize(this);

        AdView adView = (AdView)findViewById(R.id.History_BannerAdd) ;
        adView.loadAd(new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build()
                );


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
        adapter = new History_Adapter(History.this , groupPlusChildArrayList) ;
        listView.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            History.this.finish();
        return super.onOptionsItemSelected(item);
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
