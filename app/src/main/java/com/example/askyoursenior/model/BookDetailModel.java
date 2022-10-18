package com.example.askyoursenior.model;

public class BookDetailModel {
    String book_name, book_publication, posted_by, semester, branch, price;
    String description;

    public BookDetailModel() {
    }

    public BookDetailModel(String book_name, String book_publication, String posted_by, String semester, String branch, String price, String description) {
        this.book_name = book_name;
        this.book_publication = book_publication;
        this.posted_by = posted_by;
        this.semester = semester;
        this.branch = branch;
        this.price = price;
        this.description = description;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_publication() {
        return book_publication;
    }

    public void setBook_publication(String book_publication) {
        this.book_publication = book_publication;
    }

    public String getPosted_by() {
        return posted_by;
    }

    public void setPosted_by(String posted_by) {
        this.posted_by = posted_by;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
