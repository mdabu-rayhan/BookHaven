package com.bookhaven.Model;

public class Book {


    private int bookId;
    private String title;
    private String author;
    private String synopsis;
    private String pdfPath;
    private String coverImagePath;



    private int totalPages;



    public Book() {
    }


    public Book(int bookId, String title, String author, String synopsis, String pdfPath, String coverImagePath) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.synopsis = synopsis;
        this.pdfPath = pdfPath;
        this.coverImagePath = coverImagePath;
    }


    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getCoverImagePath() {
        return coverImagePath;
    }

    public void setCoverImagePath(String coverImagePath) {
        this.coverImagePath = coverImagePath;
    }




}