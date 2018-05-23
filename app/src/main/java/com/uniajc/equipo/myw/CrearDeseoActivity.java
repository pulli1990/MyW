package com.uniajc.equipo.myw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class CrearDeseoActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    RequestQueue rq;
    JsonRequest jrq;
    private EditText nombreDe, descripcionDe, costeDe, ahorroIniDe;
    private TextView idUserCrear;
    private Button guardarDe, atrasDe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_deseo);
        idUserCrear=(TextView) findViewById(R.id.et_idUserCrear);
        nombreDe=(EditText)findViewById(R.id.Et_NombreDese);
        descripcionDe=(EditText)findViewById(R.id.Et_descripcionDe);
        costeDe=(EditText)findViewById(R.id.Et_coste);
        ahorroIniDe=(EditText)findViewById(R.id.Et_AhorroIni);
        guardarDe=(Button) findViewById(R.id.btn_guardarDeseo);
        atrasDe=(Button)findViewById(R.id.btn_atrasDeseos);
// Instantiate the RequestQueue.
        rq = Volley.newRequestQueue(CrearDeseoActivity.this);

        Bundle b = getIntent().getExtras();
        String id = b.getString("idUserCrearDeseo");
        idUserCrear.setText(""+id);
        Toast.makeText(CrearDeseoActivity.this, "id crear"+id,Toast.LENGTH_LONG).show();

        guardarDe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (!nombreDe.getText().toString().trim().equalsIgnoreCase("") ||
                        !descripcionDe.getText().toString().trim().equalsIgnoreCase("") ||
                        !costeDe.getText().toString().trim().equalsIgnoreCase("") ||
                        !ahorroIniDe.getText().toString().trim().equalsIgnoreCase("")) {

                    RegistroBdDeseo();
                } else {
                    Toast.makeText(CrearDeseoActivity.this, "Hay campos por ingresar" , Toast.LENGTH_LONG).show();
                    // Toast.makeText(RegistrarActivity.this, "Hay campos por ingresar", Toast.LENGTH_LONG).show();
                }

            }

        });



    }

    @Override
    public void onErrorResponse(VolleyError error) {

        Toast.makeText(CrearDeseoActivity.this, "Deseo guardado correctamente" , Toast.LENGTH_LONG).show();
        nombreDe.setText("");
        descripcionDe.setText("");
        costeDe.setText("");
        ahorroIniDe.setText("");
        idUserCrear.setText("");
       // Toast.makeText(CrearDeseoActivity.this, "No se pudo guardar el Deseo ",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        Deseo deseo=new Deseo();

        Toast.makeText(CrearDeseoActivity.this, "Deseo guardado correctamente" , Toast.LENGTH_LONG).show();

        JSONArray jsonArray=response.optJSONArray("datosDeseo");
        JSONObject jsonObject=null;

        try {
            jsonObject= jsonArray.getJSONObject(0);

            deseo.setNombreDe(jsonObject.optString("nombre"));
            deseo.setCostoDe(jsonObject.optString("costo"));
            deseo.setDescripcionDe(jsonObject.optString("descripcion"));
            deseo.setAhorroInicialDe(jsonObject.optString("ahorro"));
            deseo.setIdUsuario(jsonObject.optString("id_usuario_fk"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void  RegistroBdDeseo(){
        String url ="http://192.168.1.34/login/registrarBdDeseo.php?nombre="+nombreDe.getText().toString().trim()+
                "&descripcion="+descripcionDe.getText().toString().trim()+"&costo="+costeDe.getText().toString().trim()+
                "&ahorro="+ahorroIniDe.getText().toString().trim()+"&id_usuario_fk="+idUserCrear.getText().toString().trim();

        jrq=new JsonObjectRequest(Request.Method.GET, url,null, this,this);
        rq.add(jrq);
    }

}
