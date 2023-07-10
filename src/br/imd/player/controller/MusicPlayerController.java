package br.imd.player.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.imd.player.DAO.MediaManager;
import br.imd.player.model.User;

public class MusicPlayerController {
    @FXML
    private ListView<String> musicList;
    @FXML
    private Pane pane;
    @FXML
    private Label songLabel;
    @FXML
    private Button playButton, pauseButton, resetButton, previousButton, nextButton;
    @FXML
    private Slider volumeSlider;
    @FXML
    private ProgressBar songProgressBar;

    private int songNumber;
    private Timer timer;
    private TimerTask task;
    private boolean running;

    private User user;
    private MediaManager dao;
    private List<File> songs;
    private MediaPlayer mediaPlayer;
    private Media media;


    public void initialize() {
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });

        songProgressBar.setStyle("-fx-accent: #00FF00;");
    }
    
    public void setUser(User user) {
        this.user = user;
        chooseFileMethod();
    }

    public void exibirAviso(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }


    //Nessa função você pode escolher qualquer musica que tiver no sistema.
    //Lembrar de alterar ela ainda.
    public void chooseFileMethod(){
    	if (user.getDirectory() != null && !user.getDirectory().isEmpty()) {

            loadFilesFromDirectory();
        } else {
            exibirAviso("Diretorio", "Selecione o seu diretorio contendo musicas");
            showDirectoryChooser();
        }

    }

    public void loadFilesFromDirectory() {
        String directoryPath = user.getDirectory();
        File directoryFile = new File(directoryPath);
        File[] files = directoryFile.listFiles();

        if (files != null) {
            songs = user.listFilesInDirectory();
            if (!songs.isEmpty()) {
                songNumber = 0;
                File selectedSong = songs.get(songNumber);
                songLabel.setText(selectedSong.getName());
                media = new Media(selectedSong.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
            }
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


    @FXML
    private void playMusic(MouseEvent event) {
        String selectedMusic = musicList.getSelectionModel().getSelectedItem();
        // Lógica para reproduzir a música selecionada
        System.out.println("Reproduzindo música: " + selectedMusic);
    }


    public void criarPlaylist(ActionEvent event) {
    }

    public void selectPlaylist(MouseEvent mouseEvent) {
    }

    public void playMedia() {

        beginTimer();
        mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
        mediaPlayer.play();
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
    	        media = new Media(songs.get(songNumber).toURI().toString());
    	        mediaPlayer = new MediaPlayer(media);
    	        songLabel.setText(songs.get(songNumber).getName());
    	        playMedia();
    	}
        
    }

    public void nextMedia() {

    	if (!songs.isEmpty()) {
            songNumber = (songNumber + 1) % songs.size();
            mediaPlayer.stop();
            if (running) {
                cancelTimer();
            }
            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            songLabel.setText(songs.get(songNumber).getName());
            playMedia();
        }
    }

    public void beginTimer() {

        timer = new Timer();

        task = new TimerTask() {

            public void run() {

                running = true;
                double current = mediaPlayer.getCurrentTime().toSeconds();
                double end = media.getDuration().toSeconds();
                songProgressBar.setProgress(current/end);

                if(current/end == 1) {

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

}