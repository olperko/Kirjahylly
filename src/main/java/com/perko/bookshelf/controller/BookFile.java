package com.perko.bookshelf.controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.perko.bookshelf.model.Book;

public class BookFile {
    private static final String BOOKS_FILE = "books.dat";

    /**
     * Metodi suorittaa HashMapissä olevien kirja-olioiden tallettamisen tiedostoon.
     */
    public static void saveBooks(Map<Integer, Book> books) {
        try (ObjectOutputStream out = new ObjectOutputStream(
                new FileOutputStream(BOOKS_FILE))) {
            out.writeObject(books);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodi palauttaa kirja-oliot tiedostosta ja kirjoittaa ne uuteen HashMappiin.
     */
    public static Map<Integer, Book> loadBooks() {
        if (!Files.exists(Paths.get(BOOKS_FILE))) {
            return new HashMap<>();
        }

        try (ObjectInputStream in = new ObjectInputStream(
                new FileInputStream(BOOKS_FILE))) {
            Map<Integer, Book> books = (Map<Integer, Book>) in.readObject();
            return books;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}

