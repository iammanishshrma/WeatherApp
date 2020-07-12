package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String URL="https://api.openweathermap.org/data/2.5/weather?q=";
    private String API = "&appid=2ce076778487262d8e7687d27533e8cf";
    private String myUrl;
    private Button button;
    private EditText city;
    private TextView result;
    private TextView tempView;
    private TextView tempMinView;
    private TextView tempMaxView;
    private TextView humidityView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        city = findViewById(R.id.getcity);
        result = findViewById(R.id.result);
        tempView = findViewById(R.id.temp);
        tempMinView = findViewById(R.id.mintemp);
        tempMaxView = findViewById(R.id.maxtemp);
        humidityView = findViewById(R.id.humidity);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUrl = URL + city.getText().toString() + API;
                Log.d("TAG", "onClick: Tapped" + myUrl);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, myUrl, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", "onResponse: " + response);


                        try {
                            String info = response.getString("weather");
                            Log.d("TAG", "Weather: " + info);

                            JSONArray ar = new JSONArray(info);
                            for(int i = 0; i < ar.length(); i++){
                                JSONObject weObj = ar.getJSONObject(i);
                                result.setText(weObj.getString("main"));
                                Log.d("TAG", "Main: " + weObj.getString("main"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                        try {
                            JSONObject main = response.getJSONObject("main");
                            //Log.d("TAG", "MAIN: " + main.getDouble("temp"));
                           /* double temp = main.getDouble("temp") - 273.15;
                            tempView.setText((int) temp);
                            Log.d("TAG", "onResponse: " + temp);
                            double tempMin = main.getDouble("temp_min") - 273.15;
                            tempMinView.setText((int) tempMin);
                            double tempMax = main.getDouble("temp_max") - 273.15;
                            tempMaxView.setText((int) tempMax);*/
//                            int humidity = main.getInt("humidity");
//                            humidityView.setText(humidity);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("TAG", "onResponse: From settemp");
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("TAG", "onErrorResponse: " + error.getMessage());

                    }
                });
                MySingleton.getInstance(MainActivity.this).addToRequestQueue(jsonObjectRequest);


            }
        });
    }
}