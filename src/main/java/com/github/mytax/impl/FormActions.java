package com.github.mytax.impl;

public class FormActions {
    public static Subtract subtract(String line) { return new Subtract(line); };

    public static class Subtract {
        public final String from;
        public Subtract(String from) { this.from = from; }
        public SubtractFrom from(String... lines) {
            return new SubtractFrom(from, lines);
        }
    }

    public static class SubtractFrom {
        public final String from;
        public final String[] lines;
        public SubtractFrom(String from, String... lines) {
            this.from = from;
            this.lines = lines;
        }
    }
}
