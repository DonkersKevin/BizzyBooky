package com.bizzybees.bizzybooky.security;

import java.util.ArrayList;
import java.util.List;

import static com.bizzybees.bizzybooky.security.Feature.*;
import static com.google.common.collect.Lists.newArrayList;

public enum Role {
    ADMIN(newArrayList(REGISTER_MEMBER,REGISTER_LIBRARIAN,VIEW_MEMBERS)),
    LIBRARIAN(newArrayList(REGISTER_MEMBER)),
    MEMBER(newArrayList(REGISTER_MEMBER));


    private List<Feature> featureList;

    Role(List<Feature> featureList) {
        this.featureList = featureList;
    }

    public boolean containsFeature(Feature feature) {
        return featureList.contains(feature);
    }

}
