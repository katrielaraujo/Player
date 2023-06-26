package br.imd.player.model;

import br.imd.player.util.PlaylistOperationException;

public class UserRegular extends User {
    private String directoryPath;

    public UserRegular() {
        super();
        this.type = false;
    }

    public String getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(String directoryPath) {
        this.directoryPath = directoryPath;
    }

    @Override
    public void createPlaylist(String playlistName) throws PlaylistOperationException {
        throw new PlaylistOperationException("Cannot create playlists");
    }
}
