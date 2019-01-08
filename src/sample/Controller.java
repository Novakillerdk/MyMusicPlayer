package sample;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.lang.Thread;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map;


public class Controller{

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

    private String path = new File(Songs.pizzaTime.getLoc()).getAbsolutePath();

    public void initialize()
    {
        playPause.setContentDisplay(ContentDisplay.CENTER);
        playPause.setGraphic(new ImageView(playImg));

        stopButton.setContentDisplay(ContentDisplay.CENTER);
        stopButton.setGraphic(new ImageView(stopImg));

        path = new File(Songs.pizzaTime.getLoc()).getAbsolutePath();
        setSong();

        mp.setAutoPlay(false);

        addSongs();
        setListView();
       // handleProgress();
    }

    @FXML
    private void handleSongs (ActionEvent event)
    {
        Button b = (Button) event.getSource();
        String buttonPressed = b.getText();
        mp.stop();
        if (buttonPressed.equals("Play track 1")){
            path = new File(Songs.pizzaTime.getLoc()).getAbsolutePath();
            setSong();


        }
        if (buttonPressed.equals("play track 2"))
        {
            path = new File(Songs.testTrack.getLoc()).getAbsolutePath();
            setSong();

        }

    }

    private void setSong ()
    {
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mediaPlayer.setMediaPlayer(mp);
    }

   private void handleProgress()
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

    public void handlePlaylist(ActionEvent event){

            Button b = (Button) event.getSource();
            String buttonPressed = b.getText();

            if (buttonPressed.equals("Create new playlist")) {

            }

            if (buttonPressed.equals("View all playlists")) {

            }

            if (buttonPressed.equals("Show all songs")) {
                setListView();
            }

    }
}
