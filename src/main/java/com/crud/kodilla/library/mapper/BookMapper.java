package com.crud.kodilla.library.mapper;

import com.crud.kodilla.library.domain.Book;
import com.crud.kodilla.library.domain.dto.BookDto;
import com.crud.kodilla.library.repository.TitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookMapper {
    @Autowired
    TitleRepository titleRepository;

    public Book mapToBook(BookDto bookDto){
        return new Book(
                bookDto.getId(),
                titleRepository.findById(bookDto.getTitleId()).orElseThrow(),
                bookDto.getStatus()
        );
    }

    public BookDto mapToBookDto(Book book){
        return new BookDto(
                book.getId(),
                book.getTitle().getId(),
                book.getStatus()
        );
    }

    public List<BookDto> mapToBookDtoList(final List<Book> books){
        return books.stream().map(this::mapToBookDto).collect(Collectors.toList());
    }
}