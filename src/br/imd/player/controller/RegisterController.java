package br.imd.player.controller;

import br.imd.player.DAO.MediaManager;
import br.imd.player.model.User;
import br.imd.player.model.UserVip;
import br.imd.player.util.UserType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class RegisterController {
    @FXML
    private CheckBox vipButton;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField senhaField;

    @FXML
    private Button registrarButton;

    @FXML
    private void initialize() {

    }

    @FXML
    private void handleRegistrarButton() {
        User user;
        boolean isVip = vipButton.isSelected();
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (isVip){
            user = new UserVip();
        }else {
            user = new UserVip();
        }

        user.setEmail(email);
        user.setPassword(senha);
        user.setType(isVip ? UserType.VIP: UserType.REGULAR);

        if (saveUser(user)) {
            exibirAviso("Sucesso no cadastro", "Usuário salvo: " + email );
            System.out.println("Usuário salvo: " + email);
            returnLoginScreen();


        } else {
            exibirAviso("Fracasso no cadastro", "Usuário já existe: " + email );
            System.out.println("Usuário já existe: " + email);
        }
    }

    private boolean saveUser(User user) {
        MediaManager media = new MediaManager();
        Map<String,User> users = media.getAllUsers();
        if(users.containsKey(user.getEmail())) {
            return false;
        }

        media.insertUser(user);
        return true;
    }


    private void exibirAviso(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void returnLoginScreen() {
        try {
            URL fileFXML = getClass().getResource("/br/imd/player/view/Login.fxml");
            Parent loginRoot = FXMLLoader.load(fileFXML);

            Scene loginScene = new Scene(loginRoot);
            Stage primaryStage = (Stage) registrarButton.getScene().getWindow();
            primaryStage.setScene(loginScene);
            primaryStage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}