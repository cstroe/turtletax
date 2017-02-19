package com.github.cstroe.turtletax.api;

import static java.lang.String.format;

public interface MistakeSource {

    class CellSource implements MistakeSource {
        private final CellId cellId;

        public CellSource(CellId cellId) {
            if(cellId == null) {
                throw new NullPointerException("Cannot allow a null cell id.");
            }
            this.cellId = cellId;
        }

        @Override
        public String toString() {
            return format("Cell '%s'", cellId.toString());
        }
    }

    class FormSource implements MistakeSource {
        private final String formName;

        public FormSource(String formName) {
            if(formName == null) {
                throw new NullPointerException("Cannot allow a null form name.");
            }

            this.formName = formName;
        }

        @Override
        public String toString() {
            return format("Form '%s'", formName);
        }
    }
}
