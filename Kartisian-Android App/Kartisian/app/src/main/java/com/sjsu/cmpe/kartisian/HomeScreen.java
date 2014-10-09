package com.sjsu.cmpe.kartisian;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;


public class HomeScreen extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    DBHandler dbhandle;
    String DestinationString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        dbhandle = new DBHandler(getApplicationContext());
        spinner = (Spinner)findViewById(R.id.placesspinner);
        spinner.setOnItemSelectedListener(this);
        loadSpinnerData();
    }

    private void loadSpinnerData()
    {
        List<String> places = dbhandle.getAllPlaces();

       // ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,places);
        ArrayAdapter adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,places);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onItemSelected(AdapterView <?> parent, View view, int position,long id)
    {
        //String places = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0 )
    {

    }
    public void Addr_Selectors(View view)
    {
        Intent intent = new Intent(this,Add_New_Location_Selections.class);
        startActivity(intent);
    }

    public void gotoMaps(View view)
    {

        Spinner spinner1 = (Spinner)findViewById(R.id.placesspinner);
        String spinnertext = spinner1.getSelectedItem().toString();
        DestinationString = dbhandle.getAPlace(spinnertext);
        if (DestinationString!=null)
        {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+DestinationString));
                startActivity(intent);
        }
        else
            Toast.makeText(getApplicationContext(),"No address found",Toast.LENGTH_LONG).show();
    }

}
