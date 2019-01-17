package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class CreatePlaylist {

    @FXML
    private ListView allSongs;
    @FXML
    private ListView selectedSongs;
    @FXML
    private Button addPlaylist;
    @FXML
    private TextField playlistName;
    @FXML
    private ChoiceBox<String> choicePlaylist;

    private Songs songList = new Songs();
    private TrackList setTrackList = new TrackList();
    private boolean isEdit = false;

    public void initialize()
    {
        songList.addArray(false,false,null);
        setTrackList.setListView(false,false,null);
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
            isEdit = false;
        }
        else
        {
            songList.setPlayListSongs(chosenPlay);
            ObservableList<String> playListSongs = FXCollections.observableArrayList(songList.getPlayListSongs());
            selectedSongs.setItems(playListSongs);
            playlistName.setVisible(false);
            addPlaylist.setText("Edit");
            isEdit = true;
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
        selectedSongs.getItems().clear();
    }
    public void sendPlaylist(ActionEvent event)
    {
        setPlaylist(isEdit);
    }
    public void setPlaylist(boolean isEdit) {
        boolean isEmpty = false;
        String nameOfPlaylist = playlistName.getText();
        if (isEdit) {
            nameOfPlaylist = choicePlaylist.getValue();

            ArrayList<String> testList = new ArrayList<>(selectedSongs.getItems());
            if(testList.isEmpty()) {
                isEmpty = true;
            }

            DB.deleteSQL("Delete from tblPlaylistSong where fldPlaylistName = '"+nameOfPlaylist+"'");
            DB.deleteSQL("Delete from tblPlaylist where fldPlaylist = '"+nameOfPlaylist+"'");
        }
        if(!isEmpty) {
            ArrayList<String> selectedList = new ArrayList<>(selectedSongs.getItems());
            int songOrder = 0;

            for (String songName : selectedList) {
                songOrder++;
                DB.insertSQL("Insert into tblPlaylistSong values('" + nameOfPlaylist + "','" + songName + "'," + songOrder + ")");
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
            for (int tryNumber : indexDataArray
            ) {
                if (indexCounter != tryNumber && !indexDone) {
                    indexDone = true;
                    newIndex = indexCounter;
                }
                indexCounter++;
            }
            if (!indexDone) {
                newIndex = indexCounter;
            }
            playlistName.clear();
            selectedSongs.getItems().clear();
            DB.insertSQL("Insert into tblPlaylist values('" + nameOfPlaylist + "'," + newIndex + ")");
        }

        setChoiceBox();
    }
}