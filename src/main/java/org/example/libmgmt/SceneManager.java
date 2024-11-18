package org.example.libmgmt;

public class SceneManager {
    private static Page page;
    private SceneManager() {
        page = new StartupPage();
    }

    public static void init() {
        if (page == null) {
            new SceneManager();
        }
    }

    public static void switchPage(Page next) {
        page = next;
    }

    public static Page getPage() {
        return page;
    }
}
