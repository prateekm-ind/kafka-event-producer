package learn.kafka.demo.domain;

public record LibraryEvent(Integer eventId,
                           Book book) {
}
