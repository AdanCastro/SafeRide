package mx.edu.uts.saferide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class RegistroConductor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_conductor);
    }
    public void registrarconduOnClick(View view){
        Thread nt = new Thread(){
            @Override
            public void run(){
                EditText ConCelular=(EditText)findViewById(R.id.txtConCel);
                EditText ConNombre=(EditText)findViewById(R.id.txtConNombre);
                EditText ConApellido=(EditText)findViewById(R.id.txtConApellido);
                EditText ConCorreo=(EditText)findViewById(R.id.txtConCorreo);
                EditText ConContra=(EditText)findViewById(R.id.txtConContra);
                EditText ConUbicacion=(EditText)findViewById(R.id.txtConUbicacion);
                EditText ConNumPasajeros=(EditText)findViewById(R.id.txtConNumPasajeros);
                EditText ConAuto=(EditText)findViewById(R.id.txtConAuto);
                EditText ConPlacas=(EditText)findViewById(R.id.txtConPlaca);
                EditText ConCLABE=(EditText)findViewById(R.id.txtConCLABE);

                try {
                    final String res;
                    res= enviarPost(ConCelular.getText().toString(), ConNombre.getText().toString(), ConApellido.getText().toString(),ConCorreo.getText().toString(),ConContra.getText().toString(), ConUbicacion.getText().toString(), ConNumPasajeros.getText().toString(),ConAuto.getText().toString(),ConPlacas.getText().toString(),ConCLABE.getText().toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!res.equals("-1")){
                                Toast.makeText(RegistroConductor.this, "Su registro como CONDUCTOR fue exitoso.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegistroConductor.this,
                                        Login.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegistroConductor.this, "Ocurrio un error al guardar sus datos, intentelo de nuevo más tarde.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(RegistroConductor.this, "Ocurrio un error al guardar sus datos, intentelo de nuevo más tarde.", Toast.LENGTH_LONG).show();
                }

            }

        };
        nt.start();

    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public String enviarPost(String ConCelular, String ConNombre,String ConApellido, String ConCorreo, String ConContra,
                             String ConUbicacion,String ConNumPasajeros,String ConAuto,String ConPlacas,String ConCLABE) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormEncodingBuilder()
                .add("ConCelular", ConCelular)
                .add("ConNombre", ConNombre)
                .add("ConApellido", ConApellido)
                .add("ConCorreo",ConCorreo )
                .add("ConContra", ConContra )
                .add("ConUbicacion", ConUbicacion)
                .add("ConNumPasajeros", ConNumPasajeros)
                .add("ConAuto", ConAuto )
                .add("ConPlacas", ConPlacas )
                .add("ConCLABE", ConCLABE )
                .build();

        Request request = new  Request.Builder()
                .url("https://saferide-adanc.c9users.io/SafeRide/registroConductor.php")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}

