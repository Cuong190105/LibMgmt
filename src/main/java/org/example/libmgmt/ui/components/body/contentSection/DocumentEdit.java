package org.example.libmgmt.ui.components.body.contentSection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.example.libmgmt.AppLogger;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.DocumentDAO;
import org.example.libmgmt.DB.GoogleBooksAPI;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.control.Validator;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleForm;

/**
 * A panel for edit document info or add new document.
 */
public class DocumentEdit extends Content {
  private final boolean isEditSession;
  private Document doc;
  private Label docIdLabel;
  private Label docTitleLabel;
  private Label authorLabel;
  private Label publisherLabel;
  private Label publishYearLabel;
  private Label typeLabel;
  private Label tagsLabel;
  private Label descriptionLabel;
  private Label isbnLabel;
  private Label numberOfCopiesLabel;
  private Label docContentLabel;
  private TextField docId;
  private TextField docTitle;
  private TextField author;
  private TextField publisher;
  private TextField publishYear;
  private ComboBox<String> type;
  private TextField tags;
  private TextArea description;
  private TextField isbn;
  private Text isbnAutoFillWarning;
  private TextField numberOfCopies;
  private Button autoFillByIsbn;
  private HBox isbnGroup;
  private Button docSelectBtn;
  private HBox docSelectGroup;
  private Text docFileName;
  private FileChooser docSelect;
  private File docFile;
  private GridPane infoTable;
  private FileChooser coverSelect;
  private Button coverSelectBtn;
  private ImageView docCover;
  private Text warningText;
  private VBox coverGroup;
  private FlowPane mainContent;
  private Button cancel;
  private Button save;
  private HBox btnGroup;
  private VBox container;
  private File coverFile;
  private ScrollPane wrapper;

  /**
   * Constructor for adding new document.
   */
  public DocumentEdit() {
    super(false);
    doc = new Document();
    isEditSession = false;
    createPanel();
  }

  /**
   * Constructor for editing existing document.
   */
  public DocumentEdit(Document doc) {
    super(false);
    this.doc = doc;
    isEditSession = true;
    createPanel();
  }

  private void createPanel() {
    docIdLabel = new Label("Mã số tài liệu:");
    docTitleLabel = new Label("Tên tài liệu:");
    authorLabel = new Label("Tác giả:");
    publisherLabel = new Label("Nhà xuất bản:");
    publishYearLabel = new Label("Năm xuất bản:");
    typeLabel = new Label("Loại tài liệu:");
    tagsLabel = new Label("Tag:");
    descriptionLabel = new Label("Mô tả:");
    isbnLabel = new Label("Mã ISBN:");
    numberOfCopiesLabel = new Label("Số lượng bản sao: ");
    docContentLabel = new Label("File tài liệu:");

    docId = new TextField();
    docTitle = new TextField();
    author = new TextField();
    publisher = new TextField();
    publishYear = new TextField();
    type = new ComboBox<>(FXCollections.observableArrayList("Sách", "Luận văn"));
    type.setPromptText("- Chọn thể loại -");
    tags = new TextField();
    description = new TextArea();
    isbn = new TextField();
    numberOfCopies = new TextField();
    autoFillByIsbn = new Button("Tự động lấy thông tin *");
    autoFillByIsbn.setTooltip(new Tooltip("Sử dụng ISBN để tự động lấy thông tin tài liệu.\n"
        + "Yêu cầu nhập ISBN trước khi thực hiện lấy thông tin."));
    isbnGroup = new HBox(isbn, autoFillByIsbn);
    isbnAutoFillWarning = new Text();
    docSelectBtn = new Button("Chọn tài liệu");
    docFileName = new Text();
    docSelectGroup = new HBox(docSelectBtn, docFileName);
    docSelect = new FileChooser();
    docSelect.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
    infoTable = new GridPane();
    infoTable.addColumn(0, docIdLabel, isbnLabel, new Group(), docTitleLabel, authorLabel, publisherLabel,
        publishYearLabel, typeLabel, tagsLabel,
        descriptionLabel, numberOfCopiesLabel, docContentLabel);
    infoTable.addColumn(1, docId, isbnGroup, isbnAutoFillWarning, docTitle, author, publisher,
        publishYear, type, tags, description, numberOfCopies, docSelectGroup);

    coverSelectBtn = new Button("Chọn ảnh bìa");
    coverSelect = new FileChooser();
    coverSelect.getExtensionFilters().add(
        new FileChooser.ExtensionFilter("Image files", "*.png", "*.jpg", "*.jpeg")
    );
    docCover = new ImageView(doc.getCover());
    warningText = new Text("Có lỗi khi tải ảnh.");
    coverGroup = new VBox(docCover, coverSelectBtn);
    mainContent = new FlowPane(infoTable, coverGroup);

    cancel = new Button("QUAY LẠI");
    save = new Button("LƯU THAY ĐỔI");
    if (!isEditSession) {
      save.setText("THÊM SÁCH");
    }
    btnGroup = new HBox(cancel, save);
    container = new VBox(mainContent, btnGroup);
    wrapper = new ScrollPane(container);
    setFunction();
    style();

    if (isEditSession) {
      updateDocInfo();
      docId.setText(Integer.toString(doc.getDocID()));
    } else {
      docIdLabel.setVisible(false);
      docId.setVisible(false);
      docIdLabel.setManaged(false);
      docId.setManaged(false);
    }
  }

