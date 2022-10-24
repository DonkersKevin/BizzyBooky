package com.bizzybees.bizzybooky.services;

import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.domain.dto.BookRentalDto;
import com.bizzybees.bizzybooky.domain.dto.BookRentalMapper;
import com.bizzybees.bizzybooky.repositories.BookRepository;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.repositories.RentalRepository;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    private RentalRepository rentalRepository;
    private BookRepository bookRepository;
    private MemberRepository memberRepository;
    private BookRentalMapper bookRentalMapper;

    public RentalService(RentalRepository rentalRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.bookRentalMapper = new BookRentalMapper();
    }

    public BookRental rentBook(String memberINSS, String bookISBN) {
        isBookAvailable(bookISBN);
        isMemberInDatabase(memberINSS);
        bookRepository.getBookDetailsByIsbn(bookISBN).setAvailableForRent(false);
        BookRental bookrental = new BookRental(memberINSS, bookISBN);
        rentalRepository.saveRental(bookrental);
        BookRentalDto bookRentalDto = bookRentalMapper.BookRentalToBookRentalDto(bookrental);
        return bookrental;
    }

    private void isMemberInDatabase(String memberINSS) {
        if (!memberRepository.isMemberInDatabase(memberINSS)) {
            throw new IllegalArgumentException("Member doesn't exist");
        }

    }

    private void isBookAvailable(String bookISBN) {
        if (!bookRepository.getBookDetailsByIsbn(bookISBN).isAvailableForRent()) {
            throw new IllegalArgumentException("This book is not available for rent");
        }
    }
}
