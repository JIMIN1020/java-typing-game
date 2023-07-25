package TermProject;
import javax.sound.sampled.*; // Java Sound API 사용
import java.io.File;
import java.io.IOException;

/*
    < ------- 스레드를 활용하여 오디오 파일을 반복재생하는 class ------- >
    - Java Sound API를 사용해서 오디오 클립 생성 및 클립 무한 반복 재생.
    - 사용자가 스레드를 중단하면 재생도 함께 중지
*/

public class AudioPlayer extends Thread {
    private String filename;    // 오디오 파일 경로랑 이름 저장
    private Clip audioClip;     // 재생할 오디오 클립 참조

    public AudioPlayer(String filename) {
        this.filename = filename;   // 파일이름을 매개변수로 받기
    }

    // run 메소드: 스레드가 시작되면 파일로부터 오디오 클립 로드 +무한반복
    public void run() {
        try {
            File audioFile = new File(filename);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();   // 오디오데이터 형식 알아내기
            DataLine.Info info = new DataLine.Info(Clip.class, format); // 오디오데이터 처리할 line 찾기
            audioClip = (Clip) AudioSystem.getLine(info);   // AudioSystem을 통해서 Line얻고 Clip으로 형변환해서 변수에 저장

            audioClip.open(audioStream);

            // 오디오 클립 반복재생
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);
            audioClip.start();

            // interrupt 날아오기 전까지 반복 실행
            while (!Thread.interrupted()) {
                Thread.sleep(1000);
            }

            audioClip.stop(); // interrupted되면 오디오 클립 멈추기

        } catch (UnsupportedAudioFileException ex) { // 지원되지 않는 오디오 파일 형식
            ex.printStackTrace();
        } catch (LineUnavailableException ex) { // 요청한 라인 사용 불가
            ex.printStackTrace();
        } catch (IOException ex) { // 입출력 오류
            ex.printStackTrace();
        } catch (InterruptedException ex) { // 스레드 중단
            audioClip.stop();
        } finally {
            if (audioClip != null && audioClip.isOpen()) {
                audioClip.close();
            }
        }
    }
}
