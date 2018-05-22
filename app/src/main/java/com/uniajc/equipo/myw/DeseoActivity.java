package com.uniajc.equipo.myw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DeseoActivity extends AppCompatActivity {

public static final  String nombre="nombre";
private TextView usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deseo);
        usuario=(TextView)findViewById(R.id.tv_nombreBienvenido);
        String usuario1=getIntent().getStringExtra("nombre");
        usuario.setText("Bienvenido  "+usuario1);
    }


}
