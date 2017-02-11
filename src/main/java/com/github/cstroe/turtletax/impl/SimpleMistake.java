package com.github.cstroe.turtletax.impl;

import com.github.cstroe.turtletax.api.CellId;
import com.github.cstroe.turtletax.api.Mistake;
import lombok.Getter;
import lombok.ToString;

import static java.lang.String.format;

@ToString
public class SimpleMistake implements Mistake {
    @Getter private final CellId source;
    @Getter private final String explanation;

    private SimpleMistake(CellId source, String explanation) {
        this.source = source;
        this.explanation = explanation;
    }

    public static SimpleMistake of(CellId source, String explanation) {
        return new SimpleMistake(source, explanation);
    }

    public static SimpleMistake of(CellId source, String explanation, Object ... args) {
        return new SimpleMistake(source, format(explanation, args));
    }
}
