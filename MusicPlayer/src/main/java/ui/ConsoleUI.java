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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

        var commands = listCommands(commandsList);
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

    public String listCommands(List<String> commandsList) {

        String commands = String.join(separator + "\t", commandsList);

        return commands;
    }

    List<String> commandsList = new ArrayList<>(
        Arrays.asList(
            new String[]{
                "Commands (insert number):",
                "[0] play/pause menu",
                "[1] choose song",
                "[2] add song",
                "[3] list songs",
                "[4] edit song",
                "[5] delete song",
                "[6] add playlist",
                "[7] list playlists",
                "[8] edit playlist",
                "[9] delete playlist",
                "[10] exit"}
        )
    );

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
                editSong();
                break;
            case 5:
                deleteSong();
                break;
            case 6:
                addPlaylist();
                break;
            case 7:
                listPlaylists();
                break;
            case 8:
                editPlaylist();
                break;
            case 9:
                deletePlaylist();
                break;
            default:
                io.print("Unknown command!" + separator);
        }

    }

    public void addPlaylist() {
        io.print("Name of the playlist:");
        var name = io.nextLine();

        var playlist = new Playlist(name, new Date(System.currentTimeMillis()));

        playlistDao.create(playlist);

        io.print(separator + separator + "Playlist created!" + separator + separator);

    }

    public void listPlaylists() {
        io.print(separator + separator);
        var list = playlistDao.list();
        if (list.isEmpty()) {
            io.print("\tNo playlists added to database");
        } else {
            list.forEach(s -> io.print("\t" + s.info()));
        }
        io.print(separator + separator);
    }

    public void deletePlaylist() {
        var playlists = enumeratePlaylists();

        if (playlists.isEmpty()) {
            return;
        }

        var input = io.nextLine();
        Playlist playlist;
        try {
            int choice = Integer.parseInt(input);
            playlist = playlists.get(choice);
        } catch (Exception e) {
            io.print(separator + separator + "Choose a valid number!" + separator + separator);
            return;
        }

        io.print(separator + separator + "Are you sure you want to delete playlist \"" + playlist.getName() + "\"? [y/n]" + separator + separator);

        if (io.nextLine().toLowerCase().strip().equals("y")) {
            playlistDao.delete(playlist);
            io.print(separator + separator + "Playlist deleted successfully!" + separator + separator);
        } else {
            io.print(separator + separator + "Playlist deletion canceled." + separator + separator);
        }
    }

    public void editPlaylist() {
        var playlists = enumeratePlaylists();

        if (playlists.isEmpty()) {
            return;
        }

        var input = io.nextLine();
        Playlist playlist;
        try {
            int choice = Integer.parseInt(input);
            playlist = playlists.get(choice);
        } catch (Exception e) {
            io.print(separator + separator + "Choose a valid number!" + separator + separator);
            return;
        }

        editPlaylist(playlist);
    }

    public void editPlaylist(Playlist playlist) {
        boolean somethingChanged = false;

        io.print(separator + "Enter new name: (empty skips)" + separator);
        String name = io.nextLine();

        if (!name.isBlank()) {
            playlist.setName(name);
            somethingChanged = true;
        }

        playlistDao.update(playlist);
        io.print(separator + separator + (somethingChanged ? "Playlist updated!" : "Nothing changed.") + separator + separator);
    }

    public void deleteSong() {
        var songs = enumerateSongs();

        if (songs.isEmpty()) {
            return;
        }

        var input = io.nextLine();
        Song song;
        try {
            int choice = Integer.parseInt(input);
            song = songs.get(choice);
        } catch (Exception e) {
            io.print(separator + separator + "Choose a valid number!" + separator + separator);
            return;
        }

        io.print(separator + separator + "Are you sure you want to delete song \"" + song.getName() + "\"? [y/n]" + separator + separator);

        if (io.nextLine().toLowerCase().strip().equals("y")) {
            songDao.delete(song);
            io.print(separator + separator + "Song deleted successfully!" + separator + separator);
        } else {
            io.print(separator + separator + "Song deletion canceled." + separator + separator);
        }
    }

    public void editSong() {
        var songs = enumerateSongs();

        if (songs.isEmpty()) {
            return;
        }

        var input = io.nextLine();
        Song song;
        try {
            int choice = Integer.parseInt(input);
            song = songs.get(choice);
        } catch (Exception e) {
            io.print(separator + separator + "Choose a valid number!" + separator + separator);
            return;
        }

        io.print(separator + "What to do with the song?");
        io.print(listCommands(songEditCommandsList));

        input = io.nextLine();
        try {
            int choice = Integer.parseInt(input);
            matchSongEditInput(choice, song);
        } catch (Exception e) {
            io.print(separator + separator + "Choose a valid number!" + separator + separator);
        }
    }

    List<String> songEditCommandsList = new ArrayList<>(
        Arrays.asList(
            new String[]{
                "Commands:",
                "[1] Edit song",
                "[2] Add song to playlist",
            }
        )
    );

    public void matchSongEditInput(int command, Song song) {
        switch (command) {
            case 1:
                editSong(song);
                break;
            case 2:
                addSongToPlaylist(song);
                break;
            default:
                io.print(separator + "No such command!" + separator);
        }
    }

    public void addSongToPlaylist(Song song) {
        var playlists = enumeratePlaylists();

        if (playlists.isEmpty()) {
            return;
        }

        var input = io.nextLine();
        Playlist playlist;
        try {
            int choice = Integer.parseInt(input);
            playlist = playlists.get(choice);
        } catch (Exception e) {
            io.print(separator + separator + "Choose a valid number!" + separator + separator);
            return;
        }

        songDao.addSongToPlaylist(song, playlist);

        io.print(separator + separator + "Song added to playlist!" + separator + separator);

    }

    public void editSong(Song song) {
        boolean somethingChanged = false;

        io.print(separator + "Enter new name: (empty skips)" + separator);
        String name = io.nextLine();

        if (!name.isBlank()) {
            song.setName(name);
            somethingChanged = true;
        }

        io.print(separator + "Enter new artist: (empty skips)" + separator);
        String artist = io.nextLine();

        if (!artist.isBlank()) {
            song.setArtist(artist);
            somethingChanged = true;
        }

        io.print(separator + "Enter path to new file: (empty skips)" + separator);
        String path = io.nextLine();

        if (!path.isBlank()) {
            File file = new File(path);
            try {
                somethingChanged = true;
                song.setFile(FileUtils.readFileToByteArray(file));


            } catch (IOException e) {
                io.print("Something went wrong... Error message:");
                io.print(e.getMessage() + separator);
            }
        }
        songDao.update(song);
        io.print(separator + separator + (somethingChanged ? "Song updated!" : "Nothing changed.") + separator + separator);
    }

    public void playSong() {
        var songs = enumerateSongs();

        if (songs.isEmpty()) {
            return;
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

    public List<Song> enumerateSongs() {
        var songs = songDao.list();
        if (songs.isEmpty()) {
            io.print(separator + "No songs added to the database. (Hint: use the command \"add\")");
            return songs;
        }
        ListIterator<Song> it = songs.listIterator();

        io.print(separator + "Choose song (enter number):" + separator);

        while (it.hasNext()) {
            io.print("\t[" + it.nextIndex() + "] " + it.next());
        }

        return songs;
    }

    public List<Playlist> enumeratePlaylists() {
        var playlists = playlistDao.list();
        if (playlists.isEmpty()) {
            io.print(separator + "No playlists added to the database. (Hint: use the command \"add\")");
            return playlists;
        }
        ListIterator<Playlist> it = playlists.listIterator();

        io.print(separator + "Choose playlist (enter number):" + separator);

        while (it.hasNext()) {
            io.print("\t[" + it.nextIndex() + "] " + it.next());
        }

        return playlists;
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
        var list = songDao.list();
        if (list.isEmpty()) {
            io.print("\tNo songs added to database");
        } else {
            list.forEach(s -> io.print("\t" + s.info()));
        }
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


}
