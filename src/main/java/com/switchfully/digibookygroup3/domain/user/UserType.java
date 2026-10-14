package com.switchfully.digibookygroup3.domain.user;

import java.util.ArrayList;
import java.util.List;

public enum UserType {
    LIBRARIAN(List.of(Feature.REGISTER_BOOK)),
    ADMIN(List.of(Feature.VIEW_MEMBERS)),
    MEMBER();

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
