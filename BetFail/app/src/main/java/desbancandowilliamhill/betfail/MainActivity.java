package desbancandowilliamhill.betfail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import desbancandowilliamhill.betfail.machineLearning.Jugador;
import desbancandowilliamhill.betfail.machineLearning.Main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> spinnerArray;

        spinnerArray = leerTxt("jugadores");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.spinner_jugador1)).setAdapter(adapter);
        ((Spinner) findViewById(R.id.spinner_jugador2)).setAdapter(adapter);

        spinnerArray = leerTxt("superficies");
        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) findViewById(R.id.spinner_superficie)).setAdapter(adapter);
    } // Cierre onCreate



    public void obtenerGanador(View v) throws IOException {
        Toast.makeText(MainActivity.this, "Buena suerte",
                Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, ResultsActivity.class);

        String nombre1 = ((Spinner) findViewById(R.id.spinner_jugador1)).getSelectedItem().toString();
        String nombre2 = ((Spinner) findViewById(R.id.spinner_jugador2)).getSelectedItem().toString();
        String superf = ((Spinner) findViewById(R.id.spinner_superficie)).getSelectedItem().toString();

        intent.putExtra("jug1", nombre1);
        intent.putExtra("jug2", nombre2);
        intent.putExtra("superf", superf);

        // Pasamos a la siguiente actividad
        startActivity(intent);
    }

    public void calcular_betas(View v){
        Main test = new Main(getApplicationContext());
        test.obtenerBeta();
    }

    private ArrayList<String> leerTxt(String nombre) {

        ArrayList<String> list = new ArrayList<>();

        try {
            InputStream is = getAssets().open(nombre + ".txt");

            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();

            // Convert the buffer into a string.
            String text = new String(buffer);

            String[] nombres = text.split("\\n");
            for (String i : nombres) {
                list.add(i);
            }

        } catch (IOException e) {
            Log.e("I/O", "Error al leer el fichero: " + nombre + ".txt");
        }

        return list;

    } // Cierre leerNobmresJugadores
} // Cierre MainActivity
