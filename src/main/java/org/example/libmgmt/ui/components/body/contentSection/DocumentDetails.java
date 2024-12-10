package org.example.libmgmt.ui.components.body.contentSection;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import org.example.libmgmt.DB.Comment;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.DocumentDAO;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.control.UserControl;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.components.body.Star;
import org.example.libmgmt.ui.components.body.card.CommentCard;
import org.example.libmgmt.ui.style.Style;

import java.util.List;

/**
 * A panel displaying document info.
 */
public class DocumentDetails extends Content {
  private final Label authorsLabel = new Label("Tác giả");
  private final Label publisherLabel = new Label("Nhà xuất bản");
  private final Label descriptionLabel = new Label("Mô tả");
  private final Label tagsLabel = new Label("Danh mục");
  private final Label ratingSectionTitle = new Label("ĐÁNH GIÁ");

  private final Document doc;
  private final ImageView cover;
  private final Label title;
  private final Text authors;
  private final Text publisher;
  private final Text description;
  private final Text tags;
  private final Button preview;
  private final Button borrowDocument;
  private Button editDocument;
  private Button removeDocument;
  private final GridPane infoTable;
  private final FlowPane detailContainer;

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
  private final Button loadMoreComments;
  private final Label commentsLabel;
  private final VBox comments;
  private final VBox container;
  private final ScrollPane wrapper;

  /**
   * Creates a new document.
   */
  public DocumentDetails(Document doc) {
    super(false);
    this.doc = doc;
    cover = new ImageView(doc.getCover());
    title = new Label(doc.getTitle());
    authors = new Text(doc.getAuthor());
    publisher = new Text(doc.getPublisher());
    description = new Text(doc.getDescription());
    tags = new Text(String.join(", ", doc.getTags()));
    preview = new Button("Xem trước");
    infoTable = new GridPane();
    infoTable.add(title, 0, 0, 2, 1);
    infoTable.addColumn(0, authorsLabel, publisherLabel,
        tagsLabel, descriptionLabel, preview);
    infoTable.addColumn(1, authors, publisher, tags, description);
    borrowDocument = new Button("Mượn tài liệu");
    infoTable.addColumn(1, borrowDocument);
    if (UserControl.getUser().isLibrarian()) {
      removeDocument = new Button("Xóa tài liệu");
      editDocument = new Button("Chỉnh sửa");
      infoTable.addColumn(1, removeDocument);
      infoTable.addColumn(0, editDocument);
    }
    detailContainer = new FlowPane(cover, infoTable);
    commentsLoaded = 0;
    setSummaryAndUserCritics();
    loadMoreComments = new Button("Tải thêm bình luận");
    commentsLabel = new Label("Đánh giá của người đọc khác");
    comments = new VBox();
    loadComment();
    container = new VBox(detailContainer, ratingSectionTitle,
        summaryAndUserCritics, commentsLabel, comments);
    wrapper = new ScrollPane(container);
    setFunction();
    style();
  }

  private void loadComment() {
    List<Comment> commentList = Comment.getComments(doc);
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
      p.setOnMouseClicked(e -> {
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

    if (UserControl.getUser().isLibrarian()) {
      editDocument.setOnMouseClicked(e -> {
        UIHandler.openDocumentEditPanel(doc);
      });
    }

    preview.setOnMouseClicked(e -> {
      UIHandler.openDocumentReader(doc);
    });

    borrowDocument.setOnMouseClicked(e -> {
      if (!doc.canBorrow()) {
        Popup p = new Popup("Không thể mượn tài liệu", "Số lượng tài liệu đã hết.");
        p.addOkBtn();
        UIHandler.addPopup(p);
      } else {
        if (UserControl.getUser().isLibrarian()) {
          UIHandler.openCheckoutPage(null, doc);
        } else {
          UIHandler.openCheckoutPage(UserControl.getUser(), doc);
        }
      }
    });

    removeDocument.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY) {
        Popup p = new Popup("CẢNH BÁO", "Bạn có chắc chắn muốn xóa sách này?" +
            " Thao tác này không thể hoàn tác.");
        p.addCancelBtn();
        p.addCustomBtn("Xóa", Style.RED, () -> {
          DocumentDAO.getInstance().deleteDocument(doc.getDocID());
          UIHandler.backToLastPage();
          return null;
        });
        UIHandler.addPopup(p);
      }
    });

    userComment.setTextFormatter(new TextFormatter<String>(change ->
        change.getControlNewText().length() <= 500 ? change : null));

    sendCritics.setOnMouseClicked(e -> {
      String comment = userComment.getText();
//            Comment.sendComment(User.getUser(), doc.getDocId(), rating, comment);
    });

