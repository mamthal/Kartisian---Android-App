package com.sjsu.cmpe.kartisian;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class newAddress extends Activity {

    EditText street, zip, city, placename;
    Button saveAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_address);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_address, menu);
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
    public void onSaveAddress(View view)
    {
        street = (EditText) findViewById(R.id.streetvalue);
        zip = (EditText) findViewById(R.id.zipvalue);
        city = (EditText) findViewById(R.id.cityvalue);
        placename = (EditText) findViewById(R.id.locnamevalue);
        String addr,place;

        addr = new String(street.getText().toString()+","+city.getText().toString()+","+zip.getText().toString());
        place = placename.getText().toString();

        DBHandler dbHandler = new DBHandler(getApplicationContext());
        dbHandler.insertPlace(place,addr,null,null);

        gobackHome(view);
    }

    public void gobackHome(View view)
    {
        Intent intent = new Intent(this,HomeScreen.class);
        startActivity(intent);
    }
}
