package br.imd.player.model;

import br.imd.player.util.PlaylistOperationException;
import br.imd.player.util.UserType;

/**
 * Representa um usuário regular do sistema.
 */
public class UserRegular extends User {

    /**
     * Construtor da classe UserRegular.
     */
    public UserRegular() {
        super();
        this.type = UserType.REGULAR;
    }

    /**
     * Cria uma nova playlist para o usuário regular.
     * Lança uma exceção PlaylistOperationException, pois usuários regulares não podem criar playlists.
     *
     * @param playlistName o nome da playlist.
     * @throws PlaylistOperationException se ocorrer um erro ao criar a playlist.
     */
    @Override
    public void createPlaylist(String playlistName) throws PlaylistOperationException {
        throw new PlaylistOperationException("Cannot create playlists");
    }
}
