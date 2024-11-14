package org.example.libmgmt.control;

import javafx.scene.Scene;
import org.example.libmgmt.ui.builder.PageBuilder;
import org.example.libmgmt.ui.director.PageDirector;
import org.example.libmgmt.ui.page.Page;

public class Animation {
    public static void transitionToLogin(PageDirector pageDirector,
                                         PageBuilder pageBuilder, Scene scene) {
        pageDirector.createLoginPage(pageBuilder);
        Page p = pageBuilder.build();
        scene.setRoot(p.getContainer());
    }

    public static void transitionToMain(PageDirector pageDirector,
                                        PageBuilder pageBuilder, Scene scene) {
        pageDirector.createMainPage(pageBuilder);
        Page p = pageBuilder.build();
        scene.setRoot(p.getContainer());
    }

    public static void transitionToSignup(PageDirector pageDirector,
                                          PageBuilder pageBuilder, Scene scene) {
        pageDirector.createSignUpPage(pageBuilder);
        Page p = pageBuilder.build();
        scene.setRoot(p.getContainer());
    }

    public static void logoutTransition(PageDirector pageDirector,
                                        PageBuilder pageBuilder, Scene scene) {
        transitionToLogin(pageDirector, pageBuilder, scene);
    }

    public static void switchMainBodyContent() {

    }
}
