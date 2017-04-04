package mx.edu.uts.saferide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Index extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
    }
    //Método para abrir la pantalla de Login
    public void Login (View view){
        Intent act=new Intent(this,Login.class);
        startActivity(act);
    }
    //Método para abrir la pantalla de Registro de conductores
    public void RegistroConductor (View view){
        Intent act=new Intent(this,RegistroConductor.class);
        startActivity(act);
    }
    //Método para abrir la pantalla de Registro de usuarios
    public void RegistroUsuario (View view) {
        Intent act = new Intent(this, RegistroUsuario.class);
        startActivity(act);
    }
}
