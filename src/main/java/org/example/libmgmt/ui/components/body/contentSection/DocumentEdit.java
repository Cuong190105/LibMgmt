package org.example.libmgmt.ui.components.body.contentSection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.example.libmgmt.AppLogger;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleForm;

/**
 * A panel for edit document info or add new document.
 */
public class DocumentEdit {
  private final Document doc;
  private Label docIdLabel;
  private Label docTitleLabel;
  private Label authorLabel;
  private Label publisherLabel;
  private Label publishYearLabel;
  private Label typeLabel;
  private Label tagsLabel;
  private Label descriptionLabel;
  private Label isbnLabel;
  private Label docContentLabel;
  private TextField docId;
  private TextField docTitle;
  private TextField author;
  private TextField publisher;
  private TextField publishYear;
  private ComboBox<String> type;
  private TextField tags;
  private TextField description;
  private TextField isbn;
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
    doc = new Document();
    createPanel(false);
  }

  /**
   * Constructor for editing existing document.
   */
  public DocumentEdit(Document doc) {
    this.doc = doc;
    createPanel(true);
  }

  private void createPanel(boolean isEditSession) {
    docIdLabel = new Label("Mã số tài liệu:");
    docTitleLabel = new Label("Tên tài liệu:");
    authorLabel = new Label("Tác giả:");
    publisherLabel = new Label("Nhà xuất bản:");
    publishYearLabel = new Label("Năm xuất bản:");
    typeLabel = new Label("Loại tài liệu:");
    tagsLabel = new Label("Tag:");
    descriptionLabel = new Label("Mô tả:");
    isbnLabel = new Label("Mã ISBN:");
    docContentLabel = new Label("File tài liệu:");

    docId = new TextField();
    docTitle = new TextField(doc.getTitle());
    author = new TextField(doc.getAuthor());
    publisher = new TextField(doc.getPublisher());
    publishYear = new TextField();
    type = new ComboBox<>(FXCollections.observableArrayList("Sách", "Luận văn"));
    type.setPromptText("- Chọn thể loại -");
    tags = new TextField(String.join(", ", doc.getTags()));
    description = new TextField(doc.getDescription());
    isbn = new TextField(doc.getISBN());
    if (isEditSession) {
      docId.setText(Integer.toString(doc.getDocID()));
      publishYear.setText(Integer.toString(doc.getPublishYear()));
      type.getSelectionModel().select(doc.isThesis() ? 1 : 0);
    }

    docSelectBtn = new Button("Chọn tài liệu");
    docFileName = new Text();
    docSelectGroup = new HBox(docSelectBtn, docFileName);
    docSelect = new FileChooser();
    docSelect.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
    infoTable = new GridPane();
    infoTable.addColumn(0, docIdLabel, docTitleLabel, authorLabel, publisherLabel,
        publishYearLabel, typeLabel, tagsLabel, descriptionLabel, isbnLabel, docContentLabel);
    infoTable.addColumn(1, docId, docTitle, author, publisher, publishYear, type,
        tags, description, isbn, docSelectGroup);

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
    btnGroup = new HBox(cancel, save);
    container = new VBox(mainContent, btnGroup);
    wrapper = new ScrollPane(container);
    setFunction();
    style();
  }

  private void setFunction() {
    warningText.setVisible(false);
    docSelectBtn.setOnMouseClicked(_ -> {
      docFile = docSelect.showOpenDialog(UIHandler.getStage());
      if (docFile != null) {
        String name = docFile.getAbsolutePath();
        docFileName.setText(name.substring(name.lastIndexOf("\\") + 1));
      }
    });

    coverSelectBtn.setOnMouseClicked(_ -> {
      coverFile = coverSelect.showOpenDialog(UIHandler.getStage());
      try {
        if (coverFile != null) {
          FileInputStream file = new FileInputStream(coverFile);
          docCover.setImage(new Image(file));
          file.close();
        }
      } catch (FileNotFoundException e) {
        warningText.setVisible(true);
      } catch (IOException e) {
        AppLogger.log(e);
      }
    });

    cancel.setOnMouseClicked(_ -> {
      UIHandler.openDocDetail(doc);
    });

    save.setOnMouseClicked(_ -> {
      doc.setTitle(docTitle.getText());
      doc.setAuthor(author.getText());
      doc.setPublisher(publisher.getText());
      doc.setPublishYear(Integer.parseInt(publishYear.getText()));
      doc.setThesis(type.getSelectionModel().getSelectedIndex() == 1);
      doc.setTags(Arrays.asList(tags.getText().split(", ")));
      doc.setDescription(description.getText());
      doc.setISBN(isbn.getText());
      try {
        if (coverFile != null) {
          FileInputStream file = new FileInputStream(coverFile);
          doc.setCover(new Image(file));
        }
      } catch (FileNotFoundException e) {
        AppLogger.log(e);
      }
      UIHandler.openDocDetail(doc);
    });

    container.setOnMouseClicked(_ -> {
      System.out.println(wrapper.getWidth() + " " + container.getWidth() + " " + mainContent.getPadding().toString());
    });
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
    Style.styleTitle(docContentLabel, 16);

    Style.styleTextField(docId, 500, 50, 16, "");
    Style.styleTextField(docTitle, 500, 50, 16, "");
    Style.styleTextField(author, 500, 50, 16, "");
    Style.styleTextField(publisher, 500, 50, 16, "");
    Style.styleTextField(publishYear, 500, 50, 16, "");
    StyleForm.styleComboBox(type, 500, 50, 16, "");
    Style.styleTextField(tags, 500, 50, 16, "");
    Style.styleTextField(description, 500, 50, 16, "");
    Style.styleTextField(isbn, 500, 50, 16, "");

    Style.styleRoundedSolidButton(docSelectBtn, Style.LIGHTGREEN, 150, 50, 16);
    Style.styleRoundedSolidButton(coverSelectBtn, Style.LIGHTGREEN, 150, 50, 16);
    Style.styleWrapText(docFileName, 300, 16);
    StyleForm.styleWarning(warningText);
    Style.styleImg(docCover, 400);
    coverGroup.setSpacing(10);
    docSelectGroup.setSpacing(50);
    docSelectGroup.setAlignment(Pos.CENTER);
    infoTable.setHgap(20);
    infoTable.setVgap(10);
    mainContent.setHgap(100);
    mainContent.setVgap(20);
    container.prefWidthProperty().bind(wrapper.widthProperty().subtract(50));
    ObjectBinding<Insets> padding = Bindings.createObjectBinding(() -> {
      double val;
      if (coverGroup.getWidth() == 0) {
        if (wrapper.getWidth() > 1200) {
          val = (wrapper.getWidth() - 1200) / 2;
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