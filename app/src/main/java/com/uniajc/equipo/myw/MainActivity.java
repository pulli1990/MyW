package com.uniajc.equipo.myw;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v4.app.FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.escenario, new SesionFragment()).commit();
    }
}