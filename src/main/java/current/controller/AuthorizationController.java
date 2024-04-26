package current.controller;

import current.model.AuthorizationRequest;
import current.model.AuthorizationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import current.service.AuthorizationService;

@RestController
public class AuthorizationController {
    @Autowired
    private AuthorizationService authorizationService;

    @PutMapping("/authorization/{messageId}")
    public ResponseEntity<AuthorizationResponse> processAuthorization(
            @PathVariable("messageId") String messageId,
            @RequestBody AuthorizationRequest authorizationRequest) {

        // Process authorization request and calculate updated balance
        AuthorizationResponse authorizationResponse = authorizationService.processAuthorization(authorizationRequest);

        return new ResponseEntity<>(authorizationResponse, HttpStatus.CREATED);
    }
}
