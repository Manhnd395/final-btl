package lma.librarymanagementapplication;

import com.dlsc.formsfx.model.structure.PasswordField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import org.junit.jupiter.api.BeforeEach;

import java.awt.*;
import java.lang.reflect.Field;

public class signInControlerJunit {
    private signinController controller;

    @BeforeEach
    public void setUp() throws Exception {
        controller = new signinController();

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
        Field field = signinController.class.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(controller, value);
    }

    /**
     * test form.
     */
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
