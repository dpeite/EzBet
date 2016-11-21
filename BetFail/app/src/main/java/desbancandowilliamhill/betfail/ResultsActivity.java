package desbancandowilliamhill.betfail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_activity);

        Bundle bundle = getIntent().getExtras();


        ((TextView) findViewById(R.id.textJugador1)).setText(bundle.getString("jug1"));
        ((TextView) findViewById(R.id.textJugador2)).setText(bundle.getString("jug2"));

    }
}
