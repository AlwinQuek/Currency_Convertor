package com.example.enhanzcom.currency_convertor;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class CConvertor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cconvertor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Auto detect amount changes and calculate the total amount between
        EditText textMessage1;
        textMessage1 = (EditText)findViewById(R.id.editText2);
        textMessage1.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                EditText Amount,Rate;
                Double Total,number1,number2;
                Amount = (EditText)findViewById(R.id.editText2);
                if (Amount.getText().toString().equals(""))
                {
                    number1 =0.00;
                }
                else
                {
                    number1 = Double.parseDouble(Amount.getText().toString());
                }
                Rate = (EditText)findViewById(R.id.editText3);
                if (Rate.getText().toString().equals(""))
                {
                    number2 =0.00;
                }
                else
                {
                    number2 = Double.parseDouble(Rate.getText().toString());
                }
                Total = number1 * number2;
                TextView t = (TextView) findViewById(R.id.textView4);
                t.setText(Total.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        EditText textMessage2;
        textMessage2 = (EditText)findViewById(R.id.editText3);
        textMessage2.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                EditText Amount,Rate;
                Double Total,number1,number2;
                Amount = (EditText)findViewById(R.id.editText2);
                if (Amount.getText().toString().equals(""))
                {
                    number1 =0.00;
                }
                else
                {
                    number1 = Double.parseDouble(Amount.getText().toString());
                }
                Rate = (EditText)findViewById(R.id.editText3);
                if (Rate.getText().toString().equals(""))
                {
                    number2 =0.00;
                }
                else
                {
                    number2 = Double.parseDouble(Rate.getText().toString());
                }
                Total = number1 * number2;
                TextView t = (TextView) findViewById(R.id.textView4);
                t.setText(Total.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i = new Intent(getApplicationContext(), Home.class);
            startActivity(i);
            setContentView(R.layout.activity_home);
        }  else if (id == R.id.nav_currency_convertor) {
            Intent i = new Intent(getApplicationContext(), MConvertor.class);
            startActivity(i);
            setContentView(R.layout.activity_mconvertor);
        } else if (id == R.id.nav_custom_convertor) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
