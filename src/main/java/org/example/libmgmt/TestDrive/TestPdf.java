package org.example.libmgmt.TestDrive;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import javafx.embed.swing.SwingFXUtils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.scene.control.ScrollPane;

public class TestPdf extends Application {

    @Override
    public void start(Stage primaryStage) {
        String filePath = "C:/Users/DELL/Downloads/BTDS_Tuan4_DoTrungKien.pdf"; // Update this path
        VBox vbox = new VBox();

        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            // Create a ScrollPane for scrolling through pages
            ScrollPane scrollPane = new ScrollPane();
            VBox pagesBox = new VBox(); // Holds multiple pages
            scrollPane.setContent(pagesBox);
            scrollPane.setFitToWidth(true); // Fit the content to width
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // Always show vertical scrollbar
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Never show horizontal scrollbar

            for (int page = 0; page < document.getNumberOfPages(); page++) {
                // Render the page to BufferedImage
                BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(page, 150); // 150 DPI for quality

                // Convert BufferedImage to JavaFX Image
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);

                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(800); // Set a fixed width for the ImageView
                imageView.setPreserveRatio(true); // Maintain aspect ratio

                // Optional: Set max height to avoid overflowing the window
                imageView.setFitHeight(600); // Adjust as necessary

                pagesBox.getChildren().add(imageView);
            }

            vbox.getChildren().add(scrollPane);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(vbox, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("PDF Viewer");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
