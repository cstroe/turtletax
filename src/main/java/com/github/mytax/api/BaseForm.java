package com.github.mytax.api;

import com.github.mytax.api.event.CellValueChangeListener;
import com.github.mytax.forms.y2016.ExemptionsCell;
import com.github.mytax.forms.y2016.Form1040;
import com.github.mytax.impl.FormActions;
import com.github.mytax.impl.TaxReturn;
import com.github.mytax.impl.cells.BaseCell;
import com.github.mytax.impl.cells.BooleanCell;
import com.github.mytax.impl.cells.CountBoxesCell;
import com.github.mytax.impl.cells.MoneyCell;
import com.github.mytax.impl.cells.NoLessThanZeroListener;
import com.github.mytax.impl.cells.SsnCell;
import com.github.mytax.impl.cells.StateAbbreviationCell;
import com.github.mytax.impl.cells.StringCell;
import com.github.mytax.impl.cells.SubtractCell;
import com.github.mytax.impl.cells.SumCell;
import com.github.mytax.impl.rules.CheckOneAndOnlyOne;
import com.github.mytax.impl.rules.RequiredCellValueIfBooleanIsTrue;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

public abstract class BaseForm implements Form {
    private Map<String, Cell> cells;
    private Map<Line, Cell> cellsByLine;
    private List<Rule> rules;
    @Getter @Setter private TaxReturn taxReturn;

    public BaseForm() {
        cells = new HashMap<>();
        cellsByLine = new HashMap<>();
        rules = new ArrayList<>();
    }

    protected void add(Cell cell) {
        if(cells.containsKey(cell.getId())) {
            throw new IllegalArgumentException(format("Cannot use duplicate cell id: %s", cell.getId()));
        }
        if(cell.getLine() != null) {
            if(cellsByLine.containsKey(cell.getLine())) {
                throw new IllegalArgumentException(format("Cannot use duplicate cell line number %s for cell id %s", cell.getLine().getLineNumber(), cell.getId()));
            }
            cellsByLine.put(cell.getLine(), cell);
        }
        cells.put(cell.getId(), cell);
    }

    @Override
    public List<Cell> getCells() {
        return new ArrayList<>(cells.values());
    }

    @Override
    public Cell getCell(String name) {
        return cells.get(name);
    }

    @Override
    public Cell getCell(Line line) {
        return cellsByLine.get(line);
    }

    protected void StringCell(String id, String label) {
        fillInAndAdd(new StringCell(), id, label);
    }

    protected void RequiredStringCell(String id, String label) {
        StringCell cell = new StringCell();
        cell.setRequired(true);
        fillInAndAdd(cell, id, label);
    }

    protected void StringCell(String id, String label, Line line) {
        fillInAndAdd(new StringCell(), id, label, line);
    }

    private void fillInAndAdd(BaseCell cell, String id, String label) {
        fillInAndAdd(cell, id, label, null);
    }

    private void fillInAndAdd(BaseCell cell, String id, String label, Line line) {
        cell.setId(id);
        cell.setLabel(label);
        cell.setLine(line);
        cell.setForm(this);
        add(cell);
    }

    protected void MoneyCell(String id, String label, Line line) {
        fillInAndAdd(new MoneyCell(), id, label, line);
    }

    protected void MoneyCell(String id, String label, Line line, Form1040 form, Line copyFromLine) {
        MoneyCell cell = new MoneyCell();
        form.getCellAsType(copyFromLine, MoneyCell.class).feeds(cell);
        fillInAndAdd(cell, id, label, line);
    }

    protected void BooleanCell(String id, String label) {
        fillInAndAdd(new BooleanCell(), id, label);
    }

    protected void BooleanCell(String id, String label, Line line) {
        fillInAndAdd(new BooleanCell(), id, label, line);
    }

    protected void SumCell(String id, String label, Line line, Line... linesToAddUp) {
        fillInAndAdd(new SumCell(this, linesToAddUp), id, label, line);
    }

    protected void SsnCell(String id, String label, Line line) {
        fillInAndAdd(new SsnCell(), id, label, line);
    }

    protected void SubtractCell(String id, String label, Line line, String subtractFrom, String... cells) {
        fillInAndAdd(new SubtractCell(this, subtractFrom, cells), id, label, line);
    }

    protected SubtractCell SubtractCell(String id, String label, Line line, FormActions.SubtractFrom subtractFrom) {
        SubtractCell cell = new SubtractCell(this, subtractFrom.from, subtractFrom.lines);
        fillInAndAdd(cell, id, label, line);
        return cell;
    }

    protected void CountBoxesCell(String id, String label, Line line, Form1040 form, String... cells) {
        fillInAndAdd(new CountBoxesCell(form, cells), id, label, line);
    }

    protected ExemptionsCell ExemptionsCell(String id, String label, Line line) {
        ExemptionsCell cell = new ExemptionsCell(this);
        fillInAndAdd(cell, id, label, line);
        return cell;
    }

    protected void StateAbbreviationCell(String id, String label, Line line) {
        fillInAndAdd(new StateAbbreviationCell(), id, label, line);
    }

    protected CellValueChangeListener<BigDecimal> NoLessThanZero() {
        return new NoLessThanZeroListener();
    }

    protected void add(Rule rule) {
        rules.add(rule);
    }

    protected void RequiredCellValueIfBooleanIsTrue(String checkBox, String requiredValue) {
        RequiredCellValueIfBooleanIsTrue rule =
                new RequiredCellValueIfBooleanIsTrue(this, checkBox, requiredValue);
        add(rule);
    }

    protected void CheckOneAndOnlyOne(String... checkboxIds) {
        Rule rule = new CheckOneAndOnlyOne(this, checkboxIds);
        add(rule);
    }

    @Override
    public List<Mistake> validate() {
        return getCombinedRules().stream()
                .flatMap(rule -> rule.validate().stream())
                .collect(Collectors.toList());
    }

    private List<Rule> getCombinedRules() {
        return Stream.of(rules, collectCellRules())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Rule> collectCellRules() {
        return cells.values().stream()
                .flatMap(cell -> (Stream<Rule>)cell.getRules().stream())
                .collect(Collectors.toList());
    }
}
