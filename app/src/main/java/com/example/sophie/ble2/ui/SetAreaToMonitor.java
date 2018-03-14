package com.example.sophie.ble2.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sophie.ble2.R;

import static java.lang.Math.sqrt;

/**
 * Created by Sophie on 3/5/2018.
 */

public class SetAreaToMonitor extends Activity {
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_ID = "id";
    //initial values of W and H
    int height = 1;
    int area = 1;
    //number to count with
    public static double final_setting;
    //views with values I am changing in the layout
    TextView area_view;
    TextView height_view;
    TextView final_text_view;
    String id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.input_heigt);
        //find my views
        area_view = findViewById(R.id.area_size);
        height_view = findViewById(R.id.device_height);
        final_text_view = findViewById(R.id.final_result);
        //get device address
        Intent intent = getIntent();
        id = intent.getStringExtra("address");
    }


    //count final distance to monitor
    public void count_final_setting(int area, int height) {
        double result;
        //a2 + b2 = c2
        result = (area * area) + (height * height);
        result = sqrt(result);
        final_setting = result;
        //show me the result
        final_text_view.setText(String.valueOf(final_setting));
    }

    //ipdate initial values
    public void edit_values(View view) {
        int id = view.getId();
        if (id == R.id.increment_area) {
            area++;
        } else if (id == R.id.decrement_area) {
            if (area > 0) {
                area--;
            }
        } else if (id == R.id.increment_height) {
            height++;
        } else if (id == R.id.decrement_height) {
            if (height > 0) {
                height--;
            }
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        //update what I see
        height_view.setText("" + height);
        area_view.setText("" + area);
        count_final_setting(area, height);
        save_final_result(final_setting);

    }

    //save to shared preferencer
    public void save_final_result(double result) {
        SharedPreferences sp = getSharedPreferences(id, MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putLong("final_result", Double.doubleToLongBits(result));
        ed.apply();
    }
}