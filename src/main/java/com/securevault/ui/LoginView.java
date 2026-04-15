package com.securevault.ui;

import com.securevault.service.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginView {

    private final AuthService authService = new AuthService();

    public void show(Stage stage) {
        Label title = new Label("SecureVault");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label subtitle = new Label(authService.userExists()
                ? "Enter your master password"
                : "Create your master password");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Master Password");

        Button actionButton = new Button(authService.userExists() ? "Login" : "Create Vault");
        actionButton.setMaxWidth(Double.MAX_VALUE);

        Label messageLabel = new Label();

        actionButton.setOnAction(e -> {
            String password = passwordField.getText().trim();

            if (password.isEmpty()) {
                messageLabel.setText("Password cannot be empty.");
                return;
            }

            if (!authService.userExists()) {
                boolean created = authService.registerMasterPassword(password);
                if (created) {
                    new Alert(Alert.AlertType.INFORMATION, "Vault created successfully. Please login now.").showAndWait();
                    show(stage);
                } else {
                    messageLabel.setText("Could not create vault.");
                }
            } else {
                boolean success = authService.login(password);
                if (success) {
                    DashboardView dashboardView = new DashboardView();
                    dashboardView.show(stage);
                } else {
                    messageLabel.setText("Invalid master password.");
                }
            }
        });

        VBox root = new VBox(15, title, subtitle, passwordField, actionButton, messageLabel);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(root, 400, 300);
        stage.setTitle("SecureVault Login");
        stage.setScene(scene);
        stage.show();
    }
}
