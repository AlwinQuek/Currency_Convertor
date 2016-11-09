package com.example.enhanzcom.currency_convertor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Iterator;

public class MConvertor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mconvertor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Spinner dropdown1 = (Spinner) findViewById(R.id.currency_spinner1);
        final Spinner dropdown2 = (Spinner) findViewById(R.id.currency_spinner2);
        String[] items = new String[]{"AUD", "BGN", "BRL", "CAD", "CHF", "CNY", "CZK", "DKK", "EUR", "GBP", "HKD", "HRK", "HUF", "IDR", "ILS", "INR", "JPY", "KRW", "MXN", "MYR", "NOK", "NZD", "PHP", "PLN", "RON", "RUB", "SEK", "SGD", "THB", "USD", "ZAR"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown1.setAdapter(adapter);
        dropdown2.setAdapter(adapter);
        try {
            Field popup = Spinner.class.getDeclaredField("mPopup");
            popup.setAccessible(true);
            android.widget.ListPopupWindow popupWindow1 = (android.widget.ListPopupWindow) popup.get(dropdown1);
            android.widget.ListPopupWindow popupWindow2 = (android.widget.ListPopupWindow) popup.get(dropdown2);
            popupWindow1.setHeight(1000);
            popupWindow2.setHeight(1000);
        }
        catch (NoClassDefFoundError | ClassCastException | NoSuchFieldException | IllegalAccessException e) {

        }

        dropdown1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner dropdown3 = (Spinner) findViewById(R.id.currency_spinner1);
                String text = dropdown3.getSelectedItem().toString();
                text = text.toLowerCase();
                ImageView image =(ImageView) findViewById(R.id.imageView2);
                int resId = getResources().getIdentifier(text, "drawable", getPackageName());
                image.setImageResource(resId);
                Calculate_Rate();
                GETCHART_Function();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        dropdown2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner dropdown3 = (Spinner) findViewById(R.id.currency_spinner2);
                String text = dropdown3.getSelectedItem().toString();
                text = text.toLowerCase();
                ImageView image =(ImageView) findViewById(R.id.imageView3);
                int resId = getResources().getIdentifier(text, "drawable", getPackageName());
                image.setImageResource(resId);
                Calculate_Rate();
                GETCHART_Function();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Another interface callback
            }
        });

        EditText textMessage;
        textMessage = (EditText)findViewById(R.id.currency_amount);
        textMessage.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                Calculate_Rate();
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("MConvertor Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
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

        } else if (id == R.id.nav_custom_convertor) {
            Intent i = new Intent(getApplicationContext(), CConvertor.class);
            startActivity(i);
            setContentView(R.layout.activity_cconvertor);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //function to calculate live rate for last 5 year between two currency and plot to a graph .
    public void GETCHART_Function()
    {
        final double[] GraphXValue = new double[6];
        final int[] Year = new int[6];
        for (int x = 1; x <= 5; x++) {
            Calendar cal = Calendar.getInstance();
            Spinner dropdown = (Spinner) findViewById(R.id.currency_spinner1);
            String text = dropdown.getSelectedItem().toString();
            int month = cal.get(Calendar.MONTH);
            int date = cal.get(Calendar.DATE);
            int year=cal.get(Calendar.YEAR) + x -5;
            Year[x]=year;
            String url1 = "http://api.fixer.io/latest?base=" + text + "&date="+year+"-"+String.valueOf(month)+"-"+String.valueOf(date)+"";
            final int finalX = x;
            JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url1, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject rates = response.getJSONObject("rates");
                                Iterator<String> keys = rates.keys();
                                while (keys.hasNext()) {
                                    Spinner dropdown1 = (Spinner) findViewById(R.id.currency_spinner2);
                                    String cc = dropdown1.getSelectedItem().toString();
                                    String currency = keys.next();
                                    if (cc.equals(currency)) {
                                        GraphXValue[finalX] = rates.getDouble(currency);
                                    }
                                    if(finalX == 5 ){
                                        GraphView graph = (GraphView) findViewById(R.id.graph);
                                        graph.removeAllSeries();
                                        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                                                new DataPoint(Year[1], GraphXValue[1]),
                                                new DataPoint(Year[2], GraphXValue[2]),
                                                new DataPoint(Year[3], GraphXValue[3]),
                                                new DataPoint(Year[4], GraphXValue[4]),
                                                new DataPoint(Year[5], GraphXValue[5])
                                        });
                                        graph.getViewport().setMinY(0);
                                        graph.getViewport().setMaxY(100);
                                        graph.getViewport().setMinX(Year[1]);
                                        graph.getViewport().setMaxX(Year[5]);
                                        graph.getViewport().setScalable(true);
                                        graph.getViewport().setScalableY(true);
                                        graph.setTitleTextSize(100);
                                        graph.setTitle("LAST 5 YEAR RATE CHART");
                                        graph.addSeries(series);
                                        series.setDrawDataPoints(true);
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            MySingleton.getInstance(MConvertor.this).addToRequestQueue(jsonObjectRequest1);
        }
    }

    //function to calculate live rate between two currency.
    public void Calculate_Rate()
    {
        Spinner dropdown = (Spinner) findViewById(R.id.currency_spinner1);
        String text = dropdown.getSelectedItem().toString();
        String url = "http://api.fixer.io/latest?base=" + text;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        String text = "";
                        double cc1_tamount, cc1_amount;
                        EditText cc1;
                        try {
                            JSONObject rates = response.getJSONObject("rates");
                            Iterator<String> keys = rates.keys();

                            StringBuilder sb = new StringBuilder();
                            while (keys.hasNext()) {
                                DecimalFormat df = new DecimalFormat("0.00");
                                Spinner dropdown1 = (Spinner) findViewById(R.id.currency_spinner2);
                                String cc = dropdown1.getSelectedItem().toString();
                                cc1 = (EditText) findViewById(R.id.currency_amount);
                                if (cc1.getText().toString().equals(""))
                                {
                                    cc1_amount =0;
                                }
                                else
                                {
                                    cc1_amount = Double.parseDouble(cc1.getText().toString());
                                }
                                String currency = keys.next();
                                if (cc.equals(currency)) {
                                    cc1_tamount = rates.getDouble(currency) * cc1_amount;
                                    text = df.format(cc1_tamount);
                                }
                            }
                            TextView t = (TextView) findViewById(R.id.cc_text1);
                            t.setText(text);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(MConvertor.this).addToRequestQueue(jsonObjectRequest);

    }

}
