package com.github.cstroe.turtletax.api;

public interface Mistake {
    MistakeSource getSource();
    String getExplanation();
}
