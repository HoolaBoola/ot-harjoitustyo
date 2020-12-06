package ui;

import dao.Playlist;
import dao.PlaylistDao;
import dao.Song;
import dao.SongDao;
import io.IO;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

public class ConsoleUI implements UI {
//
//    private Completer completer;
//    private LineReader reader;
//    private ParsedLine commandLine;

    private PlaylistDao playlistDao;
    private SongDao songDao;
    private IO io;

    public ConsoleUI(IO input, PlaylistDao playlistDao, SongDao songDao) {
        this.playlistDao = playlistDao;
        this.songDao = songDao;
        io = input;
//        completer = new Completers.FilesCompleter(new File(System.getProperty("user.home")));
//
//        reader = LineReaderBuilder.builder().
//            terminal(terminal)
//            .completer(completer)
//            .build();

    }

    @Override
    public void startApplication() {

    var commands = listCommands();
    var wrongEntered = false;
        
        while (true) {
            if (wrongEntered) {
                io.print("Please enter a number!");
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
                break;
            }
            
            matchInput(number);
        }


    }
    
    private String listCommands() {
        String commands = "Commands (insert number):\n\n" +
            "[1] play song\n" +
            "[2] add song\n" +
            "[3] list songs\n" +
            "[4] edit song\n" +
            "[5] add playlist\n" +
            "[10] exit";
        
        return commands;
    }

    public void matchInput(int command) {

        switch (command) {
            case 1:
                break;
            case 2:
                addSong();
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                addPlaylist();
                break;
        }

    }

    private void addSong() {
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
            
            io.print("\n\nSong created!\n\n");
        } catch (IOException e) {
            io.print("Something went wrong... Error message:");
            io.print(e.getMessage());
        }
    }

    private void addPlaylist() {
        io.print("Name of the playlist:");
        var name = io.nextLine();

        var playlist = new Playlist(name, new Date(System.currentTimeMillis()));
        
        playlistDao.create(playlist);

        io.print("\n\nPlaylist created!\n\n");

    }

}
