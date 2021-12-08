package JogoGalaxian.principal;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound {
	public void som(String caminho) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(caminho).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            if(caminho.compareTo("/StarWars.wav")==0) {
            	clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        } catch (Exception ex) {
            System.out.println("Erro ao executar SOM!");
            ex.printStackTrace();
        }
    } 
}
