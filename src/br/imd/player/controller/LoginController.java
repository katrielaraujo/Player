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

import static br.imd.player.util.UserType.VIP;

/**
 * Controlador responsável pela tela de login.
 */
public class LoginController {
    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button enterButton;

    @FXML
    private Button registrarButton;

    @FXML
    private MusicPlayerController musicPlayerController;


    /**
     * Manipula o evento de clique do botão "Enter".
     */
    @FXML
    private void handleEnterButton() {
        // Lógica para processar o clique no botão "Enter"
        String email = emailField.getText();
        String senha = senhaField.getCharacters().toString();

        if (authenticUser(email, senha)) {
            exibirAviso("Login bem-sucedido", "Usuário e senha corretos");
            MediaManager media = new MediaManager();
            Map<String, User> users = media.getAllUsers();
            User user = users.get(email);
            openMusicPlayerScreen(user);
        } else {
            exibirAviso("Login inválido", "Usuário ou senha incorretos");
        }
    }

    /**
     * Abre a tela do reprodutor de música, de acordo com o tipo de usuário.
     *
     * @param user o usuário autenticado.
     */
    private void openMusicPlayerScreen(User user) {
        if (user.getType() == VIP) {
            try {
                URL fileFXML = getClass().getResource("/br/imd/player/view/Player.fxml");
                FXMLLoader loader = new FXMLLoader(fileFXML);
                Parent playerRoot = loader.load();

                musicPlayerController = loader.getController(); // Obter a referência do MusicPlayerController
                musicPlayerController.setUser(user); // Passar o objeto User

                Scene playerScene = new Scene(playerRoot);
                Stage primaryStage = (Stage) enterButton.getScene().getWindow();
                primaryStage.setScene(playerScene);
                primaryStage.setTitle("Player");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                URL fileFXML = getClass().getResource("/br/imd/player/view/NormalPlayer.fxml");
                FXMLLoader loader = new FXMLLoader(fileFXML);
                Parent playerRoot = loader.load();

                musicPlayerController = loader.getController(); // Obter a referência do MusicPlayerController
                musicPlayerController.setUser(user); // Passar o objeto User

                Scene playerScene = new Scene(playerRoot);
                Stage primaryStage = (Stage) enterButton.getScene().getWindow();
                primaryStage.setScene(playerScene);
                primaryStage.setTitle("Player");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Manipula o evento de clique do botão "Registrar".
     */
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

    /**
     * Exibe um aviso em forma de diálogo.
     *
     * @param titulo   o título do aviso.
     * @param mensagem a mensagem do aviso.
     */
    private void exibirAviso(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    /**
     * Autentica um usuário com base no email e senha fornecidos.
     *
     * @param email    o email do usuário.
     * @param password a senha do usuário.
     * @return true se o usuário for autenticado com sucesso, false caso contrário.
     */
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
