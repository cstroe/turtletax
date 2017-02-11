package com.github.cstroe.turtletax.api;

public interface Mistake {
    CellId getSource();
    String getExplanation();
}
