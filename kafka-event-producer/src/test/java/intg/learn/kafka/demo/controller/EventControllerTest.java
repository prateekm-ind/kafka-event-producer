package learn.kafka.demo.controller;

import learn.kafka.demo.domain.Book;
import learn.kafka.demo.domain.LibraryEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@EmbeddedKafka(topics = {"library-events"}, partitions = 3)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-server=${spring.embedded.kafka.brokers}",
        "spring.kafka.admin.properties.bootstrap.servers=${spring.embedded.kafka.brokers}"})
class EventControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

//    private Consumer<Integer, String> consumer;
//
//    @BeforeEach
//    void setUp() {
//        consumer = new DefaultKafkaConsumerFactory<Integer, String>()
//    }

    @Test
    void postEvent() {
        //given
        Book book = Book.builder()
                .bookId(12345)
                .bookAuthor("Joshua Bloch")
                .bookName("Effective Java")
                .build();
        LibraryEvent libraryEvent = new LibraryEvent(11111, book);
        HttpHeaders headers = new HttpHeaders();
        headers.set("content-type", String.valueOf(MediaType.APPLICATION_JSON));

        HttpEntity<LibraryEvent> request = new HttpEntity<>(libraryEvent, headers);

        //when
        ResponseEntity<LibraryEvent> libraryEventResponseEntity = testRestTemplate
                .exchange("/api/v1/library-event",
                        HttpMethod.POST,
                        request,
                        LibraryEvent.class);

        //then
        assertEquals(HttpStatus.CREATED, libraryEventResponseEntity.getStatusCode());
    }
}