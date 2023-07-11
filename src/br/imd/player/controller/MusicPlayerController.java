package br.imd.player.controller;

import br.imd.player.DAO.MediaManager;
import br.imd.player.model.Playlist;
import br.imd.player.model.Song;
import br.imd.player.model.User;
import br.imd.player.model.UserVip;
import br.imd.player.util.SongNotFoundException;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class MusicPlayerController {
    @FXML
    private ListView<Song> musicList;
    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton, pauseButton, resetButton, previousButton, nextButton, addFileButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;
    @FXML
    private ListView<Playlist> playlistListView;

    private int songNumber;
    private Timer timer;
    private TimerTask task;
    private boolean running;

    private User user;
    private MediaManager dao;
    private List<File> songs;
    private ObservableList<Song> songList;
    private MediaPlayer mediaPlayer;
    private Media media;
    private FileChooser fileChooser;
    private ObservableList<Playlist> playlistList;

    public void initialize() {
        initializeVolumeSlider();
        initializeMusicListCellFactory();
        initializePlaylistListViewCellFactory();
        initializeSongList();

        songProgressBar.setStyle("-fx-accent: #00FF00;");
        fileChooser = new FileChooser();
        fileChooser.setTitle("Adicionar Arquivo de Música");
    }

    private void initializeVolumeSlider() {
        volumeSlider.valueProperty().addListener((ObservableValue<? extends Number> arg0, Number arg1, Number arg2) -> {
            mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        });
    }

    private void initializeMusicListCellFactory() {
        musicList.setCellFactory(param -> new ListCell<Song>() {
            @Override
            protected void updateItem(Song item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getTitle());
                } else {
                    setText(null);
                }
            }
        });
    }

    private void initializePlaylistListViewCellFactory() {
        playlistListView.setCellFactory(param -> new ListCell<Playlist>() {
            @Override
            protected void updateItem(Playlist item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getName());
                } else {
                    setText(null);
                }
            }
        });
    }

    private void initializeSongList() {
        songList = FXCollections.observableArrayList();
        musicList.setItems(songList);
    }

    public void setUser(User user) {
        this.user = user;
        chooseFileMethod();
        loadPlaylists();
    }

    public void exibirAviso(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void loadPlaylists() {
        if (user instanceof UserVip) {
            UserVip userVip = (UserVip) user;
            dao = new MediaManager();
            userVip.setPlaylists(dao.getPlaylistsByUserId(userVip.getId()));
            playlistList = FXCollections.observableArrayList(userVip.getPlaylists().values());
            playlistListView.setItems(playlistList);
        }
    }

    public void chooseFileMethod() {
        if (user.getDirectory() != null && !user.getDirectory().isEmpty()) {
            loadFilesFromDirectory();
        } else {
            exibirAviso("Diretorio", "Selecione o seu diretorio contendo musicas");
            showDirectoryChooser();
        }
    }

    public void loadFilesFromDirectory() {
        songs = user.listFilesInDirectory();
        if (songs != null && !songs.isEmpty()) {
            songList.addAll(convertFilesToSongs(songs));
            songNumber = 0;
            songLabel.setText(songList.get(songNumber).getTitle());
            toPlay(songNumber);
        }
    }

    public void showDirectoryChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Selecionar Pasta");

        File directory = directoryChooser.showDialog(null);

        if (directory != null) {
            user.setDirectory(directory.getAbsolutePath());
            dao = new MediaManager();
            dao.updateUser(user);
            loadFilesFromDirectory();
        }
    }

    public void addSongsToSelectedPlaylist(List<Song> songs) {
        String selectedPlaylistName = String.valueOf(playlistListView.getSelectionModel().getSelectedItem());
        if (selectedPlaylistName != null) {
            UserVip userVip = (UserVip) user;
            Playlist selectedPlaylist = userVip.getPlaylists().get(selectedPlaylistName);
            if (selectedPlaylist != null) {
                for (Song song : songs) {
                    selectedPlaylist.addSong(song);
                }
            }
        }
    }

    public void addFileToDirectory(List<File> selectedFiles, String directoryPath) {
        if (directoryPath != null && !directoryPath.isEmpty()) {
            File directory = new File(directoryPath);
            List<Song> newSongs = new ArrayList<>();

            for (File file : selectedFiles) {
                File newFile = new File(directory, file.getName());
                try {
                    // Copie ou mova o arquivo para o diretório existente
                    Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                    // Crie uma nova música e adicione-a à lista de músicas
                    Song newSong = new Song();
                    newSong.setTitle(file.getName());
                    newSong.setFilePath(newFile.getAbsolutePath());

                    if (!songList.contains(newSong)) {
                        songList.add(newSong);
                        newSongs.add(newSong);
                    }
                } catch (IOException | SongNotFoundException e) {
                    e.printStackTrace();
                }
            }

            // Chame o método para adicionar as novas músicas à playlist selecionada
            addSongsToSelectedPlaylist(newSongs);
        }
    }






    @FXML
    private void addFile( ) {
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            addFileToDirectory(selectedFiles, user.getDirectory());
        }
    }







    public void criarPlaylist(ActionEvent event) {
        String playlistName = openInputDialog();
        UserVip userVip = (UserVip) user;
        updateUserVip(userVip);

        if (!userVip.getPlaylists().containsKey(playlistName)) {
            dao = new MediaManager();
            userVip.createPlaylist(playlistName);
            dao.insertPlaylist(userVip.getPlaylists().get(playlistName));
            playlistList.add(userVip.getPlaylists().get(playlistName));
            exibirAviso("Playlist", "Playlist criada com sucesso!");
        } else {
            exibirAviso("Playlist", "A playlist já existe!");
        }
    }

    private String openInputDialog() {
        String playlistName = null;
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Adicionar Playlist");
        dialog.setHeaderText(null);
        dialog.setContentText("Digite o nome da playlist:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            playlistName = result.get();
        }
        return playlistName;
    }

    public void selectPlaylist(MouseEvent mouseEvent) {
        Playlist selectedPlaylist = playlistListView.getSelectionModel().getSelectedItem();
        if (selectedPlaylist != null) {
            Map<String, Song> songs = selectedPlaylist.getSongs();
            List<Song> playlistSongs = new ArrayList<>(songs.values());
            songList.setAll(playlistSongs); // Atualiza a lista de músicas (musicList) com as músicas da playlist

            if (!playlistSongs.isEmpty()) {
                songNumber = 0;
                songLabel.setText(playlistSongs.get(songNumber).getTitle());
                mediaPlayer.stop();
                if (running) {
                    cancelTimer();
                }
                toPlay(songNumber); // Reproduz a primeira música da playlist
            } else {
                exibirAviso("Playlist Vazia", "A playlist selecionada não contém músicas. Por favor, adicione uma música à playlist.");
                addFile();
            }
        }
    }


    private void updateUserVip(UserVip user) {
        dao = new MediaManager();
        Map<String, Playlist> playlists = dao.getPlaylistsByUserId(user.getId());
        for (Playlist play : playlists.values()) {
            play.setSongs(dao.getSongsByPlaylist(play.getId()));
        }
        user.setPlaylists(playlists);
    }

    public void playMedia() {
        beginTimer();
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        mediaPlayer.play();
    }


    public void toPlay(int songNumber) {
        media = new Media(songs.get(songNumber).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        songLabel.setText(songList.get(songNumber).getTitle());
        playMedia();
    }
    @FXML
    private void playMusic(MouseEvent event) {
        songNumber = musicList.getSelectionModel().getSelectedIndex();
        if (!songs.isEmpty()) {
            mediaPlayer.stop();
            if (running) {
                cancelTimer();
            }
            toPlay(songNumber);
        }
    }

    public void pauseMedia() {
        cancelTimer();
        mediaPlayer.pause();
    }

    public void resetMedia() {
        songProgressBar.setProgress(0);
        mediaPlayer.seek(Duration.seconds(0));
    }

    public void previousMedia() {
        if (!songs.isEmpty()) {
            songNumber = (songNumber - 1 + songs.size()) % songs.size();
            mediaPlayer.stop();
            if (running) {
                cancelTimer();
            }
            toPlay(songNumber);
        }
    }

    public void nextMedia() {
        if (!songs.isEmpty()) {
            songNumber = (songNumber + 1) % songs.size();
            mediaPlayer.stop();
            if (running) {
                cancelTimer();
            }
            toPlay(songNumber);
        }
    }

    public void beginTimer() {
        timer = new Timer();
        task = new TimerTask() {
            public void run() {
                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();

                songProgressBar.setProgress(current / end);

                if (current / end == 1) {
                    cancelTimer();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public void cancelTimer() {
        running = false;
        timer.cancel();
    }

    private List<Song> convertFilesToSongs(List<File> files) {
        List<Song> songs = new ArrayList<>();

        for (File file : files) {
            Song song = new Song();
            try {
                song.setFilePath(file.getPath());
            } catch (SongNotFoundException e) {
                e.printStackTrace();
            }
            String nameFile = file.getName();
            int posicaoPonto = nameFile.lastIndexOf(".");
            if (posicaoPonto != -1) {
                nameFile = nameFile.substring(0, posicaoPonto);
            }
            song.setTitle(nameFile);

            songs.add(song);
        }
        return songs;
    }




}
