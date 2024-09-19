package ru.mirea.stehovva.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;
    EditText ed1;
    EditText ed2;
    EditText ed3;
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getSharedPreferences("mirea_settings", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        ed1 = findViewById(R.id.editTextText);
        ed2 = findViewById(R.id.editTextText2);
        ed3 = findViewById(R.id.editTextText3);
        b = findViewById(R.id.button);

        ed1.setText(sharedPref.getString("GROUP", ""));
        ed2.setText(sharedPref.getString("NUMBER", ""));
        ed3.setText(sharedPref.getString("MOVIE", ""));


    }

    public void Save(View view) {
        editor.putString("GROUP", ed1.getText().toString());
        editor.putString("NUMBER", ed2.getText().toString());
        editor.putString("MOVIE", ed3.getText().toString());
        editor.apply();
    }
}