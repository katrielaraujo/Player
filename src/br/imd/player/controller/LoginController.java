package br.imd.player.controller;

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
    private void initialize() {
        // Aqui você pode adicionar lógica de inicialização, se necessário
    }

    @FXML
    private void handleEnterButton() {
        // Lógica para processar o clique no botão "Enter"
        String email = emailField.getText();
        String senha = senhaField.getCharacters().toString();
        // Faça algo com o email e a senha
        //exibirAviso("Login bem-sucedido", "Usuário e senha corretos");

        if (email.equals("usuario") && senha.equals("senha")) {
            exibirAviso("Login bem-sucedido", "Usuário e senha corretos");
        }
        else {
            exibirAviso("Login inválido", "Usuário ou senha incorretos");
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
}
