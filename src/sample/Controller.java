package sample;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class Controller {

    @FXML
    private Button playPause;
    @FXML
    private Button stopButton;
    @FXML
    private Button searchArtist;
    @FXML
    private Button searchSong;

    @FXML
    private MediaView mediaPlayer;
    private MediaPlayer mp;
    private Media me;

    @FXML
    private TextField getContent;
    @FXML
    private Slider timeSlider;
    @FXML
    private Label songName;
    @FXML
    private Label currentTime;
    @FXML
    private Label TTime;

    @FXML
    private TableView trackTable;
    @FXML
    private TableColumn trackTableSong;
    @FXML
    private TableColumn trackTableArtist;

    private ArrayList<Songs.songData> trackList = new ArrayList<>();
    private TrackList setTrackList = new TrackList();
    private Songs trySong = new Songs();

    private boolean isPlaying = false;
    private String playPath = new File("src/sample/media/Play.png").getAbsolutePath();
    private String pausePath = new File("src/sample/media/Pause.png").getAbsolutePath();
    private String stopPath = new File("src/sample/media/Stop.png").getAbsolutePath();
    private Image playImg = new Image(new File(playPath).toURI().toString());
    private Image pauseImg = new Image(new File(pausePath).toURI().toString());
    private Image stopImg = new Image(new File(stopPath).toURI().toString());

    private String path;
    private String songN;

    private Timer updateTimer = new Timer();

    public void initialize() {
        playPause.setContentDisplay(ContentDisplay.CENTER);
        playPause.setGraphic(new ImageView(playImg));

        stopButton.setContentDisplay(ContentDisplay.CENTER);
        stopButton.setGraphic(new ImageView(stopImg));

        setTrackList.setListView();
        setLists(setTrackList.getTrackList());

        trackList = setTrackList.getTrackList();

        path = new File(trackList.get(0).location).getAbsolutePath();
        setSong();

        mp.setAutoPlay(false);


        setTimeSlider();
    }
    private void setTimeSlider()
    {
        mp.currentTimeProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov)
            {
                updatesSlider();
            }
        });
    }
    @FXML
    private void handlePlayListChoose(MouseEvent arg0)
    {
        trackTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

            public void handle(MouseEvent click) {

                if (click.getButton() == MouseButton.PRIMARY && click.getClickCount() == 2)
                {
                    mp.stop();
                    isPlaying = true;
                    playPause.setGraphic(new ImageView(pauseImg));

                    for (int i = 0; i < trackList.size(); i++) {
                        if(trackTable.getSelectionModel().getSelectedItem().equals(trackList.get(i)))
                            {
                             path = new File(trackList.get(i).location).getAbsolutePath();
                            songN = trackList.get(i).name;
                            break;
                        }
                    }
                    songName.setText(songN);
                    setSong();

                    mp.play();
                    addTimeListener();
                    setTimeSlider();
                }
            }
        });
    }

    private void setSong ()
    {
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mediaPlayer.setMediaPlayer(mp);
        updateTimer = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                updatesValues();
            }
        }; updateTimer.schedule(tt,30);
        setTimeSlider();
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

            addTimeListener();
            setTimeSlider();
        }
    }
    private void addTimeListener() {
        mp.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                updatesValues();
            }
        });
        timeSlider.valueProperty().addListener(new InvalidationListener() {
            public void invalidated(Observable ov) {
                if (timeSlider.isPressed()) {

                    mp.seek(mp.getMedia().getDuration().multiply(timeSlider.getValue() / 100));
                }
            }
        });
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

    public void setLists(ArrayList<Songs.songData> playList)
    {

        ObservableList<Songs.songData> list = FXCollections.observableArrayList(playList);
        System.out.println(list);
        trackTableSong.setCellValueFactory(new PropertyValueFactory<>("name"));
        trackTableArtist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        trackTable.setItems(list);
    }

    private String formatTimer(long formatTime)
    {
        return  String.format("%02d : %02d",
                TimeUnit.MILLISECONDS.toMinutes(formatTime),
                TimeUnit.MILLISECONDS.toSeconds(formatTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(formatTime)));
    }
    private void updatesValues()
    {
        Platform.runLater(new Runnable() {
            public void run()
            {
                long secFormat = (long) mp.getCurrentTime().toMillis();
                currentTime.setText(formatTimer(secFormat));

                long endFormat = (long) mp.getStopTime().toMillis();
                TTime.setText(formatTimer(endFormat));

                updateTimer.cancel();
            }
        });

    }

    public void handleNewPlaylist(ActionEvent event) throws Exception{
        try {
            Stage newPlaylist = new Stage();
            Parent root1 = FXMLLoader.load(getClass().getResource("createPlaylist.fxml"));
            newPlaylist.setTitle("Hello World");
            newPlaylist.setScene(new Scene(root1));
            newPlaylist.show();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    protected void updatesSlider()
    {
        Platform.runLater(new Runnable() {
            public void run()
            {
                timeSlider.setValue(mp.getCurrentTime().toMillis() / mp.getTotalDuration().toMillis() * 100);
            }
        });
    }
    public void handlePlaylist(ActionEvent event) throws Exception{
        try {
            Stage viewPlaylists = new Stage();
            Parent root2 = FXMLLoader.load(getClass().getResource("viewPlaylist.fxml"));
            viewPlaylists.setTitle("Hello World");
            viewPlaylists.setScene(new Scene(root2));
            viewPlaylists.show();
            viewPlaylists.setOnHiding((WindowEvent t)-> {
                setTrackList.setPlaylist(setTrackList.getSelectedTracklist());
                setLists(setTrackList.getTrackList());
            });
        } catch (Exception e){
            e.printStackTrace();
        }

    }
    public void handleAllSongs(ActionEvent actionEvent) {
        setTrackList.setListView();
        setLists(setTrackList.getTrackList());
    }
    public void handleSearch(ActionEvent event) {


       ArrayList<String> searchArray = new ArrayList<>();


        Button b = (Button) event.getSource();
        String buttonPressed = b.getText();

        if (buttonPressed.equals("Search for song")) {
            String contentName = getContent.getText();

            DB.selectSQL("Select fldSong from tblSongs WHERE fldSong like '%"+ contentName +"%' ");



            do{
                String data = DB.getData();
                if (data.equals(DB.NOMOREDATA)){
                    break;
                }else{
                    System.out.print(data);
                   // ObservableList<String> list = FXCollections.observableArrayList(data);

                   // trackTable.setItems(list);
                }
                ObservableList<String> list = FXCollections.observableArrayList(data);
                searchArray.addAll(list);
                trackTable.setItems(list);
            } while(true);

        }

        if (buttonPressed.equals("Search for artist")){
            String contentName = getContent.getText();
            DB.selectSQL("Select fldArtist from tblSongs WHERE fldArtist like '%"+ contentName +"%' ");


            do{
                String data = DB.getData();
                if (data.equals(DB.NOMOREDATA)){
                    break;
                }else{
                    System.out.print(data);
                    ObservableList<String> list = FXCollections.observableArrayList(data);
                    for (String searchVal: list) {
                        searchArray.add(searchVal);
                    }
                    System.out.println(searchArray);
                    System.out.println(list);
                }



            } while(true);

        }

    }

    public void handleAddSong(ActionEvent event) throws Exception {

        try {
            Stage addsongsTo = new Stage();
            Parent root4 = FXMLLoader.load(getClass().getResource("tracklist.fxml"));
            addsongsTo.setTitle("Add songs to tracklist");
            addsongsTo.setScene(new Scene(root4));
            addsongsTo.show();

            //TODO
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Add song to tracklist");
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("MP3 files", "*.mp3"),
                    new FileChooser.ExtensionFilter("WAV files", "*.wav"));
            File defaultDirectory = new File("src/sample/media");
            chooser.setInitialDirectory(defaultDirectory);
            File openFileBrowser = chooser.showOpenDialog(addsongsTo);
            File mediaDir = new File(System.getProperty("user.home"), "src/sample/mediaDir");
            if (!mediaDir.exists()) {
                mediaDir.mkdir();
            }
            //String filePath = new File("src/sample/media").getAbsolutePath();
            addsongsTo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
