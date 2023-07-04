package br.imd.player.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
        // Aqui você pode adicionar lógica de inicialização, se necessário
    }

    @FXML
    private void handleRegistrarButton() {
        // Lógica para processar o clique no botão "Registrar"
        boolean isVip = vipButton.isSelected();
        String email = emailField.getText();
        String senha = senhaField.getText();


        // Faça algo com os dados de registro (por exemplo, salvar em um banco de dados)
    }
}
