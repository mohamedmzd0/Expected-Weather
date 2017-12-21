package com.example.mohamedabdelaziz.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView_CityName, mTextView_Time, mTextView_TextDesc, mTextView_DegreeDesc, mTextView_windDesc;
    private ImageView mImageView_TodayDesc;
    private SearchView mSearchView;
    private TextView mTextView_k, mTextView_thi, mTextView_wind;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private FloatingActionButton team;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor = sharedPreferences.edit();
        setUpViews();
        Long tt = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd / MM / yyyy \n kk:mm a");
        mTextView_Time.setText("" + simpleDateFormat.format(tt));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getapi(query.toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        getapi("banha");
    }

    private void getapi(final String country) {
        APIInterface interf = APIClient.getAPI().create(APIInterface.class);
        final Call<Example> data = interf.getdata("/data/2.5/weather?APPID=804f526daffcb2784584800c987d5447&q=" + country.toLowerCase());
        data.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.message().equalsIgnoreCase("ok")) {
                    try {
                        mTextView_CityName.setText("" + response.body().getName());
                        editor.putString("cityname", response.body().getName());
                        mTextView_TextDesc.setText("" + response.body().getWeather().get(0).getDescription());
                        editor.putString("textdesc", response.body().getWeather().get(0).getDescription());
                        int temperatue = (int) (response.body().getMain().getTemp() - 273);
                        mTextView_DegreeDesc.setText("" + temperatue);
                        mTextView_windDesc.setText("Today Wind Speed " + response.body().getWind().getDeg() + "\n" + "Today Pressure " + response.body().getMain().getPressure());
                        editor.putInt("temp", temperatue);
                        editor.putString("image", response.body().getWeather().get(0).getMain());
                        if (response.body().getWeather().get(0).getMain().equals("Clouds"))
                            mImageView_TodayDesc.setBackgroundResource(R.drawable.cloudy);
                        else if (response.body().getWeather().get(0).getMain().equals("Rain"))
                            mImageView_TodayDesc.setBackgroundResource(R.drawable.rain);
                        else if (response.body().getWeather().get(0).getMain().equals("Snow"))
                            mImageView_TodayDesc.setBackgroundResource(R.drawable.snowing);
                        else if (response.body().getWeather().get(0).getMain().equals("Clear"))
                            mImageView_TodayDesc.setBackgroundResource(R.drawable.sun);
                        editor.putString("speed", "" + response.body().getWind().getSpeed());
                        editor.putString("hum", "" + response.body().getMain().getHumidity());
                        mTextView_thi.setText("human feels " + (new ExpertSystem().THI(response.body().getMain().getTemp() - 273, response.body().getMain().getHumidity())));
                        mTextView_k.setText("expected weather " + (new ExpertSystem().K(response.body().getMain().getTemp() - 273, response.body().getWind().getSpeed())));
                        mTextView_wind.setText("expected wind " + (new ExpertSystem().detect_wind(response.body().getWind().getSpeed())));
                        editor.commit();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "something wrong happened", Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(MainActivity.this, country + " " + response.message(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Check Your Network Connection", Toast.LENGTH_SHORT).show();
                mTextView_CityName.setText("" + sharedPreferences.getString("cityname", "banha"));
                mTextView_TextDesc.setText("" + sharedPreferences.getString("textdesc", ""));
                mTextView_DegreeDesc.setText("" + sharedPreferences.getInt("temp", -1));
                if (sharedPreferences.getString("image", "non").equals("Clouds"))
                    mImageView_TodayDesc.setBackgroundResource(R.drawable.cloudy);
                else if (sharedPreferences.getString("image", "non").equals("Rain"))
                    mImageView_TodayDesc.setBackgroundResource(R.drawable.rain);
                else if (sharedPreferences.getString("image", "non").equals("Snow"))
                    mImageView_TodayDesc.setBackgroundResource(R.drawable.snowing);
                else if (sharedPreferences.getString("image", "non").equals("Clear"))
                    mImageView_TodayDesc.setBackgroundResource(R.drawable.sun);
                mTextView_thi.setText("human feels " + (new ExpertSystem().THI(sharedPreferences.getInt("temp", -1), Double.parseDouble(sharedPreferences.getString("hum", "0")))));
                mTextView_k.setText("expected weather " + (new ExpertSystem().K(sharedPreferences.getInt("temp", -1), Double.parseDouble(sharedPreferences.getString("speed", "0")))));
                mTextView_wind.setText("expected wind " + (new ExpertSystem().detect_wind(Double.parseDouble(sharedPreferences.getString("speed", "0")))));
            }
        });
    }

    private void setUpViews() {
        mSearchView = (SearchView) findViewById(R.id.search_view);
        mTextView_CityName = (TextView) findViewById(R.id.city_name_textview);
        mTextView_Time = (TextView) findViewById(R.id.time_textview);
        mTextView_TextDesc = (TextView) findViewById(R.id.today_text_desc);
        mTextView_DegreeDesc = (TextView) findViewById(R.id.today_degree_desc);
        mImageView_TodayDesc = (ImageView) findViewById(R.id.today_image_desc);
        mTextView_k = (TextView) findViewById(R.id.k_textview);
        mTextView_thi = (TextView) findViewById(R.id.thi_textview);
        mTextView_wind = (TextView) findViewById(R.id.wind_textview);
        mTextView_windDesc = (TextView) findViewById(R.id.today_wind_desc);

        team = (FloatingActionButton) findViewById(R.id.team);
        team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TeamActivity.class));
            }
        });
    }
}