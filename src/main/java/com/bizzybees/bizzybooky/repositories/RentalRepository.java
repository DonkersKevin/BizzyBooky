package com.bizzybees.bizzybooky.repositories;

import com.bizzybees.bizzybooky.domain.BookRental;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class RentalRepository {

    private ConcurrentHashMap<String, BookRental> rentalDatabase;

    public RentalRepository() {
        this.rentalDatabase = new ConcurrentHashMap<>();
        rentalDatabase.put("1" , new BookRental("1", "1000-2000-3000"));
        rentalDatabase.put("2" , new BookRental("1", "2000-3000-4000"));
        rentalDatabase.put("3" , new BookRental("2", "3000-4000-5000"));
        rentalDatabase.put("4",new BookRental("4","4000-5000-6000"));
    }

    public void saveRental(BookRental bookRental) {
        rentalDatabase.put(bookRental.getLendingID(), bookRental);
    }

    public void removeRental(String lendingId) {
        rentalDatabase.remove(lendingId);
    }

    public ConcurrentHashMap<String, BookRental> getRentalDatabase() {
        return rentalDatabase;
    }


}
