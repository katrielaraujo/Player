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
import java.util.Timer;
import java.util.TimerTask;

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

    private File directory;
    private File[] files;


    private int songNumber;
    private Timer timer;
    private TimerTask task;
    private boolean running;





    private ArrayList<File> songs;

    private String path;
    private MediaPlayer mediaPlayer;
    private Media media;


    public void initialize() {
        // Inicializar a lista de músicas
        //songs = FXCollections.observableArrayList();
        //songs.addAll("Música 1", "Música 2", "Música 3");

        // Configurar a lista no ListView
        //musicList.setItems(songs);
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {

                mediaPlayer.setVolume(volumeSlider.getValue() * 0.01);
            }
        });

        songProgressBar.setStyle("-fx-accent: #00FF00;");
        chooseFileMethod();

    }


    //Nessa função você pode escolher qualquer musica que tiver no sistema.
    //Lembrar de alterar ela ainda.
    public void chooseFileMethod(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Selecionar Pasta");

        File directory = directoryChooser.showDialog(null);

        if (directory != null) {
            songs = new ArrayList<>();
            files = directory.listFiles();

            if (files != null) {
                songs.addAll(Arrays.asList(files));
            }

            if (!songs.isEmpty()) {
                songNumber = 0;
                File selectedSong = songs.get(songNumber);
                songLabel.setText(selectedSong.getName());

                media = new Media(selectedSong.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
            }
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

        if(songNumber > 0) {

            songNumber--;

            mediaPlayer.stop();

            if(running) {

                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());

            playMedia();
        }
        else {

            songNumber = songs.size() - 1;

            mediaPlayer.stop();

            if(running) {

                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());

            playMedia();
        }
    }

    public void nextMedia() {

        if(songNumber < songs.size() - 1) {

            songNumber++;

            mediaPlayer.stop();

            if(running) {

                cancelTimer();
            }

            media = new Media(songs.get(songNumber).toURI().toString());
            mediaPlayer = new MediaPlayer(media);

            songLabel.setText(songs.get(songNumber).getName());

            playMedia();
        }
        else {

            songNumber = 0;

            mediaPlayer.stop();

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