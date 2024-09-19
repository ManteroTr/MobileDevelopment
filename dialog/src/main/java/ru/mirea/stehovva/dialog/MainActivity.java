package ru.mirea.stehovva.dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void OnClickShowDialog(View view){
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void OnClickDate(View view) {
        MyDateDialogFragment dateDialogFragment = new MyDateDialogFragment();
        dateDialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void OnClickProgress(View view) {
        MyProgressDialogFragment progressDialogFragment = new MyProgressDialogFragment();
        progressDialogFragment.show(getSupportFragmentManager(), "mirea");
    }

    public void OnClickTime(View view) {
        MyTimeDialogFragment timeDialogFragment = new MyTimeDialogFragment();
        timeDialogFragment.show(getSupportFragmentManager(), "mirea");
    }
}