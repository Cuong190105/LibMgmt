package org.example.libmgmt.DB;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class Feedback {
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
  private int number;
  private Date date;
  private String sender;
  private String content;
  private boolean important;

  /**
   * Default constructor for testing.
   */
  public Feedback() {
    number = 123;
    date = new Date(1733480231027l);
    sender = "ABC";
    content = "Video là một cách mạnh mẽ để giúp bạn chứng minh quan điểm của bạn. "
            + "Khi bấm Video Trực tuyến, bạn có thể dán mã nhúng dành cho video bạn muốn thêm. "
            + "Bạn cũng có thể nhập từ khóa để tìm kiếm trực tuyến video phù hợp với tài liệu của bạn nhất. "
            + "Để giúp tài liệu của bạn trông như được tạo thật chuyên nghiệp, Word cung cấp thiết kế đầu trang, chân trang, trang bìa và hộp văn bản bổ sung lẫn nhau. "
            + "Ví dụ, bạn có thể thêm một trang bìa, tiêu đề và thanh bên phù hợp. "
            + "Hãy bấm Chèn rồi chọn thành phần bạn muốn từ các bộ sưu tập khác nhau. "
            + "Chủ đề và kiểu cũng giúp phối hợp tài liệu. "
            + "Khi bạn bấm Thiết kế và chọn Chủ đề mới, các ảnh, biểu đồ và đồ họa SmartArt sẽ thay đổi để khớp với chủ đề mới của bạn. "
            + "Khi bạn áp dụng kiểu, các đầu đề của bạn sẽ thay đổi để phù hợp với chủ đề mới. "
            + "Tiết kiệm thời gian trong Word với các nút mới hiển thị tại nơi bạn cần.";
  }

  /**
   * Complete constructor.
   * @param number Feedback number.
   * @param date Feedback date.
   * @param sender Sender.
   * @param content Feedback content.
   * @param important Whether this comment is important or not.
   */
  public Feedback(int number, Date date, String sender, String content, boolean important) {
    this.number = number;
    this.date = date;
    this.sender = sender;
    this.content = content;
    this.important = important;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public Date getDate() {
    return date;
  }

  public String getDateString() {
    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    return f.format(date);
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public boolean isImportant() {
    return important;
  }

  public void setImportant(boolean important) {
    this.important = important;
  }

  public void updateImportant(boolean important) {
    this.important = important;
    FeedbackDAO.getInstance().updateImportant(this);
  }
}
