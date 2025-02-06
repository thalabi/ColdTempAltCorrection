package com.kerneldc.coldtempaltcorrection;

public class ColdWxCorrection {

    public enum RoundingOption {
        ROUNDING_TO_1(1), ROUNDING_TO_10(2), ROUNDING_TO_100(3);

        int intValue;
        RoundingOption (
                int intValue) {
            this.intValue = intValue;
        }

        public int getIntValue () {
            return intValue;
        }

        public static RoundingOption getRoundingOption (
                int intValue) {

            switch (intValue) {
                case 1:
                    return ROUNDING_TO_1;
                case 2:
                    return ROUNDING_TO_10;
                default:
                    return ROUNDING_TO_100;
            }
        }
    }

    private int elevation;
    public int getElevation() {
        return elevation;
    }
    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    private int temperature;
    public int getTemperature() {
        return temperature;
    }
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

//	private boolean rounding = true;
//	public boolean isRounding() {
//		return rounding;
//	}
//	public void setRounding(boolean rounding) {
//		this.rounding = rounding;
//	}

    private RoundingOption roundingOption;
    public RoundingOption getRoundingOption() {
        return roundingOption;
    }
    public void setRoundingOption(RoundingOption roundingOption) {
        this.roundingOption = roundingOption;
    }
    /**
     * Calculates the corrected altitude
     * After calculation round the fraction up
     * Then rounds up to the nearest 100 if altitude is > 20, down if <= 20
     *
     * @param altitude	an altitude to correct
     * @return			corrected altitude
     */
//	public int calculateCorrection (
//		int altitude) {
//
//		double correctedAltitude;
//
//		correctedAltitude = 4 * (altitude-elevation) / 1000d * (15 - temperature) + altitude;
//
//		int correctedAltitudeInt = (int)Math.round(correctedAltitude);
//
//		if (isRounding()) {
//			int mod = correctedAltitudeInt % 100;
//			if (mod > 20) {
//				correctedAltitudeInt = correctedAltitudeInt - mod + 100;
//			} else {
//				correctedAltitudeInt = correctedAltitudeInt - mod;
//			}
//		}
//
//		return correctedAltitudeInt;
//	}

    public int calculateCorrection2 (
            int altitude) {

        double correctedAltitude;

        correctedAltitude = 4 * (altitude-elevation) / 1000d * (15 - temperature) + altitude;

        int correctedAltitudeInt = (int)Math.round(correctedAltitude);

        int mod;
        switch (roundingOption) {
            case ROUNDING_TO_1:
                break;
            case ROUNDING_TO_10:
                mod = correctedAltitudeInt % 10;
                if (mod > 0) {
                    correctedAltitudeInt = correctedAltitudeInt - mod + 10;
                } else {
                    correctedAltitudeInt = correctedAltitudeInt - mod;
                }
                break;
            case ROUNDING_TO_100:
                mod = correctedAltitudeInt % 100;
                if (mod > 20) {
                    correctedAltitudeInt = correctedAltitudeInt - mod + 100;
                } else {
                    correctedAltitudeInt = correctedAltitudeInt - mod;
                }
                break;
        }

        return correctedAltitudeInt;
    }

}
