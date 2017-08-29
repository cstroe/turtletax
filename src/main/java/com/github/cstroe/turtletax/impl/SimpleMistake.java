package com.github.cstroe.turtletax.impl;

import com.github.cstroe.turtletax.api.CellId;
import com.github.cstroe.turtletax.api.Mistake;
import com.github.cstroe.turtletax.api.MistakeSource;
import lombok.Getter;
import lombok.ToString;

import static java.lang.String.format;

@ToString
public class SimpleMistake implements Mistake {
    @Getter private final MistakeSource source;
    @Getter private final String explanation;

    private SimpleMistake(MistakeSource source, String explanation) {
        this.source = source;
        this.explanation = explanation;
    }

    public static SimpleMistake of(CellId source, String explanation) {
        return new SimpleMistake(new MistakeSource.CellSource(source), explanation);
    }

    public static SimpleMistake of(CellId source, String explanation, Object ... args) {
        return new SimpleMistake(new MistakeSource.CellSource(source), format(explanation, args));
    }

    public static SimpleMistake of(String formName, String explanation, Object ... args) {
        return new SimpleMistake(new MistakeSource.FormSource(formName), format(explanation, args));
    }
}
