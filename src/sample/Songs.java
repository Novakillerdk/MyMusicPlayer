package sample;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Songs {

    private String loc;
    private String song;
    public ArrayList<songData> trackList = new ArrayList<>();
    private songData songD = new songData("","",0);

    public Songs()
    {
    }
    public Songs(String location,String songName){
        loc = location;
        song = songName;
    }


    public static Songs pizzaTime = new Songs("src/sample/media/pizza_time.mp3","Pizza");
    public static Songs testTrack = new Songs("src/sample/media/TestTrack.mp3","Bongo");

    public void addArray()
    {
        DB.selectSQL("Select fldPath,fldSong from tblSongs");
        do {
            songD = new songData("","",0);
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

                }
                trackList.add(songD);
            }
        } while (true);

    }

    public String getSong() {
        return song;
    }

    public String getLoc() {
        return loc;
    }

    public ArrayList<songData> getTrackList() {
        return trackList;
    }

    class songData
    {
        String location = loc, name;
        int index;
        songData(String loc,String name,int index)
        {
            this.location = loc;
            this.name = name;
            this.index = index;
        }
    }
}
