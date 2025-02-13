package it.ldas.imageprocessor.exception;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ContextConfiguration(classes = {GlobalExceptionHandler.class})
@ExtendWith(SpringExtension.class)
class GlobalExceptionHandlerTest {

    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;

    @Test
    void testHandleAllExceptions() {
        // Arrange
        Exception ex = new Exception("Test exception message");
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn("/test-url");

        // Act
        ResponseEntity<Map<String, Object>> actualHandleAllExceptionsResult = this.globalExceptionHandler
                .handleAllExceptions(ex, request);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, actualHandleAllExceptionsResult.getStatusCode());
        Map<String, Object> body = actualHandleAllExceptionsResult.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.get("status"));
        assertEquals("Internal Server Error", body.get("error"));
        assertEquals("Test exception message", body.get("message"));
        assertEquals("/test-url", body.get("url"));
        assertNotNull(body.get("timestamp"));
    }


    @Test
    void testHandleBadRequest() {
        // Arrange
        ResponseStatusException ex = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
        HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
        Mockito.when(request.getRequestURI()).thenReturn("/bad-request-url");

        // Act
        ResponseEntity<Map<String, Object>> actualHandleBadRequestResult = this.globalExceptionHandler.handleBadRequest(ex, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, actualHandleBadRequestResult.getStatusCode());
        Map<String, Object> body = actualHandleBadRequestResult.getBody();
        assertNotNull(body);
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.get("status"));
        assertEquals("Invalid request data", body.get("error"));
        assertEquals("/bad-request-url", body.get("url"));
        assertNotNull(body.get("timestamp"));
    }
}
