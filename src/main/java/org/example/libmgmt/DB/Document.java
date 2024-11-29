package org.example.libmgmt.DB;

import org.example.libmgmt.LibMgmt;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Document {
    private int docID;
    private String title;
    private String author;
    private String publisher;
    private int publishYear;
    private int quantity;
    private List<String> tags;
    private int visited;
    private boolean thesis;
    private String ISBN;
    private int votes;
    private int score;
    private InputStream cover;
    private InputStream content;
    private String description;

    public static final boolean BOOK = false;
    public static final boolean THESIS = true;

    public Document() {
        docID = 1234567890;
        title = "Yêu 1 người vô tâm";
        author = "ABC";
        publisher = "DEF";
        tags = new ArrayList<>();
        tags.add("GHI");
        description = "GHNEVUHEBFJNSDF";
        score = 40;
        votes = 12;
        cover = LibMgmt.class.getResourceAsStream("img/bookCoverPlaceholder.png");
    }

    public Document(String name, String author, String publisher, int quantity, String tags, int visited, boolean type) {
        this.title = name;
        this.author = author;
        this.publisher = publisher;
        this.quantity = quantity;
        this.visited = visited;
        this.thesis = type;
        this.tags = new ArrayList<>();
        if (tags != null && !tags.isEmpty()) {
            Collections.addAll(this.tags, tags.split(","));
        }
    }

    public void setTitle(String title) {
        this.title = title;
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

    public boolean isThesis() {
        return thesis;
    }

    public void setThesis(boolean thesis) {
        this.thesis = thesis;
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

    public int getVisited() {
        return visited;
    }

    public boolean getType() {
        return thesis;
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

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public double getRating() {
        return 1.0 * score / votes;
    }

    public double getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public InputStream getCover() {
        return cover;
    }

    public void setCover(InputStream cover) {
        this.cover = cover;
    }

    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Document doc = (Document) obj;
        return this.docID == doc.docID &&
                Objects.equals(this.title, doc.title) &&
                Objects.equals(this.author, doc.author) &&
                Objects.equals(this.publisher, doc.publisher) &&
                this.quantity == doc.quantity &&
                Objects.equals(this.tags, doc.tags) &&
                this.visited == doc.visited &&
                this.thesis == doc.thesis;
    }

    public String print() {
        return " " + docID + "  " + title + "  " + author + "  "
                + publisher + "  " + quantity + "  " + tags + "  "
                + visited + "  " + thesis + "\n";
    }
}
