package br.imd.player.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
public class MusicPlayerController {
    @FXML
    private ListView<String> musicList;

    private ObservableList<String> songs;

    public void initialize() {
        // Inicializar a lista de músicas
        songs = FXCollections.observableArrayList();
        songs.addAll("Música 1", "Música 2", "Música 3");

        // Configurar a lista no ListView
        musicList.setItems(songs);
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
}
