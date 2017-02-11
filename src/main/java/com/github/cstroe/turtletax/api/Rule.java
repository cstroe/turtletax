package com.github.cstroe.turtletax.api;

import com.github.cstroe.turtletax.impl.SimpleMistake;

import java.util.Collections;
import java.util.List;

public interface Rule {
    List<Mistake> validate();

    default List<Mistake> aSimpleMistake(CellId sourceId, String explanation) {
        return Collections.singletonList(SimpleMistake.of(sourceId, explanation));
    }

    default List<Mistake> aSimpleMistake(CellId sourceId, String explanation, Object... args) {
        return Collections.singletonList(SimpleMistake.of(sourceId, explanation, args));
    }

    default List<Mistake> noMistake() {
        return Collections.emptyList();
    }
}
