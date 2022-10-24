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

    public void saveRental(BookRental bookRental) {
        rentalDatabase.put(bookRental.getLendingID(), bookRental);
    }

    public void removeRental (String lendingId) {
        rentalDatabase.remove(lendingId);
    }

    public ConcurrentHashMap<String, BookRental> getRentalDatabase() {
        return rentalDatabase;
    }


}
