package com.bizzybees.bizzybooky.services;

import com.bizzybees.bizzybooky.domain.BookRental;
import com.bizzybees.bizzybooky.repositories.BookRepository;
import com.bizzybees.bizzybooky.repositories.MemberRepository;
import com.bizzybees.bizzybooky.repositories.RentalRepository;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

    private RentalRepository rentalRepository;
    private BookRepository bookRepository;
    private MemberRepository memberRepository;

    public RentalService(RentalRepository rentalRepository, BookRepository bookRepository, MemberRepository memberRepository) {
        this.rentalRepository = rentalRepository;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public BookRental rentBook(String memberID, String bookISBN) {
        if (!bookRepository.getBookDetailsByIsbn(bookISBN).isAvailableForRent()) {
            throw new IllegalArgumentException("This book is not available for rent");
        }
        bookRepository.getBookDetailsByIsbn(bookISBN).setAvailableForRent(false);
        return new BookRental(memberID, bookISBN);
    }

    private void isMemberInDatabase(String memberID){
        if(!memberRepository.memberDatabase.values().stream().anyMatch(member -> memberID.equals(member.getINSS()))){
            throw new IllegalArgumentException("Member doesn't exist");
        }
    }
}
