package br.imd.player.model;

import br.imd.player.util.PlaylistOperationException;
import br.imd.player.util.UserType;

public class UserRegular extends User {
    

    public UserRegular() {
        super();
        this.type = UserType.REGULAR;
    }

    @Override
    public void createPlaylist(String playlistName) throws PlaylistOperationException {
        throw new PlaylistOperationException("Cannot create playlists");
    }
}
