package com.github.mytax.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CellIdTest {
    @Test
    public void testEquals() {
        CellId cid1 = new CellId("id");
        CellId cid2 = new CellId("id");
        CellId cid3 = new CellId("no");

        assertTrue(cid1.equals(cid2));
        assertTrue(cid2.equals(cid1));
        assertFalse(cid1.equals(cid3));
        assertFalse(cid2.equals(cid3));

        assertTrue(cid1.hashCode() == cid2.hashCode());
        assertTrue(cid1.hashCode() != cid3.hashCode());
        assertTrue(cid2.hashCode() != cid3.hashCode());
    }
}