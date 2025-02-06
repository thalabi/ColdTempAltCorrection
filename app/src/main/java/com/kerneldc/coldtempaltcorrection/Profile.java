package com.kerneldc.coldtempaltcorrection;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Profile {

    String profileName;
    public String getProfileName() {
        return profileName;
    }
    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }
    int elevation;
    public int getElevation() {
        return elevation;
    }
    public void setElevation(int elevation) {
        this.elevation = elevation;
    }
    int altitude1;
    public int getAltitude1() {
        return altitude1;
    }
    public void setAltitude1(int altitude1) {
        this.altitude1 = altitude1;
    }
    int altitude2;
    public int getAltitude2() {
        return altitude2;
    }
    public void setAltitude2(int altitude2) {
        this.altitude2 = altitude2;
    }
    int altitude3;
    public int getAltitude3() {
        return altitude3;
    }
    public void setAltitude3(int altitude3) {
        this.altitude3 = altitude3;
    }
    int altitude4;
    public int getAltitude4() {
        return altitude4;
    }
    public void setAltitude4(int altitude4) {
        this.altitude4 = altitude4;
    }
    int altitude5;
    public int getAltitude5() {
        return altitude5;
    }
    public void setAltitude5(int altitude5) {
        this.altitude5 = altitude5;
    }

    /**
     * Converts a profile name string to a list of profile names
     * @param	profileString	A comma separated list of profile names
     * @return					A List<String> of profile names
     */
    public static List<String> profileStringToList (
            String profileString) {

        List<String> profileList = new ArrayList<String>();
        StringTokenizer profileStringTokenizer = new StringTokenizer (profileString);
        while (profileStringTokenizer.hasMoreTokens()) {
            profileList.add(profileStringTokenizer.nextToken());
        }
        return profileList;
    }
    /**
     * Converts a profile list to a comma separated list of profile name string
     * @param	profileList	A List<Strin> of profile names
     * @return				A comma separated list of profile names
     */
    public static String profileListToString (
            List<String> profileList) {

        StringBuilder profilesString = new StringBuilder();
        for (String profile : profileList) {
            profilesString.append(profile+",");
        }
        if (! /* not */ profileList.isEmpty()) {
            profilesString.deleteCharAt(profilesString.length()-1);
        }
        return profilesString.toString();
    }

}
