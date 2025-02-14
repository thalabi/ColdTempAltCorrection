package com.kerneldc.coldtempaltcorrection;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_1 = 1;
    //private static final int REQUEST_CODE_2 = 2;

    private ColdWxCorrection coldWxCorrection;

    private final static String ELEVATION = "ELEVATION";
    private final static String TEMPERATURE = "TEMPERATURE";
    private final static String ALTITUDE1 = "ALTITUDE1";
    private final static String ALTITUDE2 = "ALTITUDE2";
    private final static String ALTITUDE3 = "ALTITUDE3";
    private final static String ALTITUDE4 = "ALTITUDE4";
    private final static String ALTITUDE5 = "ALTITUDE5";

    private int elevation;
    private int temperature;
    private int altitude1;
    private int altitude2;
    private int altitude3;
    private int altitude4;
    private int altitude5;

    private EditText elevationEditText;
    private EditText temperatureEditText;
    private EditText altitude1EditText;
    private EditText altitude2EditText;
    private EditText altitude3EditText;
    private EditText altitude4EditText;
    private EditText altitude5EditText;

    private enum Field {
        ELEVATION, TEMPERATURE, ALTITUDE_ONE, ALTITUDE_TWO, ALTITUDE_THREE, ALTITUDE_FOUR, ALTITUDE_FIVE,
        ALL_ALTITUDES, ALL_FIELDS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("onCreate", "executed");
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Find the toolbar view inside the activity layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        coldWxCorrection = new ColdWxCorrection();

        // get rounding value from sharedPreferences
//		boolean rounding = SharedPreferencesUtil.readSettings(getApplicationContext());
//		coldWxCorrection.setRounding(rounding);
        ColdWxCorrection.RoundingOption roundingOption = SharedPreferencesUtil.readSettings2(getApplicationContext());
        coldWxCorrection.setRoundingOption(roundingOption);

        if (savedInstanceState == null) {
            // app has just started
            Log.d("MainActivity", "tarce 0");
            elevation = 0;
            temperature = 0;
            altitude1 = 0;
            altitude2 = 0;
            altitude3 = 0;
            altitude4 = 0;
            altitude5 = 0;
        } else {
            // app was resumed
            Log.d("MainActivity", "tarce 1");
            elevation = savedInstanceState.getInt(ELEVATION);
            temperature = savedInstanceState.getInt(TEMPERATURE);
            altitude1 = savedInstanceState.getInt(ALTITUDE1);
            altitude2 = savedInstanceState.getInt(ALTITUDE2);
            altitude3 = savedInstanceState.getInt(ALTITUDE3);
            altitude4 = savedInstanceState.getInt(ALTITUDE4);
            altitude5 = savedInstanceState.getInt(ALTITUDE5);
        }
        elevationEditText = (EditText) findViewById(R.id.elevationEditText);
        elevationEditText.setText(elevation != 0 ? String.valueOf(elevation) : "");
        temperatureEditText = (EditText) findViewById(R.id.temperatureEditText);
        temperatureEditText.setText(temperature != 0 ? String.valueOf(temperature) : "");
        altitude1EditText = (EditText) findViewById(R.id.altitude1EditText);
        altitude1EditText.setText(altitude1 != 0 ? String.valueOf(altitude1) : "");
        altitude2EditText = (EditText) findViewById(R.id.altitude2EditText);
        altitude2EditText.setText(altitude2 != 0 ? String.valueOf(altitude2) : "");
        altitude3EditText = (EditText) findViewById(R.id.altitude3EditText);
        altitude3EditText.setText(altitude3 != 0 ? String.valueOf(altitude3) : "");
        altitude4EditText = (EditText) findViewById(R.id.altitude4EditText);
        altitude4EditText.setText(altitude4 != 0 ? String.valueOf(altitude4) : "");
        altitude5EditText = (EditText) findViewById(R.id.altitude5EditText);
        altitude5EditText.setText(altitude5 != 0 ? String.valueOf(altitude5) : "");

        // Turn on numeric keypad
        elevationEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        temperatureEditText.setInputType(InputType.TYPE_CLASS_NUMBER  | InputType.TYPE_NUMBER_FLAG_SIGNED);
        altitude1EditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        altitude2EditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        altitude3EditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        altitude4EditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        altitude5EditText.setInputType(InputType.TYPE_CLASS_NUMBER);

        // add a GenericTextWatcher to each of the fields
        elevationEditText.addTextChangedListener(new GenericTextWatcher(Field.ELEVATION));
        temperatureEditText.addTextChangedListener(new GenericTextWatcher(Field.TEMPERATURE));
        altitude1EditText.addTextChangedListener(new GenericTextWatcher(Field.ALTITUDE_ONE));
        altitude2EditText.addTextChangedListener(new GenericTextWatcher(Field.ALTITUDE_TWO));
        altitude3EditText.addTextChangedListener(new GenericTextWatcher(Field.ALTITUDE_THREE));
        altitude4EditText.addTextChangedListener(new GenericTextWatcher(Field.ALTITUDE_FOUR));
        altitude5EditText.addTextChangedListener(new GenericTextWatcher(Field.ALTITUDE_FIVE));

        Button clearButton = findViewById(R.id.clearButton);
        clearButton.setOnClickListener(view -> {
            // Code here executes on main thread after user presses button
            elevation = 0;
            temperature = 0;
            altitude1 = 0;
            altitude2 = 0;
            altitude3 = 0;
            altitude4 = 0;
            altitude5 = 0;
            elevationEditText = (EditText) findViewById(R.id.elevationEditText);
            elevationEditText.setText(elevation != 0 ? String.valueOf(elevation) : "");
            temperatureEditText = (EditText) findViewById(R.id.temperatureEditText);
            temperatureEditText.setText(temperature != 0 ? String.valueOf(temperature) : "");
            altitude1EditText = (EditText) findViewById(R.id.altitude1EditText);
            altitude1EditText.setText(altitude1 != 0 ? String.valueOf(altitude1) : "");
            altitude2EditText = (EditText) findViewById(R.id.altitude2EditText);
            altitude2EditText.setText(altitude2 != 0 ? String.valueOf(altitude2) : "");
            altitude3EditText = (EditText) findViewById(R.id.altitude3EditText);
            altitude3EditText.setText(altitude3 != 0 ? String.valueOf(altitude3) : "");
            altitude4EditText = (EditText) findViewById(R.id.altitude4EditText);
            altitude4EditText.setText(altitude4 != 0 ? String.valueOf(altitude4) : "");
            altitude5EditText = (EditText) findViewById(R.id.altitude5EditText);
            altitude5EditText.setText(altitude5 != 0 ? String.valueOf(altitude5) : "");
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.i("onCreateOptionsMenu", "executed");
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        boolean consumed = false;
        int id = item.getItemId();
        if (id == R.id.action_save) {
            showSaveProfileDialog();
            consumed = true;
        } else if (id == R.id.action_load) {
            showLoadProfileDialog();
            consumed = true;
        } else if (id == R.id.action_delete) {
            showDeleteProfileDialog();
            consumed = true;
        } else if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            settingsIntent.putExtra("roundingOption", coldWxCorrection.getRoundingOption().getIntValue());
            startActivityForResult(settingsIntent, REQUEST_CODE_1);
            consumed = true;
        } else if (id == R.id.action_about) {
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            consumed = true;
        } else if (id == R.id.action_exit) {
            finish();
            showToastMessage("Have a good flight!");
            consumed = true;
        } else {
                consumed = super.onOptionsItemSelected(item);
        }
        return consumed;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {

        if (requestCode == REQUEST_CODE_1) {
            if (resultCode == RESULT_OK) {
                //boolean rounding = data.getBooleanExtra("rounding", true);
                ColdWxCorrection.RoundingOption roundingOption = ColdWxCorrection.RoundingOption.getRoundingOption(data.getIntExtra("roundingOption", 1));
                //coldWxCorrection.setRounding(rounding);
                coldWxCorrection.setRoundingOption(roundingOption);
                if (altitude1 != 0) calculateAltitude(Field.ALTITUDE_ONE);
                if (altitude2 != 0) calculateAltitude(Field.ALTITUDE_TWO);
                if (altitude3 != 0) calculateAltitude(Field.ALTITUDE_THREE);
                if (altitude4 != 0) calculateAltitude(Field.ALTITUDE_FOUR);
                if (altitude5 != 0) calculateAltitude(Field.ALTITUDE_FIVE);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onSaveInstanceState (
            Bundle outState) {

        super.onSaveInstanceState(outState);

        outState.putInt(ELEVATION, elevation);
        outState.putInt(TEMPERATURE, temperature);
        outState.putInt(ALTITUDE1, altitude1);
        outState.putInt(ALTITUDE2, altitude2);
        outState.putInt(ALTITUDE3, altitude3);
        outState.putInt(ALTITUDE4, altitude4);
        outState.putInt(ALTITUDE5, altitude5);
    }

    private void clearCorrectedAltitudeField (
            Field field) {

        TextView altitude1CorrectedTextView;
        TextView altitude2CorrectedTextView;
        TextView altitude3CorrectedTextView;
        TextView altitude4CorrectedTextView;
        TextView altitude5CorrectedTextView;
        switch (field) {
            case ALTITUDE_ONE:
                altitude1CorrectedTextView = (TextView) findViewById(R.id.altitude1CorrectedTextView);
                altitude1CorrectedTextView.setText("");
                break;
            case ALTITUDE_TWO:
                altitude2CorrectedTextView = (TextView) findViewById(R.id.altitude2CorrectedTextView);
                altitude2CorrectedTextView.setText("");
                break;
            case ALTITUDE_THREE:
                altitude3CorrectedTextView = (TextView) findViewById(R.id.altitude3CorrectedTextView);
                altitude3CorrectedTextView.setText("");
                break;
            case ALTITUDE_FOUR:
                altitude4CorrectedTextView = (TextView) findViewById(R.id.altitude4CorrectedTextView);
                altitude4CorrectedTextView.setText("");
                break;
            case ALTITUDE_FIVE:
                altitude5CorrectedTextView = (TextView) findViewById(R.id.altitude5CorrectedTextView);
                altitude5CorrectedTextView.setText("");
                break;
            case ALL_ALTITUDES:
                clearCorrectedAltitudeField(Field.ALTITUDE_ONE);
                clearCorrectedAltitudeField(Field.ALTITUDE_TWO);
                clearCorrectedAltitudeField(Field.ALTITUDE_THREE);
                clearCorrectedAltitudeField(Field.ALTITUDE_FOUR);
                clearCorrectedAltitudeField(Field.ALTITUDE_FIVE);
        }

    }

    private void calculateAltitude (
            Field field) {

        switch (field) {
            case ALTITUDE_ONE:
                //String altitude1Corrected = String.valueOf(coldWxCorrection.calculateCorrection(altitude1));
                String altitude1Corrected = String.valueOf(coldWxCorrection.calculateCorrection2(altitude1));

                TextView altitude1CorrectedTextView = (TextView) findViewById(R.id.altitude1CorrectedTextView);

                altitude1CorrectedTextView.setText(altitude1Corrected);

                altitude1CorrectedTextView.setTypeface(null, Typeface.BOLD);
                break;
            case ALTITUDE_TWO:
                //String altitude2Corrected = String.valueOf(coldWxCorrection.calculateCorrection(altitude2));
                String altitude2Corrected = String.valueOf(coldWxCorrection.calculateCorrection2(altitude2));

                TextView altitude2CorrectedTextView = (TextView) findViewById(R.id.altitude2CorrectedTextView);

                altitude2CorrectedTextView.setText(altitude2Corrected);

                altitude2CorrectedTextView.setTypeface(null, Typeface.BOLD);
                break;
            case ALTITUDE_THREE:
                //String altitude3Corrected = String.valueOf(coldWxCorrection.calculateCorrection(altitude3));
                String altitude3Corrected = String.valueOf(coldWxCorrection.calculateCorrection2(altitude3));

                TextView altitude3CorrectedTextView = (TextView) findViewById(R.id.altitude3CorrectedTextView);

                altitude3CorrectedTextView.setText(altitude3Corrected);

                altitude3CorrectedTextView.setTypeface(null, Typeface.BOLD);
                break;
            case ALTITUDE_FOUR:
                //String altitude4Corrected = String.valueOf(coldWxCorrection.calculateCorrection(altitude4));
                String altitude4Corrected = String.valueOf(coldWxCorrection.calculateCorrection2(altitude4));

                TextView altitude4CorrectedTextView = (TextView) findViewById(R.id.altitude4CorrectedTextView);

                altitude4CorrectedTextView.setText(altitude4Corrected);

                altitude4CorrectedTextView.setTypeface(null, Typeface.BOLD);
                break;
            case ALTITUDE_FIVE:
                //String altitude5Corrected = String.valueOf(coldWxCorrection.calculateCorrection(altitude5));
                String altitude5Corrected = String.valueOf(coldWxCorrection.calculateCorrection2(altitude5));

                TextView altitude5CorrectedTextView = (TextView) findViewById(R.id.altitude5CorrectedTextView);

                altitude5CorrectedTextView.setText(altitude5Corrected);

                altitude5CorrectedTextView.setTypeface(null, Typeface.BOLD);
                break;
            case ALL_ALTITUDES:
                calculateAltitude(Field.ALTITUDE_ONE);
                calculateAltitude(Field.ALTITUDE_TWO);
                calculateAltitude(Field.ALTITUDE_THREE);
                calculateAltitude(Field.ALTITUDE_FOUR);
                calculateAltitude(Field.ALTITUDE_FIVE);
        }
    }

    /**
     * Inner class GenericTextWatcher
     * @author Tarif Halabi
     *
     */
    private class GenericTextWatcher implements TextWatcher {

        private Field field;
        /**
         * Constructor
         * @param field	Field name to watch for
         */
        private GenericTextWatcher (
                Field field) {

            this.field = field;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }


        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            switch (field) {
                case ELEVATION:
                    try {
                        elevation = Integer.parseInt(s.toString());
                        coldWxCorrection.setElevation(elevation);
                        if (temperature == 0) {
                            clearCorrectedAltitudeField(Field.ALL_ALTITUDES);
                        } else {
                            if (altitude1 != 0) calculateAltitude(Field.ALTITUDE_ONE);
                            if (altitude2 != 0) calculateAltitude(Field.ALTITUDE_TWO);
                            if (altitude3 != 0) calculateAltitude(Field.ALTITUDE_THREE);
                            if (altitude4 != 0) calculateAltitude(Field.ALTITUDE_FOUR);
                            if (altitude5 != 0) calculateAltitude(Field.ALTITUDE_FIVE);
                        }
                    } catch (NumberFormatException e) {
                        elevation = 0;
                        clearCorrectedAltitudeField(Field.ALL_ALTITUDES);
                    }
                    break;
                case TEMPERATURE:
                    try {
                        temperature = Integer.parseInt(s.toString());
                        coldWxCorrection.setTemperature(temperature);
                        if (elevation == 0) {
                            clearCorrectedAltitudeField(Field.ALL_ALTITUDES);
                        } else {
                            if (altitude1 != 0) calculateAltitude(Field.ALTITUDE_ONE);
                            if (altitude2 != 0) calculateAltitude(Field.ALTITUDE_TWO);
                            if (altitude3 != 0) calculateAltitude(Field.ALTITUDE_THREE);
                            if (altitude4 != 0) calculateAltitude(Field.ALTITUDE_FOUR);
                            if (altitude5 != 0) calculateAltitude(Field.ALTITUDE_FIVE);
                        }
                    } catch (NumberFormatException e) {
                        temperature = 0;
                        clearCorrectedAltitudeField(Field.ALL_ALTITUDES);
                    }
                    break;
                case ALTITUDE_ONE:
                    try {
                        altitude1 = Integer.parseInt(s.toString());
                        if (elevation != 0 && temperature != 0) calculateAltitude(Field.ALTITUDE_ONE);
                    } catch (NumberFormatException e) {
                        altitude1 = 0;
                        clearCorrectedAltitudeField(Field.ALTITUDE_ONE);
                    }
                    break;
                case ALTITUDE_TWO:
                    try {
                        altitude2 = Integer.parseInt(s.toString());
                        if (elevation != 0 && temperature != 0) calculateAltitude(Field.ALTITUDE_TWO);
                    } catch (NumberFormatException e) {
                        altitude2 = 0;
                        clearCorrectedAltitudeField(Field.ALTITUDE_TWO);
                    }
                    break;
                case ALTITUDE_THREE:
                    try {
                        altitude3 = Integer.parseInt(s.toString());
                        if (elevation != 0 && temperature != 0) calculateAltitude(Field.ALTITUDE_THREE);
                    } catch (NumberFormatException e) {
                        altitude3 = 0;
                        clearCorrectedAltitudeField(Field.ALTITUDE_THREE);
                    }
                    break;
                case ALTITUDE_FOUR:
                    try {
                        altitude4 = Integer.parseInt(s.toString());
                        if (elevation != 0 && temperature != 0) calculateAltitude(Field.ALTITUDE_FOUR);
                    } catch (NumberFormatException e) {
                        altitude4 = 0;
                        clearCorrectedAltitudeField(Field.ALTITUDE_FOUR);
                    }
                    break;
                case ALTITUDE_FIVE:
                    try {
                        altitude5 = Integer.parseInt(s.toString());
                        if (elevation != 0 && temperature != 0) calculateAltitude(Field.ALTITUDE_FIVE);
                    } catch (NumberFormatException e) {
                        altitude5 = 0;
                        clearCorrectedAltitudeField(Field.ALTITUDE_FIVE);
                    }
                    break;
            }
        }

        //calculate(column);
    }

    private AlertDialog.Builder profileDialogBuilder;

    private void showSaveProfileDialog () {
        profileDialogBuilder = new AlertDialog.Builder(this);
        final EditText profileNameEditText = new EditText(this);
        // force uppercase in profile name
        profileNameEditText.setFilters(new InputFilter[] {new InputFilter.AllCaps()});
        // force keyboard to appear
        if(profileNameEditText.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        profileDialogBuilder.setTitle("Save Profile");
        profileDialogBuilder.setMessage("Profile name");
        profileDialogBuilder.setView(profileNameEditText);
        profileDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String profileName = profileNameEditText.getText().toString();
                Profile profile = new Profile();
                profile.setProfileName(profileName);
                profile.setElevation(elevation);
                profile.setAltitude1(altitude1);
                profile.setAltitude2(altitude2);
                profile.setAltitude3(altitude3);
                profile.setAltitude4(altitude4);
                profile.setAltitude5(altitude5);
                SharedPreferencesUtil.writeProfile(getApplicationContext(), profile);

                showToastMessage("Profile saved");
            }
        });

        profileDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToastMessage("Profile not saved");
            }
        });

        AlertDialog saveDialog = profileDialogBuilder.create();
        // force keyboard to appear
        //saveDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        saveDialog.show();
    }

    private String profileName;
    private void showLoadProfileDialog () {

        Set<String> profileNamesSet = SharedPreferencesUtil.readProfileNames(getApplicationContext());
        final String[] profileNamesArray = profileNamesSet.toArray(new String[profileNamesSet.size()]);


        profileDialogBuilder = new AlertDialog.Builder(this);
        profileDialogBuilder.setTitle("Load Profile");
        profileDialogBuilder.setItems(profileNamesArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                profileName = profileNamesArray[which];
                Profile profile = SharedPreferencesUtil.readProfile(getApplication(), profileName);
                elevation = profile.getElevation();
                altitude1 = profile.getAltitude1();
                altitude2 = profile.getAltitude2();
                altitude3 = profile.getAltitude3();
                altitude4 = profile.getAltitude4();
                altitude5 = profile.getAltitude5();
                elevationEditText.setText(elevation != 0 ? String.valueOf(elevation) : "");
                altitude1EditText.setText(altitude1 != 0 ? String.valueOf(altitude1) : "");
                altitude2EditText.setText(altitude2 != 0 ? String.valueOf(altitude2) : "");
                altitude3EditText.setText(altitude3 != 0 ? String.valueOf(altitude3) : "");
                altitude4EditText.setText(altitude4 != 0 ? String.valueOf(altitude4) : "");
                altitude5EditText.setText(altitude5 != 0 ? String.valueOf(altitude5) : "");
                showToastMessage("Profile "+profileName+" has been selected");
            }
        });
