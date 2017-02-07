package com.github.mytax.impl.cells;

import com.github.mytax.impl.SocialSecurityNumber;
import com.github.mytax.impl.cells.BaseCell;
import lombok.Setter;

import java.util.Optional;

public class SsnCell extends BaseCell<SocialSecurityNumber> {
    @Setter private SocialSecurityNumber value;

    @Override
    public Optional<SocialSecurityNumber> getValue() {
        return Optional.ofNullable(value);
    }
}
