package org.example.libmgmt.ui.components.body;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.example.libmgmt.DB.Comment;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.ui.style.Style;

import static org.example.libmgmt.control.MainControl.thisUser;

public class DocumentDetails {
    private final Label authorsLabel = new Label("Tác giả");
    private final Label publisherLabel = new Label("Nhà xuất bản");
    private final Label descriptionLabel = new Label("Mô tả");
    private final Label tagsLabel = new Label("Danh mục");
    private final Label ratingSectionTitle = new Label("ĐÁNH GIÁ");

    private Document doc;
    private ImageView cover;
    private Label title;
    private Text authors;
    private Text publisher;
    private Text description;
    private Text tags;
    private Button readDocument;
    private Button borrowDocument;
    private GridPane infoTable;
    private FlowPane detailContainer;

    private Label avgRatingScore;
    private HBox avgRatingStar;
    private Text votes;
    private VBox summary;
    private Label userCriticsLabel;
    private TextArea userComment;
    private int userRating;
    private Label summaryLabel;
    private HBox ratingStar;
    private Button sendCritics;
    private VBox userCritics;
    private FlowPane summaryAndUserCritics;
    private int commentsLoaded;
    private Button loadMoreComments;
    private Label commentsLabel;
    private VBox comments;
    private VBox container;

    public DocumentDetails(Document doc) {
        this.doc = doc;
        cover = new ImageView(new Image(doc.getCover()));
        title = new Label(doc.getTitle());
        authors = new Text(doc.getAuthor());
        publisher = new Text(doc.getPublisher());
        description = new Text(doc.getDescription());
        tags = new Text(doc.getTagsString());
        readDocument = new Button("Đọc trực tuyến");
        borrowDocument = new Button("Mượn sách");
        infoTable = new GridPane();
        infoTable.add(title, 0, 0, 2, 1);
        infoTable.addColumn(0, authorsLabel, publisherLabel,
                tagsLabel, descriptionLabel, readDocument);
        infoTable.addColumn(1, authors, publisher, tags, description, borrowDocument);
        detailContainer = new FlowPane(cover, infoTable);
        commentsLoaded = 0;
        setSummaryAndUserCritics();
        loadMoreComments = new Button("Tải thêm bình luận");
        commentsLabel = new Label("Đánh giá của người đọc khác");
        comments = new VBox();
        loadComment();
        container = new VBox(detailContainer, ratingSectionTitle,
                summaryAndUserCritics, commentsLabel, comments);
        setFunction();
        style();
    }

    private void loadComment() {
        Comment[] commentList = Comment.getComments(doc, commentsLoaded);
        for (Comment c : commentList) {
            CommentCard cmt = new CommentCard(c);
            comments.getChildren().add(cmt.getCard());
            commentsLoaded++;
        }
        if (commentsLoaded < doc.getVotes()) {
            comments.getChildren().add(loadMoreComments);
        }
    }

    private void setFunction() {
        sendCritics.setDisable(true);
//        Comment c = Comment.getComment(User.getUser(), doc.getDocId());
//        if (c != null) {
//            userComment.setText(c.getComment());
//            for (int i = 0; i < c.getRating(); i++) {
//                ((Polygon) ratingStar.getChildren()).setFill(Color.YELLOW);
//            }
//        }
        for (Node p : ratingStar.getChildren()) {
            p.setOnMouseClicked(_ -> {
                boolean fill = true;
                for (Node q : ratingStar.getChildren()) {
                    if (fill) {
                        ((Polygon) q).setFill(Style.DARKGREEN);
                    } else {
                        ((Polygon) q).setFill(Color.TRANSPARENT);
                    }
                    if (p.equals(q)) {
                        fill = false;
                    }
                }
                if (sendCritics.isDisabled()) {
                    sendCritics.setDisable(false);
                }
            });
        }

        userComment.setOnKeyTyped(_ -> {
            if (sendCritics.isDisabled()) {
                sendCritics.setDisable(false);
            }
            if (userComment.getLength() > 500) {
                userComment.setText(userComment.getText().substring(0, 500));
                userComment.positionCaret(500);
            }
        });

        sendCritics.setOnMouseClicked(_ -> {
            String comment = userComment.getText();
//            Comment.sendComment(User.getUser(), doc.getDocId(), rating, comment);
        });

        loadMoreComments.setOnMouseClicked(_ -> {
            //
        });
    }

