package org.example.libmgmt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Document {
    private int docID;
    private String name;
    private String author;
    private String publisher;
    private int quantity;
    private List<String> tags;
    private int visited;
    private boolean type;
    private String ISBN;
    public static final boolean BOOK = false;
    public static final boolean THESIS = true;

    public Document() {}

    public Document(String name, String author, String publisher, int quantity, String tags, int visited, boolean type) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
        this.visited = visited;
        this.type = type;
        this.tags = new ArrayList<>();
        if (tags != null && !tags.isEmpty()) {
            Collections.addAll(this.tags, tags.split(","));
        }
    }


    public void setName(String name) {
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


    public void setVisited(int visited) {
        this.visited = visited;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public void setDocID(int docID) {
        this.docID = docID;

    }

    public int getDocID() {
        return docID;
    }

    public String getName() {
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

    public int getVisited() {
        return visited;
    }

    public boolean getType() {
        return type;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = new ArrayList<>();
        if (tags != null && !tags.isEmpty()) {
            Collections.addAll(this.tags, tags.split(","));
        }
    }

    public String getTagsString() {
        String ans = "";
        for (int i = 0; i < tags.size(); ++i) {
            ans += tags.get(i) + (i + 1 != tags.size() ? ",":"");
        }
        return ans;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Document doc = (Document) obj;
        return this.docID == doc.docID &&
                Objects.equals(this.name, doc.name) &&
                Objects.equals(this.author, doc.author) &&
                Objects.equals(this.publisher, doc.publisher) &&
                this.quantity == doc.quantity &&
                Objects.equals(this.tags, doc.tags) &&
                this.visited == doc.visited &&
                this.type == doc.type;
    }

    public String print() {
        return " " + docID + "  " + name + "  " + author + "  "
                + publisher + "  " + quantity + "  " + tags + "  "
                + visited + "  " + type + "\n";
    }
}
