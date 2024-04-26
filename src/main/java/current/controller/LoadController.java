package current.controller;

import current.model.LoadRequest;
import current.model.LoadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import current.service.LoadService;

@RestController
public class LoadController {

    @Autowired
    private LoadService loadService;

    @PutMapping("/load/{messageId}")
    public ResponseEntity<LoadResponse> processLoad(
            @PathVariable("messageId") String messageId,
            @RequestBody LoadRequest loadRequest) {

        // Process load request and calculate updated balance
        LoadResponse loadResponse = loadService.processLoad(loadRequest);

        return new ResponseEntity<>(loadResponse, HttpStatus.CREATED);
    }
}
