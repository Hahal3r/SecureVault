package com.securevault.ui;

import com.securevault.model.Credential;
import com.securevault.service.PasswordGenerator;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class CredentialDialog extends Dialog<Credential> {

    public CredentialDialog(Credential existingCredential) {
        setTitle(existingCredential == null ? "Add Credential" : "Edit Credential");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        TextField websiteField = new TextField();
        TextField usernameField = new TextField();
        TextField passwordField = new TextField();
        TextField categoryField = new TextField();
        TextArea notesArea = new TextArea();

        notesArea.setPrefRowCount(3);

        Button generateButton = new Button("Generate Password");
        generateButton.setOnAction(e -> passwordField.setText(PasswordGenerator.generate(12)));

        if (existingCredential != null) {
            websiteField.setText(existingCredential.getWebsite());
            usernameField.setText(existingCredential.getUsername());
            passwordField.setText(existingCredential.getPassword());
            categoryField.setText(existingCredential.getCategory());
            notesArea.setText(existingCredential.getNotes());
        }

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        grid.add(new Label("Website:"), 0, 0);
        grid.add(websiteField, 1, 0);

        grid.add(new Label("Username:"), 0, 1);
        grid.add(usernameField, 1, 1);

        grid.add(new Label("Password:"), 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(generateButton, 2, 2);

        grid.add(new Label("Category:"), 0, 3);
        grid.add(categoryField, 1, 3);

        grid.add(new Label("Notes:"), 0, 4);
        grid.add(notesArea, 1, 4);

        getDialogPane().setContent(grid);

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                if (websiteField.getText().trim().isEmpty() || usernameField.getText().trim().isEmpty()) {
                    return null;
                }

                Credential credential = new Credential();
                if (existingCredential != null) {
                    credential.setId(existingCredential.getId());
                }

                credential.setWebsite(websiteField.getText().trim());
                credential.setUsername(usernameField.getText().trim());
                credential.setPassword(passwordField.getText().trim());
                credential.setCategory(categoryField.getText().trim());
                credential.setNotes(notesArea.getText().trim());

                return credential;
            }
            return null;
        });
    }
}
