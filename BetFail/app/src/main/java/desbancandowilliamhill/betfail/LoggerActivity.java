package desbancandowilliamhill.betfail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoggerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logger);

        ((TextView) findViewById(R.id.textView3)).setText(getIntent().getExtras().getString("log").trim());
    }
}
