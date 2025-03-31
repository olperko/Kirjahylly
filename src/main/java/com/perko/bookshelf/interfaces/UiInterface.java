package com.perko.bookshelf.interfaces;

import com.perko.bookshelf.application.Bookshelf;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public interface UiInterface {
    Bookshelf getBookshelf();
    void saveBooks();
    void loadExistingBooks();
    void startUp();
    void colorConverter();
    void newBook();
    void newBookAction();
    void addBookToShelf(HBox shelf);
    void resetInput();
    Label missingInfo();
    Label shelfFull();
    Pane getRoot();

}
