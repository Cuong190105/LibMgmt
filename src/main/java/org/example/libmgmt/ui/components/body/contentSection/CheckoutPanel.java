package org.example.libmgmt.ui.components.body.contentSection;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.ui.style.Style;

/**
 * Creates checkout panel.
 */
public class CheckoutPanel {
  private static final Label readerIdLabel = new Label("Mã số người mượn: ");
  private static final Label readerLabel = new Label("Tên người mượn: ");
  private static final Label readerStatusLabel = new Label("Tình trạng người mượn: ");
  private static final Label documentIdLabel = new Label("Mã số tài liệu: ");
  private static final Label documentLabel = new Label("Tài liệu: ");
  private static final Label dueDurationLabel = new Label("Thời hạn mượn: ");
  private static final Label coverLabel = new Label("Ảnh bìa: ");
  private final TextField readerId;
  private final TextField reader;
  private final TextField readerStatus;
  private final GridPane readerField;
  private final TextField documentId;
  private final TextField document;
  private final TextField dueDuration;
  private final ImageView cover;
  private final GridPane documentField;
  private final FlowPane infoTable;
  private final Button back;
  private final Button submit;
  private final HBox btnGroup;
  private final VBox container;
  private final ScrollPane wrapper;

  /**
   * Creates checkout panel.
   *
   * @param user Member borrowing book.
   * @param doc Document that member wants to borrow.
   */
  public CheckoutPanel(User user, Document doc) {
    this.readerId = new TextField(Integer.toString(user.getUid()));
    this.reader = new TextField(user.getName());
    this.readerStatus = new TextField();
    this.documentId = new TextField(Integer.toString(doc.getDocID()));
    this.document = new TextField(doc.getTitle());
    this.dueDuration = new TextField("14 ngày kể từ khi yêu cầu được duyệt");
    this.cover = new ImageView(doc.getCover());
    this.readerField = new GridPane();
    this.readerField.addColumn(0, readerIdLabel, readerLabel, readerStatusLabel);
    this.readerField.addColumn(1, readerId, reader, readerStatus);
    this.documentField = new GridPane();
    this.documentField.addColumn(0, documentIdLabel, documentLabel, dueDurationLabel, coverLabel);
    this.documentField.addColumn(1, documentId, document, dueDuration, cover);
    this.infoTable = new FlowPane(readerField, documentField);
    this.back = new Button("Quay lại");
    this.submit = new Button("Xác nhận");
    this.btnGroup = new HBox(back, submit);
    this.container = new VBox(infoTable, btnGroup);
    this.wrapper = new ScrollPane(container);
    setFunction();
    style();
  }

  private void setFunction() {
    back.setOnMouseClicked(_ -> {
      System.out.println("back");
    });

    submit.setOnMouseClicked(_ -> {
      System.out.println("submit");
    });
  }

  private void style() {
    Style.styleTitle(readerIdLabel, 16);
    Style.styleTitle(readerLabel, 16);
    Style.styleTitle(readerStatusLabel, 16);
    Style.styleTitle(documentIdLabel, 16);
    Style.styleTitle(documentLabel, 16);
    Style.styleTitle(dueDurationLabel, 16);
    Style.styleTitle(coverLabel, 16);
    GridPane.setValignment(coverLabel, VPos.TOP);
    Style.styleTextField(readerId, 500, 50, 16, "");
    Style.styleTextField(reader, 500, 50, 16, "");
    Style.styleTextField(readerStatus, 500, 50, 16, "");
    Style.styleTextField(documentId, 500, 50, 16, "");
    Style.styleTextField(document, 500, 50, 16, "");
    Style.styleTextField(dueDuration, 500, 50, 16, "");
    Style.styleImg(cover, 400);

    back.setBorder(new Border(new BorderStroke(
        Style.DARKGREEN, BorderStrokeStyle.SOLID, Style.SMALL_CORNER, BorderWidths.DEFAULT
    )));
    Style.styleRoundedButton(back, Style.WHITE, 200, 40, 16);
    Style.styleRoundedButton(submit, Style.LIGHTGREEN, 200, 40, 16);
    ObjectBinding<Insets> padding = Bindings.createObjectBinding(() -> {
      int val;
      if (wrapper.getWidth() > 1500) {
        val = (int) (wrapper.getWidth() - 1500) / 2;
      } else {
        val = (int) (wrapper.getWidth() - 750) / 2;
        val = Math.max(0, val);
      }
      return new Insets(0, val, 0, val);
    }, wrapper.widthProperty());
    infoTable.paddingProperty().bind(padding);

    infoTable.setHgap(50);
    infoTable.setVgap(20);
    readerField.setVgap(20);
    documentField.setVgap(20);

    readerField.setPrefWidth(700);
    documentField.setPrefWidth(700);
    ColumnConstraints labelCol = new ColumnConstraints(200);
    readerField.getColumnConstraints().add(labelCol);
    documentField.getColumnConstraints().add(labelCol);

    btnGroup.setAlignment(Pos.CENTER);
    btnGroup.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    btnGroup.setSpacing(20);

    container.prefWidthProperty().bind(wrapper.widthProperty().subtract(50));
    container.setAlignment(Pos.CENTER);
    container.setSpacing(20);
  }

  public ScrollPane getContent() {
    return wrapper;
  }
}
