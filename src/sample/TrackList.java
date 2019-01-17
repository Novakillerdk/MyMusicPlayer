package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Iterator;


public class TrackList {
    private Songs songList = new Songs();

    private ArrayList<Songs.songData> trackList = new ArrayList<>();
    private ArrayList<String> trackName = new ArrayList<>();
    private ArrayList<String> ArrayPlaylist = new ArrayList<>();
    private ObservableList listNames= FXCollections.observableArrayList();
    private static String selectedTracklist = "";
    private static boolean closedWithSelect = false;

    /**
     * Adds the song name for easier access, to change the label of current song
     */
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

    /**
     * This will make an array to get the playlist to select
     * If the function calls for withCreateNew, only used in "Create / edit new playlist", it will add it before.
     * @param withCreateNew Requests to add "Create new..." to the array before the other playlist
     */
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

    /**
     * Returns the array ArrayPlaylist
     * @return returns the array
     */
    public ArrayList<String> getArrayPlaylist() {
        return ArrayPlaylist;
    }

    /**
     * Changes the array trackList to setup the table view with all the songs.
     * Makes a separate observable array to get the song name only
     * @param isSearch If the input is a search function
     * @param isArtist If the input search is for artist or song
     * @param searchInput The input of which one wants to search for
     */
    public void setListView(boolean isSearch,boolean isArtist,String searchInput)
    {
        songList.addArray(isSearch,isArtist,searchInput);
        trackList = songList.getTrackList();
        addSongName();
        listNames=FXCollections.observableArrayList(trackName);
    }

    /**
     * This works like the previous, by adding songs to the array trackList,
     * it will however make sure they are all part of the same playlist
     * @param selectedPlaylist Input of what the current selected playlist is
     */
    public void setPlaylist(String selectedPlaylist)
    {
        songList.addArrayPlayList(selectedPlaylist);
        trackList = songList.getTrackList();
    }

    /**
     * Returns the array list trackList
     * @return returns the array
     */
    public ArrayList<Songs.songData> getTrackList() {
        return trackList;
    }

    /**
     * Returns the observable list getList
     * @return returns the list
     */
    public ObservableList getList() {
        return listNames;
    }

    /**
     * Sets the static value selectedTracklist to use in mulitple windows
     * @param selectedTracklist Sets the current selected track list
     */
    public static void setSelectedTracklist(String selectedTracklist) {
        TrackList.selectedTracklist = selectedTracklist;
    }

    /**
     * Gets the selectedTracklist
     * @return returns the value of selectedTracklist
     */
    public static String getSelectedTracklist() {
        return selectedTracklist;
    }

    /**
     * Sets the static value closedWithSelect to use in the controller
     * @param closedWithSelect The information if the "view playlist" was closed by using select or just closed
     */
    public static void setClosedWithSelect(boolean closedWithSelect) {
        TrackList.closedWithSelect = closedWithSelect;
    }

    /**
     * Gets the info closedWithSelect to use
     * @return returns the value cloesdWithSelect
     */
    public static boolean getClosedWithSelect()
    {
        return closedWithSelect;
    }
}