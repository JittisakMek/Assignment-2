package com.egci428.assignment2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainPage extends AppCompatActivity {

    private static ListView listView;
    private static TextView title;
    private  ProfileDataSource dataSource;
    ArrayAdapter<Profile> profileArrayAdapter;
    protected List<Profile> data;

    public Profile mapUser;
    Cursor CR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_HOME_AS_UP
                | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.abs3_layout);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        setActiobarTitle("Main Page");

        dataSource = new ProfileDataSource(this);
        dataSource.open();
        data = dataSource.getAllProfiles();

        profileArrayAdapter = new ProfileArrayAdapter(this,0,data);
        listView = (ListView)findViewById(R.id.listProfile);
        listView.setAdapter(profileArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long id)
            {
                Profile profile = data.get(position);
                CR = dataSource.getInformation(dataSource);
                CR.moveToFirst();

                if( CR != null && CR.moveToFirst() ){
                    do{

                        if(profile.getUser().equals(CR.getString(1))){
                            mapUser = new Profile(CR.getString(1),CR.getString(2),CR.getString(3),CR.getString(4));
                            Intent intent = new Intent(MainPage.this, MyMap.class);
                            intent.putExtra("username",mapUser.getUser());
                            intent.putExtra("latitude",mapUser.getLatitude());
                            intent.putExtra("longitude",mapUser.getLongitude());
                            startActivityForResult(intent,1);
//                            setActiobarTitle(profile.getUser()+"'s "+"Location");

                            Toast.makeText(getBaseContext(), "Welcome "+profile.getUser(), Toast.LENGTH_SHORT ).show();
                        }

                    }while(CR.moveToNext());
                }

            }
        });

    }
    //Custom ArrayAdapter

    class ProfileArrayAdapter extends ArrayAdapter<Profile>{
        Context context;
        List<Profile> objects;

        public ProfileArrayAdapter(Context context, int resource, List<Profile> objects){
            super(context,resource,objects);
            this.context = context;
            this.objects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            Profile profile = objects.get(position);
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.listview_layout, null);

            TextView profileText = (TextView)view.findViewById(R.id.resultText);
            System.out.println("User: "+profile.getUser());
            profileText.setText(profile.getUser());
            return view;
        }
    }

    private void setActiobarTitle(String title)
    {
        View v = getSupportActionBar().getCustomView();
        TextView titleTxtView = (TextView)v.findViewById(R.id.myTitle);
        titleTxtView.setText(title);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; goto parent activity.
                AlertDialog.Builder a_builder = new AlertDialog.Builder(MainPage.this);
                a_builder.setMessage("Logout?")
                        .setCancelable(false)
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        })
                        .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                AlertDialog alert = a_builder.create();
                alert.setTitle("Alert!!!");
                alert.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


//view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable()
//        {
//@Override
//public void run()
//        {
//        Profile profile = data.get(position);
//        ArrayAdapter<Profile> adapter = (ArrayAdapter<Profile>) listView.getAdapter();
//        if(listView.getAdapter().getCount()>0){
//        dataSource.deleteProfile(profile);
//        adapter.remove(profile);
//        }
//        adapter.notifyDataSetChanged();
//        view.setAlpha(1);
//        }
//        });