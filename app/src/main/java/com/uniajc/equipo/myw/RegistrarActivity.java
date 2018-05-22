package com.uniajc.equipo.myw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrarActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener{
    RequestQueue rq;
    JsonRequest jrq;
    private EditText nombre, apellido, contrasena, email;
    private Button guardar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        nombre=(EditText)findViewById(R.id.Et_nombreR);
        apellido=(EditText)findViewById(R.id.Et_apellidoR);
        contrasena=(EditText)findViewById(R.id.Et_contrsaeñaR);
        email=(EditText)findViewById(R.id.Et_emailR);
        guardar=(Button)findViewById(R.id.Btn_registrarR);
        // Instantiate the RequestQueue.
        rq = Volley.newRequestQueue(RegistrarActivity.this);

        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!nombre.getText().toString().trim().equalsIgnoreCase("") ||
                        !apellido.getText().toString().trim().equalsIgnoreCase("") ||
                        !contrasena.getText().toString().trim().equalsIgnoreCase("") ||
                        !email.getText().toString().trim().equalsIgnoreCase("")) {

                    RegistroBd();
                } else {
                    Toast.makeText(RegistrarActivity.this, "Datos insertados con exito" , Toast.LENGTH_LONG).show();
                   // Toast.makeText(RegistrarActivity.this, "Hay campos por ingresar", Toast.LENGTH_LONG).show();
                }

            }

        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(RegistrarActivity.this, "No se pudo registrar el usuario "+error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        User usuario=new User();
        Toast.makeText(RegistrarActivity.this, "Datos insertados con exito" , Toast.LENGTH_LONG).show();

        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObject=null;

        try {
            jsonObject= jsonArray.getJSONObject(0);
            usuario.setNombre(jsonObject.optString("nombre"));
            usuario.setApellido(jsonObject.optString("apellido"));
            usuario.setContrasena(jsonObject.optString("contrasena"));
            usuario.setEmail(jsonObject.optString("email"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    private void  RegistroBd(){
        String url ="http://192.168.1.34/login/registrarBd.php?nombre="+nombre.getText().toString().trim()+
                "&apellido="+apellido.getText().toString().trim()+"&contrasena="+contrasena.getText().toString().trim()+
                "&email="+email.getText().toString().trim();

        jrq=new JsonObjectRequest(Request.Method.GET, url,null, this,this);
        rq.add(jrq);
    }
}
