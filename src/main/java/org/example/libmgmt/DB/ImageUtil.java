package org.example.libmgmt.DB;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageUtil {
  private ImageUtil() {
  }

  /**
   * Converts a JavaFX Image to an InputStream.
   *
   * @param image the JavaFX Image to convert.
   * @return InputStream representing the image data, or null if the image is null.
   */
  public static InputStream convertImageToInputStream(Image image) throws Exception {
    if (image == null) {
      return null;
    }

    BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    ImageIO.write(bufferedImage, "png", outputStream);

    return new ByteArrayInputStream(outputStream.toByteArray());
  }

  /**
   * Download and store Image
   * @param imageUrl https url to image from api
   * @return Image to store in
   */
  public static Image downloadImage(String imageUrl) throws Exception {
    URL url = new URL(imageUrl);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");
    InputStream inputStream = connection.getInputStream();
    return new Image(inputStream);
  }

}