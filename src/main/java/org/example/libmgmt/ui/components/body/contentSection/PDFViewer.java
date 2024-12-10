package org.example.libmgmt.ui.components.body.contentSection;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashSet;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.DocumentDAO;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.ui.style.Style;

/**
 * Create a page to view PDF file.
 */
public class PDFViewer extends Content {
  private final VBox docContainer;
  private final ScrollPane wrapper;
  private PDDocument docFile;
  private DoubleBinding pageHeight;
  private double originalPageHeight;
  private double originalPageWidth;
  private double scaling;
  private final HBox allContainer;
  private final VBox bookmarkList;
  private final ScrollPane bookmarkWrapper;
  private final HashSet<Integer> bookmarks;
  private final Image delImg;
  private final Label bookmarkLabel = new Label("DẤU TRANG");
  private final Text noBookmark = new Text("Chưa có dấu trang.\n"
          + "Thêm dấu trang bằng nút \"Dấu trang\" trên thanh công cụ.");

  /**
   * Constructor.
   *
   * @param doc The document displayed.
   */
  public PDFViewer(Document doc) {
    super(false);
    docContainer = new VBox();
    bookmarkList = new VBox(bookmarkLabel, noBookmark);
    bookmarkWrapper = new ScrollPane(bookmarkList);
    wrapper = new ScrollPane(docContainer);
    allContainer = new HBox(bookmarkWrapper, wrapper);
    bookmarks = new HashSet<>();
    scaling = 1;
    delImg = new Image(
            LibMgmt.class.getResourceAsStream("img/pdfcontroller/delete.png"));
    Task<Void> loadDocument = new Task<Void>() {
      @Override
      protected Void call() throws Exception {
        try {
//          load local pdf with loadPDF(File);
//          File f = new File("E:\\Multimedia\\Docs\\01.pdf");
//          docFile = Loader.loadPDF(f);

//           Load content from server using InputStream, or buffer byte[].
//          InputStream docStream;
//          docFile = Loader.loadPDF(docStream);
          DocumentDAO documentDAO = DocumentDAO.getInstance();
          Blob content = documentDAO.getContent(doc.getDocID());
          byte[] btt = blobToByteArray(content);
          docFile = Loader.loadPDF(btt);
          PDFRenderer renderer = new PDFRenderer(docFile);
          for (int i = 0; i < docFile.getNumberOfPages(); i++) {
            BufferedImage page = renderer.renderImage(i);
            ImageView img = new ImageView(SwingFXUtils.toFXImage(page, null));
            img.setSmooth(true);
            originalPageHeight = page.getHeight();
            originalPageWidth = page.getWidth();
            Platform.runLater(() -> {
              docContainer.getChildren().add(new Group(img));
            });
          }
          Node img = ((Group) docContainer.getChildren().getFirst()).getChildren().getFirst();
          pageHeight = Bindings.multiply(img.scaleYProperty(), originalPageHeight);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
        return null;
      }
    };
    Thread t = new Thread(loadDocument);
    t.setDaemon(true);
    t.start();
    setFunction();
    style();
  }

  private void setFunction() {
    bookmarkWrapper.setVisible(false);
    bookmarkWrapper.setManaged(false);
    docContainer.setOnMouseClicked(e -> {
      System.out.println("Max: " + wrapper.getVmax());
    });
  }

  private void style() {
    docContainer.setSpacing(20);
    docContainer.setAlignment(Pos.CENTER);
    docContainer.setPadding(new Insets(0, 0, 20, 0));
    docContainer.prefWidthProperty().bind(wrapper.widthProperty().subtract(30));
    wrapper.vmaxProperty().bind(docContainer.heightProperty().subtract(wrapper.heightProperty()));
    HBox.setHgrow(wrapper, Priority.ALWAYS);

    Style.styleTitle(bookmarkLabel, 24);
    Style.styleWrapText(noBookmark, 280, 16);
    bookmarkList.setPrefWidth(300);
    bookmarkList.setMaxWidth(Region.USE_PREF_SIZE);
    bookmarkList.setSpacing(10);
    bookmarkList.setPadding(new Insets(10));
  }

  public ScrollPane getDocViewer() {
    return wrapper;
  }

  public HBox getContent() {
    return allContainer;
  }

  public int getTotalPage() {
    return docFile == null ? -1 : docFile.getNumberOfPages();
  }

  /**
   * Get the current displaying page on the screen.
   * A displaying page is a page that has the largest visible height.
   * In case there are multiple pages that has the same visible height,
   * the top page will be selected.
   *
   * @return the order number of current displaying page.
   */
  public int getCurrentPage() {
    if (pageHeight == null) {
      return 1;
    }
    double y = wrapper.getVvalue();
    int topBorder = (int) (y / (pageHeight.get() + 20));
    if (Double.compare(y - topBorder * (pageHeight.get() + 20), 1e-6) <= 0) {
      return topBorder + 1;
    }
    double currentPageVisibleHeight = (topBorder + 1) * (pageHeight.get() + 20) - 20 - y;
    double nextPageVisibleHeight = wrapper.getHeight() - 20 - currentPageVisibleHeight;
    return currentPageVisibleHeight > nextPageVisibleHeight ? topBorder + 1 : topBorder + 2;
  }

  /**
   * Move the viewport to the selected page.
   *
   * @param pageNumber Target page.
   */
  public void switchToPage(int pageNumber) {
    wrapper.setVvalue((pageNumber - 1) * (pageHeight.get() + 20));
  }

  /**
   * Scale elements.
   *
   * @param v Scaling coefficient.
   */
  public void scaleViewport(double v) {
    double orgY = (wrapper.getVvalue() + wrapper.getHeight() / 2) / scaling * v - (wrapper.getHeight() / 2);
    for (Node p : docContainer.getChildren()) {
      ((Group) p).getChildren().getFirst().setScaleX(v);
      ((Group) p).getChildren().getFirst().setScaleY(v);
    }
    orgY = Math.max(orgY, 0);
    double finalOrgY = orgY;
    docContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
      wrapper.setVvalue(finalOrgY);
    });
    scaling = v;
  }

  /**
   * return the scaling level to make a page fit its width/height to viewport.
   *
   * @param isFitHeight Determine whether the page should fit to width or height.
   * @return Scaling level.
   */
  public double getScalingLevelToFit(boolean isFitHeight) {
    if (isFitHeight) {
      return wrapper.getHeight() / originalPageHeight;
    } else {
      return (wrapper.getWidth() - 30) / originalPageWidth;
    }
  }

  /**
   * Add current page to bookmark.
   */
  public void addBookmark() {
    int cur = getCurrentPage();
    if (bookmarks.contains(cur)) {
      return;
    }
    if (bookmarks.isEmpty()) {
      bookmarkList.getChildren().remove(noBookmark);
    }
    bookmarks.add(cur);
    Text label = new Text("Trang " + cur);
    Button del = new Button();
    del.setTooltip(new Tooltip("Xóa dấu trang"));
    del.setGraphic(new ImageView(delImg));
    HBox mark = new HBox(label, del);

    Style.styleRoundedSolidButton(del, 30, 30, 16);
    Style.styleWrapText(label, 190, 16);
    mark.setPrefWidth(230);
    mark.setAlignment(Pos.CENTER);
    mark.setSpacing(10);
    mark.setPadding(new Insets(10));
    mark.setBackground(Background.EMPTY);
    Style.styleHoverEffect(mark);
    if (bookmarkList.getChildren().size() == 1) {
      bookmarkList.getChildren().add(mark);
    } else {
      for (int i = 1; i < bookmarkList.getChildren().size(); i++) {
        String t = ((Text) (
                (HBox) bookmarkList.getChildren().get(i)).getChildren().getFirst()
        ).getText();
        int end = t.indexOf(" ", 6);
        int page = Integer.parseInt(t.substring(6, end != -1 ? end : t.length()));
        if (page > cur) {
          bookmarkList.getChildren().add(i, mark);
          break;
        } else if (i + 1 == bookmarkList.getChildren().size()) {
          bookmarkList.getChildren().add(mark);
          break;
        }
      }
    }
    mark.setOnMouseClicked(e -> switchToPage(cur));

    del.setOnMouseClicked(e -> {
      bookmarkList.getChildren().remove(mark);
      bookmarks.remove(cur);
      if (bookmarks.isEmpty()) {
        bookmarkList.getChildren().add(noBookmark);
      }
    });
  }

  public void toggleBookmarkList() {
    bookmarkWrapper.setManaged(!bookmarkWrapper.isVisible());
    bookmarkWrapper.setVisible(!bookmarkWrapper.isVisible());
  }

  public static byte[] blobToByteArray(Blob blob) throws IOException, SQLException {
    byte[] buffer = new byte[(int) blob.length()]; // Create buffer to hold the content of the Blob
    InputStream inputStream = blob.getBinaryStream(); // Get the input stream of the Blob
    inputStream.read(buffer); // Read the content into the buffer
    inputStream.close(); // Close the InputStream

    return buffer; // Return the byte array containing the content of the Blob
  }
}
