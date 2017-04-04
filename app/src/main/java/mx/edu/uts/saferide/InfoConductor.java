package mx.edu.uts.saferide;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.snowdream.android.widget.SmartImageView;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class InfoConductor extends AppCompatActivity {


    Correo c = new Correo();
    String correo = c.getCorreo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_conductor);
        Button btnAlertas2 = (Button)findViewById(R.id.btnContratar);
        final String corr = recogerExtras();
        CargarPerfilCon(corr);
        btnAlertas2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notificacioncompuesta(corr);
            }
        });

    }

    public String recogerExtras() {
        Bundle extras = this.getIntent().getExtras();
        String value = extras.getString("parametro");
        return value;
    }
    //Método para mostrar la notificación a la hora de contratar a un conductor
    private void notificacioncompuesta(final String corr) {
        android.app.AlertDialog.Builder notificacion = new android.app.AlertDialog.Builder(this);
        notificacion.setTitle("Confirmación");
        notificacion.setMessage("Se va a contratar a este conductor");

        notificacion.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, int which) {

                Thread nt = new Thread(){
                    @Override
                    public void run(){
                        final Usuario usu = usuarioJSON(consultarUsuario(correo));
                        TextView f = (TextView)findViewById(R.id.tvFecha);
                        String fechainicio = f.getText().toString();
                        String fechafinal= "2017-04-07";
                        String escuela=usu.getUsuEscuela();
                        String ubicacion=usu.getUsuUbicacion();
                        try {
                            final String res;
                            res= contratoConductor(fechainicio,fechafinal,escuela,ubicacion,correo,corr);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (!res.equals("-1")){
                                        dialog.cancel();
                                        Toast.makeText(InfoConductor.this, "Se ha contratado a este condutor", Toast.LENGTH_LONG).show();
                                        Intent act = new Intent(InfoConductor.this, IndexUser.class);
                                        startActivity(act);
                                    }else{
                                        Toast.makeText(InfoConductor.this, "Ocurrio un error al guardar sus datos, intentelo de nuevo más tarde.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }catch (Exception e){
                            Toast.makeText(InfoConductor.this, "Ocurrio un error al guardar sus datos, intentelo de nuevo más tarde.", Toast.LENGTH_LONG).show();
                        }

                    }

                };
                nt.start();
                /*
                consultarUsuario(correo);

                TextView f = (TextView)findViewById(R.id.tvFecha);
                String fecha = f.getText().toString();
                String fechafinal= "2017-04-31";


                //DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = format.parse(fecha);

                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
            }
        });
        notificacion.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        notificacion.show();
    }
    public String contratoConductor(String FechaInicio,String FechaFinal, String Escuela, String Ubicacion, String UsuCorreo, String ConCorreo) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormEncodingBuilder()
                .add("PaqFechaI", FechaInicio)
                .add("PaqFechaF", FechaFinal)
                .add("PaqEscuela", Escuela)
                .add("PaqUbicacion",Ubicacion )
                .add("PaqUsuCorreo", UsuCorreo )
                .add("PaqConCorreo", ConCorreo)
                .build();

        Request request = new  Request.Builder()
                .url("https://saferide-adanc.c9users.io/SafeRide/Contrato.php")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private void CargarPerfilCon(final String corre) {
        Thread tr = new Thread(){

            @Override
            public void  run(){
                final Conductor con = conductorJSON(consultarConductor(corre));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //try {

                        TextView tvnombre, tvapellido, tvauto,tvubicacion,tvpasajeros;
                        SmartImageView sivperfilC;
                        tvnombre=(TextView)findViewById(R.id.tvNombreC);
                        tvapellido=(TextView)findViewById(R.id.tvApellidoC);
                        tvpasajeros=(TextView)findViewById(R.id.tvPasajerosC);
                        tvauto=(TextView)findViewById(R.id.tvAutoC);
                        tvubicacion=(TextView)findViewById(R.id.tvUbicacionC);
                        sivperfilC = (SmartImageView)findViewById(R.id.img_infoConductor);

                        tvnombre.setText(con.getNombre());
                        tvapellido.setText( con.getApellido());
                        tvauto.setText( con.getAuto());
                        tvubicacion.setText( con.getUbicacion());
                        tvpasajeros.setText(con.getPasajeros());
                        String foto = con.getFotoC();
                        String urlfinal="https://saferide-adanc.c9users.io/SafeRide/Images/"+foto;
                        Rect rect = new Rect(sivperfilC.getLeft(),sivperfilC.getTop(),sivperfilC.getRight(),sivperfilC.getBottom());
                        sivperfilC.setImageUrl(urlfinal,rect);
                    }
                });
            }
        };
        tr.start();
    }
    public String consultarConductor(String correo) {

        try{
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormEncodingBuilder().add("ConCorreo", correo).build();
            Request request = new Request.Builder().url("https://saferide-adanc.c9users.io/SafeRide/perfilConductor.php")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            return  null;
        }
    }
    public String consultarUsuario(String correo) {

        try{
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormEncodingBuilder().add("UsuCorreo", correo).build();
            Request request = new Request.Builder().url("https://saferide-adanc.c9users.io/SafeRide/perfilUsuario.php")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            return  null;
        }

    }
    public Usuario usuarioJSON(String cadenaJSON){
        Usuario usu = new Usuario();

        try {
            JSONArray jsonarr = new JSONArray(cadenaJSON);
            JSONObject jObj = jsonarr.getJSONObject(0);

            usu.setUsucorreo(jObj.getString("UsuCorreo"));
            usu.setUsunombre(jObj.getString("UsuNombre"));
            usu.setUsuapellido(jObj.getString("UsuApellido"));
            usu.setUsuUbicacion(jObj.getString("UsuUbicacion"));
            usu.setUsuFoto(jObj.getString("UsuFoto"));
            usu.setUsuEscuela(jObj.getString("UsuEscuela"));


        } catch (JSONException e) {
            return null;
        }

        return usu;
    }

    // Convertir en objeto

    public Conductor conductorJSON(String cadenaJSON){
        Conductor con = new Conductor();

        try {
            JSONArray jsonarr = new JSONArray(cadenaJSON);
            JSONObject jObj = jsonarr.getJSONObject(0);
            con.setNombre(jObj.getString("ConNombre"));
            con.setApellido(jObj.getString("ConApellido"));
            con.setUbicacion(jObj.getString("ConUbicacion"));
            con.setFotoC(jObj.getString("ConFoto"));
            con.setAuto(jObj.getString("ConAuto"));
            con.setPasajeros(jObj.getString("ConNumPasajeros"));


        } catch (JSONException e) {
            return null;
        }

        return con;
    }
}
