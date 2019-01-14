package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CreatePlaylist {

    @FXML
    private ListView allSongs;
    @FXML
    private ListView selectedSongs;
    @FXML
    private Button addSongTo;
    @FXML
    private Button removeSong;
    @FXML
    private Button clearPlaylist;
    @FXML
    private Button refreshSongs;
    @FXML
    private Button addPlaylist;
    @FXML
    private TextField playlistName;

    private Songs songList = new Songs();
    private Tracklist setTrackList = new Tracklist();

    public void initialize()
    {
        songList.addArray();
        setTrackList.setListView();
    }
    public void handleRefresh(ActionEvent event)  {
        allSongs.setItems(setTrackList.getList());
    }

    /***
     * Here the user can select which songs
     * should be added to their playlist
     * where after they can give the playlist a name
     * @param event
     */
    public void handleAllSongs(ActionEvent event) {

        Button b = (Button) event.getSource();
        String buttonPressed = b.getText();
        if (buttonPressed.equals("Add")) {

            ObservableList<String> songSelection;
            songSelection = allSongs.getSelectionModel().getSelectedItems();

            for (String sendSongTo: songSelection) {
                selectedSongs.getItems().addAll(sendSongTo);
            }
        }
        if (buttonPressed.equals("Remove")) {

            ObservableList<String> removeSong;
            removeSong = selectedSongs.getSelectionModel().getSelectedItems();

            for (String removed: removeSong) {
                selectedSongs.getItems().removeAll(removeSong);
            }

        }
    }

    public void ClearPlaylist(ActionEvent event) {
        Button b = (Button) event.getSource();
        String buttonPressed = b.getText();
        if(buttonPressed.equals("Clear playlist")){
            /*
            ObservableList<String> removeAll;
            removeAll = selectedSongs.getItems().remove(0, );

            for (String removed: removeAll) {
                selectedSongs.getItems().removeAll(removeAll);
            }*/

        }
    }

    public void sendPlaylist(ActionEvent event) {

        String nameOfPlaylist = playlistName.getText();
        System.out.println(nameOfPlaylist);


        ArrayList<String> testList = new ArrayList<>(selectedSongs.getItems());
        ObservableList testObs = FXCollections.observableArrayList(selectedSongs.getSelectionModel().getSelectedItems());
        String testString = (String) testObs.get(0);
        System.out.println(testString);

        for (String name : testList) {
            System.out.println(name);

        }
        /*
        //TODO
        DB.insertSQL("Insert into tblSongs values('"+playlistName+"'");
        do{
            String data = DB.getDisplayData();
            if (data.equals(DB.NOMOREDATA)){
                break;
            }else{
                System.out.print(data);
            }
        } while(true);*/

        playlistName.clear();

    }

    public void handleMouseAll(MouseEvent mouseEvent) {
        selectedSongs.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent click) {

                if (click.getButton() == MouseButton.PRIMARY && click.getClickCount() == 2) {


                    ObservableList<String> songSelection;
                    songSelection = allSongs.getSelectionModel().getSelectedItems();

                    for (String sendSongTo: songSelection) {
                        songSelection.addAll(sendSongTo);
                    }


                }

            }
        });
    }
}