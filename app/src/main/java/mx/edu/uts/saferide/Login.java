package mx.edu.uts.saferide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class Login extends AppCompatActivity {
    private String correo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public Login(){}
    public void InicioSesion(View view){
        Thread nt = new Thread(){
            @Override
            public void run(){
                EditText Correo =(EditText) findViewById(R.id.txtLogCorreo);
                EditText Contra =(EditText) findViewById(R.id.txtLogContra);
                try{
                    final String res;
                    final String res2;
                    final String ids= Correo.getText().toString();
                    final String idc= Contra.getText().toString();
                    Correo cor = new Correo();
                    cor.setCorreo(ids);
                    res = leerUsuarios(ids,idc);
                    if (res.equals(res.toString())) {
                        res2 = leerConductores(ids, idc);
                    }else {
                        res2="[]";
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!res.equals("-1")){
                                if (ids.equals("admin")&&idc.equals("admin")){
                                    Intent intent = new Intent(Login.this, IndexAdmin.class);
                                    startActivity(intent);

                                }else {

                                    if (!res.equals("[]")) {
                                        Intent intent = new Intent(Login.this,
                                                IndexUser.class);
                                        startActivity(intent);


                                    } else {


                                        if (!res2.equals("[]")) {
                                            Intent intent = new Intent(Login.this,
                                                    IndexConductor.class);
                                            startActivity(intent);


                                        } else {

                                            Toast.makeText(Login.this, "Ingrese datos correctos", Toast.LENGTH_LONG).show();
                                        }

                                    }
                                }


                            }else{
                                Toast.makeText(Login.this, "Llene todos los campos", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(Login.this, "Error al eliminar", Toast.LENGTH_LONG).show();
                }
            }

        };
        nt.start();
    }

    public String leerUsuarios(String correo, String contra){

        try{
            OkHttpClient client = new OkHttpClient();
            //          RequestBody body = new FormEncodingBuilder().add("UsuCorreo", correo)
//                    .add("UsuContra", contra).build();
            Request request = new Request.Builder().url("https://saferide-adanc.c9users.io/SafeRide/loginUsuarios.php?UsuCorreo="+correo+"&UsuContra="+contra).build();
            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            return  null;
        }
    }

    public String leerConductores(String correo, String contra){

        try{
            OkHttpClient client = new OkHttpClient();
            //RequestBody body = new FormEncodingBuilder().add("ConCorreo", correo)
            //      .add("ConContra", contra).build();
            Request request2 = new Request.Builder().url("https://saferide-adanc.c9users.io/SafeRide/loginConductores.php?ConCorreo="+correo+"&ConContra="+contra).build();
            Response response2 = client.newCall(request2).execute();
            return response2.body().string();
        } catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            return  null;
        }
    }

}