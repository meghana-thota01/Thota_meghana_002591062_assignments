
package model;

import java.time.LocalDate;

public class Book {
    private static int nextSerial = 10001;
    private final int serialNumber;
    private String name;
    private LocalDate registeredDate;
    private int pages;
    private String language;
    private boolean isRented;
    private Author author;

    public Book(String name, LocalDate registeredDate, int pages, String language, Author author) {
        this.serialNumber = nextSerial++;
        this.name = name;
        this.registeredDate = registeredDate;
        this.pages = pages;
        this.language = language;
        this.author = author;
        this.isRented = false;
    }

    public int getSerialNumber() { return serialNumber; }
    public String getName() { return name; }
    public LocalDate getRegisteredDate() { return registeredDate; }
    public int getPages() { return pages; }
    public String getLanguage() { return language; }
    public boolean isRented() { return isRented; }
    public Author getAuthor() { return author; }

    public void setRented(boolean rented) { isRented = rented; }
}
