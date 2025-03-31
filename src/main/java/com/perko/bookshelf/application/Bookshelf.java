package com.perko.bookshelf.application;

import com.perko.bookshelf.view.BookshelfUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Bookshelf extends Application {
    @Override
    public void start(Stage primaryStage) {

        BookshelfUI bookshelfUI = new BookshelfUI();

        Pane root = bookshelfUI.getRoot();

        Scene scene = new Scene(root, 720, 560);
        primaryStage.setTitle("Bookshelf");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}