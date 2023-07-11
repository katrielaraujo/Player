package br.imd.player.model;

import br.imd.player.util.PlaylistOperationException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.imd.player.util.FileLister;
import br.imd.player.util.UserType;

/**
 * Classe abstrata que representa um usuário do sistema.
 */
public abstract class User implements FileLister {
    protected Integer id;
    protected String email;
    protected String password;
    protected UserType type;
    protected String directory;

    /**
     * Lista os arquivos no diretório do usuário.
     *
     * @return uma lista de arquivos no diretório do usuário.
     */
    @Override
    public List<File> listFilesInDirectory() {
        List<File> fileList = new ArrayList<>();

        File directoryFile = new File(directory);
        File[] files = directoryFile.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().toLowerCase().endsWith(".mp3")) {
                    fileList.add(file);
                }
            }
        }
        return fileList;
    }

    /**
     * Retorna o diretório do usuário.
     *
     * @return o diretório do usuário.
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * Define o diretório do usuário.
     *
     * @param directoryPath o caminho do diretório do usuário.
     */
    public void setDirectory(String directoryPath) {
        this.directory = directoryPath;
    }

    /**
     * Retorna o ID do usuário.
     *
     * @return o ID do usuário.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retorna o tipo de usuário.
     *
     * @return o tipo de usuário.
     */
    public UserType getType() {
        return type;
    }

    /**
     * Define o tipo de usuário.
     *
     * @param type o tipo de usuário.
     */
    public void setType(UserType type) {
        this.type = type;
    }

    /**
     * Retorna o email do usuário.
     *
     * @return o email do usuário.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o email do usuário.
     *
     * @param email o email do usuário.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retorna a senha do usuário.
     *
     * @return a senha do usuário.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define a senha do usuário.
     *
     * @param password a senha do usuário.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Cria uma nova playlist para o usuário.
     *
     * @param playlistName o nome da playlist.
     * @throws PlaylistOperationException se ocorrer um erro ao criar a playlist.
     */
    public abstract void createPlaylist(String playlistName) throws PlaylistOperationException;

    /**
     * Define o ID do usuário.
     *
     * @param id o ID do usuário.
     */
    public void setId(int id) {
        this.id = id;
    }
}
