package com.github.mytax.impl.rules;

import com.github.mytax.api.Form;
import com.github.mytax.api.Mistake;
import com.github.mytax.api.Rule;
import com.github.mytax.forms.y2016.Form1040;
import com.github.mytax.impl.BooleanCell;
import com.github.mytax.impl.SimpleMistake;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CheckOneAndOnlyOne implements Rule {
    private final Form form;
    private final String[] cells;

    public CheckOneAndOnlyOne(Form form, String... cells) {
        this.form = form;
        this.cells = cells;
    }

    @Override
    public List<Mistake> validate() {
        List<Mistake> mistakes = new ArrayList<>();
        int numChecked = Stream.of(cells)
                .map(name -> form.getCellAsType(name, BooleanCell.class))
                .mapToInt(cell -> {if(cell.getValue().get()) { return 1; } else { return 0;}})
                .sum();

        if(numChecked == 0) {
            mistakes.add(SimpleMistake.of("You must select your filing status."));
        } else if(numChecked > 1) {
            mistakes.add(SimpleMistake.of("You must select only one filing status."));
        }

        return mistakes;
    }
}
