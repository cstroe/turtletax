package com.github.mytax.api;

import lombok.Data;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Line {
    private final String lineNumber;

    public static Line line(String lineNumber) {
        return new Line(lineNumber);
    }

    public static Line line(int lineNumber) {
        return new Line(Integer.toString(lineNumber));
    }

    public static List<Line> lines(String... lines) {
        return Arrays.stream(lines)
                .map(Line::new)
                .collect(Collectors.toList());
    }
}
