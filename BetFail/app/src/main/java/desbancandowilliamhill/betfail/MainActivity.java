package desbancandowilliamhill.betfail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> spinnerArray =  new ArrayList<String>();

        spinnerArray = leerTxt("jugadores");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.spinner_jugador1)).setAdapter(adapter);
        ((Spinner) findViewById(R.id.spinner_jugador2)).setAdapter(adapter);

        spinnerArray = leerTxt("superficies");
        adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.spinner_superficie)).setAdapter(adapter);
    } // Cierre onCreate

    public void obtenerGanador(View v) {
        Toast.makeText(MainActivity.this, "Nunca apuestes a Nadal",
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ResultsActivity.class);

        intent.putExtra("jug1", ((Spinner) findViewById(R.id.spinner_jugador1)).getSelectedItem().toString());
        intent.putExtra("jug2", ((Spinner) findViewById(R.id.spinner_jugador2)).getSelectedItem().toString());
        startActivity(intent);
    }

    private ArrayList<String> leerTxt(String nombre) {

        ArrayList<String> list = new ArrayList<String>();

        try {
            InputStream is = getAssets().open(nombre + ".txt");

            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            String text = new String(buffer);

            String[] nombres = text.split("\\n");

            for (String i : nombres) {
                list.add(i);
            }
     /*       for (int i = 0; i < nombres.length; i++ ) {
                list.add(nombres[i]);
            } */

            return list;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    } // Cierre leerNobmresJugadores
}
