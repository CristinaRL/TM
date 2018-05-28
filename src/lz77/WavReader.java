package lz77;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author JDPrades
 */
public class WavReader {

    public static final int MAX_AUDIO_LENGTH = Integer.MAX_VALUE / 256;

    /**
     * @param filename the path and the name to the .wav file
     * @return dataout integer array with all the data samples
     */
    public static int[] Wav2Array(String filename) {
        File fileIn = null;
        AudioInputStream ais = null;
        byte[] data;
        int[] dataint = new int[MAX_AUDIO_LENGTH];
        int[] dataout = null;
        int numIntRead = 0;
        try {
            fileIn = new File(filename);
            ais = AudioSystem.getAudioInputStream(fileIn);
            int bytesPerFrame = ais.getFormat().getFrameSize();
            int bitspersample = ais.getFormat().getSampleSizeInBits();
            data = new byte[bitspersample / 8];

            // Try to read numBytes bytes from the file.
            while ((ais.read(data, 0, bitspersample / 8)) != -1) {
                //System.out.print("i = " + numIntRead + "  ");
                for (int idx = 0; idx < data.length; idx++) {
                    //System.out.print(data[idx] + "  ");
                    if (ais.getFormat().isBigEndian()) {
                        dataint[numIntRead] += data[(data.length - 1) - idx] << ((data.length - 1) - idx);
                    } else {
                        dataint[numIntRead] += (data[idx] << (idx * 8));
                    }
                }
                //System.out.println(dataint[numIntRead]);
                numIntRead++;
            }
            
            dataout = new int[numIntRead];
            for (int i = 0; i < numIntRead; i++) {
                dataout[i] = dataint[i];
            }

        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(WavReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(WavReader.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                ais.close();
            } catch (IOException ex) {
                Logger.getLogger(WavReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return (dataout);
    }

}
