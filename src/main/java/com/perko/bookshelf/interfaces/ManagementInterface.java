package com.perko.bookshelf.interfaces;

import com.perko.bookshelf.model.Book;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.util.Map;

public interface ManagementInterface {
    Button openBookButton(Book book, Map<Integer, Book> books);
    void bookPage(Book book, TextArea writeText);
    TextArea readAndWrite(Book book);
    void saveToFile(String bookTitle, String text);
}
