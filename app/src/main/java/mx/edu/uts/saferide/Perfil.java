package mx.edu.uts.saferide;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Perfil extends AppCompatActivity {

    private static final int SELECT_FILE = 1;
    Uri imageUri;
    Correo co = new Correo();
    String cor = co.getCorreo();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        CargarPerfilU(cor);
    }

    //Codigo para setear los datos de la consulta

    private void CargarPerfilU(final String corre) {

        Thread tr = new Thread(){

            @Override
            public void  run(){
                //Aqui va el recoger Extras
                final Usuario usu = usuarioJSON(consultarUsuario(corre));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //try {

                        TextView tvnombre, tvapellido, tvcorreo,tvubicacion;
                        SmartImageView sivperfilU;
                        tvnombre=(TextView)findViewById(R.id.lblNombrePU);
                        tvapellido=(TextView)findViewById(R.id.lblApellidoPU);
                        tvcorreo=(TextView)findViewById(R.id.lblCorreoPU);
                        tvubicacion=(TextView)findViewById(R.id.lblUbiPU);
                        sivperfilU = (SmartImageView)findViewById(R.id.img_perfilu);

                        tvnombre.setText(usu.getUsunombre());
                        tvapellido.setText( usu.getUsuapellido());
                        tvcorreo.setText( usu.getUsucorreo());
                        tvubicacion.setText( usu.getUsuUbicacion());
                        String foto = usu.getUsuFoto();
                        String urlfinal="https://saferide-adanc.c9users.io/SafeRide/Images/"+foto;
                        Rect rect = new Rect(sivperfilU.getLeft(),sivperfilU.getTop(),sivperfilU.getRight(),sivperfilU.getBottom());
                        sivperfilU.setImageUrl(urlfinal,rect);

                    }
                });
            }
        };


        tr.start();


    }

    //Consulta

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

    // Convertir en objeto

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


        } catch (JSONException e) {

        }

        return usu;
    }

    public void abrirGaleriaU(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, "Seleccione una imagen"),
                SELECT_FILE);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == SELECT_FILE) {
            imageUri = data.getData();
            InputStream imageStream = null;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap foto = BitmapFactory.decodeStream(imageStream);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                foto.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                String image_str = Base64.encodeToString(byteArray, Base64.DEFAULT);
                Intent forwardToB = new Intent(getApplicationContext(), VerFotoU.class);
                forwardToB.putExtra("image", image_str);
                startActivity(forwardToB);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

