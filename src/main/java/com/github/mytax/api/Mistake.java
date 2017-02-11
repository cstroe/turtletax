package com.github.mytax.api;

public interface Mistake {
    CellId getSource();
    String getExplanation();
}
