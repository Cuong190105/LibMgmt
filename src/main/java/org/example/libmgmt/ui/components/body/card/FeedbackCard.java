package org.example.libmgmt.ui.components.body.card;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.example.libmgmt.ui.style.Style;

public class FeedbackCard {
    private static final Background BG_IMPORTANT = new Background(new BackgroundFill(
            Style.YELLOW, Style.SMALL_CORNER, Insets.EMPTY));
    private static final Background BG_STANDARD = new Background(new BackgroundFill(
            Style.LIGHTGREEN, Style.SMALL_CORNER, Insets.EMPTY));
    //    private Feedback fb;
    private boolean isImportant;
    private Label feedbackNumber;
    private Text from;
    private Text feedbackDate;
    private Text feedbackContent;
    private Button markAsImportant;
    private VBox container;

    public FeedbackCard() {
        isImportant = false;
        feedbackNumber = new Label("#012345");
        feedbackDate = new Text("23/06/2005");
        from = new Text("Người gửi: Lorem Ipsum");
        feedbackContent = new Text("Video là một cách mạnh mẽ để giúp bạn chứng minh quan điểm của bạn. Khi bấm Video Trực tuyến, bạn có thể dán mã nhúng dành cho video bạn muốn thêm. Bạn cũng có thể nhập từ khóa để tìm kiếm trực tuyến video phù hợp với tài liệu của bạn nhất. Để giúp tài liệu của bạn trông như được tạo thật chuyên nghiệp, Word cung cấp thiết kế đầu trang, chân trang, trang bìa và hộp văn bản bổ sung lẫn nhau. Ví dụ, bạn có thể thêm một trang bìa, tiêu đề và thanh bên phù hợp. Hãy bấm Chèn rồi chọn thành phần bạn muốn từ các bộ sưu tập khác nhau. Chủ đề và kiểu cũng giúp phối hợp tài liệu. Khi bạn bấm Thiết kế và chọn Chủ đề mới, các ảnh, biểu đồ và đồ họa SmartArt sẽ thay đổi để khớp với chủ đề mới của bạn. Khi bạn áp dụng kiểu, các đầu đề của bạn sẽ thay đổi để phù hợp với chủ đề mới. Tiết kiệm thời gian trong Word với các nút mới hiển thị tại nơi bạn cần. Để thay đổi cách thức ảnh phù hợp trong tài liệu của bạn, hãy bấm vào ảnh và nút dành cho tùy chọn bố trí xuất hiện kế bên ảnh.");
        markAsImportant = new Button("Đánh dấu là quan trọng");
        container = new VBox(feedbackNumber, feedbackDate, from, feedbackContent, markAsImportant);
        setFunction();
        style();
    }

    private void setFunction() {
        markAsImportant.setOnMouseClicked(_ -> {
            if (isImportant) {
                container.setBackground(BG_STANDARD);
                markAsImportant.setText("Đánh dấu là quan trọng");
            } else {
                container.setBackground(BG_IMPORTANT);
                markAsImportant.setText("Bỏ đánh dấu");
            }
            isImportant = !isImportant;
        });
    }

//    public FeedbackCard(Feedback fb) {
//        this.fb = fb;
//        isImportant = fb.isImportant();
//        feedbackNumber = new Label("#" + fb.getNumber());
//        feedbackDate = new Label(fb.getDate().toString());
//        from = new Label("Người gửi: " + fb.getSenderName());
//        feedbackContent = new Label(fb.getContent());
//        contentWrapper = new TextFlow(feedbackContent);
//        markAsImportant = new Button();
//        container = new VBox(feedbackNumber, feedbackDate, from, contentWrapper, markAsImportant);
//        setFunction();
//        style();
//    }

    private void style() {
        Style.styleTitle(feedbackNumber, 20);
        Style.styleText(from, 12);
        Style.styleText(feedbackDate, 12);
        Style.styleText(feedbackContent, 12);
        feedbackContent.wrappingWidthProperty().bind(container.widthProperty().subtract(50));
        Style.styleRoundedButton(markAsImportant, Style.DARKGREEN, 200, 50, 16);
        VBox.setMargin(markAsImportant, new Insets(20, 0, 0, 0));
        if (isImportant) {
            container.setBackground(BG_IMPORTANT);
        } else {
            container.setBackground(BG_STANDARD);
        }
        container.setPadding(new Insets(20));
    }

    public VBox getCard() {
        return container;
    }
}