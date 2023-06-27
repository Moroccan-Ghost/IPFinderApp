package com.example.geolocalisationapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText ipInput;
    String IP_ADDRESS;
    LinearLayout container;
    List<String> infoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.ipInput = findViewById(R.id.IpInput);
        this.container = findViewById(R.id.container);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IP_ADDRESS = ipInput.getText().toString();
                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                String url = "https://ipinfo.io/"+IP_ADDRESS+"/geo";
                System.out.println(url);
                StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            System.out.println(jsonObject);
                            String city = jsonObject.getString("city");
                            String region = jsonObject.getString("region");
                            String country = jsonObject.getString("country");

                            infoList.add("city : " + city);
                            infoList.add("region : " + region);
                            infoList.add("country : " + country);

                            String LatLang = jsonObject.getString("loc");
                            infoList.forEach(text -> {
                                TextView ToAdd = new TextView(MainActivity.this);

                                ToAdd.setText(text);
                                ToAdd.setTextSize(16);
                                ToAdd.setTypeface(null, Typeface.BOLD);
                                ToAdd.setTextColor(Color.WHITE);

                                String hexColor = "#62317E";
                                int color = Color.parseColor(hexColor);
                                ToAdd.setBackgroundColor(color);

                                int paddingY = 20;
                                ToAdd.setPadding(0, paddingY, 0, paddingY);

                                int paddingValue = 20;
                                ToAdd.setPadding(paddingValue, paddingValue, paddingValue, paddingValue);
                                ToAdd.setGravity(Gravity.CENTER);

                                int marginBottom = 20;
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.WRAP_CONTENT
                                );

                                layoutParams.setMargins(0, 0, 0, marginBottom);
                                ToAdd.setLayoutParams(layoutParams);
                                container.addView(ToAdd);

                            });

                            Button MapBtn = new Button(MainActivity.this);
                            MapBtn.setText("Show Map");
                            int paddingY = 30;
                            MapBtn.setPadding(0, paddingY, 0, paddingY);
                            container.addView(MapBtn);

                            MapBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent Map = new Intent(v.getContext(), MapIntent.class);
                                    Map.putExtra("LatLand", LatLang);
                                    startActivity(Map);
                                }
                            });

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("API ERROR  : ",error.toString());
                    }
                });
            }
        });
    }
}
