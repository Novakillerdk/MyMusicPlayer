package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;


public class TrackList {
    private Songs songList = new Songs();

    public ArrayList<Songs.songData> trackList;
    public ObservableList list= FXCollections.observableArrayList();

    public void addSong()
    {
        trackList = new ArrayList<>(songList.getTrackList());
        trackList.add(trackList.get(0));
    }
    public void setListView()
    {
        addSong();
        list=FXCollections.observableArrayList(trackName);
    }

    public ObservableList getList() {
        return list;
    }
}