package com.github.mytax.impl;

import lombok.Setter;

import java.util.Optional;

public class SsnCell extends BaseCell<SocialSecurityNumber> {
    @Setter private SocialSecurityNumber value;

    @Override
    public Optional<SocialSecurityNumber> getValue() {
        return Optional.ofNullable(value);
    }
}
