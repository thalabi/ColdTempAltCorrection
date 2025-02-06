package com.kerneldc.coldtempaltcorrection;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class SharedPreferencesUtil {

    private static final String PREFERENCES_FILE_NAME = "com.kerneldc.coldtempaltcorrection";

    public static Set<String> readProfileNames (
            Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SortedSet<String> profileNamesSet = new TreeSet<String>(sharedPreferences.getStringSet("profileNamesSet", new HashSet<String>()));
        return profileNamesSet;
    }

    public static void writeProfile (
            Context context,
            Profile profile) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Add to set of profile names
        Set<String> profileNamesSet = sharedPreferences.getStringSet("profileNamesSet", new HashSet<String>());
        if (! profileNamesSet.contains(profile.getProfileName())) {
            profileNamesSet.add(profile.getProfileName());
        }
        editor.putStringSet("profileNamesSet", profileNamesSet);


        //if (profileNames.contains(profile.getProfileName()))
        editor.putString(profile.getProfileName()+".profileName", profile.getProfileName());
        editor.putInt(profile.getProfileName()+".elevation", profile.getElevation());
        editor.putInt(profile.getProfileName()+".altitude1", profile.getAltitude1());
        editor.putInt(profile.getProfileName()+".altitude2", profile.getAltitude2());
        editor.putInt(profile.getProfileName()+".altitude3", profile.getAltitude3());
        editor.putInt(profile.getProfileName()+".altitude4", profile.getAltitude4());
        editor.putInt(profile.getProfileName()+".altitude5", profile.getAltitude5());
        editor.commit();
    }

    public static Profile readProfile (
            Context context,
            String profileName) {

        Profile profile = new Profile();

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        String profileNameRead = sharedPreferences.getString(profileName+".profileName", "");
        if (profileNameRead.equals("")) {
            // exception
        }
        profile.setProfileName(profileNameRead);
        profile.setElevation(sharedPreferences.getInt(profileName+".elevation", 0));
        profile.setAltitude1(sharedPreferences.getInt(profileName+".altitude1", 0));
        profile.setAltitude2(sharedPreferences.getInt(profileName+".altitude2", 0));
        profile.setAltitude3(sharedPreferences.getInt(profileName+".altitude3", 0));
        profile.setAltitude4(sharedPreferences.getInt(profileName+".altitude4", 0));
        profile.setAltitude5(sharedPreferences.getInt(profileName+".altitude5", 0));

        return profile;
    }

    public static void deleteProfile (
            Context context,
            String profileName) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Remove profile name from set of profile names
        Set<String> profileNamesSet = sharedPreferences.getStringSet("profileNamesSet", new HashSet<String>());
        boolean removed = profileNamesSet.remove(profileName);
        if (! removed) {
            // exception
        }
        editor.putStringSet("profileNamesSet", profileNamesSet);

        // Remove profile
        editor.remove(profileName+".profileName");
        editor.remove(profileName+".elevation");
        editor.remove(profileName+".altitude1");
        editor.remove(profileName+".altitude2");
        editor.remove(profileName+".altitude3");
        editor.remove(profileName+".altitude4");
        editor.remove(profileName+".altitude5");
        editor.commit();
    }
/*
    public static boolean readSettings(
            Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("settings.rounding", false);
    }

    public static void writeSettings (
            Context context,
            boolean rounding) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("settings.rounding", rounding);
        editor.commit();
    }
*/

    // new version, delete two methods above
    public static ColdWxCorrection.RoundingOption readSettings2 (
            Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        int intValue = sharedPreferences.getInt("settings.roundingOption", ColdWxCorrection.RoundingOption.ROUNDING_TO_100.getIntValue());
        return ColdWxCorrection.RoundingOption.getRoundingOption(intValue);
    }

    public static void writeSettings2 (
            Context context,
            ColdWxCorrection.RoundingOption roundingOption) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("settings.roundingOption", roundingOption.getIntValue());
        editor.commit();
    }


}