    loadMoreComments.setOnMouseClicked(e -> {
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
    Style.styleImg(cover, 400);
    Style.styleTitle(title, 36);
    Style.styleTitle(authorsLabel, 16);
    Style.styleTitle(publisherLabel, 16);
    Style.styleTitle(tagsLabel, 16);
    Style.styleTitle(descriptionLabel, 16);
    Style.styleWrapText(authors, 400, 16);
    Style.styleWrapText(publisher, 400, 16);
    Style.styleWrapText(tags, 400, 16);
    Style.styleWrapText(description, 400, 16);
    GridPane.setValignment(descriptionLabel, VPos.TOP);
    DoubleBinding maxWidth = Bindings.createDoubleBinding(() -> {
      if (wrapper.getWidth() > 1200) {
        return wrapper.getWidth() - 600;
      } else {
        return wrapper.getWidth() - 100;
      }
    }, wrapper.widthProperty());
    description.wrappingWidthProperty().bind(maxWidth.subtract(250));
    title.prefWidthProperty().bind(maxWidth);
    title.setWrapText(true);

    Style.styleRoundedBorderButton(preview, Style.DARKGREEN, 200, 50, 20);
    if (UserControl.getUser().isLibrarian()) {
      Style.styleRoundedBorderButton(editDocument, Style.DARKGREEN, 200, 50, 20);
      Style.styleRoundedSolidButton(removeDocument, Style.RED, 200, 50, 20);
      Style.styleRoundedSolidButton(borrowDocument, Style.DARKGREEN, 200, 50, 20);
      Insets rowGap = new Insets(20, 0, 0, 0);
      GridPane.setMargin(removeDocument, rowGap);
      GridPane.setMargin(editDocument, rowGap);
    } else {
      Style.styleRoundedSolidButton(borrowDocument, Style.DARKGREEN, 200, 50, 20);
    }
    GridPane.setMargin(preview, new Insets(30, 0, 0, 0));
    GridPane.setMargin(borrowDocument, new Insets(30, 0, 0, 0));
    detailContainer.setPadding(new Insets(0,0,0,50));
    detailContainer.setHgap(100);
    detailContainer.setVgap(50);
    infoTable.setVgap(5);
    infoTable.setHgap(50);

    title.setPadding(new Insets(0, 0, 20, 0));
    Style.styleTitle(ratingSectionTitle, 28);

    Style.styleTitle(summaryLabel, 24);
    Style.styleTitle(avgRatingScore, 24);
    Style.styleWrapText(votes, 200, 16);
    avgRatingStar.setAlignment(Pos.CENTER);
    votes.setTextAlignment(TextAlignment.CENTER);
    summary.setSpacing(10);
    summary.setAlignment(Pos.CENTER);
    FlowPane.setMargin(summary, new Insets(0, 0, 0, 100));

    Style.styleTitle(userCriticsLabel, 24);
    userCritics.setSpacing(10);
    if (/*thisUser.hasRead(doc.getDocId())*/true) {
      Style.styleTextArea(userComment, 500, 200, 16,
          "Nhập đánh giá của bạn (Tối đa 500 kí tự).");
    } else {
      userComment.setDisable(true);
      ratingStar.setDisable(true);
      sendCritics.setDisable(true);
      Style.styleTextArea(userComment, 500, 200, 16,
          "Hãy mượn và đọc ít nhất một lần trước khi đánh giá tài liệu!");
    }
    ratingStar.setSpacing(50);
    ratingStar.setAlignment(Pos.CENTER);
    userComment.setMaxHeight(160);
    Style.styleRoundedSolidButton(sendCritics, Style.LIGHTGREEN, 150, 50, 16);
    userCritics.setMaxWidth(Region.USE_PREF_SIZE);
    userCritics.setBackground(new Background(new BackgroundFill(
        Color.rgb(250, 250, 250), Style.BIG_CORNER, Insets.EMPTY
    )));

    ObjectBinding<Insets> padding = Bindings.createObjectBinding(() -> {
      int value;
      if (summary.getWidth() + userCritics.getWidth()
          < container.getWidth() - 120) {
        value = (int) ((container.getWidth() - 120
            - (summary.getWidth() + userCritics.getWidth())) / 2);
      } else {
        value = (int) ((container.getWidth() - 120
            - Math.max(summary.getWidth(), userCritics.getWidth())) / 2);
      }
      value = Math.max(0, value);
      return new Insets(0, value, 0, value);
    }, container.widthProperty());
    summaryAndUserCritics.paddingProperty().bind(padding);

    summaryAndUserCritics.setVgap(50);
    Style.styleTitle(commentsLabel,24);
    comments.setSpacing(20);
    VBox.setMargin(comments, new Insets(0, 0, 0, 50));
    container.setSpacing(20);
    wrapper.setFitToWidth(true);
  }

  public ScrollPane getContent() {
    return wrapper;
  }
}
