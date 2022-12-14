package com.bizzybees.bizzybooky.services;

import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.domain.dto.bookDtos.BookDto;
import com.bizzybees.bizzybooky.domain.dto.bookDtos.BookMapper;
import com.bizzybees.bizzybooky.domain.dto.bookRentalDtos.BookRentalDto;
import com.bizzybees.bizzybooky.domain.dto.bookRentalDtos.BookRentalMapper;
import com.bizzybees.bizzybooky.exceptions.LendingIdNotFoundException;
import com.bizzybees.bizzybooky.repositories.BookRepository;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.repositories.RentalRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class RentalService {

    private RentalRepository rentalRepository;
    private BookRepository bookRepository;
    private MemberRepository memberRepository;
    private BookRentalMapper bookRentalMapper;
    private BookService bookService;

    private BookMapper bookMapper;

    public RentalService(RentalRepository rentalRepository, BookRepository bookRepository, MemberRepository memberRepository,BookService bookService) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.bookRentalMapper = new BookRentalMapper();
        this.bookMapper = new BookMapper();
        this.bookService = bookService;

    }

    public static void main(String[] args) {
        RentalService rentalService = new RentalService(new RentalRepository(), new BookRepository(), new MemberRepository(), new BookService(new BookRepository()));
        rentalService.rentBook("1", "1000-2000-3000");
        rentalService.rentBook("1", "1000-2000-3000");


    }

    public BookRentalDto rentBook(String memberINSS, String bookISBN) {
        isBookAvailable(bookISBN);
        isMemberInDatabase(memberINSS);
        bookRepository.getBookDetailsByIsbn(bookISBN).setAvailableForRent(false);
        BookRental bookrental = new BookRental(memberINSS, bookISBN);
        rentalRepository.saveRental(bookrental);
        return bookRentalMapper.BookRentalToBookRentalDto(bookrental);
    }

    private void isMemberInDatabase(String memberINSS) {
        if (!memberRepository.isMemberInDatabase(memberINSS)) {
            throw new IllegalArgumentException("Member doesn't exist");
        }
    }

    private void isBookAvailable(String bookISBN) {
        if (!bookRepository.getBookDetailsByIsbn(bookISBN).getIsAvailableForRent()) {
            throw new NoSuchElementException("This book is not available for lending");
        }
    }

    public String returnBook(String lendingId) {
        if (!rentalRepository.getRentalDatabase().containsKey(lendingId)) {
            throw new LendingIdNotFoundException();
        }
        LocalDate returnDate = rentalRepository.getRentalDatabase().get(lendingId).getDueDate();
        bookRepository.getBookDetailsByIsbn(rentalRepository.getRentalDatabase().get(lendingId).getBookISBN()).setAvailableForRent(true);
        rentalRepository.removeRental(lendingId);
        if (returnDate.isBefore(LocalDate.now())) {
            return "This book should have been returned by: " + returnDate;
        }
        return "Thank you for renting books with us!";
    }

    public RentalRepository getRentalRepository() {
        return rentalRepository;
    }

    public BookRepository getBookRepository() {
        return bookRepository;
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    public List<BookDto> getLentBooksOfMember(String memberId) {
        List<BookRental> bookRentals = new ArrayList<>(rentalRepository.getRentalDatabase().values());
        List<String> isbnList = bookRentals.stream().filter(rental -> rental.getMemberID().equals(memberId)).map(BookRental::getBookISBN).collect(Collectors.toList());
        List<BookDto> rentedBooksDto = bookRepository
                .getAllBooks()
                .stream()
                .filter(book -> isbnList.contains(book.getIsbn()))
                .map(book -> bookMapper.bookToDto(book))
                .collect(Collectors.toList());
        return rentedBooksDto;
    }

    public List<BookDto> getAllBooksThatAreOverdue() {
        List<BookDto> overDueBooks = new ArrayList<>();
        for (BookRental bookRental:getRentalRepository().getRentalDatabase().values()) {
            if(LocalDate.now().isAfter(bookRental.getDueDate())){
                overDueBooks.add(bookService.getBookByIsbn(bookRentalMapper.BookRentalToBookRentalDto(bookRental).getBookISBN()));

            }
        }
        return overDueBooks;
    }
}
