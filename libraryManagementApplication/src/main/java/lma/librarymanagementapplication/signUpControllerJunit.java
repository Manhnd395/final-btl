package lma.librarymanagementapplication;

import com.dlsc.formsfx.model.structure.PasswordField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.lang.reflect.Field;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class signUpControllerJunit {
    private signupController controller;

    @BeforeEach
    public void setUp() throws Exception {

        controller = new signupController();

        setUpField("signup_fullname", new TextField());
        setUpField("signup_username", new TextField());
        setUpField("signup_email", new TextField());
        setUpField("signup_password", new javafx.scene.control.PasswordField());
        setUpField("signup_passwordAgain", new javafx.scene.control.PasswordField());
        setUpField("signup_MSV", new TextField());
        setUpField("signup_day", new ChoiceBox<>());
        setUpField("signup_month", new ChoiceBox<>());
        setUpField("signup_year", new ChoiceBox<>());
        setUpField("signup_male", new CheckBox());
        setUpField("signup_female", new CheckBox());
        setUpField("message", new Label());
    }

    private void setUpField(String fieldName, Object value) throws Exception {
        Field field = signupController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, value);
    }

    @Test
    public void testIncompleteSignup() throws SQLException {

        setFieldValue("signup_fullname", "");
        setFieldValue("signup_username", "testuser");

        controller.signup_form();

        Label messageLabel = (Label) getPrivateField(controller, "message");
        assertEquals("Vui lòng nhập đầy đủ thông tin!", messageLabel.getText());
    }

    @Test
    public void testInvalidEmail() throws SQLException {

        setupCompleteForm();
        setFieldValue("signup_email", "invalidemail");

        controller.signup_form();

        Label messageLabel = (Label) getPrivateField(controller, "message");
        assertEquals("Email không hợp lệ!", messageLabel.getText());
    }

    @Test
    public void testInvalidFullname() throws SQLException {

        setupCompleteForm();
        setFieldValue("signup_fullname", "John123");

        controller.signup_form();

        Label messageLabel = (Label) getPrivateField(controller, "message");
        assertEquals("Tên chỉ được sử dụng ký tự chữ!", messageLabel.getText());
    }

    private void setupCompleteForm() {
        setFieldValue("signup_fullname", "Manhdz");
        setFieldValue("signup_username", "Manhdz");
        setFieldValue("signup_email", "Manhdz@.com");
        setFieldValue("signup_password", "pass");
        setFieldValue("signup_passwordAgain", "pass");
        setFieldValue("signup_MSV", "123456");

        ChoiceBox<Integer> dayChoice = (ChoiceBox<Integer>) getPrivateField(controller, "signup_day");
        dayChoice.setValue(3);

        ChoiceBox<Integer> monthChoice = (ChoiceBox<Integer>) getPrivateField(controller, "signup_month");
        monthChoice.setValue(9);

        ChoiceBox<Integer> yearChoice = (ChoiceBox<Integer>) getPrivateField(controller, "signup_year");
        yearChoice.setValue(2005);

        CheckBox maleCheckbox = (CheckBox) getPrivateField(controller, "signup_male");
        maleCheckbox.setSelected(true);
    }

    private void setupCompleteForm2 () {
        setFieldValue("signup_fullname", "Hungdien");
        setFieldValue("signup_username", "Hungdien");
        setFieldValue("signup_email", "Hungdien@gmail.com");
        setFieldValue("signup_password", "pass");
        setFieldValue("signup_passwordAgain", "pass");
        setFieldValue("signup_MSV", "123456");

        ChoiceBox<Integer> dayChoice = (ChoiceBox<Integer>) getPrivateField(controller, "signup_day");
        dayChoice.setValue(5);

        ChoiceBox<Integer> monthChoice = (ChoiceBox<Integer>) getPrivateField(controller, "signup_month");
        monthChoice.setValue(9);

        ChoiceBox<Integer> yearChoice = (ChoiceBox<Integer>) getPrivateField(controller, "signup_year");
        yearChoice.setValue(2005);

        CheckBox maleCheckbox = (CheckBox) getPrivateField(controller, "signup_male");
        maleCheckbox.setSelected(true);
    }

    /**
     * test set field.
     * @param fieldName
     * @param value
     */
    private void setFieldValue(String fieldName, String value) {
        try {
            Field field = signupController.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object fieldObject = field.get(controller);

            if (fieldObject instanceof TextField) {
                ((TextField) fieldObject).setText(value);
            } else if (fieldObject instanceof PasswordField) {
                ((javafx.scene.control.PasswordField) fieldObject).setText(value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Kiểm tra lấy giá trị của một đối tươợng.
     * @param object
     * @param fieldName
     * @return ex
     */
    private Object getPrivateField(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
