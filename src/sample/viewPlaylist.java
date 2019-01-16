package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.InputEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Observable;

public class viewPlaylist {


    @FXML ListView playlistViewer;
    @FXML Button removePlaylist;
    @FXML Button selectPlaylist;

    private Songs songList = new Songs();
    private Controller getControllerMethods = new Controller();
    private ArrayList<String> playlistArray = new ArrayList<>();
    private TrackList trackList = new TrackList();

    public void initialize(){
        setPlaylistViewer();
    }
    public void setPlaylistViewer()
    {
        playlistArray.clear();
        DB.selectSQL("select fldPlaylist from tblPlaylist");

        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            } else {
                playlistArray.add(data);
            }
        } while (true);

        ObservableList<String> DBData = FXCollections.observableArrayList(playlistArray);
        playlistViewer.setItems(DBData);
    }

    public void handleRemove(ActionEvent event) {
        String playlistName = "";
        ObservableList<String> playlist = playlistViewer.getSelectionModel().getSelectedItems();
        for (String getFromArray:playlist) {
            playlistName = getFromArray;
        }
        DB.deleteSQL("DELETE FROM tblPlaylist where fldPlaylist = '"+ playlistName+"'");
        DB.deleteSQL("DELETE from tblPlaylistSong where fldPlaylistName = '"+playlistName+"'");
        setPlaylistViewer();

    }

    public void handleSelectPlaylist(ActionEvent event) {

        Button b = (Button) event.getSource();
        String buttonpresed = b.getText();

        String playlistName = "";
        ObservableList<String> playlist = playlistViewer.getSelectionModel().getSelectedItems();
        for (String getFromArray:playlist) {
            playlistName = getFromArray;
        }
        if (buttonpresed.equals("Select playlist")){
        trackList.setSelectedTracklist(playlistName);

        }
        final Node source = (Node) event.getSource();
        final Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

    }
}

