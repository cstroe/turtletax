package com.github.cstroe.turtletax.impl.cells;

import com.github.cstroe.turtletax.impl.SocialSecurityNumber;
import lombok.Setter;

import java.util.Optional;

public class SsnCell extends BaseCell<SocialSecurityNumber> {
    @Setter private SocialSecurityNumber value;

    @Override
    public Optional<SocialSecurityNumber> getValue() {
        return Optional.ofNullable(value);
    }
}
