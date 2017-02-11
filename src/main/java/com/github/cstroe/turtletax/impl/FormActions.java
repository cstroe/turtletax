package com.github.cstroe.turtletax.impl;

import com.github.cstroe.turtletax.api.CellId;
import com.github.cstroe.turtletax.api.Form;

public class FormActions {
    public static class Subtract {
        private final Form form;
        private final CellId from;
        public Subtract(Form form, CellId from) {
            this.form = form;
            this.from = from;
        }

        public SubtractFrom from(String... lines) {
            return new SubtractFrom(form, from, form.getCellIdsFromLines(lines));
        }
    }

    public static class SubtractFrom {
        private final Form form;
        public final CellId from;
        public final CellId[] lines;
        public SubtractFrom(Form form, CellId from, CellId... lines) {
            this.form = form;
            this.from = from;
            this.lines = lines;
        }
    }
}
