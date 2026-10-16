package com.switchfully.digibookygroup3.domain.user;

import java.util.ArrayList;
import java.util.List;

public enum UserType {
    LIBRARIAN(List.of(Feature.REGISTER_BOOK, Feature.DELETE_BOOK, Feature.RESTORE_BOOK)),
    ADMIN(List.of(Feature.VIEW_MEMBERS, Feature.REGISTER_LIBRARIAN)),
    MEMBER(List.of(Feature.LEND_BOOK));

    private List<Feature> features = new ArrayList<>();

    UserType() {
    }

    UserType(List<Feature> features) {
        this.features = features;
    }

    public boolean containsFeature(Feature feature) {
        return features.contains(feature);
    }

}
