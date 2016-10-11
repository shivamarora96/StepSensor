package com.example.shivamarora.stepsensor;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import java.util.ArrayList;
import java.util.List;
public class History extends AppCompatActivity {

    ListView listView ;
    CustomAdapter adapter ;


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


        ArrayList<DbData> dbDatas = new ArrayList<DbData>();
        List<DbData> l = new Select().from(DbData.class).execute() ;

        for(int i = 0 ; i <l.size() ; i++){
           dbDatas.add(l.get(i)) ;
        }


        listView =(ListView)findViewById(R.id.HistoryListView);
        adapter = new CustomAdapter(History.this ,dbDatas ) ;
        listView.setAdapter(adapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
            History.this.finish();
        return super.onOptionsItemSelected(item);
    }

}


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
}