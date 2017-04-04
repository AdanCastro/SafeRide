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
import android.widget.Button;

public class IndexConductor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_conductor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void PerfilConductor (View view) {
        Intent act = new Intent(this, PerfilC.class);
        startActivity(act);
    }
/*
    public void PaquetesCondu   (View view) {
        Intent act = new Intent(this,ListaPaquetesConductor.class);
        startActivity(act);
    }/*
    public void ListaPagosConductor (View view) {
        Intent act = new Intent(this, ListaPagosConductor.class);
        startActivity(act);
    }*/
    static Button notifCount;
    static int mNotifCount = 2;
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

}
