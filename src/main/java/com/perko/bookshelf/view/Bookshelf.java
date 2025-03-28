package com.perko.bookshelf.view;
import com.perko.bookshelf.model.Book;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class Bookshelf extends Application {

    private Stage bookPage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Map<Integer, Book> books = new HashMap<>();

        /*
         * Luodaan ohjelman ydinpaneelit
         */
        Pane root = new Pane();
        HBox booksUpper = new HBox();
        booksUpper.setLayoutX(15);
        booksUpper.setLayoutY(215);
        HBox booksLower = new HBox();
        booksLower.setLayoutX(15);
        booksLower.setLayoutY(395);

        /*
         * Hyllyjen visuaalisus
         */
        Rectangle upperShelf = bookshelfStructure(10, 190);
        Rectangle lowerShelf = bookshelfStructure(10, 370);

        /*
         * Otsikko kirjan luonnille
         */
        Label newBookText = new Label("Uusi kirja:");
        newBookText.setStyle("-fx-font-size: 20");
        newBookText.setLayoutX(20);
        newBookText.setLayoutY(5);

        /*
         * Kenttä kirjan nimelle, joka näkyy kirjan selkämyksessä.
         */
        TextField bookTitle = new TextField();
        bookTitle.setLayoutX(20);
        bookTitle.setLayoutY(40);

        /*
         * ChoiceBox josta käyttäjä voi valita minkä värisen kirjan haluaa.
         */
        ChoiceBox<Color> bookColor = new ChoiceBox<>
                (FXCollections.observableArrayList(
                        Color.RED, Color.GREEN, Color.BLUE,
                        Color.YELLOW, Color.ORANGE, Color.PURPLE));
        bookColor.setLayoutX(20);
        bookColor.setLayoutY(80);

        /*
         * Käytetään StringConverteria, jotta ChoiceBoxissa olevat värivaihtoehdot eivät ole heksadesimaalissa.
         */
        bookColor.setConverter(new StringConverter<>() {
            @Override
            public String toString(Color color) {
                if (color == null) return "Väriä ei ole valittu";
                if (color.equals(Color.RED)) return "Punainen";
                if (color.equals(Color.GREEN)) return "Vihreä";
                if (color.equals(Color.BLUE)) return "Sininen";
                if (color.equals(Color.YELLOW)) return "Keltainen";
                if (color.equals(Color.ORANGE)) return "Oranssi";
                if (color.equals(Color.PURPLE)) return "Purppura";
                return "Ei tietoa.";
            }

            @Override
            public Color fromString(String string) {
                return null;
            }
        });

        /*
         * Luodaan funktio hyllytason valitsemiselle
         */
        ToggleGroup upperOrLowerGroup = new ToggleGroup();

        ToggleButton upperSelected = new ToggleButton("Ylähylly");
        upperSelected.setToggleGroup(upperOrLowerGroup);
        upperSelected.setLayoutX(20);
        upperSelected.setLayoutY(115);

        ToggleButton lowerSelected = new ToggleButton("Alahylly");
        lowerSelected.setToggleGroup(upperOrLowerGroup);
        lowerSelected.setLayoutX(80);
        lowerSelected.setLayoutY(115);

        /*
         * newBook -nappi lisää kirjan hyllyyn, kun otsikko ja väri on annettu.
         */
        Button newBook = new Button("Lisää kirja");
        newBook.setLayoutX(20);
        newBook.setLayoutY(150);

        /*
         * Luodaan toiminto newBook-napille.
         * Tarkistetaan onko kaikki kirjan tiedot annettu.
         */
        newBook.setOnAction(event -> {
            boolean titleEntered = bookTitle.getLength() > 0;
            boolean toggleSelected = upperOrLowerGroup.getSelectedToggle() != null;
            boolean colorSelected = bookColor.getValue() != null;
            boolean allSelected = titleEntered && toggleSelected && colorSelected;

            if (!allSelected) {
                root.getChildren().add(missingInfo());
            } else if (upperSelected.isSelected()) {

                if (booksUpper.getChildren().size() >= 23) {
                    root.getChildren().add(shelfFull());
                } else {

                    StackPane bookRectangleTop = bookRectangle(bookTitle.getText(), bookColor.getValue());
                    booksUpper.getChildren().add(bookRectangleTop);

                    Book book = new Book(bookTitle.getText(), bookColor.getValue());
                    books.put(books.size() + 1, book);

                    Button bookButton = new Button();
                    bookButton.setRotate(90);
                    bookButton.setMinWidth(150);
                    bookButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                    bookButton.setOnMouseClicked(e -> {
                        bookPage(book, readBook(book));
                    });
                    bookRectangleTop.getChildren().add(bookButton);
                }

            } else if (lowerSelected.isSelected()) {

                if (booksLower.getChildren().size() >= 23) {
                    root.getChildren().add(shelfFull());
                } else {

                    StackPane bookRectangleBot = bookRectangle(bookTitle.getText(), bookColor.getValue());
                    booksLower.getChildren().add(bookRectangleBot);

                    Book book = new Book(bookTitle.getText(), bookColor.getValue());
                    books.put(books.size() + 1, book);

                    Button bookButton = new Button();
                    bookButton.setRotate(90);
                    bookButton.setMinWidth(150);
                    bookButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
                    bookButton.setOnMouseClicked(e -> {
                        bookPage(book, readBook(book));
                    });
                    bookRectangleBot.getChildren().add(bookButton);
                }
            }

            try {
                Files.createFile(Paths.get(bookTitle.getText()));
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            bookTitle.clear();
        });

        /*
         * Lisätään oliot root-paneeliin.
         */
        root.getChildren().addAll(newBookText, upperShelf, lowerShelf,
                booksUpper, booksLower, bookTitle, bookColor,
                upperSelected, lowerSelected, newBook);

        /*
         * Luodaan scene ja näytetään ikkuna.
         */
        Scene scene = new Scene(root, 720, 560);
        primaryStage.setTitle("Bookshelf");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*
     * Luodaan kirjahyllyn rakenne
     */
    private Rectangle bookshelfStructure(double X, double Y) {
        Rectangle rectangle = new Rectangle(700, 180);
        rectangle.setFill(Color.TRANSPARENT);
        rectangle.setStroke(Color.DARKSALMON);
        rectangle.setStrokeWidth(10);

        rectangle.setX(X);
        rectangle.setY(Y);

        return rectangle;
    }

    /*
     * Kirjojen luominen
     */
    protected StackPane bookRectangle(String title, Color color) {

        StackPane bookCovers = new StackPane();
        bookCovers.setPrefSize(30, 150);
        bookCovers.setMaxSize(30, 150);
        bookCovers.setMinSize(30, 150);

        Rectangle rectangle = new Rectangle(30, 150);
        rectangle.setFill(color);
        rectangle.setStroke(Color.BLACK);

        Label bookTitle = new Label(title);
        bookTitle.setStyle("-fx-font-size: 13; -fx-text-fill: black;");
        bookTitle.setRotate(90);
        bookTitle.setMinWidth(140);
        bookTitle.setMinHeight(30);

        bookCovers.getChildren().addAll(rectangle, bookTitle);

        return bookCovers;
    }

    private Label missingInfo() {

        Label missingInformation = new Label("Sinun tulee antaa kaikki tiedot, jotta kirja voidaan lisätä hyllyyn.");
        missingInformation.setLayoutX(105);
        missingInformation.setLayoutY(155);

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> missingInformation.setVisible(false));
        pause.play();

        return missingInformation;
    }

    private Label shelfFull() {

        Label shelfFull = new Label("Valitsemasi hylly on täynnä, valitse toinen tai poista kirja hyllyltä.");
        shelfFull.setLayoutX(105);
        shelfFull.setLayoutY(155);

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> shelfFull.setVisible(false));
        pause.play();

        return shelfFull;
    }

    /*
     * Metodi joka suorittaa kirjaan kijoittamisen.
     */
    private void saveToFile(String bookTitle, String text) {

        File file = new File(bookTitle);

        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * Luodaan ikkuna kirjaan kirjoittamista varten.
     * Pyritään luomaan vain yksi ikkuna per kirja.
     * Uutta ikkunaa ei luoda, vaan käytetään olemassaolevaa.
     */
    private void bookPage(Book book, TextArea writeText) {
        Stage currentBookPage = new Stage();
        currentBookPage.setTitle(book.getTitle());

        Button saveText = new Button("Lopeta kirjoittaminen.");
        saveText.setOnAction(e -> {
            String text = writeText.getText();
            saveToFile(book.getTitle(), text);
            currentBookPage.close();
        });

        VBox page = new VBox(writeText, saveText);
        currentBookPage.setScene(new Scene(page, 700, 400));
        currentBookPage.show();
    }

    /*
     * Luodaan alue johon kirjoiteetaan ja tuodaan olemassaoleva teksti.
     */
    private TextArea readBook(Book book) {

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
}