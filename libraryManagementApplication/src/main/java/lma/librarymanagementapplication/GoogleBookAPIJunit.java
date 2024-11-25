package lma.librarymanagementapplication;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GoogleBookAPIJunit {
    @Test
    public void testSearchBookValidQuery() throws GoogleBookAPIException {
        try {
            String result = GoogleBookAPI.searchBook("Java Programming");
            assertNotNull(result, "Search result should not be null");
            assertTrue(result.contains("items"), "Search result should contain book items");
        } catch (Exception e) {
            throw new GoogleBookAPIException("Failed to search books", e);
        }
    }

    @Test
    public void testSearchBookEmptyQuery() {
        assertThrows(IllegalArgumentException.class, () -> {
            GoogleBookAPI.searchBook("");
        });
    }
}
