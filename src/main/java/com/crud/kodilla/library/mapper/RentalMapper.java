package com.crud.kodilla.library.mapper;

import com.crud.kodilla.library.domain.Rental;
import com.crud.kodilla.library.domain.dto.RentalDto;
import com.crud.kodilla.library.repository.BookRepository;
import com.crud.kodilla.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalMapper {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    public Rental mapToRental(final RentalDto rentalDto){
        return new Rental(
                rentalDto.getId(),
                userRepository.findById(rentalDto.getUserId()).get(),
                bookRepository.findById(rentalDto.getBookId()).get(),
                rentalDto.getRentDate(),
                rentalDto.getReturnDate()
        );
    }

    public RentalDto mapToRentalDto(final Rental rental){
        return new RentalDto(
                rental.getId(),
                rental.getUser().getId(),
                rental.getBook().getId(),
                rental.getRentDate(),
                rental.getReturnDate()
        );
    }

    public List<RentalDto> mapToRentalDtoList(List<Rental> rentals){
        return rentals.stream().map(this::mapToRentalDto).collect(Collectors.toList());
    }
}