package com.bizzybees.bizzybooky.security;

import java.util.List;

import static com.bizzybees.bizzybooky.security.Feature.*;
import static com.google.common.collect.Lists.newArrayList;

public enum Role {
    ADMIN(newArrayList(REGISTER_MEMBER, REGISTER_LIBRARIAN, VIEW_MEMBERS)),
    LIBRARIAN(newArrayList(REGISTER_MEMBER, RENT_BOOK, RETURN_BOOK, VIEW_LENT_BOOKS_OF_MEMBER, ADD_BOOK,VIEW_OVERDUE_BOOKS,CAN_UPDATE_BOOK, CAN_SOFT_DELETE_BOOK)),
    MEMBER(newArrayList(REGISTER_MEMBER, RENT_BOOK, RETURN_BOOK));


    private List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean containsFeature(Feature feature) {
        return featureList.contains(feature);
    }

}
