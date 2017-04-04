package mx.edu.uts.saferide;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class VerFotoU extends AppCompatActivity {

    Correo co = new Correo();
    String cor = co.getCorreo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_foto_u);
        mostrarfoto(recogerExtras());
    }
    public void subirfotoUOnclick(View view){
        Thread nt = new Thread() {
            @Override
            public void run() {
                try {
                    final String res;
                    res = enviarPost(recogerExtras(),cor);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!res.equals("-1")) {
                                Toast.makeText(VerFotoU.this, "Su foto se subió con exito.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(VerFotoU.this,
                                        Perfil.class);
                                startActivity(intent);

                            } else {
                                Toast.makeText(VerFotoU.this, "Ocurrio un error al subir su foto, intentelo de nuevo más tarde.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(VerFotoU.this, "Ocurrio un error al subir su foto, intentelo de nuevo más tarde.", Toast.LENGTH_LONG).show();
                }
            }
        };
        nt.start();

    }
    public String recogerExtras() {
        Bundle extras = this.getIntent().getExtras();
        String value = extras.getString("image");
        return value;
    }

    public void mostrarfoto(String foto){
        byte[] imagen = Base64.decode(foto, Base64.DEFAULT);
        Bitmap myBitmap = BitmapFactory.decodeByteArray(imagen,0,imagen.length);
        ImageView myImage = (ImageView) findViewById(R.id.imgPerfilU);
        myImage.setImageBitmap(myBitmap);
    }

    public String enviarPost(String foto,String correo) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormEncodingBuilder()
                .add("FotoPerfil", foto)
                .add("Correo", correo)
                .build();

        Request request = new  Request.Builder()
                .url("https://saferide-adanc.c9users.io/SafeRide/SubirFotoU.php")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
