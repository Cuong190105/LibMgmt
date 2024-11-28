package org.example.libmgmt.ui.director;

import org.example.libmgmt.ui.builder.HeaderBuilder;
import org.example.libmgmt.ui.page.PageType;

/**
 * A directer instance which tells builder to build header.
 */
public class HeaderDirector {
  /**
   * Displays a fullscreen logo on startup.
   *
   * @param headerBuilder Builder.
   */
  public void createStartupLogo(HeaderBuilder headerBuilder) {
    headerBuilder.reset();
    headerBuilder.setType(PageType.STARTUP_PAGE);
    headerBuilder.style();
  }

  /**
   * Displays a logo on top of login/signup form.
   *
   * @param headerBuilder Builder.
   */
  public void createLoginLogo(HeaderBuilder headerBuilder) {
    headerBuilder.reset();
    headerBuilder.setType(PageType.LOGIN_PAGE);
    headerBuilder.style();
  }

  /**
   * Creates a functional ribbon header to navigate around the app.
   *
   * @param headerBuilder Builder.
   */
  public void createMainPageHeader(HeaderBuilder headerBuilder) {
    headerBuilder.reset();
    headerBuilder.setType(PageType.MAIN_PAGE);
    headerBuilder.setControl();
    headerBuilder.style();
  }
}
