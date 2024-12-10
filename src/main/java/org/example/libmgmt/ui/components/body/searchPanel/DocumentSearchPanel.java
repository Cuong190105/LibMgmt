package org.example.libmgmt.ui.components.body.searchPanel;

import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.libmgmt.AppLogger;
import org.example.libmgmt.DB.Document;
import org.example.libmgmt.DB.DocumentDAO;
import org.example.libmgmt.control.UserControl;
import org.example.libmgmt.ui.components.body.contentSection.DocumentLibrary;

/**
 * Search panel for document gallery.
 */
public class DocumentSearchPanel extends SearchPanel {
  public static final int VIEW_ALL = 0;
  public static final int VIEW_BOOK = 1;
  public static final int VIEW_THESIS = 2;
  public static final int SORTBY_ID = 0;
  public static final int SORTBY_NAME = 1;
  public static final int SORTBY_PUBLISHYEAR = 2;
  public static final int SORT_LARGEST_FIRST = 4;
  private int currentViewOption;
  private int currentSortOption;
  private final DocumentLibrary library;
  private final Service<Void> searchService = new SearchService();
  private String currentKeyword;

  /**
   * Constructor.
   *
   * @param library Target library gallery.
   */
  public DocumentSearchPanel(DocumentLibrary library) {
    this.library = library;
    currentKeyword = "";
    addViewOption();
    addSortOption();
    setFunction();
  }

  @Override
  public void setFunction() {
    clearSearch.setVisible(false);
    searchDocument();

    clearSearch.setOnMouseClicked(_ -> {
      searchBox.setText("");
      currentKeyword = "";
      searchDocument();
      clearSearch.setVisible(false);
      searchBox.requestFocus();
    });

    searchBox.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
      if (!keyEvent.getCode().equals(KeyCode.ENTER) || searchBox.getText().isEmpty()) {
        return;
      }
      currentKeyword = searchBox.getText();
      clearSearch.setVisible(true);
      searchDocument();
    });

    viewOption.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
      if (viewOption.isFocused()) {
        currentViewOption = newVal.intValue();
        searchDocument();
      }
    });

    sortingOption.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
      if (sortingOption.isFocused()) {
        int selection = newVal.intValue();
        currentSortOption = selection / 2 + (selection % 2) * SORT_LARGEST_FIRST;
        searchDocument();
      }
    });
  }

  private void searchDocument() {
    if (currentKeyword.isEmpty() && !UserControl.getUser().isLibrarian()) {
      library.loadSearchResults(0, null, 0, 0);
      library.showSearchResults();
      return;
    }
    library.toggleLoadingRing(true);
    searchService.restart();
  }

  @Override
  public void addViewOption() {
    viewOption.setItems(FXCollections.observableArrayList(
        "Tất cả",   "Sách", "Luận án"
    ));
  }

  @Override
  public void addSortOption() {
    sortingOption.getItems().addAll(
        "Mã số - Tăng dần", "Mã số - Giảm dần",
        "Tên - A đến Z", "Tên - Z đến A",
        "Năm xuất bản - Cũ nhất", "Năm xuât bản - Mới nhất"
    );
  }

  private class SearchService extends Service<Void> {
    @Override
    protected Task<Void> createTask() {
      return new Task<Void>() {
        @Override
        protected Void call() throws Exception {
          try {
            List<Document> result = DocumentDAO.getInstance()
                .searchDocKey(currentKeyword);
            library.loadSearchResults(1, result, currentViewOption, currentSortOption);
          } catch (Exception e) {
            AppLogger.log(e);
            library.loadSearchResults(-1, null, 0, 0);
          }
          Platform.runLater(library::showSearchResults);
          return null;
        }
      };
    }
  }
}
