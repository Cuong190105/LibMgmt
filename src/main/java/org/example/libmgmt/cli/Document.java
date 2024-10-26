package org.example.libmgmt.cli;

public class Document {
    private int docID;
    private String title;
    private String author;
    private String publisher;
    private int quantity;
    private String tags;
    private int visited;

    public Document(String title, String author, String publisher, int quantity, String tags, int visited) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
        this.tags = tags;
        this.visited = visited;
    }

    public void setDocID(int docID) {
        this.docID = docID;
    }
    public int getDocID() {
        return docID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getTags() {
        return tags;
    }

    public int getVisited() {
        return visited;
    }
}
