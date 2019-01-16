package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

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
    @FXML
    private ChoiceBox<String> choicePlaylist;

    private Songs songList = new Songs();
    private TrackList setTrackList = new TrackList();

    public void initialize()
    {
        songList.addArray();
        setTrackList.setListView();
        allSongs.setItems(setTrackList.getList());
        setChoiceBox();

        choicePlaylist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setPlayLists(newValue);
            }
        });
    }
    public void setPlayLists(String chosenPlay)  {
        if(chosenPlay.equals("Create new..."))
        {
            selectedSongs.getItems().clear();
            playlistName.setVisible(true);
            addPlaylist.setText("Insert");
        }
        else
        {
            System.out.println(chosenPlay);
            songList.setPlayListSongs(chosenPlay);
            ObservableList<String> playListSongs = FXCollections.observableArrayList(songList.getPlayListSongs());
            selectedSongs.setItems(playListSongs);
            playlistName.setVisible(false);
            addPlaylist.setText("Edit");
        }
    }
    public void setChoiceBox()
    {
        ObservableList<String> emptyList = FXCollections.observableArrayList("");
        choicePlaylist.getItems().setAll(emptyList);
        setTrackList.setArrayPlaylist(true);
        choicePlaylist.getItems().setAll(setTrackList.getArrayPlaylist());
        choicePlaylist.setValue("Create new...");
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
        /*Button b = (Button) event.getSource();
        String buttonPressed = b.getText();
        if(buttonPressed.equals("Clear playlist")){

            ObservableList<String> removeAll;
            removeAll = selectedSongs.getItems().remove(0);

            for (String removed: removeAll) {
                selectedSongs.getItems().removeAll(removeAll);
            }

        }*/
    }

    public void sendPlaylist(ActionEvent event) {

        String nameOfPlaylist = playlistName.getText();
        System.out.println(nameOfPlaylist);


        ArrayList<String> testList = new ArrayList<>(selectedSongs.getItems());
        int songOrder = 0;

        for (String songName : testList) {
            songOrder++;
            DB.insertSQL("Insert into tblPlaylistSong values('"+nameOfPlaylist+"','"+songName+"',"+songOrder+")");
        }
        ArrayList<Integer> indexDataArray = new ArrayList<>();

        DB.selectSQL("Select fldIndex from tblPlaylist order by fldIndex asc");
        do {
            String indexDataStr = DB.getData();

            if (indexDataStr.equals(DB.NOMOREDATA)) {
                break;
            } else {
                int indexData = Integer.valueOf(indexDataStr);
                indexDataArray.add(indexData);
            }
        } while (true);

        int indexCounter = 1;
        boolean indexDone = false;
        int newIndex = 1;
        for (int tryNumber: indexDataArray
             ) {
            if(indexCounter != tryNumber && !indexDone)
            {
                indexDone = true;
                newIndex = indexCounter;
            }
            indexCounter++;
        }
        if(!indexDone)
        {
            newIndex = indexCounter;
        }
        playlistName.clear();
        selectedSongs.getItems().clear();
        DB.insertSQL("Insert into tblPlaylist values('"+nameOfPlaylist+"',"+newIndex+")");
        setChoiceBox();
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