    /**
     * Creates summary and this user's critics.
     */
    private void setSummaryAndUserCritics() {
        summaryLabel = new Label("TỔNG QUAN");
        avgRatingScore = new Label(String.format("%.1f", doc.getRating()) + "/5");
        avgRatingStar = Star.getStar(2, doc.getRating());
        votes = new Text(String.format("%,d", doc.getVotes()) + " đánh giá");
        summary = new VBox(summaryLabel, avgRatingScore, avgRatingStar, votes);

        userCriticsLabel = new Label("Đánh giá của bạn");
        userComment = new TextArea();
        ratingStar = Star.getStar(2, 0);
        sendCritics = new Button("Gửi đánh giá");
        userCritics = new VBox(userCriticsLabel, ratingStar, userComment, sendCritics);
        summaryAndUserCritics = new FlowPane(userCritics, summary);
    }

    private void style() {
        cover.setSmooth(true);
        cover.setPreserveRatio(true);
        cover.setFitWidth(400);

        Style.styleTitle(title, 36);
        Style.styleTitle(authorsLabel, 16);
        Style.styleTitle(publisherLabel, 16);
        Style.styleTitle(tagsLabel, 16);
        Style.styleTitle(descriptionLabel, 16);
        Style.styleWrapText(authors, 400, 16);
        Style.styleWrapText(publisher, 400, 16);
        Style.styleWrapText(tags, 400, 16);
        Style.styleWrapText(description, 400, 16);

        Style.styleRoundedButton(readDocument, Style.LIGHTGREEN, 200, 50, 20);
        Style.styleRoundedButton(borrowDocument, Style.DARKGREEN, 200, 50, 20);
        GridPane.setMargin(readDocument, new Insets(30, 0, 0, 0));
        GridPane.setMargin(borrowDocument, new Insets(30, 0, 0, 0));
        detailContainer.setPadding(new Insets(0,0,0,50));
        detailContainer.setHgap(150);
        detailContainer.setVgap(50);
        infoTable.setHgap(50);
        infoTable.setVgap(5);

        title.setPadding(new Insets(0, 0, 20, 0));
        Style.styleTitle(ratingSectionTitle, 28);

        Style.styleTitle(summaryLabel, 24);
        Style.styleTitle(avgRatingScore, 24);
        Style.styleWrapText(votes, 200, 16);
        avgRatingStar.setAlignment(Pos.CENTER);
        votes.setTextAlignment(TextAlignment.CENTER);
        summary.setSpacing(10);
        summary.setAlignment(Pos.CENTER);
        summary.setMinWidth(300);
        FlowPane.setMargin(summary, new Insets(0, 0, 0, 100));

        Style.styleTitle(userCriticsLabel, 24);
        userCritics.setSpacing(10);
        if (thisUser.hasRead(doc.getDocID())) {
            Style.styleTextArea(userComment, 500, 16,
                    "Nhập đánh giá của bạn (Tối đa 500 kí tự).");
        } else {
            userComment.setDisable(true);
            ratingStar.setDisable(true);
            sendCritics.setDisable(true);
            Style.styleTextArea(userComment, 500, 16,
                    "Hãy mượn và đọc ít nhất một lần trước khi đánh giá tài liệu!");
        }
        ratingStar.setSpacing(50);
        ratingStar.setAlignment(Pos.CENTER);
        userComment.setMaxHeight(160);
        Style.styleRoundedButton(sendCritics, Style.LIGHTGREEN, 150, 50, 16);
        userCritics.setMinWidth(400);
        userCritics.setMaxWidth(800);
        userCritics.setPrefWidth(Region.USE_COMPUTED_SIZE);
        userCritics.setPadding(new Insets(20));
        userCritics.setBackground(new Background(new BackgroundFill(
                Color.rgb(250, 250, 250), Style.BIG_CORNER, Insets.EMPTY
        )));
        summaryAndUserCritics.setHgap(10);
        summaryAndUserCritics.setVgap(50);
        Style.styleTitle(commentsLabel,24);
        comments.setSpacing(20);
        VBox.setMargin(comments, new Insets(0, 50, 0, 50));
        container.setSpacing(20);
    }

    public VBox getContent() {
        return container;
    }
}
