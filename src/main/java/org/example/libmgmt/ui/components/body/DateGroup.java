package org.example.libmgmt.ui.components.body;

import java.util.concurrent.Callable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import org.example.libmgmt.control.Validator;
import org.example.libmgmt.ui.style.Style;
import org.example.libmgmt.ui.style.StyleForm;

/**
 * A group to display and input date.
 */
public class DateGroup {
  private TextField dayField;
  private ComboBox<String> monthField;
  private TextField yearField;
  private GridPane dateContainer;

  /**
   * Default constructor.
   */
  public DateGroup() {
    setDateGroup("", "", "");
  }

  /**
   * Constructor with predefined date.
   *
   * @param sqlDate preset date.
   */
  public DateGroup(String sqlDate) {
    String year = sqlDate.substring(0, 4);
    String month = sqlDate.substring(5 + (sqlDate.charAt(5) == '0' ? 1 : 0), 7);
    String day = sqlDate.substring(8, 10);
    setDateGroup(day, month, year);
  }

  private void setDateGroup(String day, String month, String year) {
    dayField = new TextField(day);
    monthField = new ComboBox<>();
    monthField.getItems().addAll(
        "Tháng 1",
        "Tháng 2",
        "Tháng 3",
        "Tháng 4",
        "Tháng 5",
        "Tháng 6",
        "Tháng 7",
        "Tháng 8",
        "Tháng 9",
        "Tháng 10",
        "Tháng 11",
        "Tháng 12"
    );
    if (!month.isEmpty()) {
      monthField.getSelectionModel().select("Tháng " + month);
    }
    yearField = new TextField(year);
    dateContainer = new GridPane();
    dateContainer.add(dayField, 0, 0);
    dateContainer.add(monthField, 1, 0);
    dateContainer.add(yearField, 2, 0);
  }

  private void trimText(TextField tf, int limit) {
    if (tf.getLength() > limit) {
      tf.setText(tf.getText().substring(0, limit));
      tf.positionCaret(limit);
    }
  }

  /**
   * Sets function for each field.
   *
   * @param func Additional function for the group.
   */
  public void setFunction(Callable<Integer> func) {
    monthField.setOnAction(_ -> {
      monthField.show();
      try {
        func.call();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });

    dayField.setOnKeyTyped(_ -> {
      trimText(dayField, 2);
      try {
        func.call();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
    yearField.setOnKeyTyped(_ -> {
      trimText(yearField, 4);
      try {
        func.call();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    });
  }

  public String getDay() {
    return dayField.getText();
  }

  public String getMonth() {
    String mth = monthField.getValue();
    if (mth == null) {
      return "";
    }
    mth = mth.substring(mth.indexOf(" ") + 1);
    if (mth.length() < 2) {
      mth = "0" + mth;
    }
    return mth;
  }

  public String getYear() {
    return yearField.getText();
  }

  /**
   * Turns warning border on or off.
   *
   * @param b True to show, false to hide border.
   */
  public void toggleWarningBorder(boolean b) {
    if (b) {
      Style.setFieldWarningBorder(dayField);
      Style.setFieldWarningBorder(monthField);
      Style.setFieldWarningBorder(yearField);
    } else {
      dayField.setBorder(null);
      StyleForm.toggleNormalBorder(monthField);
      yearField.setBorder(null);
    }
  }

  /**
   * Styles the group with a custom max width.
   *
   * @param size Max width.
   */
  public void style(double size) {
    Style.styleTextField(dayField, size, 50, 16, false, "Ngày");
    StyleForm.styleComboBox(monthField, size, 50, 16, "Tháng");
    Style.styleTextField(yearField, size, 50, 16, false, "Năm");

    ColumnConstraints c0 = new ColumnConstraints();
    ColumnConstraints c1 = new ColumnConstraints();
    ColumnConstraints c2 = new ColumnConstraints();
    c0.setPercentWidth(25);
    c1.setPercentWidth(40);
    c2.setPercentWidth(35);
    dateContainer.getColumnConstraints().addAll(c0, c1, c2);
    dateContainer.setMaxWidth(size);
    dateContainer.setHgap(10);
  }



  public GridPane getContent() {
    return dateContainer;
  }

  public boolean validateDate() {
    return Validator.isValidDate(getDay(), getMonth(), getYear());
  }
}
