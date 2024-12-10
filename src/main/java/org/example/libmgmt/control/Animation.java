package org.example.libmgmt.control;

import javafx.scene.Scene;
import org.example.libmgmt.ui.builder.PageBuilder;
import org.example.libmgmt.ui.director.PageDirector;
import org.example.libmgmt.ui.page.Page;

/**
 * Handling page transition.
 */
public class Animation {
  /**
   * Play a transition on switching to login page.
   *
   * @param pageDirector Director
   * @param pageBuilder Builder
   * @param scene Scene
   */
  public static void transitionToLogin(PageDirector pageDirector,
                                       PageBuilder pageBuilder, Scene scene) {
    pageDirector.createLoginPage(pageBuilder);
    Page p = pageBuilder.build();
    scene.setRoot(p.getContainer());
  }

  /**
   * Play a transition on switching to main page.
   *
   * @param pageDirector Director
   * @param pageBuilder Builder
   * @param scene Scene
   */
  public static void transitionToMain(PageDirector pageDirector,
                                      PageBuilder pageBuilder, Scene scene) {
    pageDirector.createMainPage(pageBuilder);
    Page p = pageBuilder.build();
    scene.setRoot(p.getContainer());
  }

  /**
   * Play a transition on switching to sign-up page.
   *
   * @param pageDirector Director
   * @param pageBuilder Builder
   * @param scene Scene
   */
  public static void transitionToSignup(PageDirector pageDirector,
                                        PageBuilder pageBuilder, Scene scene) {
    pageDirector.createSignUpPage(pageBuilder);
    Page p = pageBuilder.build();
    scene.setRoot(p.getContainer());
  }

  /**
   * Play a transition on logging out.
   *
   * @param pageDirector Director
   * @param pageBuilder Builder
   * @param scene Scene
   */
  public static void logoutTransition(PageDirector pageDirector,
                                      PageBuilder pageBuilder, Scene scene) {
    transitionToLogin(pageDirector, pageBuilder, scene);
  }

  /**
   * Play a transition on switching to other sections in main page.
   */
  public static void switchMainBodyContent() {

  }
}
