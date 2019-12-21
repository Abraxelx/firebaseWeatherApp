package com.example.myweatherapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    TextView sicaklik;
    TextView cityName;
    TextView date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sicaklik = findViewById(R.id.celcius);
        cityName = findViewById(R.id.cityName);
        date = findViewById(R.id.dateTime);

        myWeather();
    }

    private void myWeather() {
        String URL ="http://api.openweathermap.org/data/2.5/weather?id=300821&appid=79d7253465fa82dbab2190e018c68038&units=Imperial";
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject main_object = response.getJSONObject("main");
                            JSONArray jsonArray = response.getJSONArray("weather");
                            JSONObject object = jsonArray.getJSONObject(0);
                            String temp = String.valueOf(main_object.getDouble("temp"));
                            String description = object.getString("description");
                            String city = response.getString("name");

                            //Sıcaklık
                            double temp_int = Double.parseDouble(temp);
                            double cc = (temp_int - 32)/1.8000;
                            cc = Math.round(cc);
                            int deger = (int)cc;

                            sicaklik.setText(String.valueOf(cc));
                            cityName.setText(city);

                            //ZAMAN
                            Calendar calendar = Calendar.getInstance();
                           // TimeZone tz = TimeZone.getTimeZone("GMT+03");
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", new Locale("tr"));
                            String format_date = simpleDateFormat.format(calendar.getTime());

                            date.setText(format_date);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);

    }



}

