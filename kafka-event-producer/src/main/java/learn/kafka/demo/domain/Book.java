package learn.kafka.demo.domain;

import lombok.Builder;

public record Book(Integer bookId,
                   String bookName,
                   String bookAuthor) {

    @Builder
    public Book {

    }
}
