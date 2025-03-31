package com.perko.bookshelf.view;

import com.perko.bookshelf.application.Bookshelf;
import com.perko.bookshelf.controller.BookFile;
import com.perko.bookshelf.controller.BookshelfManagement;
import com.perko.bookshelf.controller.BookshelfVisual;
import com.perko.bookshelf.interfaces.UiInterface;
import com.perko.bookshelf.model.Book;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.util.StringConverter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class BookshelfUI implements UiInterface {
    private final BookshelfManagement bookManager;
    private final BookshelfVisual bookshelfVisual;

    private final Map<Integer, Book> books;
    private final Pane root;
    private final HBox booksUpper;
    private final HBox booksLower;
    private final Rectangle upperShelf;
    private final Rectangle lowerShelf;
    private final Label newBookText;
    private final Label yourBookshelf;
    private final Label userInstructions_1;
    private final Label userInstructions_2;
    private final Label userInstructions_3;
    private final Label userInstructions_4;
    private final Label userInstructions_5;
    private final TextField bookTitle;
    private final ChoiceBox<Color> bookColor;
    private final ToggleGroup upperOrLowerGroup;
    private final ToggleButton upperSelected;
    private final ToggleButton lowerSelected;
    private final Button newBook;

    private final int BOOKSHELF_FULL_SIZE = 23;

    public BookshelfUI() {
        this.bookManager = new BookshelfManagement();
        this.bookshelfVisual = new BookshelfVisual();

        this.books = BookFile.loadBooks();
        this.root = new Pane();
        this.booksUpper = new HBox();
        this.booksLower = new HBox();
        this.upperShelf = bookshelfVisual.bookshelfStructure(10, 190);
        this.lowerShelf = bookshelfVisual.bookshelfStructure(10, 370);
        this.upperOrLowerGroup = new ToggleGroup();
        this.upperSelected = new ToggleButton("Ylempi hylly");
        this.lowerSelected = new ToggleButton("Alempi hylly");
        this.newBookText = new Label("Lisää uusi kirja");
        this.yourBookshelf = new Label("OMA VISUAALINEN KIRJAHYLLYSI");
        this.userInstructions_1 = new Label("1. Anna kirjalle nimi");
        this.userInstructions_2 = new Label("2. Valitse kirjan väri");
        this.userInstructions_3 = new Label("3. Ala- vai ylähylly?");
        this.userInstructions_4 = new Label("4. Lisää kirja hyllyyn");
        this.userInstructions_5 = new Label("5. Klikkaa kirjaa hiiren vasemmalla ja kirjoita!");
        this.bookTitle = new TextField();
        this.bookColor = new ChoiceBox<>();

        this.newBook = new Button("Lisää kirja");

        startUp();
        newBook();
        loadExistingBooks();
    }

    @Override
    public Bookshelf getBookshelf() {
        return null;
    }

    /**
     * Tallennetaan HashMapissa olevat kirja-oliot tiedostoon.
     */
    public void saveBooks() {
        BookFile.saveBooks(books);
    }

    /**
     * Käydään HashMapissa olevien kirja-olioiden tiedot läpi,
     * ja lisätään niiden visuaalisuus StackPaneen.
     * Lisätään kirjan päälle nappi, jota painamalla kirjoitusikkuna aukeaa.
     */
    public void loadExistingBooks() {
        for (Book book : books.values()) {
            StackPane bookVisual = bookshelfVisual.bookRectangle(book.getTitle(), book.getSpineColor());

            if (book.isUpperShelf()) {
                if (booksUpper.getChildren().size() < BOOKSHELF_FULL_SIZE) {
                    booksUpper.getChildren().add(bookVisual);
                }
            } else {
                if (booksLower.getChildren().size() < BOOKSHELF_FULL_SIZE) {
                    booksLower.getChildren().add(bookVisual);
                }
            }

            bookVisual.getChildren().add(bookManager.openBookButton(book, books));
        }
    }

    public void startUp() {

        booksUpper.setLayoutX(15);
        booksUpper.setLayoutY(215);

        booksLower.setLayoutX(15);
        booksLower.setLayoutY(395);

        newBookText.setStyle("-fx-font-size: 20");
        newBookText.setLayoutX(20);
        newBookText.setLayoutY(5);

        this.bookTitle.setPromptText("Kirjan nimi");
        bookTitle.setLayoutX(20);
        bookTitle.setLayoutY(37);

        bookColor.setItems(FXCollections.observableArrayList(
                Color.RED, Color.GREEN, Color.BLUE,
                Color.YELLOW, Color.ORANGE, Color.PURPLE
        ));
        bookColor.setLayoutX(20);
        bookColor.setLayoutY(70);
        colorConverter();

        upperSelected.setToggleGroup(upperOrLowerGroup);
        upperSelected.setLayoutX(20);
        upperSelected.setLayoutY(107);

        lowerSelected.setToggleGroup(upperOrLowerGroup);
        lowerSelected.setLayoutX(120);
        lowerSelected.setLayoutY(107);

        newBook.setLayoutX(20);
        newBook.setLayoutY(145);

        yourBookshelf.setStyle("-fx-font-size: 30");
        yourBookshelf.setLayoutX(250);
        yourBookshelf.setLayoutY(20);

        userInstructions_1.setStyle("-fx-font-size: 20");
        userInstructions_1.setLayoutX(250);
        userInstructions_1.setLayoutY(50);
        userInstructions_2.setStyle("-fx-font-size: 20");
        userInstructions_2.setLayoutX(250);
        userInstructions_2.setLayoutY(70);
        userInstructions_3.setStyle("-fx-font-size: 20");
        userInstructions_3.setLayoutX(250);
        userInstructions_3.setLayoutY(90);
        userInstructions_4.setStyle("-fx-font-size: 20");
        userInstructions_4.setLayoutX(250);
        userInstructions_4.setLayoutY(110);
        userInstructions_5.setStyle("-fx-font-size: 20");
        userInstructions_5.setLayoutX(250);
        userInstructions_5.setLayoutY(130);

        root.getChildren().addAll(
                newBookText, upperShelf, lowerShelf,
                booksUpper, booksLower, bookTitle, bookColor,
                upperSelected, lowerSelected, newBook, yourBookshelf,
                userInstructions_1, userInstructions_2, userInstructions_3,
                userInstructions_4, userInstructions_5
        );
    }

    /**
     * Converter muuttaa värivalikossa olevat heksadesimaalit luettavaan muotoon.
     */
    public void colorConverter() {
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
    }

    public void newBook() {
        newBook.setOnAction(event -> newBookAction());
    }

    /**
     * Luodaan toiminto napille josta kirja lisätään.
     * Tarkistetaan onko tarpeelliset tiedot annettu -> missingInfo()-metodi
     * Asetetaan kirja ylä- tai alahyllyyn.
     * Luodaan kirjalle oma tiedosto, joka tallettaa kirjaan kirjoitetut tekstit.
     */
    public void newBookAction() {
        boolean titleEntered = bookTitle.getLength() > 0;
        boolean toggleSelected = upperOrLowerGroup.getSelectedToggle() != null;
        boolean colorSelected = bookColor.getValue() != null;
        boolean allSelected = titleEntered && toggleSelected && colorSelected;

        if (!allSelected) {
            root.getChildren().add(missingInfo());
            return;
        }

        if (upperSelected.isSelected()) {
            addBookToShelf(booksUpper);
        }
        else if (lowerSelected.isSelected()) {
            addBookToShelf(booksLower);
        }

        try {
            Files.createFile(Paths.get(bookTitle.getText()));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        resetInput();
        saveBooks();
    }

    /**
     * Lisätään visuaalinen kirja hyllyyn ja luodaan kirja-olio.
     * Lisätään kirja-olio HashMappiin ja asetetaan kirja-oliolle tiedot.
     * Jos hyllytaso on täynnä -> shelfFull()-metodi.
     */
    public void addBookToShelf(HBox shelf) {
        if (shelf.getChildren().size() >= BOOKSHELF_FULL_SIZE) {
            root.getChildren().add(shelfFull());
            return;
        }

        StackPane bookVisual = bookshelfVisual.bookRectangle(bookTitle.getText(), bookColor.getValue());
        shelf.getChildren().add(bookVisual);

        Book book = new Book(bookTitle.getText(), bookColor.getValue());
        books.put(books.size() + 1, book);
        book.setId(books.size() + 1);
        book.setSpineColor(bookColor.getValue());
        book.setUpperShelf(shelf == booksUpper);

        bookVisual.getChildren().add(bookManager.openBookButton(book, books));
    }

    /**
     * Tyhjennetään kentät joissa kirjan tiedot on valittu.
     */
    public void resetInput() {
        upperSelected.setSelected(false);
        lowerSelected.setSelected(false);
        bookColor.setValue(null);
        bookTitle.clear();
    }

    /**
     * Ilmoittaa käyttäjälle jos kaikkia kirjan tietoja ei ole täytetty.
     */
    public Label missingInfo() {
        Label missingInformation = new Label("Sinun tulee antaa kaikki tiedot, jotta kirja voidaan lisätä hyllyyn.");
        missingInformation.setLayoutX(105);
        missingInformation.setLayoutY(155);

        // Show the message for 5 seconds then hide it
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> missingInformation.setVisible(false));
        pause.play();

        return missingInformation;
    }

    /**
     * Ilmoittaa käyttäjälle jos valittu hyllytaso on täynnä.
     */
    public Label shelfFull() {
        Label shelfFull = new Label("Valitsemasi hylly on täynnä, valitse toinen hyllytaso.");
        shelfFull.setLayoutX(105);
        shelfFull.setLayoutY(155);

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(e -> shelfFull.setVisible(false));
        pause.play();

        return shelfFull;
    }

    /**
     * Palauttaa root-paneelin, joka sisältää kaiken mitä käyttäjä näkee.
     */
    public Pane getRoot() {
        return root;
    }
}