//	    profileDialogBuilder.setSingleChoiceItems(profileNamesArray, -1, new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//	            profileName = profileNamesArray[which];
//			}
//		});
//	    profileDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//	            Profile profile = readProfile(profileName);
//	            elevation = profile.getElevation();
//	            altitude1 = profile.getAltitude1();
//	            altitude2 = profile.getAltitude2();
//	            altitude3 = profile.getAltitude3();
//	            altitude4 = profile.getAltitude4();
//	            altitude5 = profile.getAltitude5();
//	            elevationEditText.setText(elevation != 0 ? String.valueOf(elevation) : "");
//	            altitude1EditText.setText(altitude1 != 0 ? String.valueOf(altitude1) : "");
//	            altitude2EditText.setText(altitude2 != 0 ? String.valueOf(altitude2) : "");
//	            altitude3EditText.setText(altitude3 != 0 ? String.valueOf(altitude3) : "");
//	            altitude4EditText.setText(altitude4 != 0 ? String.valueOf(altitude4) : "");
//	            altitude5EditText.setText(altitude5 != 0 ? String.valueOf(altitude5) : "");
//	            showToastMessage("Profile "+profileName+" has been selected");
//			}
//		});

        AlertDialog loadDialog = profileDialogBuilder.create();
        loadDialog.show();
    }

    private void showDeleteProfileDialog () {
        Set<String> profileNamesSet = SharedPreferencesUtil.readProfileNames(getApplicationContext());
        final String[] profileNamesArray = profileNamesSet.toArray(new String[profileNamesSet.size()]);


        profileDialogBuilder = new AlertDialog.Builder(this);
        profileDialogBuilder.setTitle("Delete Profile");
        profileDialogBuilder.setItems(profileNamesArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                profileName = profileNamesArray[which];
                SharedPreferencesUtil.deleteProfile(getApplication(), profileName);
                showToastMessage("Profile "+profileName+" deleted");
            }
        });
        AlertDialog loadDialog = profileDialogBuilder.create();
        loadDialog.show();
    }

    //	public void onSave (
//		View view) {
//
//		profileSaveDialog();
//	}
//	public void onLoad (
//		View view) {
//
//		profileLoadDialog();
//	}
    public void onClear (
            View view) {

        elevation = 0;
        temperature = 0;
        altitude1 = 0;
        altitude2 = 0;
        altitude3 = 0;
        altitude4 = 0;
        altitude5 = 0;
        elevationEditText.setText("");
        temperatureEditText.setText("");
        altitude1EditText.setText("");
        altitude2EditText.setText("");
        altitude3EditText.setText("");
        altitude4EditText.setText("");
        altitude5EditText.setText("");

        clearCorrectedAltitudeField(Field.ALL_ALTITUDES);
    }
    private void showToastMessage (
            String message) {

        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }


}
