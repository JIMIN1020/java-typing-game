package TermProject;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

/*
< -------  1회만 재생되는  class ------- >
    - 오디오 파일과 루프 여부를 파라미터로 받아온 후 오디오 파일을 재생하는 메소드를 가진 class
        -> 모든 클래스에서 접근하여 사용할 수 있도록
*/

public class SoundEffect {
    public void play(String file, boolean Loop) { //play 메소드로 파일을 재생한다.
        Clip clip;
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
            if (Loop) clip.loop(-1);
        } catch (Exception e) {   // 예외처리
            e.printStackTrace();

        }
    }
}
