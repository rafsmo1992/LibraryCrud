package com.crud.kodilla.library.service;

import com.crud.kodilla.library.domain.Book;
import com.crud.kodilla.library.domain.Rental;
import com.crud.kodilla.library.domain.User;
import com.crud.kodilla.library.exceptions.BookNotFoundException;
import com.crud.kodilla.library.exceptions.RentalNotFoundException;
import com.crud.kodilla.library.exceptions.UserNotFoundException;
import com.crud.kodilla.library.repository.BookRepository;
import com.crud.kodilla.library.repository.RentalRepository;
import com.crud.kodilla.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class RentalService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private RentalRepository rentalRepository;
    @Autowired
    private UserRepository userRepository;

    public Optional<Rental> findRental(Long rentId){
        return rentalRepository.findById(rentId);
    }

    public Rental rentBook(Rental rental) throws BookNotFoundException, UserNotFoundException {
        Long userId = rental.getUser().getId();
        Long bookId = rental.getBook().getId();
        Book book = bookRepository.findByIdAndStatus(bookId, "available").orElseThrow(BookNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        book.setStatus("rent");
        bookRepository.save(book);
        rental.setBook(book);
        rental.setRentDate(LocalDate.now());
        rental.setUser(user);
        rentalRepository.save(rental);
        return rental;
    }

    public Rental returnBook(Long rentId) throws RentalNotFoundException {
        Rental rental = rentalRepository.findById(rentId).orElseThrow(RentalNotFoundException::new);
        rental.getBook().setStatus("available");
        rental.setReturnDate(LocalDate.now());
        bookRepository.save(rental.getBook());
        rentalRepository.save(rental);
        return rental;
    }

    public List<Rental> getAllRentals(){
        return rentalRepository.findAll();
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public void deleteRentalById(Long rentalId){
        rentalRepository.deleteById(rentalId);
    }
}