package com.bizzybees.bizzybooky.security;

public enum Feature {
    REGISTER_MEMBER,
    REGISTER_LIBRARIAN,
    VIEW_MEMBERS,
    ADD_BOOK,

    RENT_BOOK,
    RETURN_BOOK,
    VIEW_LENT_BOOKS_OF_MEMBER,
    VIEW_OVERDUE_BOOKS,

    CAN_SOFT_DELETE_BOOK,

    CAN_UPDATE_BOOK;
}
