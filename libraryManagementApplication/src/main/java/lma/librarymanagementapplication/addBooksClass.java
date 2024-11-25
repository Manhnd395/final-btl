package lma.librarymanagementapplication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class addBooksClass {

    private String title;
    private String author;
    private String image;
    private String publishedDate;
    private int ID;
    private String ISBN;
    private String previewLink;
    private String infoLink;
    private String comments;

    /**
     * Constructor của sách.
     * @param title
     * @param author
     * @param image
     * @param publishedDate
     * @param comments
     */
    public addBooksClass(String title, String author, String publishedDate, int ID, String image, String previewLink, String comments) {
        this.author = author;
        this.title = title;
        this.image = image;
        this.publishedDate = publishedDate;
        this.ID = ID;
        this.previewLink = previewLink;
        this.comments = comments;
    }

    public addBooksClass(String title, String author, String publishedDate, String ISBN, String image, String previewLink, String infoLink) {
        this.author = author;
        this.title = title;
        this.publishedDate = publishedDate;
        this.ISBN = ISBN;
        this.image = image;
        this.previewLink = previewLink;
        this.infoLink = infoLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
    }

    public String getInfoLink() {
        return infoLink;
    }

    public void setInfoLink(String infoLink) {
        this.infoLink = infoLink;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comment) {
        this.comments = comment;
    }

    public void addBooktoDatabase() {
        String insertQuery = "INSERT INTO books(Title, Author, PublishedDate, Image, ISBN, PreviewLink) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connectDB = Database.connectDB();
             PreparedStatement statement = connectDB.prepareStatement(insertQuery)) {

            statement.setString(1, title);
            statement.setString(2, author);

            if (!publishedDate.equals("Unknown")) {
                try {
                    if(publishedDate.matches("\\d{4}")) {
                        publishedDate += "-01-01";
                    } else if (publishedDate.matches("\\d{4}-\\d{2}")) {
                        publishedDate += "-01";
                    }
                    statement.setDate(3, java.sql.Date.valueOf(publishedDate));
                } catch (IllegalArgumentException e) {
                    statement.setNull(3, java.sql.Types.DATE);
                }
            } else {
                statement.setNull(3, java.sql.Types.DATE);
            }

            if (!image.isEmpty()) {
                statement.setString(4, image);
            } else {
                statement.setNull(4, java.sql.Types.VARCHAR);
            }

            if (!ISBN.isEmpty()) {
                statement.setString(5, ISBN);
            } else {
                statement.setNull(5, java.sql.Types.VARCHAR);
            }

            if (!previewLink.isEmpty()) {
                statement.setString(6, previewLink);
            } else {
                statement.setNull(6, java.sql.Types.VARCHAR);
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Database error", e);
            throw new RuntimeException("Cannot add book to database. Please try again.");
        }
    }

    public String formatPublishedDate () {
        String formattedDate = "Unknown";

        if (publishedDate != null && !publishedDate.isEmpty()) {
            try {
                if (publishedDate.matches("\\d{4}")) {
                    // yyyy
                    formattedDate = LocalDate.parse(publishedDate + "-01-01").format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } else if (publishedDate.matches("\\d{4}-\\d{2}")) {
                    // yyyy-MM
                    YearMonth yearMonth = YearMonth.parse(publishedDate, DateTimeFormatter.ofPattern("yyyy-MM"));
                    formattedDate = yearMonth.atDay(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } else if (publishedDate.matches("\\d{2}-\\d{4}")) {
                    // MM-yyyy
                    YearMonth yearMonth = YearMonth.parse(publishedDate, DateTimeFormatter.ofPattern("MM-yyyy"));
                    formattedDate = yearMonth.atDay(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } else if (publishedDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
                    // yyyy-MM-dd
                    formattedDate = LocalDate.parse(publishedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                } else if (publishedDate.matches("\\d{2}-\\d{2}-\\d{4}")) {
                    // dd-MM-yyyy
                    formattedDate = LocalDate.parse(publishedDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                }
            } catch (DateTimeParseException e) {
                System.err.println("Lỗi phân tích ngày: " + publishedDate);
            }
        }
        return formattedDate;
    }

    public String toString() {
        return title + " - " + author;
    }

}
