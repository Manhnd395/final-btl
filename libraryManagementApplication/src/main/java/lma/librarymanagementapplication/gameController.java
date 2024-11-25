package lma.librarymanagementapplication;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class gameController implements Initializable {

    ArrayList<Button> buttons = new ArrayList<>();
    MemoryGame memoryGame = new MemoryGame();
    private final Map<Integer, Boolean> flippedStates = new HashMap<>(); // Hàm kiểm tra trạng thái lật hay úp.

    private AudioClip clickSound;
    private int firstButtonIndex = -1;
    private int secondButtonIndex = -1;

    @FXML
    private Button button0;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;
    @FXML
    private Button button5;
    @FXML
    private Button button6;
    @FXML
    private Button button7;
    @FXML
    private Button button8;
    @FXML
    private Button button9;
    @FXML
    private Button button10;
    @FXML
    private Button button11;
    @FXML
    private Button button12;
    @FXML
    private Button button13;
    @FXML
    private Button button14;
    @FXML
    private Button button15;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        memoryGame.setupGame();

        buttons.addAll(Arrays.asList(button0, button1, button2, button3, button4, button5, button6, button7,
                button8, button9, button10, button11, button12, button13, button14, button15));

        clickSound = new AudioClip(Objects.requireNonNull(getClass().getResource("/Images/sound.wav")).toString());

        for (int i = 0; i < buttons.size(); i++) {
            flippedStates.put(i, false); // Khởi tạo toàn bộ lá là úp.
        }
    }

    @FXML
    void buttonClicked(ActionEvent event) {
        clickSound.play();

        Button clickedButton = (Button) event.getSource();
        int clickedButtonIndex = buttons.indexOf(clickedButton);

        // Khi 1 hoặc 2 lá bị lật thì không click được nữa.
        if (flippedStates.get(clickedButtonIndex) || (firstButtonIndex != -1 && secondButtonIndex != -1)) {
            return;
        }

        // Lật lá được chọn.
        flipCard(clickedButton, clickedButtonIndex);

        if (firstButtonIndex == -1) {
            // Lật lá 1.
            firstButtonIndex = clickedButtonIndex;
        } else {
            // Lật lá 2.
            secondButtonIndex = clickedButtonIndex;

            // Kiểm tra trùng.
            if (memoryGame.checkTwoPositions(firstButtonIndex, secondButtonIndex)) {
                // Nếu trùng thì sau 1 giây xóa.
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> {
                    buttons.get(firstButtonIndex).setVisible(false);
                    buttons.get(secondButtonIndex).setVisible(false);
                    buttons.get(firstButtonIndex).setDisable(true);
                    buttons.get(secondButtonIndex).setDisable(true);

                    // Đặt lại chỉ số cho lượt tiếp theo.
                    resetFlipIndices();
                });
                pause.play();
            } else {
                // Nếu không trùng thì sau 1 giây bị lật xuống.
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(e -> {
                    hideButtons(firstButtonIndex, secondButtonIndex);
                    resetFlipIndices();
                });
                pause.play();
            }
        }
    }

    private void flipCard(Button button, int clickedButtonIndex) {
        // Chuyển đổi tỷ lệ cho hoạt ảnh lật.
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(300), button);
        scaleOut.setFromX(1);
        scaleOut.setToX(0);

        scaleOut.setOnFinished(e -> {
            // Hiển thị chữ sau khi lật.
            button.setText(memoryGame.getOptionAtIndex(clickedButtonIndex));
            flippedStates.put(clickedButtonIndex, true); // Đánh dấu đã lật.

            // Chuyển thẻ sang trạng thái lật hoặc úp.
            ScaleTransition scaleIn = new ScaleTransition(Duration.millis(300), button);
            scaleIn.setFromX(0);
            scaleIn.setToX(1);
            scaleIn.play();
        });

        scaleOut.play();
    }

    private void hideButtons(int index1, int index2) {
        // Ẩn các chữ cái trên cả hai nút và lật xuống.
        buttons.get(index1).setText("");
        buttons.get(index2).setText("");
        flippedStates.put(index1, false);
        flippedStates.put(index2, false);
    }

    private void resetFlipIndices() {
        // Đặt lại index cho lượt tiếp theo.
        firstButtonIndex = -1;
        secondButtonIndex = -1;
    }
}
