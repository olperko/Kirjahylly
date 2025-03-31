package com.perko.bookshelf.model;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;

public class Book implements Serializable {

    private int id;
    private String title;
    private transient Color spineColor;
    private boolean isUpperShelf;

    private double red;
    private double green;
    private double blue;
    private double opacity;

    /**
     * Konstruktori joka luo kirja olion parametreillä String ja Color.
     */
    public Book(String title, Color color) {
        this.title = title;
        setSpineColor(color);
    }

    /**
     * Palauttaa ID-arvon.
     */
    public int getId() {
        return id;
    }

    /**
     * Asettaa kirjalle oman ID-arvon.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Palauttaa kirjan otsikon.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Asettaa kirjalle otsikon.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Palauttaa kirjan värin.
     */
    public Color getSpineColor() {
        return spineColor;
    }

    /**
     * Asettaa kirjalle värin.
     */
    public void setSpineColor(Color spineColor) {
        this.spineColor = spineColor;
        if (spineColor != null) {
            this.red = spineColor.getRed();
            this.green = spineColor.getGreen();
            this.blue = spineColor.getBlue();
            this.opacity = spineColor.getOpacity();
        }
    }

    /**
     * Lukee kirja-olion Color-arvot tiedostoon.
     */
    @Serial
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        if (spineColor != null) {
            red = spineColor.getRed();
            green = spineColor.getGreen();
            blue = spineColor.getBlue();
            opacity = spineColor.getOpacity();
        }
        out.defaultWriteObject();
    }

    /**
     * Lukee kirja-olion Color-arvon kun se luetaan tiedostota.
     * Color-arvoa ei voida serializoida joten tämä suorittaa se kustomoidusti.
     */
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if (red > 0 || green > 0 || blue > 0) {
            spineColor = new Color(red, green, blue, opacity);
        }
    }

    /**
     * Asettaa olille arvon ylähylly
     */
    public void setUpperShelf(boolean upperShelf) {
        this.isUpperShelf = upperShelf;
    }

    /**
     * Tarkistaa onko olio ylähyllyssä
     */
    public boolean isUpperShelf() {
        return isUpperShelf;
    }

}
