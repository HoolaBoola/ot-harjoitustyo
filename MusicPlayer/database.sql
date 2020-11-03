CREATE TABLE [Songs] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [name] nvarchar(255),
  [created_at] timestamp,
  [artist] nvarchar(255),
  [file] File,
  [picture] File
)
GO

CREATE TABLE [Playlists] (
  [id] int PRIMARY KEY IDENTITY(1, 1),
  [picture] File
)
GO

CREATE TABLE [SongPlaylist] (
  [song_id] int,
  [playlist_id] int
)
GO

ALTER TABLE [SongPlaylist] ADD FOREIGN KEY ([song_id]) REFERENCES [Songs] ([id])
GO

ALTER TABLE [SongPlaylist] ADD FOREIGN KEY ([playlist_id]) REFERENCES [Playlists] ([id])
GO
