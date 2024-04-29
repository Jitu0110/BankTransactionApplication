package current.controllertest;
import current.controller.HealthCheckController;
import current.model.Ping;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.Instant;


import lombok.extern.slf4j.Slf4j;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class HealthCheckControllerTest {

    @Mock
    private HealthCheckController healthCheckController;

    @Test
    void testHealthCheck() {
        // Arrange
        String timestamp = Instant.now().toString();
        Ping ping = new Ping(timestamp);
        when(healthCheckController.healthCheck()).thenReturn(ResponseEntity.ok(ping));

        // Act
        ResponseEntity<Ping> response = healthCheckController.healthCheck();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(timestamp, response.getBody().getServerTime());
    }
}