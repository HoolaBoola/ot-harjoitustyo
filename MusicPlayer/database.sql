CREATE TABLE Songs
(
    id         INTEGER PRIMARY KEY,
    name       varchar(255),
    created_at DATETIME,
    artist     varchar(255),
    file       VARBINARY
);
CREATE TABLE Playlists
(
    id      INTEGER PRIMARY KEY,
    picture IMAGE
);
CREATE TABLE SongPlaylist
(
    song_id INTEGER,
    playlist_id INTEGER,
    FOREIGN KEY (song_id) REFERENCES Songs (id),
    FOREIGN KEY (playlist_id) REFERENCES Playlists (id)
);

