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
import javafx.scene.input.MouseButton;
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
import org.example.libmgmt.DB.BorrowDAO;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.DocumentDAO;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.DB.UserDAO;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.components.Popup;
import org.example.libmgmt.ui.style.Style;

/**
 * Creates checkout panel.
 */
public class ReturnDocument extends Content {
  private static final Label readerIdLabel = new Label("Mã số người mượn: ");
  private static final Label readerLabel = new Label("Tên người mượn: ");
  private static final Label documentIdLabel = new Label("Mã số tài liệu: ");
  private static final Label documentLabel = new Label("Tài liệu: ");
  private final TextField readerId;
  private final TextField reader;
  private final GridPane readerField;
  private final TextField documentId;
  private final TextField document;
  private final GridPane documentField;
  private final FlowPane infoTable;
  private final Button back;
  private final Button submit;
  private final HBox btnGroup;
  private final VBox container;
  private final ScrollPane wrapper;

  /**
   * Creates return panel.
   */
  public ReturnDocument() {
    super(false);
    this.readerId = new TextField();
    this.reader = new TextField();
    this.documentId = new TextField();
    this.document = new TextField();
    this.readerField = new GridPane();
    this.readerField.addColumn(0, readerIdLabel, readerLabel);
    this.readerField.addColumn(1, readerId, reader);
    this.documentField = new GridPane();
    this.documentField.addColumn(0, documentIdLabel, documentLabel);
    this.documentField.addColumn(1, documentId, document);
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
    readerId.focusedProperty().addListener(((observable, oldValue, newValue) -> {
      if (!newValue) {
        if (readerId.getText().isEmpty()) {
          return;
        }
        User u = UserDAO.getInstance().getUserFromUID(Integer.parseInt(readerId.getText()));
        if (u == null) {
          reader.setText("KHÔNG TÌM THẤY NGƯỜI ĐỌC VỚI ID NÀY");
        } else {
          reader.setText(u.getName());
        }
      }
    }));
    readerId.textProperty().addListener((obs, oldVal, newVal) -> {
      String mod = "";
      for (int i = 0; i < newVal.length(); i++) {
        if ('0' <= newVal.charAt(i) && newVal.charAt(i) <= '9') {
          mod += newVal.charAt(i);
        }
      }
      readerId.setText(mod);
    });
    documentId.focusedProperty().addListener(((observable, oldValue, newValue) -> {
      if (!newValue) {
        if (documentId.getText().isEmpty()) {
          return;
        }
        Document d = DocumentDAO.getInstance().getDocFromID(Integer.parseInt(documentId.getText()));
        if (d == null) {
          document.setText("KHÔNG TÌM THẤY TÀI LIỆU VỚI ID NÀY");
        } else {
          document.setText(d.getTitle());
        }
      }
    }));
    documentId.textProperty().addListener((obs, oldVal, newVal) -> {
      String mod = "";
      for (int i = 0; i < newVal.length(); i++) {
        if ('0' <= newVal.charAt(i) && newVal.charAt(i) <= '9') {
          mod += newVal.charAt(i);
        }
      }
      documentId.setText(mod);
    });
    back.setOnMouseClicked(_ -> {
      UIHandler.backToLastPage();
    });

    submit.setOnMouseClicked(e -> {
      if (e.getButton() != MouseButton.PRIMARY) {
        return;
      }
      if (readerId.getText().isEmpty() || documentId.getText().isEmpty()
          || reader.getText().startsWith("KHÔNG") || document.getText().startsWith("KHÔNG")) {
        Popup p = new Popup("Không thể trả tài liệu", "Kiểm tra lại dữ liệu và thử lại.");
        p.addOkBtn();
        UIHandler.addPopup(p);
      } else {
        if (BorrowDAO.getInstance().returnDocument(Integer.parseInt(readerId.getText()),
            Integer.parseInt(documentId.getText())) == 0) {
          Popup p = new Popup("Không tìm thấy bản ghi", "Kiểm tra lại dữ liệu và thử lại.");
          p.addOkBtn();
          UIHandler.addPopup(p);
        } else {
          Popup p = new Popup("Thành công", "Đã trả tài liệu thành công.");
          p.addCustomBtn("Quay lại", Style.LIGHTGREEN, () -> {
            UIHandler.backToLastPage();
            return null;
          });
          UIHandler.addPopup(p);
        }
      }
    });
  }

  private void style() {
    reader.setEditable(false);
    document.setEditable(false);
    Style.styleTitle(readerIdLabel, 16);
    Style.styleTitle(readerLabel, 16);
    Style.styleTitle(documentIdLabel, 16);
    Style.styleTitle(documentLabel, 16);
    Style.styleTextField(readerId, 500, 50, 16, false, "");
    Style.styleTextField(reader, 500, 50, 16, false, "");
    Style.styleTextField(documentId, 500, 50, 16, false, "");
    Style.styleTextField(document, 500, 50, 16, false, "");

    back.setBorder(new Border(new BorderStroke(
        Style.DARKGREEN, BorderStrokeStyle.SOLID, Style.SMALL_CORNER, BorderWidths.DEFAULT
    )));
    Style.styleRoundedSolidButton(back, Style.WHITE, 200, 40, 16);
    Style.styleRoundedSolidButton(submit, Style.LIGHTGREEN, 200, 40, 16);
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

  @Override
  public ScrollPane getContent() {
    return wrapper;
  }
}
