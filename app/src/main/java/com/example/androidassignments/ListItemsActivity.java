package com.example.androidassignments;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class ListItemsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        RadioButton radioButton = findViewById(R.id.radioButton);
        CheckBox checkBox = findViewById(R.id.checkBox);
        SwitchCompat switchButton = findViewById(R.id.switchButton);
        ImageButton imageButton = findViewById(R.id.imageButton);
    }
}