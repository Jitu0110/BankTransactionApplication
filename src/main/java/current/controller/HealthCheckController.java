package current.controller;


import current.model.Ping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;


@RestController
@RequestMapping("/ping")
@Slf4j
public class HealthCheckController {

    @GetMapping()
    public ResponseEntity<Ping> healthCheck(){
        log.info("Received ping request");
        return ResponseEntity.ok(new Ping(Instant.now().toString()));
    }

}
