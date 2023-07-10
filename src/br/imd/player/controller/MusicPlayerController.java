package br.imd.player.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;

import br.imd.player.DAO.MediaManager;
import br.imd.player.model.Song;
import br.imd.player.model.User;
import br.imd.player.util.SongNotFoundException;

public class MusicPlayerController {
    @FXML
    private ListView<Song> musicList;
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
    private ObservableList<Song> songList;
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
        
     // Inicialize a songList
        songList = FXCollections.observableArrayList();
        // Defina songList como os itens de musicList
        musicList.setItems(songList);

        songProgressBar.setStyle("-fx-accent: #00FF00;");
    }
    
    public void setUser(User user) {
        this.user = user;
        chooseFileMethod();
    }


    //Nessa função você pode escolher qualquer musica que tiver no sistema.
    //Lembrar de alterar ela ainda.
    public void chooseFileMethod(){
    	if (user.getDirectory() != null && !user.getDirectory().isEmpty()) {
            loadFilesFromDirectory();
        } else {
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


    @FXML
    private void playMusic(MouseEvent event) {
    	Song selectedMusic = musicList.getSelectionModel().getSelectedItem();
        // Lógica para reproduzir a música selecionada
        System.out.println("Reproduzindo música: " + selectedMusic.getTitle());
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
    
    public void toPlay(int songNumber) {
    	media = new Media(songs.get(songNumber).toURI().toString());
    	mediaPlayer = new MediaPlayer(media);
	    songLabel.setText(songList.get(songNumber).getTitle());
	    playMedia();
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
    
    private List<Song> convertFilesToSongs(List<File> files){
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