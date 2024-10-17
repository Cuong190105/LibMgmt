package org.example.libmgmt.cli;

public class Main {
    public static void main() {
        //Initialize app
        SceneManager.init();

        //App loop
        while (SceneManager.getPage() != null) {
            SceneManager.getPage().show();
        }
    }
}
