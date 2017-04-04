package mx.edu.uts.saferide;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class InfoTarjeta extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_tarjeta);
    }
    /*public String recogerExtras() { //Aquí recogemos y tratamos los parámetros
        Bundle extras= getIntent().getExtras();
        String cor = extras.getString("texto");
        return cor;
    }*/

    public void registrarTarjetaOnClick(View view){
        Thread nt = new Thread(){
            @Override
            public void run(){

                EditText NumTarjeta=(EditText)findViewById(R.id.txtNumTarjeta);
                RadioGroup rg = (RadioGroup) findViewById(R.id.rgTarjeta);
                int checkedId = rg.getCheckedRadioButtonId();
                String tipo=null;
                switch (checkedId) {
                    case R.id.rbCredito:
                        RadioButton c = (RadioButton)findViewById(R.id.rbCredito);
                        tipo = c.getText().toString();
                        break;
                    case R.id.rbDebito:
                        RadioButton d = (RadioButton)findViewById(R.id.rbDebito);
                        tipo = d.getText().toString();
                        break;
                    case R.id.rbSafe:
                        RadioButton sr = (RadioButton)findViewById(R.id.rbSafe);
                        tipo = sr.getText().toString();
                        break;
                    default:
                        tipo = "";
                }
                Spinner Mes = (Spinner) findViewById(R.id.dpMes);
                Spinner Year = (Spinner) findViewById(R.id.dpYear);
                EditText Codigo=(EditText)findViewById(R.id.txtCodigo);
                Correo c = new Correo();
                String correo = c.getCorreo();
                try {
                    if(NumTarjeta.getText().toString() !="" && tipo!="" && Codigo.getText().toString()!="") {

                        final String res;
                        res= enviarPost(correo,NumTarjeta.getText().toString(),tipo,Mes.getSelectedItem().toString(),Year.getSelectedItem().toString(),Codigo.getText().toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!res.equals("-1")){
                                    Toast.makeText(InfoTarjeta.this, "Sus datos han sido registrados, gracias.", Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(InfoTarjeta.this, "Ocurrio un error al guardar sus datos, intentelo de nuevo más tarde.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(InfoTarjeta.this, "Datos incorrectos, ingrese datos correctos", Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    Toast.makeText(InfoTarjeta.this, "Ocurrio un error al guardar sus datos, intentelo de nuevo más tarde.", Toast.LENGTH_LONG).show();
                }

            }

        };
        nt.start();
    }
    public String enviarPost(String correo,String numero, String tipo, String mes, String year, String codigo) throws IOException{
        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = new FormEncodingBuilder()
                    .add("UsuCorreo", correo)
                    .add("UsuTarNumero", numero)
                    .add("UsuTarTipo", tipo)
                    .add("UsuTarMes", mes)
                    .add("UsuTarAnio", year)
                    .add("UsuTarCodigo", codigo)
                    .build();
            Request request = new Request.Builder()
                    .url("https://saferide-adanc.c9users.io/SafeRide/registroTarjeta.php")
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (IOException e) {
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
            return  null;
        }
    }
}
