package learn.kafka.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import learn.kafka.demo.domain.LibraryEvent;
import learn.kafka.demo.producer.LibraryEventProducer;
import org.apache.tomcat.jni.Library;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class EventController {

    @Autowired
    LibraryEventProducer libraryEventProducer;

    @PostMapping("/library-event")
    public ResponseEntity<LibraryEvent>  postLibraryEvent(@RequestBody LibraryEvent libraryEvent) throws JsonProcessingException {
        libraryEventProducer.sendLibraryEvent(libraryEvent);
        return ResponseEntity.status(HttpStatus.CREATED).body(libraryEvent);
    }
}
