package com.kerneldc.coldtempaltcorrection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Add toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        var roundingOption = ColdWxCorrection.RoundingOption.getRoundingOption(getIntent().getIntExtra("roundingOption", 1));
        switch (roundingOption) {
            case ROUNDING_TO_1:
                var rounding1RadioButton = (RadioButton)findViewById(R.id.rounding1);
                rounding1RadioButton.setChecked(true);
                break;
            case ROUNDING_TO_10:
                var rounding10RadioButton = (RadioButton)findViewById(R.id.rounding10);
                rounding10RadioButton.setChecked(true);
                break;
            case ROUNDING_TO_100:
                var rounding100RadioButton = (RadioButton)findViewById(R.id.rounding100);
                rounding100RadioButton.setChecked(true);
                break;
        }

        Button button = findViewById(R.id.saveSettingsButton);
        button.setOnClickListener(view -> {
            // Code here executes on main thread after user presses button
            Log.d("SettingsActivity", "setOnClickListener saveSettingsButton");
            saveRoundingOption(getRoundingOption());
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public ColdWxCorrection.RoundingOption getRoundingOption () {
        var roundingButtonSelected = ((RadioGroup)findViewById(R.id.roundingOptionRadioGroup)).getCheckedRadioButtonId();
        if (roundingButtonSelected == -1) {
            throw new RuntimeException("Rounding radio button should be selected.");
        }

        if (roundingButtonSelected == R.id.rounding1) {
            return ColdWxCorrection.RoundingOption.ROUNDING_TO_1;
        } else if (roundingButtonSelected == R.id.rounding10) {
            return ColdWxCorrection.RoundingOption.ROUNDING_TO_10;
        } else if (roundingButtonSelected == R.id.rounding100) {
            return ColdWxCorrection.RoundingOption.ROUNDING_TO_100;
        } else {
            throw new RuntimeException("Can't determine rounding button selected");
        }
    }

    public void saveRoundingOption (ColdWxCorrection.RoundingOption roundingOption) {

        SharedPreferencesUtil.writeSettings2(getApplicationContext(), roundingOption);

        Intent intent = new Intent();
        intent.putExtra("roundingOption", roundingOption.getIntValue());
        setResult(RESULT_OK, intent);
        finish();
    }

}