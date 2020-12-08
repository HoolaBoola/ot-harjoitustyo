package ui;

import dao.Playlist;
import dao.PlaylistDao;
import dao.Song;
import dao.SongDao;
import io.IO;
import io.SongPlayer;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ListIterator;

public class ConsoleUI implements UI {

    public final String separator = System.lineSeparator();
    public PlaylistDao playlistDao;
    public SongDao songDao;
    public IO io;
    public SongPlayer player;

    public ConsoleUI(IO input, PlaylistDao playlistDao, SongDao songDao, SongPlayer player) {
        this.playlistDao = playlistDao;
        this.songDao = songDao;
        io = input;
        this.player = player;

    }

    @Override
    public void startApplication() {

        var commands = listCommands();
        var wrongEntered = false;

        while (true) {
            if (wrongEntered) {
                io.print("Please enter a number!" + separator);
                wrongEntered = false;
            } else {
                io.print(commands);
            }

            var input = io.nextLine();
            int number;
            try {
                number = Integer.parseInt(input);
            } catch (Exception e) {
                wrongEntered = true;
                continue;
            }

            if (number == 10) {
                player.quit();
                break;
            }

            matchInput(number);
        }

        System.exit(0);

    }

    public String listCommands() {
        String commands = "Commands (insert number):" + separator +
            "[0] play/pause menu" + separator +
            "[1] choose song" + separator +
            "[2] add song" + separator +
            "[3] list songs" + separator +
            "[4] edit song" + separator +
            "[5] add playlist" + separator +
            "[10] exit" + separator;

        return commands;
    }

    public void matchInput(int command) {

        switch (command) {
            case 0:
                playPause();
                break;
            case 1:
                playSong();
                break;
            case 2:
                addSong();
                break;
            case 3:
                listSongs();
                break;
            case 4:
                break;
            case 5:
                addPlaylist();
                break;
            default:
                io.print("Unknown command!" + separator);
        }

    }

    public void playSong() {
        var songs = songDao.list();
        ListIterator<Song> it = songs.listIterator();
        
        io.print(separator + "Choose song to play (enter number):" + separator);
        
        while(it.hasNext()) {
            io.print("[" + it.nextIndex() + "] " + it.next());
        }
        
        var input = io.nextLine();
        
        try {
            int choice = Integer.parseInt(input);
            Song song = songs.get(choice);
            
            player.playSong(song.getFile());
        } catch (Exception e) {
            io.print(separator + separator + "Choose a valid number!" + separator + separator);
        }
        
    }



    public void playPause() {
        switch (player.getStatus()) {
            case "PLAYING":
                player.pauseSong();
                break;
            case "PAUSED":
                player.continuePlaying();
                break;
        }
    }
    
    public void listSongs() { 
        io.print(separator + separator);
        songDao.list().forEach(s -> io.print("\t" + s.info()));
        io.print(separator + separator);
    }
    
    public void addSong() {
        io.print("Name of the song:");
        var name = io.nextLine();

        io.print("Artist:");
        var artist = io.nextLine();

        io.print("Path to the file:");
        var path = io.nextLine();

        File file = new File(path);
        try {
            var song = new Song(name, new Date(System.currentTimeMillis()), artist, FileUtils.readFileToByteArray(file));

            songDao.create(song);

            io.print(separator + separator + "Song created!" + separator + separator);
        } catch (IOException e) {
            io.print("Something went wrong... Error message:");
            io.print(e.getMessage() + separator);
        }
    }

    public void addPlaylist() {
        io.print("Name of the playlist:");
        var name = io.nextLine();

        var playlist = new Playlist(name, new Date(System.currentTimeMillis()));

        playlistDao.create(playlist);

        io.print(separator + separator + "Playlist created!" + separator + separator);

    }

}
