/*
 * package com.example.demo.service;
 * 
 * 
 * import org.junit.jupiter.api.Test; import org.junit.jupiter.api.BeforeEach;
 * import org.mockito.InjectMocks; import org.mockito.Mock; import
 * org.mockito.MockitoAnnotations;
 * 
 * import com.example.demo.client.UserClient; import
 * com.example.demo.security.JwtUtil;
 * 
 * import static org.junit.jupiter.api.Assertions.*; import static
 * org.mockito.Mockito.*;
 * 
 * class AuthServiceTest {
 * 
 * @Mock private UserClient userClient;
 * 
 * @Mock private JwtUtil jwtUtil;
 * 
 * @InjectMocks private AuthService service;
 * 
 * private User inputUser; private User dbUser;
 * 
 * @BeforeEach void setup() { MockitoAnnotations.openMocks(this);
 * 
 * inputUser = new User(); inputUser.setEmail("test@mail.com");
 * inputUser.setPassword("123");
 * 
 * dbUser = new User(); dbUser.setEmail("test@mail.com");
 * dbUser.setPassword("123"); dbUser.setRole("OWNER"); }
 * 
 * @Test void testLoginSuccess() {
 * when(userClient.getUserByEmail("test@mail.com")).thenReturn(dbUser);
 * when(jwtUtil.generateToken(anyString(), anyString())).thenReturn("token");
 * 
 * String result = service.login(inputUser);
 * 
 * assertEquals("token", result); }
 * 
 * @Test void testLoginWrongPassword() { dbUser.setPassword("wrong");
 * when(userClient.getUserByEmail("test@mail.com")).thenReturn(dbUser);
 * 
 * assertThrows(RuntimeException.class, () -> service.login(inputUser)); }
 * 
 * @Test void testLoginUserNotFound() {
 * when(userClient.getUserByEmail("test@mail.com")).thenReturn(null);
 * 
 * assertThrows(RuntimeException.class, () -> service.login(inputUser)); }
 * 
 * @Test void testLoginNullPassword() { dbUser.setPassword(null);
 * when(userClient.getUserByEmail("test@mail.com")).thenReturn(dbUser);
 * 
 * assertThrows(RuntimeException.class, () -> service.login(inputUser)); } }
 */