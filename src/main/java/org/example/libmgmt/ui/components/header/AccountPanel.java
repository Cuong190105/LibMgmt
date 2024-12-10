package org.example.libmgmt.ui.components.header;

import javafx.scene.control.Button;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.control.UserControl;
import org.example.libmgmt.ui.style.Style;

/**
 * A quick panel that provides user with some options to see or change their info.
 */
public class AccountPanel extends QuickPanel {
  private final Button accountChange;
  private final Button userinfoChange;
  private final Button logout;

  /**
   * Constructor.
   */
  public AccountPanel() {
    accountChange = new Button("Tài khoản");
    userinfoChange = new Button("Thông tin người dùng");
    logout = new Button("Đăng xuất");
    container.getChildren().addAll(accountChange, userinfoChange, logout);
    setFunction();
    style();
  }

  private void style() {
    Style.styleRoundedSolidButton(accountChange, 200, 30, 16);
    Style.styleRoundedSolidButton(userinfoChange, 200, 30, 16);
    Style.styleRoundedSolidButton(logout, 200, 30, 16);
    container.setPrefWidth(220);
    Style.styleShadowBorder(container);
  }

  private void setFunction() {
    accountChange.setOnMouseClicked(e -> {
      UIHandler.openAccountDetails();
    });

    userinfoChange.setOnMouseClicked(e -> {
      UIHandler.openMemberDetails(UserControl.getUser());
    });

    logout.setOnMouseClicked(e -> {
      UserControl.logout();
      UIHandler.switchToLogin();
    });
  }
}
