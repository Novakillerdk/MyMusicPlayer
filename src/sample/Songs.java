package sample;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Songs {

    private String loc;
    private String song;

    public Songs(String location,String songName){
        loc = location;
        song = songName;
    }


    public static Songs pizzaTime = new Songs("src/sample/media/pizza_time.mp3","Pizza");
    public static Songs testTrack = new Songs("src/sample/media/TestTrack.mp3","Bongo");

    public String getSong() {
        return song;
    }
}
