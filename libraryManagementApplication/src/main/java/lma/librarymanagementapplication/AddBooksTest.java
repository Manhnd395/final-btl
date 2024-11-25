package lma.librarymanagementapplication;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AddBooksTest {
    public void testAddBookToDatabase() {
        addBooksClass book = new addBooksClass(
                "OOP",
                "Manh Nguyen",
                "https://example.com/image.jpg",
                "2005-09-03",
                "testID",
                "https://example.com/preview",
                "https://example.com/infoLink"
        );

        assertDoesNotThrow(() -> {
            book.addBooktoDatabase();
        }, "Khong xay ra ngoai le");
    }

    @Test
    public void testAddBookWithInvalidDate() {
        addBooksClass book = new addBooksClass(
                "Dien Hung",
                "me HUng",
                "https://example.com/image.jpg",
                "Invalid Date",
                "testID",
                "https://example.com/preview",
                "https://example.com/infoLink"
        );

        assertDoesNotThrow(() -> {
            book.addBooktoDatabase();
        }, "Ngày được thêm vào không hợp lệ");
    }
}
