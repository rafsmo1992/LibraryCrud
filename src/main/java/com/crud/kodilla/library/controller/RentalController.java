package com.crud.kodilla.library.controller;


import com.crud.kodilla.library.domain.Rental;
import com.crud.kodilla.library.domain.dto.RentalDto;
import com.crud.kodilla.library.exceptions.BookNotFoundException;
import com.crud.kodilla.library.exceptions.RentalNotFoundException;
import com.crud.kodilla.library.exceptions.UserNotFoundException;
import com.crud.kodilla.library.mapper.RentalMapper;
import com.crud.kodilla.library.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/library")
@RequiredArgsConstructor
public class RentalController {

    private final RentalMapper rentalMapper;
    private final RentalService rentalService;

    @PostMapping(value = "/rent", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RentalDto rentBook(@RequestBody final RentalDto rentalDto) throws BookNotFoundException, UserNotFoundException {
        Rental rental = rentalService.rentBook(rentalMapper.mapToRental(rentalDto));
        return rentalMapper.mapToRentalDto(rental);
    }

    @PutMapping(value = "/rent/{id}")
    public RentalDto returnBook(@PathVariable("id") final Long id) throws RentalNotFoundException {
        Rental rental = rentalService.findRental(id).orElseThrow(RentalNotFoundException::new);
        rentalService.returnBook(id);
        return rentalMapper.mapToRentalDto(rental);
    }

    @GetMapping("/rentals/{id}")
    public RentalDto getRental(@PathVariable("id") final Long id) throws RentalNotFoundException {
        Rental rental = rentalService.findRental(id).orElseThrow(RentalNotFoundException::new);
        return rentalMapper.mapToRentalDto(rental);
    }

    @PutMapping(value = "/rentals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RentalDto updateRental(@RequestBody RentalDto rentalDto) {
        return rentalMapper.mapToRentalDto(rentalService.saveRental(rentalMapper.mapToRental(rentalDto)));
    }

    @DeleteMapping(value = "/rentals/{rentalId}")
    public void deleteRental(@PathVariable Long rentalId) {
        rentalService.deleteRentalById(rentalId);
    }

    @GetMapping(value = "/rentals")
    public List<RentalDto> getRentals() {
        return rentalMapper.mapToRentalDtoList(rentalService.getAllRentals());
    }
}