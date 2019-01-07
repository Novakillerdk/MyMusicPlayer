package sample;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class Songs {

    private String loc;

    public Songs(String location){
        loc = location;
    }


    public static Songs pizzaTime = new Songs("src/sample/media/pizza_time.mp3");
    public static Songs testTrack = new Songs("src/sample/media/TestTrack.mp3");
}
