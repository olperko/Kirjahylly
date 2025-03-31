package com.perko.bookshelf.interfaces;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public interface VisualInterface {
    Rectangle bookshelfStructure(double X, double Y);
    StackPane bookRectangle(String title, Color color);
}
