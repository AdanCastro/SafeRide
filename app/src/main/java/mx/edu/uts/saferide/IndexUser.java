package mx.edu.uts.saferide;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class IndexUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent act = new Intent(this, Login.class);
            startActivity(act);
        }

        return super.onOptionsItemSelected(item);
    }
/*
    public void PerfilUsuario(View view){
        Intent intent = new Intent(this, Perfil.class);
        startActivity(intent);
    }
    public void Tarjeta (View view) {
        Intent act = new Intent(this, InfoTarjeta.class);
        startActivity(act);
    }
    public void Reportes(View view){
        Intent intent = new Intent(this, ReportesUsuario.class);
        startActivity(intent);
    }
    public void PaquetesUsu (View view) {
        Intent act = new Intent(this, ListaPaquetesUsuario.class);
        startActivity(act);
    }
    public void Contratacion (View view) {
        Intent act = new Intent(this, Contratacion.class);
        startActivity(act);
    }*/


   /* public void Reportes(View view){
        Intent intent = new Intent(this, Reportes.class);
        startActivity(intent);
    }



    public void ListaConductores(View view){
        Intent intent = new Intent(this, ListaConductores.class);
        startActivity(intent);
    }


    }*/



}
