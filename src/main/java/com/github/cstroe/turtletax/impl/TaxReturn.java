package com.github.cstroe.turtletax.impl;

import com.github.cstroe.turtletax.api.Cell;
import com.github.cstroe.turtletax.api.CellRef;
import com.github.cstroe.turtletax.api.Form;
import com.github.cstroe.turtletax.api.Mistake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaxReturn {
    private List<Form> forms = new ArrayList<>();

    public void addForm(Form form) {
        form.setTaxReturn(this);
        forms.add(form);
    }

    public List<Form> getForms() {
        return Collections.unmodifiableList(forms);
    }

    public Optional<Form> getForm(String id) {
        return forms.stream()
                .filter(form -> form.getName().equals(id))
                .findFirst();
    }

    public List<Mistake> validate() {
        return forms.stream()
                .flatMap(form -> form.validate().stream())
                .collect(Collectors.toList());
    }

    public <T extends Cell> Optional<T> getRef(CellRef<T> cellRef) {
        String ref = cellRef.getRef();
        String[] refParts = ref.split("/");
        Optional<Form> form = getForm(refParts[0]);

        if(!form.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(form.get().getCellAsType(refParts[1], cellRef.getAClass()));
    };
}
