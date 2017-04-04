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
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReportesUsuario extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes_usuario);
    }
    public void reportarCondu(View view){
        Thread nt = new Thread(){
            @Override
            public void run(){
                EditText RepPaqId=(EditText)findViewById(R.id.txtNumPaq);
                EditText RepReporte=(EditText)findViewById(R.id.txtReporte);
                Calendar c = Calendar.getInstance();
                System.out.println("Current time => " + c.getTime());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String formattedDate = df.format(c.getTime());


                try {
                    final String res;
                    res= enviarPost(RepPaqId.getText().toString(), RepReporte.getText().toString(), formattedDate);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!res.equals("-1")){
                                Toast.makeText(ReportesUsuario.this, "Su reporte fue enviado con exito.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(ReportesUsuario.this,
                                        IndexUser.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(ReportesUsuario.this, "Ocurrio un error mientras se enviaba su reporte, intentelo más tarde.", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }catch (Exception e){
                    Toast.makeText(ReportesUsuario.this, "Ocurrio un error mientras se enviaba su reporte, intentelo más tarde.", Toast.LENGTH_LONG).show();
                }

            }

        };
        nt.start();

    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public String enviarPost(String RepPaqId, String RepReporte, String RepFecha ) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody body = new FormEncodingBuilder()
                .add("RepPaqId", RepPaqId)
                .add("RepReporte", RepReporte)
                .add("RepFecha", RepFecha)
                .build();

        Request request = new  Request.Builder()
                .url("https://saferide-adanc.c9users.io/SafeRide/reportes.php")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
