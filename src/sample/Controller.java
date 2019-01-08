package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;

public class Controller {

    @FXML
    private Button playPause;

    @FXML
    private Button stopButton;

    @FXML
    private MediaView mediaPlayer;

    private MediaPlayer mp;
    private Media me;

    @FXML
    private Button track1;
    @FXML
    private Button track2;
    @FXML
    private ProgressBar proBar;
    @FXML
    private Label songName;
    @FXML
    private Label currentTime;
    @FXML
    private Label TTime;

    @FXML
    private ListView listView;

    private ArrayList<String> trackList;

    private boolean isPlaying = false;
    private String playPath = new File("src/sample/media/Play.png").getAbsolutePath();
    private String pausePath = new File("src/sample/media/Pause.png").getAbsolutePath();
    private String stopPath = new File("src/sample/media/Stop.png").getAbsolutePath();
    private Image playImg = new Image(new File(playPath).toURI().toString());
    private Image pauseImg = new Image(new File(pausePath).toURI().toString());
    private Image stopImg = new Image(new File(stopPath).toURI().toString());

    public void initialize()
    {
        playPause.setContentDisplay(ContentDisplay.CENTER);
        playPause.setGraphic(new ImageView(playImg));

        stopButton.setContentDisplay(ContentDisplay.CENTER);
        stopButton.setGraphic(new ImageView(stopImg));

        // Build the path to the location of the media file
        String path = new File("src/sample/media/pizza_time.mp3").getAbsolutePath();
        // Create new Media object (the actual media content)
        me = new Media(new File(path).toURI().toString());
        // Create new MediaPlayer and attach the media to be played
        mp = new MediaPlayer(me);
        //
        mediaPlayer.setMediaPlayer(mp);
        // mp.setAutoPlay(true);
        // If autoplay is turned of the method play(), stop(), pause() etc controls how/when medias are played
        mp.setAutoPlay(false);


        addSongs();
        setListView();
    }

    @FXML
    private void handleSongs (ActionEvent event)
    {
        if (track1.isPressed()){

        }
        else if (track2.isPressed())
        {

        }
    }

    @FXML
    private void handleProgress (ActionEvent event)
    {
       
    }



    public void addSongs()
    {
        trackList = new ArrayList<>();
        trackList.add(Songs.pizzaTime.getSong());
        trackList.add(Songs.testTrack.getSong());
    }

    @FXML
    private void handlePlayPause (ActionEvent event)
    {
        if(isPlaying)
        {
            isPlaying = false;
            playPause.setGraphic(new ImageView(playImg));
            mp.pause();
        }
        else if (!isPlaying)
        {
            isPlaying = true;
            playPause.setGraphic(new ImageView(pauseImg));
            mp.play();
        }
    }

    @FXML
    private void handleStopMedia (ActionEvent event)
    {
        if(isPlaying)
        {
            isPlaying = false;
            playPause.setGraphic(new ImageView(playImg));
        }
        mp.stop();
    }

    private void setListView()
    {
        ObservableList list=FXCollections.observableArrayList(trackList);
        listView.setItems(list);
    }
}
