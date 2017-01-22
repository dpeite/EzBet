package desbancandowilliamhill.betfail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import desbancandowilliamhill.betfail.machineLearning.Main;

public class BetasActivity extends AppCompatActivity {
    Double[] beta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_betas);

        if ( ResultsActivity.beta == null){
            beta = ResultsActivity.beta_fija.clone();
        }
        else {
            beta = ResultsActivity.beta.clone();
        }
        ((TextView) findViewById(R.id.beta1)).setText(beta[0].toString());
        ((TextView) findViewById(R.id.beta2)).setText(beta[2].toString());
        ((TextView) findViewById(R.id.beta3)).setText(beta[4].toString());

    }

    public void guardar_betas(View v){
        beta[0] =  Double.parseDouble(((TextView) findViewById(R.id.beta1)).getText().toString());
        beta[2] =  Double.parseDouble(((TextView) findViewById(R.id.beta2)).getText().toString());
        beta[4] =  Double.parseDouble(((TextView) findViewById(R.id.beta3)).getText().toString());

        ResultsActivity.beta = beta;
    }

    public void reset_betas(View v){
        ResultsActivity.beta = ResultsActivity.beta_fija.clone();
        ((TextView) findViewById(R.id.beta1)).setText(ResultsActivity.beta[0].toString());
        ((TextView) findViewById(R.id.beta2)).setText(ResultsActivity.beta[2].toString());
        ((TextView) findViewById(R.id.beta3)).setText(ResultsActivity.beta[4].toString());
    }
    public void calcular_betas(View v){
        Main test = new Main(getApplicationContext());
        ResultsActivity.beta = test.obtenerBeta();

        ((TextView) findViewById(R.id.beta1)).setText(beta[0].toString());
        ((TextView) findViewById(R.id.beta2)).setText(beta[2].toString());
        ((TextView) findViewById(R.id.beta3)).setText(beta[4].toString());

        /*Intent intent = new Intent(this, CalculoBetas.class);
        startActivity(intent);*/
    }
}
