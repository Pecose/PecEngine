package sound;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {
	
	private Long currentFrame; 
	private Clip clip; 
	private String status = ""; 
	private AudioInputStream audioInputStream; 
	private String filePath; 
	private float volume = 1.0f;
	
	public void setPath(String filePath){ this.filePath = filePath; }

	public Sound(String filePath){ 
	    try {
	    	this.setPath(filePath);
		    audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
		    clip = AudioSystem.getClip(); 
		    clip.open(audioInputStream);
		}catch(Exception e){ e.printStackTrace(); } 
	} 

	public void setVolume(double volume){ 
		this.volume = (float)volume;
		this.adjustVolume();
	}
	
	public Sound upVolume(){
		if(this.volume < 1.0f) this.volume = this.volume + 0.01f;
		this.adjustVolume();
		return this;
	}
	
	public Sound downVolume(){
		if(this.volume > 0.0f) this.volume = this.volume - 0.01f;
		this.adjustVolume();
		return this;
	}
	
	public void play(){ 
		if (status.equals("pause")){ this.resumeAudio(); } 
		if (status.equals("play")){ this.resetAudioStream(); } 
		this.adjustVolume();
	    clip.start(); 
	    status = "play"; 
	} 
	  
	public void pause(){ 
	    if (status.equals("paused")){ 
	        System.out.println("audio is already paused"); 
	        return; 
	    } 
	    this.currentFrame = this.clip.getMicrosecondPosition(); 
	    clip.stop(); 
	    status = "paused"; 
	} 
	  
	public void restart(){ 
	    clip.stop(); 
	    clip.close(); 
	    resetAudioStream(); 
	    currentFrame = 0L; 
	    clip.setMicrosecondPosition(0); 
	    this.play(); 
	} 
	  
	public void stop(){ 
	    currentFrame = 0L; 
	    clip.stop(); 
	    clip.close(); 
	} 
	  
	public void jump(long c){ 
	    if (c > 0 && c < clip.getMicrosecondLength()){ 
	        clip.stop(); 
	        clip.close(); 
	        resetAudioStream(); 
	        currentFrame = c; 
	        clip.setMicrosecondPosition(c); 
	        this.play(); 
	    } 
	} 
	  
	public void loop(){ 
	    clip.loop(Clip.LOOP_CONTINUOUSLY);
	} 
	
	private void resetAudioStream(){ 
		try {
		    audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile()); 
		    clip = AudioSystem.getClip(); 
		    clip.open(audioInputStream); 
		}catch(Exception e){ e.printStackTrace(); } 
		
	} 

	private void adjustVolume(){ 
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		float range = gainControl.getMaximum() - gainControl.getMinimum();
		float gain = (range * this.volume) + gainControl.getMinimum();
		gainControl.setValue(gain); 
	}
	  
	private void resumeAudio(){ 
	    if (status.equals("play")){ 
	        System.out.println("Audio is already "+"being played"); 
	        return; 
	    } 
	    clip.close(); 
	    resetAudioStream(); 
	    clip.setMicrosecondPosition(currentFrame); 
	    this.play(); 
	} 
}