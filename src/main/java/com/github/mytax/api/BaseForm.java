package com.github.mytax.api;

import com.github.mytax.forms.y2016.ExemptionsCell;
import com.github.mytax.forms.y2016.Form1040;
import com.github.mytax.impl.cells.BaseCell;
import com.github.mytax.impl.cells.BooleanCell;
import com.github.mytax.impl.cells.CountBoxesCell;
import com.github.mytax.impl.FormActions;
import com.github.mytax.impl.cells.MoneyCell;
import com.github.mytax.impl.cells.NoLessThanZeroCell;
import com.github.mytax.impl.cells.SsnCell;
import com.github.mytax.impl.cells.StringCell;
import com.github.mytax.impl.cells.SubtractCell;
import com.github.mytax.impl.cells.SumCell;
import com.github.mytax.impl.rules.CheckOneAndOnlyOne;
import com.github.mytax.impl.rules.RequiredCellValue;
import com.github.mytax.impl.rules.RequiredCellValueIfBooleanIsTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseForm implements Form {
    private Map<String, Cell> cells;
    private Map<Line, Cell> cellsByLine;
    private List<Rule> rules;

    public BaseForm() {
        cells = new HashMap<>();
        cellsByLine = new HashMap<>();
        rules = new ArrayList<>();
    }

    protected void add(Cell cell) {
        cells.put(cell.getId(), cell);
        if(cell.getLine() != null) {
            cellsByLine.put(cell.getLine(), cell);
        }
    }

    @Override
    public Cell getCell(String name) {
        return cells.get(name);
    }

    public Cell getCell(Line line) {
        return cellsByLine.get(line);
    }

    protected void StringCell(String id, String label) {
        fillInAndAdd(new StringCell(), id, label);
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

    protected void NoLessThanZero(MoneyCell sourceCell) {
        fillInAndAdd(new NoLessThanZeroCell(sourceCell), sourceCell.getId(), sourceCell.getLabel(), sourceCell.getLine());
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

    protected void requiredValue(String cellId) {
        add(new RequiredCellValue(this, cellId));
    }
}
