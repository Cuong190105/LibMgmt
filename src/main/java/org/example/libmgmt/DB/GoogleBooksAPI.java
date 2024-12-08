package org.example.libmgmt.DB;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.scene.image.Image;

public class GoogleBooksAPI {

  private static final String API_KEY = "AIzaSyCQfk7qiYWtxtzfOQu5dJUbfSZUE0Y-juk";

  /**
   * Convert ["a","b","c"] to a, b, c.
   * @param text format ["a","b","c"]
   */
  private static String separate(String text) {
    // notice: endIndex is exclusive
    text = text.substring(1, text.length() - 1); // exclude [ ]

    String[] tmp = text.split(",");
    StringBuilder ret = new StringBuilder();
    for (String i : tmp) {
      ret.append(i, 1, i.length() - 1).append(", "); // exclude " "
    }
    if (tmp.length != 0) {
      ret.setLength(ret.length() - 2);
    }
    return ret.toString();
  }

  public static String getApiAsString(String isbn) throws Exception {
    String endpoint = "https://www.googleapis.com/books/v1/volumes?q=isbn:" + isbn + "&key=" + API_KEY;
    System.out.println(endpoint);
    URL url = new URL(endpoint);
    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    conn.setRequestMethod("GET");

    // 200 means successfully connected
    if (conn.getResponseCode() == 200) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        response.append(line);
      }
      reader.close();
      return response.toString();
    } else {
      throw new Exception("Failed to fetch data. HTTP Code: " + conn.getResponseCode());
    }
  }

  public static Document getDocData(String isbn) throws Exception {
    String jsonResponse = getApiAsString(isbn);
    JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
    JsonObject volumeInfo = jsonObject.getAsJsonArray("items")
        .get(0)
        .getAsJsonObject()
        .getAsJsonObject("volumeInfo");

    String title = volumeInfo.get("title").getAsString();
    String authors = volumeInfo.has("authors") ? volumeInfo.getAsJsonArray("authors").toString() : null;
    if (authors != null) {
      authors = separate(authors);
    }
    String publisher = volumeInfo.has("publisher") ? volumeInfo.get("publisher").getAsString() : null;
    String publishedDate = volumeInfo.has("publishedDate") ? volumeInfo.get("publishedDate").getAsString() : null;
    String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : null;
    String tags = volumeInfo.has("categories") ? volumeInfo.getAsJsonArray("categories").toString() : null;
    if (tags != null) {
      tags = separate(tags);
    }
    Image cover = null;
    if (volumeInfo.has("imageLinks")) {
      JsonObject imageLinks = volumeInfo.getAsJsonObject("imageLinks");
      String imageUrl = imageLinks.has("thumbnail") ? imageLinks.get("thumbnail").getAsString() : null;
      cover = ImageUtil.downloadImage(imageUrl);
    }

    Document newDoc = new Document();
    newDoc.setTitle(title);
    newDoc.setAuthor(authors);
    newDoc.setPublisher(publisher);
    newDoc.setPublishYear(Integer.parseInt(publishedDate.substring(0, 4))); // 4 first character is year
    newDoc.setDescription(description);
    if (tags != null) {
      newDoc.setTags(Arrays.asList(tags.split(", ")));
    }
    newDoc.setCover(cover);
    newDoc.setISBN(isbn);
    return newDoc;
  }

  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    String isbn = sc.next();
    Document test = getDocData(isbn);
    System.out.println(test.print());
  }
}