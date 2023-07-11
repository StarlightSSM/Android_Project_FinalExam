package com.inhatc.android_finalexamproject;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class WeatherActivity extends AppCompatActivity {
    private TextView dateView;
    private TextView cityView;
    private TextView weatherView;
    private TextView tempView;
    private static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        dateView = findViewById(R.id.dateView);
        cityView = findViewById(R.id.cityView);
        weatherView = findViewById(R.id.weatherView);
        tempView = findViewById(R.id.tempView);
        ImageButton button = findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            } //Image Button의 선언 및 클릭이벤트를 정의하는 과정
        });
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat simpleDateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm:ss a");
        String getDay = simpleDateFormatDay.format(date);
        String getTime = simpleDateFormatTime.format(date);
        //SimpleDateFormat을 통해 다양한 형식으로 현재 시간을 받아 올 수 있다.
        String getDate = getDay + "\n" + getTime;
        dateView.setText(getDate);
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        CurrentWeatherCall();
    }

    private void CurrentWeatherCall() {
        String apiKey = "9756c123e5d680daeb1eaffe19e0ba57";
        String location = "Incheon";
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + apiKey;
        // TODO: url을 사용하여 API 호출 및 데이터 처리 로직 작성
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // JSON 데이터 처리 로직 작성
                    String city = jsonObject.getString("name");
                    cityView.setText(city);
                    JSONArray weatherJson = jsonObject.getJSONArray("weather");
                    JSONObject weatherObj = weatherJson.getJSONObject(0);
                    String weather = weatherObj.getString("description");
                    weatherView.setText(weather);
                    JSONObject tempK = new JSONObject(jsonObject.getString("main"));
                    double tempDo = (Math.round((tempK.getDouble("temp")-273.15)*100)/100.0);
                    tempView.setText(tempDo +  "°C");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }}, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }};
        request.setShouldCache(false);
        requestQueue.add(request);
    }
}