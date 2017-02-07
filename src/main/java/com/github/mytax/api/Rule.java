package com.github.mytax.api;

import com.github.mytax.impl.SimpleMistake;

import java.util.Collections;
import java.util.List;

public interface Rule {
    List<Mistake> validate();

    default List<Mistake> aSimpleMistake(String explanation) {
        return Collections.singletonList(SimpleMistake.of(explanation));
    }

    default List<Mistake> aSimpleMistake(String explanation, Object... args) {
        return Collections.singletonList(SimpleMistake.of(explanation, args));
    }

    default List<Mistake> noMistake() {
        return Collections.emptyList();
    }
}
