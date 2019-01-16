package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;


public class TrackList {
    private Songs songList = new Songs();

    private ArrayList<Songs.songData> trackList = new ArrayList<>();
    private ArrayList<String> trackName = new ArrayList<>();
    private ArrayList<String> trackArtist = new ArrayList<>();
    private ArrayList<String> ArrayPlaylist = new ArrayList<>();
    private ObservableList listNames= FXCollections.observableArrayList();
    private static String selectedTracklist = "";
    private static boolean closedWithSelect = false;

    private void addSongName()
    {
        trackName.clear();
        Iterator itr=trackList.iterator();
        while(itr.hasNext()) {
            Songs.songData st = (Songs.songData) itr.next();
            String songN = st.getName();
            trackName.add(songN);
        }
    }
    private void addSongArtist()
    {
        songList.addArray();

        trackArtist.clear();
        trackList = songList.getTrackList();
        Iterator itr=trackList.iterator();
        while(itr.hasNext()) {
            Songs.songData st = (Songs.songData) itr.next();
            String songArt = st.getArtist();
            trackArtist.add(songArt);
        }
    }
    public void setArrayPlaylist(boolean withCreateNew)
    {
        ArrayPlaylist.clear();
        if (withCreateNew)
        {
            ArrayPlaylist.add("Create new...");
        }
        DB.selectSQL("Select Trim(fldPlaylist) from tblPlaylist");
        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            } else {
                ArrayPlaylist.add(data);
            }
        } while (true);
    }

    public ArrayList<String> getArrayPlaylist() {
        return ArrayPlaylist;
    }

    public void setListView()
    {
        songList.addArray();
        trackList = songList.getTrackList();
        addSongName();
        listNames=FXCollections.observableArrayList(trackName);
    }
    public void setPlaylist(String selectedPlaylist)
    {
        songList.addArrayPlayList(selectedPlaylist);
        trackList = songList.getTrackList();
    }

    public ArrayList<Songs.songData> getTrackList() {
        return trackList;
    }

    public ObservableList getList() {
        return listNames;
    }

    public static void setSelectedTracklist(String selectedTracklist) {
        TrackList.selectedTracklist = selectedTracklist;
    }

    public static String getSelectedTracklist() {
        return selectedTracklist;
    }

    public static void setClosedWithSelect(boolean closedWithSelect) {
        TrackList.closedWithSelect = closedWithSelect;
    }
    public static boolean getClosedWithSelect()
    {
        return closedWithSelect;
    }
}
/*
    private ArrayList<Songs.songData> trackList = new ArrayList<>();
    private ArrayList<String> trackList1 = new ArrayList<>();

    public void initialize()
    {
        songList.addArray();
    }
    public void handleRefresh(ActionEvent event)  {

        trackList1.clear();
        trackList = songList.getTrackList();
        Iterator itr=trackList.iterator();
        while(itr.hasNext()) {
            Songs.songData st = (Songs.songData) itr.next();
            String songN = st.name;
            trackList1.add(songN);
        }
            ObservableList list=FXCollections.observableArrayList(trackList1);
            allSongs.setItems(list);

    }
    */