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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

public class Bookshelf extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Map<Integer, Book> books = new HashMap<>();

        Pane root = new Pane();
        HBox booksUpper = new HBox();
        booksUpper.setLayoutX(15);
        booksUpper.setLayoutY(215);
        HBox booksLower = new HBox();
        booksLower.setLayoutX(15);
        booksLower.setLayoutY(395);

        Rectangle upperShelf = bookshelfStructure(10,190);
        Rectangle lowerShelf = bookshelfStructure(10,370);

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

        ToggleGroup upperOrLowerGroup = new ToggleGroup();

        ToggleButton upperSelected = new ToggleButton("Ylähylly");
        ToggleButton lowerSelected = new ToggleButton("Alahylly");

        upperSelected.setToggleGroup(upperOrLowerGroup);
        lowerSelected.setToggleGroup(upperOrLowerGroup);

        upperSelected.setLayoutX(20);
        upperSelected.setLayoutY(115);

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
        newBook.setOnAction(e -> {
            boolean titleEntered = bookTitle.getLength() > 0;
            boolean toggleSelected = upperOrLowerGroup.getSelectedToggle() != null;
            boolean colorSelected = bookColor.getValue() != null;
            boolean allSelected = titleEntered && toggleSelected && colorSelected;

            if (!allSelected) { root.getChildren().add(missingInfo());
            } else if (upperSelected.isSelected()) {

                if (booksUpper.getChildren().size() >= 23) { root.getChildren().add(shelfFull());
                } else {

                    StackPane bookRectangle = bookRectangle(bookTitle.getText(), bookColor.getValue());
                    booksUpper.getChildren().add(bookRectangle);

                    Book book = new Book(bookTitle.getText(), bookColor.getValue());
                    books.put(books.size()+1, book);
                }

            } else if (lowerSelected.isSelected()) {

                if (booksLower.getChildren().size() >= 23) { root.getChildren().add(shelfFull());
                } else {

                    StackPane bookRectangle = bookRectangle(bookTitle.getText(), bookColor.getValue());
                    booksLower.getChildren().add(bookRectangle);

                    Book book = new Book(bookTitle.getText(), bookColor.getValue());
                    books.put(books.size()+1, book);
                }

            }

            try {
                createFile(bookTitle.getText());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
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

        bookCovers.setOnMouseClicked(event -> {readBook();});

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

    private void createFile(String bookTitle) throws IOException {
        File obj = new File(bookTitle);
    }

    private void readBook() {

    }

}