  private void setFunction() {
    docId.setEditable(false);
    warningText.setVisible(false);
    isbnAutoFillWarning.setVisible(false);
    isbnAutoFillWarning.setManaged(false);
    isbn.setOnKeyTyped(e -> {
      if (isbnAutoFillWarning.isVisible()) {
        isbnAutoFillWarning.setVisible(false);
        isbnAutoFillWarning.setManaged(false);
      }
    });
    docSelectBtn.setOnMouseClicked(e -> {
      docFile = docSelect.showOpenDialog(UIHandler.getStage());
      if (docFile != null) {
        String name = docFile.getAbsolutePath();
        docFileName.setText(name.substring(name.lastIndexOf("\\") + 1));
      }
    });

    coverSelectBtn.setOnMouseClicked(e -> {
      coverFile = coverSelect.showOpenDialog(UIHandler.getStage());
      try {
        if (coverFile != null) {
          FileInputStream file = new FileInputStream(coverFile);
          docCover.setImage(new Image(file));
          file.close();
        }
      } catch (FileNotFoundException ex) {
        warningText.setVisible(true);
      } catch (IOException ex) {
        AppLogger.log(ex);
      }
    });

    isbn.textProperty().addListener((obs, oldVal, newVal) -> {
      if (isbnAutoFillWarning.isVisible()) {
        isbnAutoFillWarning.setVisible(false);
        isbnAutoFillWarning.setManaged(false);
      }
      isbn.setText(Validator.formatIsbn(newVal));
    });

    autoFillByIsbn.setOnMouseClicked(e -> {
      if (e.getButton() != MouseButton.PRIMARY) {
        return;
      }
      if (isbnAutoFillWarning.isVisible()) {
        isbnAutoFillWarning.setVisible(false);
        isbnAutoFillWarning.setManaged(false);
      }
      autoFillByIsbn.setText("Đang lấy thông tin...");
      autoFillByIsbn.setMouseTransparent(true);
      autoFillByIsbn.setFocusTraversable(false);
      Style.styleRoundedSolidButton(autoFillByIsbn, Style.DARKGREEN, 230, 50, 16);
      Task<Void> fetchData = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
          String error = null;
          try {
            String isbnString = isbn.getText();
            if (isbnString.length() != 10 && isbnString.length() != 13) {
              error = "Mã ISBN phải có độ dài 10 hoặc 13 ký tự.";
            } else {
              doc = GoogleBooksAPI.getDocData(isbn.getText());
              if (doc.getTitle() == "") {
                error = "Không tìm thấy sách tương ứng.";
              }
            }
          } catch (Exception ex) {
            System.out.println(ex.getMessage());
            error = "Không thể kết nối đến máy chủ.";
          }
          String finalError = error;
          Platform.runLater(() -> {
            if (finalError == null) {
              updateDocInfo();
            } else {
              isbnAutoFillWarning.setText(finalError);
              isbnAutoFillWarning.setVisible(true);
              isbnAutoFillWarning.setManaged(true);
            }
            Style.styleRoundedSolidButton(autoFillByIsbn, Style.LIGHTGREEN, 230, 50, 16);
            autoFillByIsbn.setMouseTransparent(false);
            autoFillByIsbn.setFocusTraversable(true);
            autoFillByIsbn.setText(("Tự động lấy thông tin *"));
          });
          return null;
        }
      };
      new Thread(fetchData).start();
    });

    cancel.setOnMouseClicked(e -> {
      if (e.getButton() == MouseButton.PRIMARY) {
        UIHandler.backToLastPage();
      }
    });

    save.setOnMouseClicked(e -> {
      if (isEditSession) {
        doc.setDocID(Integer.parseInt(docId.getText()));
      }
      doc.setTitle(docTitle.getText());
      doc.setAuthor(author.getText());
      doc.setPublisher(publisher.getText());
      doc.setPublishYear(Integer.parseInt(publishYear.getText()));
      doc.setThesis(type.getSelectionModel().getSelectedIndex() == 1);
      doc.setTags(Arrays.asList(tags.getText().split(", ")));
      doc.setDescription(description.getText());
      doc.setISBN(isbn.getText());
      doc.setCover(docCover.getImage());
      doc.setQuantity(Integer.parseInt(numberOfCopies.getText()));
      Task<Void> saveChanges = new Task<Void>() {
        @Override
        protected Void call() throws Exception {
          if (isEditSession) {
            DocumentDAO.getInstance().updateDocument(doc);
          } else {
            DocumentDAO.getInstance().addDocument(doc);
          }
          Platform.runLater(() -> {
            if (isEditSession) {
              UIHandler.backToLastPage();
            }
            UIHandler.backToLastPage();
            UIHandler.openDocDetail(doc);
          });
          return null;
        }
      };
      new Thread(saveChanges).start();
    });
  }

  private void updateDocInfo() {
    docTitle.setText(doc.getTitle() == null ? docTitle.getText() : doc.getTitle());
    author.setText(doc.getAuthor() == null ? author.getText() : doc.getAuthor());
    publisher.setText(doc.getPublisher() == null ? publisher.getText() : doc.getPublisher());
    tags.setText(doc.getTagsString() == null ? tags.getText() : doc.getTagsString());
    description.setText(doc.getDescription() == null
        ? description.getText() : doc.getDescription());
    isbn.setText(doc.getISBN());
    publishYear.setText(doc.getPublishYear() == 0
        ? publishYear.getText() : Integer.toString(doc.getPublishYear()));
    type.getSelectionModel().select(doc.isThesis() ? 1 : 0);
    docCover.setImage(doc.getCover());
  }

  private void style() {
    Style.styleTitle(docIdLabel, 16);
    Style.styleTitle(docTitleLabel, 16);
    Style.styleTitle(authorLabel, 16);
    Style.styleTitle(publisherLabel, 16);
    Style.styleTitle(publishYearLabel, 16);
    Style.styleTitle(typeLabel, 16);
    Style.styleTitle(tagsLabel, 16);
    Style.styleTitle(descriptionLabel, 16);
    Style.styleTitle(isbnLabel, 16);
    Style.styleTitle(numberOfCopiesLabel, 16);
    Style.styleTitle(docContentLabel, 16);

    Style.styleTextField(docId, 500, 50, 16, false,  "");
    Style.styleTextField(docTitle, 500, 50, 16, false,  "");
    Style.styleTextField(author, 500, 50, 16, false,  "");
    Style.styleTextField(publisher, 500, 50, 16, false,  "");
    Style.styleTextField(publishYear, 500, 50, 16, false,  "");
    StyleForm.styleComboBox(type, 500, 50, 16, "");
    Style.styleTextField(tags, 500, 50, 16, false,  "");
    Style.styleTextArea(description, 500, 200, 16,  "");
    Style.styleTextField(isbn, 250, 50, 16, false,  "");
    Style.styleTextField(numberOfCopies, 500, 50, 16, false,  "");

    Style.styleRoundedSolidButton(docSelectBtn, Style.LIGHTGREEN, 150, 50, 16);
    Style.styleRoundedSolidButton(coverSelectBtn, Style.LIGHTGREEN, 150, 50, 16);
    Style.styleRoundedSolidButton(autoFillByIsbn, Style.LIGHTGREEN, 230, 50, 16);
    isbnGroup.setSpacing(20);
    Style.styleWrapText(docFileName, 300, 16);
    StyleForm.styleWarning(warningText);
    StyleForm.styleWarning(isbnAutoFillWarning);
    Style.styleImg(docCover, 400);
    coverGroup.setSpacing(10);
    docSelectGroup.setSpacing(50);
    docSelectGroup.setAlignment(Pos.CENTER);
    infoTable.setHgap(20);
    infoTable.setVgap(10);
    mainContent.setHgap(100);
    mainContent.setVgap(20);
    GridPane.setValignment(descriptionLabel, VPos.TOP);
    container.prefWidthProperty().bind(wrapper.widthProperty().subtract(50));
    ObjectBinding<Insets> padding = Bindings.createObjectBinding(() -> {
      double val;
      if (coverGroup.getWidth() == 0) {
        if (wrapper.getWidth() > 1250) {
          val = (wrapper.getWidth() - 1250) / 2;
        } else {
          val = (wrapper.getWidth() - 650) / 2;
        }
      } else {
        if (coverGroup.getWidth() + infoTable.getWidth() + 150 < wrapper.getWidth()) {
          val = (wrapper.getWidth() - coverGroup.getWidth() - infoTable.getWidth() - 150) / 2;
        } else {
          val = (wrapper.getWidth() - infoTable.getWidth() - 50) / 2;
        }
      }
      val = Math.max(val, 0);
      return new Insets(0, val, 0, val);
    }, wrapper.widthProperty());
    mainContent.paddingProperty().bind(padding);
    Style.styleRoundedSolidButton(cancel, 200, 50, 16);
    Style.styleRoundedSolidButton(save, Style.LIGHTGREEN, 200, 50, 16);
    btnGroup.setSpacing(50);
    btnGroup.setAlignment(Pos.CENTER);
    container.setSpacing(50);
    container.setAlignment(Pos.CENTER);
  }

  public ScrollPane getContent() {
    return wrapper;
  }
}