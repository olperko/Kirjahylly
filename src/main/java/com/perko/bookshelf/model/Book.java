package com.perko.bookshelf.model;
import javafx.scene.paint.Color;

public class Book {

    private int id;
    private int numOfPages;
    private String title;
    private String contents;
    private Color spineColor;

    public Book() {}

    public Book(int id, String title, Color color, String contents, int pages) {
        this.id = id;
        this.title = title;
        this.spineColor = color;
        this.contents = contents;
        this.numOfPages = pages;
    }

    public Book(String title, Color color) {
        this.title = title;
        this.spineColor = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Color getSpineColor() {
        return spineColor;
    }

    public void setSpineColor(Color spineColor) {
        this.spineColor = spineColor;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }
}
