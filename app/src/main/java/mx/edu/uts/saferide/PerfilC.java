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

public class PerfilC extends AppCompatActivity {

    private static final int SELECT_FILE = 1;
    Uri imageUri;
    Correo co = new Correo();
    String cor = co.getCorreo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_c);
        CargarPerfilC(cor);
    }
    private void CargarPerfilC(final String corre) {
        Thread tr = new Thread(){

            @Override
            public void  run(){
                final Conductor con = conductorJSON(consultarConductor(corre));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //try {

                        TextView tvnombre, tvapellido, tvcorreo,tvubicacion;
                        SmartImageView sivperfilC;
                        tvnombre=(TextView)findViewById(R.id.lblNombrePC);
                        tvapellido=(TextView)findViewById(R.id.lblApellidoPC);
                        tvcorreo=(TextView)findViewById(R.id.lblCorreoPC);
                        tvubicacion=(TextView)findViewById(R.id.lblUbiPC);

                        sivperfilC = (SmartImageView)findViewById(R.id.img_conductor);
                        tvnombre.setText(con.getNombre());
                        tvapellido.setText( con.getApellido());
                        tvcorreo.setText( con.getCorreo());
                        tvubicacion.setText( con.getUbicacion());
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


    //Consulta

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

    // Convertir en objeto

    public Conductor conductorJSON(String cadenaJSON){
        Conductor con = new Conductor();

        try {
            JSONArray jsonarr = new JSONArray(cadenaJSON);
            JSONObject jObj = jsonarr.getJSONObject(0);

            con.setCorreo(jObj.getString("ConCorreo"));
            con.setNombre(jObj.getString("ConNombre"));
            con.setApellido(jObj.getString("ConApellido"));
            con.setUbicacion(jObj.getString("ConUbicacion"));
            con.setFotoC(jObj.getString("ConFoto"));


        } catch (JSONException e) {

        }

        return con;
    }
    public void abrirGaleriaC(View v) {
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
                Intent forwardToB = new Intent(getApplicationContext(), VerFotoC.class);
                forwardToB.putExtra("image", image_str);
                startActivity(forwardToB);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
}