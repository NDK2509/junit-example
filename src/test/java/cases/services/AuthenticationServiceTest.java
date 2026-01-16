package cases.services;

import base.BaseTestCase;
import org.example.dto.AuthData;
import org.example.dto.RegisterDTO;
import org.example.models.User;
import org.example.repositories.UserRepository;
import org.example.services.AuthenticationService;
import org.example.utils.TokenUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class AuthenticationServiceTest extends BaseTestCase {
    @Test
    void testAuthenticate_shouldBeSuccessful() { // AAA
        // Arrange -> chuan bi data, conditions
        // Create a fake user
        User fakeUser = new User(1L, "u01", "u01@test.com", "123456");
        UserRepository repo = mock(UserRepository.class);
        when(repo.getByEmail("u01@test.com")).thenReturn(fakeUser);  // Fake returned result
        MockedStatic<TokenUtils> tokenUtilsMocked = mockStatic(TokenUtils.class, CALLS_REAL_METHODS);
        tokenUtilsMocked.when(TokenUtils::generate).thenReturn("token");  // Fake a token to check below

        // Act
        AuthData auth = new AuthenticationService(repo).authenticate("u01@test.com", "123456");

        // Assert -> check xem ham/logic chay dung khong
        verify(repo, times(1)).getByEmail("u01@test.com");  // Xac minh cai ham getByEmail no co thuc su chay khong va chay dung 1 lan
        tokenUtilsMocked.verify(TokenUtils::generate, times(1)); // Xac minh cai ham generate co thuc su chay khong va phai chay dung 1 lan

        assertNotNull(auth); // Thanh cong -> phai tra ve data -> data khong null
        assertEquals(fakeUser, auth.getUser()); // Phai bang nhau, line 23 da fake
        assert auth.getAccessToken().equals("token"); // Phai bang nhau
    }

    @ParameterizedTest
    @CsvSource(
            value = {
                    "u01@test.com,wrongPassword",
                    "wrongEmail@gmail.com,wrongPassword"
            }, nullValues = "null")
    void testAuthenticate_shouldBeFailed(String email, String password) {
        // Arrange
        User fakeUser = new User(1L, "u01", "u01@test.com", "password");
        UserRepository repo = mock(UserRepository.class);

        // ? vi sao co case null -> case phuc vu cho wrong email -> user khong ton tai trong db -> null
        when(repo.getByEmail(email)).thenReturn(email.equals("wrongEmail@gmail.com") ? null : fakeUser);
        // ? tai sao khong mock returned result -> that bai thi khong generate token -> khong can return
        MockedStatic<TokenUtils> tokenUtilsMocked = mockStatic(TokenUtils.class, CALLS_REAL_METHODS);

        // Act
        AuthData auth = new AuthenticationService(repo).authenticate(email, password);

        // Assert
        verify(repo, times(1)).getByEmail(email);
        tokenUtilsMocked.verify(TokenUtils::generate, times(0));  // Khong toi buoc generate token -> times = 0
        assertNull(auth);
    }

    @Test
    void testRegister_shouldBeSuccessful() {
        // Arrange
        UserRepository repo = mock(UserRepository.class);
        String email = "u01@gmail.com";
        when(repo.existsByEmail(email)).thenReturn(false);
        RegisterDTO dto = new RegisterDTO("User 01", email, "123456");

        MockedStatic<User> userMocked = mockStatic(User.class, CALLS_REAL_METHODS);
        User expectedUser = new User(dto.getName(), dto.getEmail(), dto.getPassword());
        userMocked.when(() -> User.fromRegisterDTO(dto)).thenReturn(expectedUser);

        // Act
        AuthData data = new AuthenticationService(repo).register(dto);

        // Assert
        verify(repo, times(1)).existsByEmail(email);
        verify(repo, times(1)).save(expectedUser);

        assertNotNull(data);
        assertEquals(expectedUser, data.getUser());
        assertNotNull(data.getAccessToken());
    }
}
