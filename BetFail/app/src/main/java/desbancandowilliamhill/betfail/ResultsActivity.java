package desbancandowilliamhill.betfail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_activity);

        Bundle bundle = getIntent().getExtras();


        ((TextView) findViewById(R.id.textJugador1)).setText(bundle.getString("jug1"));
        ((TextView) findViewById(R.id.textJugador2)).setText(bundle.getString("jug2"));

    }

    public void mostrarlog(View v) {
        Toast.makeText(ResultsActivity.this, "Huehuehuehue",
                Toast.LENGTH_LONG).show();
       // String log_path = null;
        Intent intent = new Intent(this, LoggerActivity.class);

        //intent.putExtra("path", log_path);
        startActivity(intent);
    }
}
