package com.perko.bookshelf.controller;

import com.perko.bookshelf.interfaces.VisualInterface;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BookshelfVisual implements VisualInterface {

    /**
     * Luodaan kirjahyllyn visuaalinen rakenne.
     * @param X rectleveys
     * @param Y korkeus
     * @return palauttaa Recktangle-olion.
     */
    public Rectangle bookshelfStructure(double X, double Y) {
        Rectangle rectangle = new Rectangle(700, 180);
        rectangle.setFill(Color.ROSYBROWN);
        rectangle.setStroke(Color.DARKSALMON);
        rectangle.setStrokeWidth(10);

        rectangle.setX(X);
        rectangle.setY(Y);

        return rectangle;
    }

    /**
     * Luodaan hyllyssä näkyvä kirjan visuaalinen ulkoasu.
     * @param title kirjan otsikko näkyy kirjan selässä
     * @param color kirjan väri.
     * @return palauttaa StackPanen.
     */
    public StackPane bookRectangle(String title, Color color) {

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
}
