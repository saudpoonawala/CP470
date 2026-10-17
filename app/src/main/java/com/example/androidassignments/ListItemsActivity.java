package com.example.androidassignments;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AlertDialog;
import android.provider.MediaStore;
import android.graphics.Bitmap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

public class ListItemsActivity extends AppCompatActivity {
    private static final String TAG = "ListItemsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "inside onCreate");
        setContentView(R.layout.activity_list_items);

        RadioButton radioButton = findViewById(R.id.radioButton);
        CheckBox checkBox = findViewById(R.id.checkBox);
        checkBox.setOnClickListener(v -> {
            new AlertDialog.Builder(ListItemsActivity.this)
                    .setMessage(R.string.finish_activity_question)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("Response",
                                getString(R.string.activity_response));
                        setResult(Activity.RESULT_OK, resultIntent);

                        dialog.dismiss();
                        finish();
                    })
                    .setNegativeButton(R.string.no, (dialog, which) -> {
                        dialog.dismiss();
                        checkBox.setChecked(false);
                    })
                    .show();
        });

        SwitchCompat switchButton = findViewById(R.id.switchButton);
        switchButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Toast.makeText(ListItemsActivity.this,
                        R.string.switch_on, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ListItemsActivity.this,
                        R.string.switch_off, Toast.LENGTH_LONG).show();
            }
        });

        ImageButton imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            try {
                startActivityForResult(takePictureIntent, 20);
            } catch (android.content.ActivityNotFoundException e) {
                print(getString(R.string.camera_unavailable));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 20 && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Object photo = data.getExtras().get("data");

                if (photo instanceof Bitmap) {
                    ImageButton imageButton = findViewById(R.id.imageButton);
                    imageButton.setImageBitmap((Bitmap) photo);
                    Log.i(TAG, "Camera photo captured successfully");
                } else {
                    print(getString(R.string.photo_load_error));
                }
            } else {
                print(getString(R.string.no_photo_returned));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "inside onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "inside onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "inside onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "inside onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "inside onDestroy");
    }
    private void print(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}