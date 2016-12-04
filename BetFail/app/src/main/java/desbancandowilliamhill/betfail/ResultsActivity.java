package desbancandowilliamhill.betfail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.StringWriter;

import desbancandowilliamhill.betfail.machineLearning.Main;

public class ResultsActivity extends AppCompatActivity {

    private static Double[] beta = new Double[] {0.9996293074506556, 1.0, 0.9877174795248658, 1.0, 0.9977511270260427};
    private static StringWriter sw = new StringWriter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_activity);

        Bundle bundle = getIntent().getExtras();

        String n1 = bundle.getString("jug1").trim();
        String n2 = bundle.getString("jug2").trim();
        String superf = bundle.getString("superf").trim();


        ((TextView) findViewById(R.id.textJugador1)).setText(n1);
        ((TextView) findViewById(R.id.textJugador2)).setText(n2);

        Main test = new Main(getApplicationContext(), findViewById(android.R.id.content), this.sw);
        try {
            this.sw = test.estimarResultado(n1, n2, superf,"2015", beta);
        } catch(Exception e) {
            Log.e("Error onCreate Results", e.getLocalizedMessage());
        }

    } // Cierre onCreate

    public void mostrarlog(View v) {
        Toast.makeText(ResultsActivity.this, "Huehuehuehue",
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, LoggerActivity.class);

        intent.putExtra("log", this.sw.toString());
        startActivity(intent);
    }
}
