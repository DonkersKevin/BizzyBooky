package com.bizzybees.bizzybooky.repositories;

import com.bizzybees.bizzybooky.domain.BookRental;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RentalRepository {

    private ConcurrentHashMap<String, BookRental> rentalDatabase;

    public RentalRepository() {
        this.rentalDatabase = new ConcurrentHashMap<>();
    }
}
