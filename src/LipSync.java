
import gnu.io.NRSerialPort;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

class FindLine {

    public int servoDeg = 0;

    void MicData(PrintWriter pw) {

        AudioFormat fmt = new AudioFormat(44100f, 16, 1, true, false);
        final int bufferByteSize = 2048;

        TargetDataLine line;
        try {
            line = AudioSystem.getTargetDataLine(fmt);
            line.open(fmt, bufferByteSize);
        } catch (LineUnavailableException e) {
            System.err.println(e);
            return;
        }

        byte[] buf = new byte[bufferByteSize];
        float[] samples = new float[bufferByteSize / 2];

        float lastPeak = 0f;

        line.start();
        for (int b; (b = line.read(buf, 0, buf.length)) > -1;) {

            // convert bytes to samples here
            for (int i = 0, s = 0; i < b;) {
                int sample = 0;

                sample |= buf[i++] & 0xFF; // (reverse these two lines
                sample |= buf[i++] << 8;   //  if the format is big endian)

                // normalize to range of +/-1.0f
                samples[s++] = sample / 32768f;
            }

            float rms = 0f;
            float peak = 0f;
            for (float sample : samples) {

                float abs = Math.abs(sample);
                if (abs > peak) {
                    peak = abs;
                }

                rms += sample * sample;
            }

            rms = (float) Math.sqrt(rms / samples.length);

            if (lastPeak > peak) {
                peak = lastPeak * 0.875f;

            }

            lastPeak = peak;

            float level = 0;
            level = lastPeak * 100;
            int lb = Math.round(level);
            if (lb < 20) {
                System.out.println("No sound!");
            } else if (lb > 20) {
                servoDeg = 0;
                servoDeg = lb - 20;
                servoDeg = servoDeg/2;
                
                pw.write(servoDeg);
                System.out.println("servo angle: " + servoDeg);
                pw.flush();

            }

        }
    }

}

public class LipSync {

    LipSync() throws IOException {

//        String port = "COM5";
//        int baudRate = 9600;
//        NRSerialPort serial = new NRSerialPort(port, baudRate);
//        serial.connect();

//        BufferedReader br = new BufferedReader(new InputStreamReader(serial.getInputStream()));
//        PrintWriter pw = new PrintWriter(serial.getOutputStream());

//        while (!br.ready()) {
//
//        }
        System.out.println("wait over.");

        FindLine fn = new FindLine();

        
//        pw.write(fn.servoDeg);
    //    System.out.println("servo angle" + fn.servoDeg);
//        pw.flush();

    }

    public static void main(String[] args) throws IOException {
        LipSync ls = new LipSync();
    }

}
