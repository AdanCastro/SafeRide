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

public class RegistroUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);
    }
    public void registrarUsuarioOnclick(View view){
        Thread nt = new Thread() {
            @Override
            public void run() {
                EditText UsuCelular = (EditText) findViewById(R.id.txtCel);
                EditText UsuNombre = (EditText) findViewById(R.id.txtNombre);
                EditText UsuApellido = (EditText) findViewById(R.id.txtApellido);
                EditText UsuCorreo = (EditText) findViewById(R.id.txtCorreo);
                EditText UsuContra = (EditText) findViewById(R.id.txtContra);
                EditText UsuUbicacion = (EditText) findViewById(R.id.txtUbicacion);
                EditText UsuEscuela = (EditText) findViewById(R.id.txtEscuela);
                try {
                    final String res;
                    res = enviarPost(UsuCelular.getText().toString(), UsuNombre.getText().toString(), UsuApellido.getText().toString(), UsuCorreo.getText().toString(), UsuContra.getText().toString(), UsuUbicacion.getText().toString(), UsuEscuela.getText().toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!res.equals("-1")) {
                                Toast.makeText(RegistroUsuario.this, "Su registro como Usuario fue exitoso.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegistroUsuario.this,
                                        Login.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegistroUsuario.this, "Ocurrio un error al guardar sus datos, intentelo de nuevo más tarde.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(RegistroUsuario.this, "Ocurrio un error al guardar sus datos, intentelo de nuevo más tarde.", Toast.LENGTH_LONG).show();
                }

            }

        };
        nt.start();

    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public String enviarPost(String UsuCelular, String UsuNombre,String UsuApellido, String UsuCorreo, String UsuContra,
                             String UsuUbicacion,String UsuEscuela) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormEncodingBuilder()
                .add("UsuCelular", UsuCelular)
                .add("UsuNombre", UsuNombre)
                .add("UsuApellido", UsuApellido)
                .add("UsuCorreo",UsuCorreo )
                .add("UsuContra", UsuContra )
                .add("UsuUbicacion", UsuUbicacion)
                .add("UsuEscuela", UsuEscuela)
                .build();

        Request request = new  Request.Builder()
                .url("https://saferide-adanc.c9users.io/SafeRide/registroUsuario.php")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }


}
