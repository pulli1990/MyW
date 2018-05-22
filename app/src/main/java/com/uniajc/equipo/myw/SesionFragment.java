package com.uniajc.equipo.myw;

import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class SesionFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener{
    RequestQueue rq;
    JsonRequest jrq;
    private EditText email, contrasena;
    private Button login, registrarse;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,
                             Bundle savedInstanceState) {
       // return inflater.inflate(R.layout.fragment_sesion, container, false);
View vista=inflater.inflate(R.layout.fragment_sesion, container, false);
        email=(EditText)vista.findViewById(R.id.Pt_email);
        contrasena=(EditText)vista.findViewById(R.id.Et_contraseña);
        login=(Button) vista.findViewById(R.id.Btn_ingresar);
        registrarse=(Button)vista.findViewById(R.id.Btn_registrarse);
// Instantiate the RequestQueue.
       rq = Volley.newRequestQueue(getContext());
registrarse.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        lanzarRegistrarse();
    }
});

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!email.getText().toString().trim().equalsIgnoreCase("") &&
                        !contrasena.getText().toString().trim().equalsIgnoreCase("")) {

                     iniciarSesion();
                } else {
                    Toast.makeText(getContext(), "Hay campos por ingresar", Toast.LENGTH_LONG).show();
                }

            }

        });
        return vista;
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(), "No se encontro el usuario "+error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        User usuario=new User();
        Toast.makeText(getContext(), "El usuario con el email"+email.getText().toString()+" se ha encontrado en nuestras base de datos" , Toast.LENGTH_LONG).show();

        JSONArray jsonArray=response.optJSONArray("datos");
        JSONObject jsonObject=null;

        try {
            jsonObject= jsonArray.getJSONObject(0);
            usuario.setEmail(jsonObject.optString("email"));
            usuario.setContrasena(jsonObject.optString("contrasena"));
            usuario.setNombre(jsonObject.optString("nommbre"));
            usuario.setApellido(jsonObject.optString("apellido"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(getContext(), DesActivity.class);
          intent.putExtra(DesActivity.nombre, usuario.getApellido());
        startActivity(intent);

    }


    private void  iniciarSesion(){
        String url ="http://192.168.1.34/login/sesion.php?email="+email.getText().toString()+
                "&contrasena="+contrasena.getText().toString();

jrq=new JsonObjectRequest(Request.Method.GET, url,null, this,this);
rq.add(jrq);
    }

    public void lanzarRegistrarse(){
        Intent i=new Intent(getContext(),RegistrarActivity.class);
        startActivity(i);
    }

}
