package com.bizzybees.bizzybooky.repositories;

import com.bizzybees.bizzybooky.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository {
    private List<Book> bookList;

    public BookRepository() {
        this.bookList = new ArrayList<>(List.of(
                new Book("1000-2000-3000", "Pirates", "Mister", "Crabs", "Lorem Ipsum"),
                new Book("2000-3000-4000", "Farmers", "Misses", "Potato", "Lorem Ipsum"),
                new Book("3000-4000-5000", "Gardeners", "Miss", "Lettuce", "Lorem Ipsum"),
                new Book("6000-7000-8000", "Programmes", "Boy", "Name", "Lorem Ipsum")));
    }

    public List<Book> getAllBooks() {
        return bookList;
    }

    public Book getBookDetailsByIsbn(String isbn) {
        return bookList.stream().filter(book -> book.getIsbn().equals(isbn)).findFirst().orElseThrow();
    }

    /**
     * Main method for testing purposes - to be removed later
     */

    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepository();
        System.out.println(bookRepository.getBookDetailsByIsbn("1000-2000-3000").toString());
    }

    /** Main method for testing purposes - to be removed later*/

    /*
    public List<Book> getBooksByTitleAtLeastContaining(String title) {
        return bookList.stream().filter(b -> b.getTitle().contains(title)).toList();
    }

     */


    public List<Book> getBooksByTitleWithWildcards(String title){
        return bookList.stream().filter(b -> b.getTitle().matches(wildcardToRegex(title))).toList();
    }


    //Needs refactoring
    public static String wildcardToRegex(String wildcard){
        StringBuffer s = new StringBuffer(wildcard.length());
        s.append('^');
        for (int i = 0, is = wildcard.length(); i < is; i++) {
            char c = wildcard.charAt(i);
            switch(c) {
                case '*':
                    s.append(".*");
                    break;
                case '?':
                    s.append(".");
                    break;
                // escape special regexp-characters
                case '(': case ')': case '[': case ']': case '$':
                case '^': case '.': case '{': case '}': case '|':
                case '\\':
                    s.append("\\");
                    s.append(c);
                    break;
                default:
                    s.append(c);
                    break;
            }
        }
        s.append('$');
        return(s.toString());
    }
}
