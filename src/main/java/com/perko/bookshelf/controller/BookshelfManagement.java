package com.perko.bookshelf.controller;

import com.perko.bookshelf.interfaces.ManagementInterface;
import com.perko.bookshelf.model.Book;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class BookshelfManagement implements ManagementInterface {

    /**
     * Button-olio joka asetetaan visuaalisen kirjan päälle.
     * Käyttää bookPage()- ja readAndWrite()-metodeja.
     * @param book kirja olio
     * @param books HashMap joka sisältää kirja oliot
     * @return palauttaa Button olion.
     */
    public Button openBookButton(Book book, Map<Integer, Book> books) {
        Button bookButton = new Button();
        bookButton.setRotate(90);
        bookButton.setMinWidth(150);
        bookButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        bookButton.setOnMouseClicked(e -> {
            bookPage(book, readAndWrite(book));
        });

        return bookButton;
    }

    /**
     * Luodaan ikkuna kirjaan kirjoittamista varten.
     * Pyritään luomaan vain yksi ikkuna per kirja.
     * Uutta ikkunaa ei luoda, vaan käytetään olemassaolevaa.
     * @param book kirja olio
     * @param writeText TextArea johon kirjoitetaan
     */
    public void bookPage(Book book, TextArea writeText) {
        Stage currentBookPage = new Stage();
        currentBookPage.setTitle(book.getTitle());

        Button saveText = new Button("Lopeta kirjoittaminen.");
        saveText.setOnAction(e -> {
            String text = writeText.getText();
            saveToFile(book.getTitle(), text);
            currentBookPage.close();
        });

        VBox page = new VBox(writeText, saveText);
        currentBookPage.setScene(new Scene(page, 700, 290));
        currentBookPage.show();
    }

    /**
     * Luodaan TextArea johon kirjoitetaan.
     * Jos avattu kirja on jo olemassa, tuodaan olemassa oleva teksti.
     * @param book tuodaan book-olio johon kirjoitetaan.
     * @return palauttaa TextArean.
     */
    public TextArea readAndWrite(Book book) {

        TextArea writeText = new TextArea();
        writeText.setPrefSize(700, 500);
        writeText.setWrapText(true);

        try {
            Path path = Paths.get(book.getTitle());
            String existingContent = String.join("", Files.readAllLines(path));
            writeText.setText(existingContent);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return writeText;
    }

    /**
     * Kirjaan kirjoitettu teksti tallennetaan tiedostoon.
     * @param bookTitle kirjan nimeä käytetään tiedoston nimeen.
     * @param text teksti joka on kirjoitettu
     */
    public void saveToFile(String bookTitle, String text) {

        File file = new File(bookTitle);

        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
