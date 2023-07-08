package br.imd.player.controller;

import br.imd.player.DAO.MediaManager;
import br.imd.player.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class LoginController{
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button enterButton;

    @FXML
    private Button registrarButton;
    private Stage primaryStage;

    @FXML
    private void initialize() {
        // Aqui você pode adicionar lógica de inicialização, se necessário
    }

    @FXML
    private void handleEnterButton() {
        // Lógica para processar o clique no botão "Enter"
        String email = emailField.getText();
        String senha = senhaField.getCharacters().toString();

        if (authenticUser(email, senha)) {
            exibirAviso("Login bem-sucedido", "Usuário e senha corretos");
            openMusicPlayerScreen();

        } else {
            exibirAviso("Login inválido", "Usuário ou senha incorretos");
        }
    }

    private void openMusicPlayerScreen() {
        try {
            URL fileFXML = getClass().getResource("/br/imd/player/view/Player.fxml");
            Parent registerRoot = FXMLLoader.load(fileFXML);

            Scene playerScene = new Scene(registerRoot);
            Stage primaryStage = (Stage) enterButton.getScene().getWindow();
            primaryStage.setScene(playerScene);
            primaryStage.setTitle("Player");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegistrarButton() {
        try {
            URL fileFXML = getClass().getResource("/br/imd/player/view/Registro.fxml");
            Parent registerRoot = FXMLLoader.load(fileFXML);

            Scene registerScene = new Scene(registerRoot);
            Stage primaryStage = (Stage) registrarButton.getScene().getWindow();
            primaryStage.setScene(registerScene);
            primaryStage.setTitle("Registro");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void exibirAviso(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private boolean authenticUser(String email, String password) {
        MediaManager media = new MediaManager();
        Map<String, User> users = media.getAllUsers();

        if (users.containsKey(email)) {
            User user = users.get(email);
            return user.getPassword().equals(password);
        }

        return false;
    }



}
