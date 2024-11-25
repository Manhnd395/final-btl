package lma.librarymanagementapplication;

/**
 * Hàm trung gian để kết nối đăng ký với trang cá nhân.
 */
public class personalInformationManager {

    private static userClass currentUser;

    public static userClass getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(userClass user) {
        currentUser = user;
    }
}
