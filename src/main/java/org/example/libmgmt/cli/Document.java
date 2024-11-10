package org.example.libmgmt.cli;

public class Document {
    private int docID;
    private String name;
    private String author;
    private String publisher;
    private int quantity;
    private String tags;
    private int visited;
    private boolean type;
    public static final boolean BOOK = false;
    public static final boolean THESIS = true;

    public Document() {}

    public void setTitle(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public Document(String name, String author, String publisher, int quantity, String tags, int visited, boolean type) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
        this.tags = tags;
        this.visited = visited;
        this.type = type;
    }

    public void setDocID(int docID) {
        this.docID = docID;
    }
    public int getDocID() {
        return docID;
    }

    public String getTitle() {
        return name;
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

    public boolean getType() {
        return type;
    }
}
