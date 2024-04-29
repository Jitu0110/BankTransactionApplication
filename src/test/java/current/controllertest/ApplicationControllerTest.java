package current.controllertest;
import current.controller.ApplicationController;
import current.exception.DuplicateMessageIdException;
import current.exception.UserNotFoundException;
import current.model.*;
import current.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import current.model.Error;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/*
Unit test cases for ApplicationController Class
 */

@ExtendWith(MockitoExtension.class)
class ApplicationControllerTest {

    @Mock
    private ApplicationService service;

    @InjectMocks
    private ApplicationController controller;

    @Test
    void testProcessAuthorization_Success() throws Exception {
        // Arrange
        String messageId = "123";
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId(messageId);
        AuthorizationResponse response = new AuthorizationResponse();
        response.setResponseCode(ResponseCode.APPROVED);

        when(service.processAuthorization(request)).thenReturn(response);

        // Act
        ResponseEntity<?> result = controller.processAuthorization(messageId, request);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testProcessAuthorization_NotMatchingMessageId() {
        // Arrange
        String messageId = "123";
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId("456");


        // Act
        ResponseEntity<?> result = controller.processAuthorization(messageId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("MessageID in path variable and in JSON body does not match! 123 and 456. Please change it and try again.", ((Error) result.getBody()).getMessage());
    }

    @Test
    void testProcessLoad_Success() throws Exception {
        // Arrange
        String messageId = "123";
        LoadRequest request = new LoadRequest();
        request.setMessageId(messageId);
        LoadResponse response = new LoadResponse();

        when(service.processLoad(request)).thenReturn(response);

        // Act
        ResponseEntity<?> result = controller.processLoad(messageId, request);

        // Assert
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void testProcessLoad_NotMatchingMessageId() throws Exception {
        // Arrange
        String messageId = "123";
        LoadRequest request = new LoadRequest();
        request.setMessageId("456");


        // Act
        ResponseEntity<?> result = controller.processLoad(messageId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("MessageID in path variable and in JSON body does not match! 123 and 456. Please change it and try again.", ((Error) result.getBody()).getMessage());
    }

    @Test
    void testProcessAuthorization_DuplicateMessageId() {
        // Arrange
        String messageId = "123";
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId(messageId);

        when(service.processAuthorization(request)).thenThrow(new DuplicateMessageIdException(messageId));

        // Act
        ResponseEntity<?> result = controller.processAuthorization(messageId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("MessageId : 123 already exists in database . Please change it and try again.", ((Error) result.getBody()).getMessage());
    }

    @Test
    void testProcessAuthorization_UserNotFound() {
        // Arrange
        String messageId = "123";
        AuthorizationRequest request = new AuthorizationRequest();
        request.setMessageId(messageId);

        when(service.processAuthorization(request)).thenThrow(new UserNotFoundException(request.getUserId()));

        // Act
        ResponseEntity<?> result = controller.processAuthorization(messageId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("User not found with Id : null", ((Error) result.getBody()).getMessage());
    }

    @Test
    void testProcessLoad_DuplicateMessageId() throws Exception {
        // Arrange
        String messageId = "123";
        LoadRequest request = new LoadRequest();
        request.setMessageId(messageId);

        when(service.processLoad(request)).thenThrow(new DuplicateMessageIdException(messageId));

        // Act
        ResponseEntity<?> result = controller.processLoad(messageId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals("MessageId : 123 already exists in database . Please change it and try again.", ((Error) result.getBody()).getMessage());
    }

    @Test
    void testProcessLoad_InvalidMessageId() {
        // Arrange
        String messageId = "123";
        LoadRequest request = new LoadRequest();
        request.setMessageId(null);

        // Act
        ResponseEntity<?> result = controller.processLoad(messageId, request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        Error error = (Error) result.getBody();
        assertEquals("MessageID in path variable and in JSON body does not match! 123 and null. Please change it and try again.", error.getMessage());
        assertEquals("400", error.getCode());
    }

}