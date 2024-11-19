package org.example.libmgmt.ui.components.body;

import org.example.libmgmt.LibMgmt;

import java.io.InputStream;

public class Document {
    private long docId;
    private String title;
    private String authors;
    private String publisher;
    private String tags;
    private String description;
    private double score;
    private int votes;
    private InputStream cover;

    public Document() {
        docId = 1234567890;
        title = "Yêu 1 người vô tâm";
        authors = "ABC";
        publisher = "DEF";
        tags = "GHI";
        description = "GHNEVUHEBFJNSDF";
        score = 40;
        votes = 12;
        cover = LibMgmt.class.getResourceAsStream("img/bookCoverPlaceholder.png");
    }

    public long getDocId() {
        return docId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getTags() {
        return tags;
    }

    public String getDescription() {
        return description;
    }

    public InputStream getCover() {
        return cover;
    }

    public double getRating() {
        return score / votes;
    }

    public int getVotes() {
        return votes;
    }
}
