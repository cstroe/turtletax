package com.github.mytax.impl;

import com.github.mytax.api.Form;
import com.github.mytax.api.Mistake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TaxReturn {
    private List<Form> forms = new ArrayList<>();

    public void addForm(Form form) {
        forms.add(form);
    }

    public List<Form> getForms() {
        return Collections.unmodifiableList(forms);
    }

    public Optional<Form> getForm(String id) {
        return forms.stream()
                .filter(form -> form.getId().equals(id))
                .findFirst();
    }

    public List<Mistake> validate() {
        return forms.stream()
                .flatMap(form -> form.validate().stream())
                .collect(Collectors.toList());
    }
}
