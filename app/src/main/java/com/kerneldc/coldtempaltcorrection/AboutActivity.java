package com.kerneldc.coldtempaltcorrection;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Add toolbar
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        CharSequence appName = "N/A";
        var versionName = "N/A";
        var buildTimestamp = "N/A";
        try {
            appName = getApplicationContext().getApplicationInfo().loadLabel(getPackageManager());
            versionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            long buildTimestampLong = getPackageManager().getPackageInfo(getPackageName(), 0).lastUpdateTime;
            buildTimestamp = SimpleDateFormat.getInstance().format(buildTimestampLong);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        var appNameTextView = (TextView) findViewById(R.id.appNameTextView);
        appNameTextView.setText(appName);
        var versionTextView = (TextView) findViewById(R.id.versionTextView);
        versionTextView.setText(versionName);
        //buildTimestamp
        var buildTimestampTextView = (TextView) findViewById(R.id.buildTimestampTextView);
        buildTimestampTextView.setText(buildTimestamp);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(view -> {
            finish();
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

}