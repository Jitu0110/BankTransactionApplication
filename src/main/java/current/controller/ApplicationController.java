package current.controller;

import current.exception.DuplicateMessageIdException;
import current.exception.NotMatchingMessageIdException;
import current.exception.UserNotFoundException;
import current.model.*;
import current.model.Error;
import current.service.ApplicationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class ApplicationController {

    @Autowired
    private ApplicationService service;



    @PutMapping("/authorization/{messageId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Authorization successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AuthorizationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
    })
    public ResponseEntity<?> processAuthorization(
            @PathVariable("messageId") String messageId,
            @Valid @RequestBody AuthorizationRequest authorizationRequest) {
        log.info("Received authorization request:" + authorizationRequest);

        try {
            if(!messageId.equals(authorizationRequest.getMessageId())){
                throw new NotMatchingMessageIdException(messageId,authorizationRequest.getMessageId());
            }
            // Process authorization request and calculate updated balance
            AuthorizationResponse authorizationResponse = service.processAuthorization(authorizationRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(authorizationResponse);
        }
        catch (NotMatchingMessageIdException | DuplicateMessageIdException | UserNotFoundException | IllegalArgumentException e){
            Error error = new Error();
            error.setMessage(e.getMessage());
            error.setCode("400");
            return ResponseEntity.badRequest().body(error);
        }
        catch (Exception e) {
            Error error = new Error();
            error.setMessage(e.getMessage());
            error.setCode("500");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


    @PutMapping("/load/{messageId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Load successful", content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoadResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))
    })
    public ResponseEntity<?> processLoad(
            @PathVariable("messageId") String messageId,
            @Valid  @RequestBody LoadRequest loadRequest) {

        log.info("Received load request:" + loadRequest);

        try {

            if(!messageId.equals(loadRequest.getMessageId())){
                throw new NotMatchingMessageIdException(messageId,loadRequest.getMessageId());
            }

            // Process load request and calculate updated balance
            LoadResponse loadResponse = service.processLoad(loadRequest);

            return ResponseEntity.status(HttpStatus.CREATED).body(loadResponse);
        }
        catch ( NotMatchingMessageIdException | DuplicateMessageIdException | IllegalArgumentException e){
            Error error = new Error();
            error.setMessage(e.getMessage());
            error.setCode("400");
            return ResponseEntity.badRequest().body(error);
        }
        catch (Exception e) {
            Error error = new Error();
            error.setMessage(e.getMessage());
            error.setCode("500");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Error> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        Error error = new Error();
        error.setMessage(errors.toString());
        error.setCode("400");
        return ResponseEntity.badRequest().body(error);
    }
}
