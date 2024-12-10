package org.example.libmgmt.ui.components.body.searchPanel;

import java.util.List;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.libmgmt.AppLogger;
import org.example.libmgmt.DB.User;
import org.example.libmgmt.DB.UserDAO;
import org.example.libmgmt.ui.components.body.contentSection.MemberListView;

/**
 * Search panel for member gallery.
 */
public class MemberSearchPanel extends SearchPanel {
  public static final int VIEW_ALL = 0;
  public static final int VIEW_READER = 1;
  public static final int VIEW_LIBRARIAN = 2;
  public static final int SORTBY_ID = 0;
  public static final int SORTBY_NAME = 1;
  public static final int SORT_LARGEST_FIRST = 4;
  private final SearchService searchService = new SearchService();
  private int currentViewOption;
  private int currentSortOption;
  private String currentKeyword;
  private final MemberListView member;

  /**
   * Constructor.
   *
   * @param member Target member gallery
   */
  public MemberSearchPanel(MemberListView member) {
    this.member = member;
    currentKeyword = "";
    currentSortOption = SORTBY_ID;
    currentViewOption = VIEW_ALL;
    addViewOption();
    addSortOption();
    setFunction();
  }

  private void searchMember() {
    member.toggleLoadingRing(true);
    searchService.restart();
  }

  @Override
  public void setFunction() {
    clearSearch.setVisible(false);
    searchMember();

    clearSearch.setOnMouseClicked(_ -> {
      searchBox.setText("");
      currentKeyword = "";
      searchMember();
      clearSearch.setVisible(false);
      searchBox.requestFocus();
    });

    searchBox.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent -> {
      if (!keyEvent.getCode().equals(KeyCode.ENTER) || searchBox.getText().isEmpty()) {
        return;
      }
      currentKeyword = searchBox.getText();
      clearSearch.setVisible(true);
      searchMember();
    });

    viewOption.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
      if (viewOption.isFocused()) {
        currentViewOption = newVal.intValue();
        searchMember();
      }
    });

    sortingOption.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> {
      if (sortingOption.isFocused()) {
        int selection = newVal.intValue();
        currentSortOption = selection / 2 + (selection % 2) * SORT_LARGEST_FIRST;
        searchMember();
      }
    });
  }

  @Override
  public void addViewOption() {
    viewOption.setItems(FXCollections.observableArrayList(
        "Tất cả", "Người đọc", "Thủ thư"
    ));
    viewOption.getSelectionModel().selectFirst();
  }

  @Override
  public void addSortOption() {
    sortingOption.getItems().addAll(
        "Mã số - Tăng dần", "Mã số - Giảm dần",
        "Tên - A đến Z", "Tên - Z đến A"
    );
    sortingOption.getSelectionModel().selectFirst();
  }

  private class SearchService extends Service<Void> {

    @Override
    protected Task<Void> createTask() {
      return new Task<>() {
        @Override
        protected Void call() {
          try {
            List<User> result = UserDAO.getInstance()
                .searchUser(currentKeyword, currentViewOption, currentSortOption);
            member.loadSearchResults(1, result);
          } catch (Exception e) {
            AppLogger.log(e);
            member.loadSearchResults(-1, null);
          }
          Platform.runLater(() -> member.showSearchResults());
          return null;
        }
      };
    }
  }
}
