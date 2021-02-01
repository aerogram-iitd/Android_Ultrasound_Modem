package in.aerogram.eziosense.tone.lib;

import static in.aerogram.eziosense.tone.db.PreferencesUtil.getDuration;
import static in.aerogram.eziosense.tone.db.PreferencesUtil.getHailFreq;
import static in.aerogram.eziosense.tone.db.PreferencesUtil.getLowFreq;
import static in.aerogram.eziosense.tone.db.PreferencesUtil.getSamplwFreq;
import static in.aerogram.eziosense.tone.db.PreferencesUtil.getStepFreq;

/**
 * @author rishabh-goel on 29-01-2021
 * @project eziotone
 */
public class Constants {

    public static int kLowFrequency = getLowFreq(); //the lowest frequency used
    public static int kFrequencyStep = getStepFreq(); //the distance between frequencies

    public static final int kBytesPerDuration = 1; //how wide is the data stream
    // (rps - that is, the pre-encoding data stream, not the audio)
    // (rps - kFrequencies must be this long)

    public static final int kBitsPerByte = 8; //unlikely to change, I know

    // Amplitude of each frequency in a frame.
    public static final double kAmplitude = 0.125d; /* (1/8) */

    // Sampling frequency (number of sample values per second)
    public static double kSamplingFrequency = getSamplwFreq(); //rps - reduced to 11025 from 22050
    // to enable the decoder to keep up with the audio on Motorola CLIQ

    // Sound duration of encoded byte (in seconds)
    public static double kDuration = getDuration(); // rps - increased from 0.1 to improve reliability on Android

    // Number of samples per duration
    public static final int kSamplesPerDuration = (int) (kSamplingFrequency * kDuration);

    //This is used to convert the floats of the encoding to the bytes of the audio
    public static final int kFloatToByteShift = 128;

    // The length, in durations, of the key sequence
    public static final int kDurationsPerKey = 3;

    //The frequency used in the initial hail of the key
    public static int kHailFrequency = getHailFreq();

    //The frequencies we use for each of the 8 bits
    public static final int[] kFrequencies = {
            kLowFrequency + kFrequencyStep,
            kLowFrequency + 2 * kFrequencyStep,
            kLowFrequency + 3 * kFrequencyStep,
            kLowFrequency + 4 * kFrequencyStep,
            kLowFrequency + 5 * kFrequencyStep,
            kLowFrequency + 6 * kFrequencyStep,
            kLowFrequency + 7 * kFrequencyStep,
            kLowFrequency + 8 * kFrequencyStep
    };

    //The frequencies we use for each of the 8 bits
//    public static final int[] kFrequencies = {1000,                       //1000
//            (int) (1000 * (float) 27 / 24), //1125
//            (int) (1000 * (float) 30 / 24), //1250
//            (int) (1000 * (float) 36 / 24), //1500
//            (int) (1000 * (float) 40 / 24), //1666
//            (int) (1000 * (float) 48 / 24), //2000
//            (int) (1000 * (float) 54 / 24), //2250
//            (int) (1000 * (float) 60 / 24)};//2500

//    public static final int[] kFrequencies = {19000,                       //1000
//					    19125, //1125
//					    19250, //1250
//					    19500, //1500
//					    19666, //1666
//					    20000, //2000
//					    20250, //2250
//					    20500};//2500

}
