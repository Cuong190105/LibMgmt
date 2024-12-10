package org.example.libmgmt.ui.builder;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.example.libmgmt.LibMgmt;
import org.example.libmgmt.control.UIHandler;
import org.example.libmgmt.ui.components.body.Body;
import org.example.libmgmt.ui.components.body.BodyType;
import org.example.libmgmt.ui.components.body.contentSection.Content;
import org.example.libmgmt.ui.style.Style;

import java.util.Objects;

/**
 *  A builder for page body.
 */
public class BodyBuilder implements BodyBuilderInterface, GeneralBuilder {
  private final Image backBtnImg;
  private BodyType bodyType;
  private HBox titleGroup;
  private Button backBtn;
  private Label sectionTitle;
  private HBox subsectionList;
  private GridPane searchPanel;
  private Content content;
  private VBox container;

  /**
   * Constructor.
   */
  public BodyBuilder() {
    backBtnImg = new Image(
        LibMgmt.class.getResourceAsStream("img/backArrow.png"));
    reset();
  }

  @Override
  public void reset() {
    bodyType = null;
    content = null;
    subsectionList = new HBox();
    sectionTitle = new Label();
    searchPanel = new GridPane();
    container = new VBox();
    backBtn = new Button();
    titleGroup = new HBox(backBtn, sectionTitle);
    backBtn.setGraphic(new ImageView(backBtnImg));
    backBtn.setOnMouseClicked(e -> UIHandler.backToLastPage());
    backBtn.setVisible(false);
    backBtn.setManaged(false);
  }

  @Override
  public void setType(BodyType bodyType) {
    this.bodyType = bodyType;
  }

  @Override
  public void setTitle(String sectionTitle, boolean isSubPage) {
    this.sectionTitle.setText(sectionTitle.toUpperCase());
    if (isSubPage) {
      backBtn.setVisible(true);
      backBtn.setManaged(true);
    }
  }

  @Override
  public void setSubsection(HBox subsectionList) {
    this.subsectionList = subsectionList;
  }

  @Override
  public void setSearchPanel(GridPane searchPanel) {
    this.searchPanel = searchPanel;
  }

  @Override
  public void setContent(Content content) {
    this.content = content;
  }

  @Override
  public void style() {
    titleGroup.setSpacing(20);
    Style.styleRoundedSolidButton(backBtn, 30, 30, 16);
    content.getContent().getStylesheets().add(
        LibMgmt.class.getResource("viewport.css").toExternalForm());
    VBox.setVgrow(content.getContent(), Priority.ALWAYS);

    container.setSpacing(20);
    if (bodyType != BodyType.PDF_VIEWER) {
      Style.styleTitle(sectionTitle, 40);
      container.setPadding(new Insets(25));
      Style.styleShadowBorder(container);
      BackgroundFill bgF = new BackgroundFill(Color.WHITE, Style.BIG_CORNER, Insets.EMPTY);
      container.setBackground(new Background(bgF));
    }
    switch (bodyType) {
      case AUTHENTICATION -> {
        content.getContent().setMaxWidth(Region.USE_PREF_SIZE);
        HBox.setHgrow(sectionTitle, Priority.ALWAYS);
        titleGroup.setAlignment(Pos.CENTER);
        container.setAlignment(Pos.CENTER);
        sectionTitle.setAlignment(Pos.CENTER);
        container.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
      }
      case MAIN -> {
        titleGroup.setAlignment(Pos.CENTER_LEFT);
        content.getContent().prefWidthProperty().bind(container.widthProperty());
        container.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        container.setAlignment(Pos.TOP_LEFT);
      }
      case PDF_VIEWER -> {
        titleGroup.setManaged(false);
      }
    }
  }

  @Override
  public Body build() {
    container.getChildren().add(titleGroup);
    if (!subsectionList.getChildren().isEmpty()) {
      container.getChildren().add(subsectionList);
    }
    if (!searchPanel.getChildren().isEmpty()) {
      container.getChildren().add(searchPanel);
    }
    container.getChildren().add(content.getContent());

    return new Body(bodyType, titleGroup, subsectionList, searchPanel,
        content, container);
  }
}