package com.github.mytax.impl;

import com.github.mytax.api.Form;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
}
