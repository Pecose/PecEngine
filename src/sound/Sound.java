package sound;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
 
public class Sound extends Thread{
				private double vol = 0;
                static boolean isSet = false;
                static String fileName;
               
        public Sound(String path){ fileName = path; }
        public void setVolume(double x){ vol = x;}
 
        public void run(){
                SourceDataLine line;
                AudioInputStream audioInputStream;
                AudioFormat audioFormat;
                File file = new File(fileName);
                        
                try {
                    audioInputStream = AudioSystem.getAudioInputStream(file);
                    audioFormat = audioInputStream.getFormat();
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
                    line = (SourceDataLine) AudioSystem.getLine(info);
                    line.open(audioFormat);
                    line.start();
	                if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
	                	FloatControl volume = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
	                        byte bytes[] = new byte[2];
	                        int bytesRead = 0;
	                        while ((bytesRead = audioInputStream.read(bytes, 0, bytes.length)) != -1) {
	                                volume.setValue((float) vol); // de -80 a 6.02
	                                line.write(bytes, 0, bytesRead);
	                        }
	                	line.close();
	                }
                }catch (Exception e){ e.printStackTrace(); return; }
                        
               
                
        }
}