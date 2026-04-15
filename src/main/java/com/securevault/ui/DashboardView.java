package com.securevault.ui;

import com.securevault.model.Credential;
import com.securevault.service.CredentialService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class DashboardView {

    private final CredentialService credentialService = new CredentialService();
    private final TableView<Credential> table = new TableView<>();
    private final TextField searchField = new TextField();

    public void show(Stage stage) {
        Label title = new Label("SecureVault Dashboard");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        searchField.setPromptText("Search by website or username");
        searchField.textProperty().addListener((obs, oldVal, newVal) -> loadData());

        Button addButton = new Button("Add");
        Button editButton = new Button("Edit");
        Button deleteButton = new Button("Delete");
        Button copyButton = new Button("Copy Password");
        Button refreshButton = new Button("Refresh");
        Button logoutButton = new Button("Logout");

        TableColumn<Credential, Number> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getId()));

        TableColumn<Credential, String> websiteCol = new TableColumn<>("Website");
        websiteCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getWebsite()));

        TableColumn<Credential, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getUsername()));

        TableColumn<Credential, String> passwordCol = new TableColumn<>("Password");
        passwordCol.setCellValueFactory(data -> new SimpleStringProperty(maskPassword(data.getValue().getPassword())));

        TableColumn<Credential, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCategory()));

        table.getColumns().addAll(idCol, websiteCol, usernameCol, passwordCol, categoryCol);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        addButton.setOnAction(e -> {
            CredentialDialog dialog = new CredentialDialog(null);
            Optional<Credential> result = dialog.showAndWait();
            result.ifPresent(credential -> {
                boolean success = credentialService.addCredential(credential);
                showMessage(success ? "Credential added." : "Could not add credential.");
                loadData();
            });
        });

        editButton.setOnAction(e -> {
            Credential selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showMessage("Please select a credential first.");
                return;
            }

            CredentialDialog dialog = new CredentialDialog(selected);
            Optional<Credential> result = dialog.showAndWait();
            result.ifPresent(updatedCredential -> {
                boolean success = credentialService.updateCredential(updatedCredential);
                showMessage(success ? "Credential updated." : "Could not update credential.");
                loadData();
            });
        });

        deleteButton.setOnAction(e -> {
            Credential selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showMessage("Please select a credential first.");
                return;
            }

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Are you sure you want to delete this credential?",
                    ButtonType.YES, ButtonType.NO);

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.YES) {
                boolean success = credentialService.deleteCredential(selected.getId());
                showMessage(success ? "Credential deleted." : "Could not delete credential.");
                loadData();
            }
        });

        copyButton.setOnAction(e -> {
            Credential selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showMessage("Please select a credential first.");
                return;
            }

            ClipboardContent content = new ClipboardContent();
            content.putString(selected.getPassword());
            Clipboard.getSystemClipboard().setContent(content);
            showMessage("Password copied to clipboard.");
        });

        refreshButton.setOnAction(e -> loadData());

        logoutButton.setOnAction(e -> {
            LoginView loginView = new LoginView();
            loginView.show(stage);
        });

        HBox topBar = new HBox(10, searchField, addButton, editButton, deleteButton, copyButton, refreshButton, logoutButton);
        topBar.setAlignment(Pos.CENTER_LEFT);

        VBox root = new VBox(15, title, topBar, table);
        root.setPadding(new Insets(20));

        loadData();

        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("SecureVault");
        stage.setScene(scene);
        stage.show();
    }

    private void loadData() {
        String keyword = searchField.getText() == null ? "" : searchField.getText().trim();
        List<Credential> credentials = keyword.isEmpty()
                ? credentialService.getAllCredentials()
                : credentialService.searchCredentials(keyword);

        table.setItems(FXCollections.observableArrayList(credentials));
    }

    private String maskPassword(String password) {
        if (password == null || password.isEmpty()) {
            return "";
        }
        return "*".repeat(Math.min(password.length(), 8));
    }

    private void showMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }
}
