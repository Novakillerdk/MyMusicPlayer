package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.util.ArrayList;

public class viewPlaylist {


    @FXML ListView playlistViewer;
    @FXML Button selectPlaylist;

    private ArrayList<String> playlistArray = new ArrayList<>();
    private TrackList trackList = new TrackList();

    /**
     * Calls the method to setup the playlist viewer
     */
    public void initialize(){
        trackList.setClosedWithSelect(false);
        setPlaylistViewer();
    }

    /**
     * This method calls the database and returns all the playlist in the playlist viewer
     */
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

    /**
     * This method will delete the selected playlist from the list as well as the database
     * @param event Upon selecting a playlist and clicking "Remove playlist"
     */
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

    /**
     * This allows the user to select a playlist to listen to
     * In case they did not select anything nothing happens.
     * @param event Upon clicking "select playlist"
     */
    public void handleSelectPlaylist(ActionEvent event) {

        Button b = (Button) event.getSource();
        String buttonpresed = b.getText();

        boolean selectedSometing = false;
        String playlistName = "";
        ObservableList<String> playlist = playlistViewer.getSelectionModel().getSelectedItems();
        if(playlist.isEmpty())
        {
            selectedSometing = false;
        }
        else
        {
            selectedSometing = true;
        }
        for (String getFromArray:playlist) {
            playlistName = getFromArray;
        }
        if (buttonpresed.equals("Select playlist") && selectedSometing){
            trackList.setClosedWithSelect(true);
            trackList.setSelectedTracklist(playlistName);
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
        }
    }
}

