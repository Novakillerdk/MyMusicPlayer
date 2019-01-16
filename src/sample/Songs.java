package sample;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Songs {

    public ArrayList<songData> trackList = new ArrayList<>();
    public ArrayList<String> playListSong = new ArrayList<>();
    private songData songD = new songData("","","");
    private songData playList = new songData("","","");

    public void addArray()
    {
        trackList.clear();
        DB.selectSQL("Select fldPath,fldSong,fldArtist from tblSongs");
        do {
            songD = new songData("","","");
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            }
            else
            {
                songD.location = data;
                data = DB.getData();
                if (data.equals(DB.NOMOREDATA)) {
                    break;
                }
                else
                {
                    songD.name = data;
                    data = DB.getData();
                    if (data.equals(DB.NOMOREDATA)) {
                        break;
                    }
                    else
                    {
                        songD.artist = data;

                    }
                    trackList.add(songD);
                }

            }
        } while (true);

    }
    public void addArrayPlayList(String playListName)
    {
        trackList.clear();
        ArrayList<String> songsInPlaylist = new ArrayList<>();

        DB.selectSQL("Select fldSong from tblPlaylistSong where fldPlaylistName = '"+playListName+"'");
        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            }
            else
            {
                songsInPlaylist.add(data);
            }
        } while (true);
        for (String songName : songsInPlaylist
             ) {
            DB.selectSQL("Select fldPath,fldArtist from tblSongs where fldSong = '"+songName+"'");
            do {
                String data = DB.getData();
                playList = new songData("","","");
                if (data.equals(DB.NOMOREDATA)) {
                    break;
                }
                else
                {
                    playList.location = data;
                    data = DB.getData();
                    if (data.equals(DB.NOMOREDATA)) {
                        break;
                    }
                    else
                    {
                        playList.name = songName;
                        playList.artist = data;
                        trackList.add(playList);
                    }
                    System.out.println(playList.name);
                    System.out.println(playList.location);
                    System.out.println(playList.artist);

                }
            } while (true);
        }
    }
    public void setPlayListSongs(String playList)
    {
        playListSong.clear();
        DB.selectSQL("Select fldSong from tblPlaylistSong where fldPlaylistName = '"+playList+"'");
        do {
            String data = DB.getData();
            if (data.equals(DB.NOMOREDATA)) {
                break;
            } else {
                playListSong.add(data);
            }
        }while(true);
    }
    public ArrayList<String> getPlayListSongs()
    {
        return playListSong;
    }
    public ArrayList<songData> getTrackList() {
        return trackList;
    }

    public static class songData
    {
        String location, name, artist;
        songData(String loc,String name,String artist)
        {
            this.location = loc;
            this.name = name;
            this.artist = artist;
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public String getArtist() {
            return artist;
        }
    }
}
