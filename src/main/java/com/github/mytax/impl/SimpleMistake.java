package com.github.mytax.impl;

import com.github.mytax.api.Cell;
import com.github.mytax.api.Mistake;
import lombok.Getter;
import lombok.ToString;

import static java.lang.String.format;

@ToString
public class SimpleMistake implements Mistake {
    @Getter private final String explanation;

    private SimpleMistake(String explanation) {
        this.explanation = explanation;
    }

    public static SimpleMistake of(String explanation) {
        return new SimpleMistake(explanation);
    }

    public static SimpleMistake of(String explanation, Object ... args) {
        return new SimpleMistake(format(explanation, args));
    }
}
