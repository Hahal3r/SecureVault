package com.securevault;

import com.securevault.ui.LoginView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginView loginView = new LoginView();
        loginView.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
