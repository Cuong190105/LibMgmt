package org.example.libmgmt.ui.components.body.contentSection;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import org.example.libmgmt.DB.Borrow;
import org.example.libmgmt.DB.BorrowDAO;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.style.Style;

/**
 * A panel for displaying user's requests.
 */
public class BorrowHistoryPanel extends Content implements UpdatableContent {
  private final Label error = new Label("Có lỗi xày ra! Vui lòng thử lại sau.");
  private final Label noResult = new Label("Không có kết quả");
  private final AnchorPane container;
  private final TableView<Borrow> borrowTable;
  private final Button returnBook;
  private List<Borrow> borrowList;
  private int page;
  private int totalPage;
  private int loadStatus;

  /**
   * Constructor.
   */
  public BorrowHistoryPanel() {
    super(true);
    borrowTable = new TableView<>();
    TableColumn<Borrow, String> borrowerId = new TableColumn<>("Mã người mượn");
    TableColumn<Borrow, String> docId = new TableColumn<>("Mã tài liệu");
    TableColumn<Borrow, Date> checkoutDate = new TableColumn<>("Ngày mượn");
    TableColumn<Borrow, Date> estimatedReturnDate = new TableColumn<>("Hạn trả");
    TableColumn<Borrow, Date> returnDate = new TableColumn<>("Ngày trả");
//    TableColumn<Borrow, String> status = new TableColumn<>("Trạng thái");
    returnBook = new Button("Trả tài liệu");
    container = new AnchorPane(borrowTable, returnBook);
    borrowList = new ArrayList<>();
    borrowTable.getColumns().addAll(
        borrowerId, docId, checkoutDate, estimatedReturnDate, returnDate
    );
    borrowerId.setCellValueFactory(new PropertyValueFactory<>("UID"));
    docId.setCellValueFactory(new PropertyValueFactory<>("docID"));
    checkoutDate.setCellValueFactory(new PropertyValueFactory<>("borrowingDate"));
    estimatedReturnDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
    returnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
//    status.setCellValueFactory(new PropertyValueFactory<>("UID"));
    setFunction();
    style();
  }


  private void style() {
    Style.styleRoundedSolidButton(returnBook, Style.LIGHTGREEN, 300, 50, 16);
    Style.styleTitle(error, 16);
    Style.styleTitle(noResult, 16);
    for (TableColumn t : borrowTable.getColumns()) {
      t.prefWidthProperty().bind(borrowTable.widthProperty().subtract(5).divide(6));
    }
    borrowTable.getStylesheets().add(LibMgmt.class.getResource("tableview.css").toExternalForm());
    AnchorPane.setTopAnchor(borrowTable, 0.0);
    AnchorPane.setLeftAnchor(borrowTable, 0.0);
    AnchorPane.setRightAnchor(borrowTable, 0.0);
    AnchorPane.setBottomAnchor(borrowTable, 60.0);
    AnchorPane.setLeftAnchor(returnBook, 0.0);
    AnchorPane.setRightAnchor(returnBook, 0.0);
    AnchorPane.setBottomAnchor(returnBook, 0.0);
  }

  private void setFunction() {
    borrowList = BorrowDAO.getInstance().allBorrow();
    borrowTable.getItems().addAll(borrowList);
    returnBook.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY) {
        UIHandler.openReturnDocument();
      }
    });
    Callback<TableColumn<Borrow, Date>, TableCell<Borrow, Date>> dateFormat = new Callback<TableColumn<Borrow, Date>, TableCell<Borrow, Date>>() {
      @Override
      public TableCell<Borrow, Date> call(TableColumn<Borrow, Date> borrowDateTableColumn) {
        TableCell<Borrow, Date> cell = new TableCell<Borrow, Date>() {
          private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
          @Override
          protected void updateItem(Date item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
              setText(null);
            } else {
              setText(format.format(item));
            }
          }
        };
        return cell;
      }
    };
    for (int i = 2; i < 5; i++) {
      ((TableColumn<Borrow, Date>)borrowTable.getColumns().get(i)).setCellFactory(dateFormat);
    }
  }

  public AnchorPane getContent() {
    return container;
  }

  @Override
  public void update() {
    borrowList = BorrowDAO.getInstance().allBorrow();
  }
}
