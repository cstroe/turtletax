package com.github.cstroe.turtletax.api;

import com.github.cstroe.turtletax.forms.y2016.ExemptionsCell;
import com.github.cstroe.turtletax.forms.y2016.Form1040;
import com.github.cstroe.turtletax.impl.FormActions;
import com.github.cstroe.turtletax.impl.TaxReturn;
import com.github.cstroe.turtletax.impl.cells.CountFilledInCell;
import com.github.cstroe.turtletax.impl.cells.NoLessThanZeroListener;
import com.github.cstroe.turtletax.impl.cells.SsnCell;
import com.github.cstroe.turtletax.impl.cells.StringCell;
import com.github.cstroe.turtletax.impl.cells.SubtractCell;
import com.github.cstroe.turtletax.impl.cells.SumCell;
import com.github.cstroe.turtletax.impl.rules.RequiredCellIfValueIsFilledIn;
import com.github.cstroe.turtletax.impl.rules.RequiredCellValue;
import com.github.cstroe.turtletax.impl.rules.RequiredCellValueIfBooleanIsTrue;
import com.github.cstroe.turtletax.api.event.CellValueChangeListener;
import com.github.cstroe.turtletax.impl.cells.BaseCell;
import com.github.cstroe.turtletax.impl.cells.BooleanCell;
import com.github.cstroe.turtletax.impl.cells.CountBoxesCell;
import com.github.cstroe.turtletax.impl.cells.MoneyCell;
import com.github.cstroe.turtletax.impl.cells.StateAbbreviationCell;
import com.github.cstroe.turtletax.impl.rules.CheckOneAndOnlyOne;
import lombok.Getter;
import lombok.Setter;
import lombok.val;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.lang.String.format;

public abstract class BaseForm implements Form {

    public class Require {
        private final CellId requiredId;

        Require(CellId requiredId) {
            this.requiredId = requiredId;
        }

        public void always() {
            BaseForm.this.add(new RequiredCellValue(BaseForm.this, requiredId));
        }

        public void ifFilled(String checkThisId) {
            CellId cid = getCellId(checkThisId)
                    .orElseThrow(() -> new IllegalArgumentException(format("No cell with id '%s' was found.", checkThisId)));;
            BaseForm.this.add(new RequiredCellIfValueIsFilledIn(BaseForm.this, cid, requiredId));
        }

        public void ifChecked(String booleanCell) {
            CellId booleanCellId = getCellId(booleanCell)
                    .orElseThrow(() -> new IllegalArgumentException(format("No cell with id '%s' was found.", booleanCell)));
            BaseForm.this.add(new RequiredCellValueIfBooleanIsTrue(BaseForm.this, booleanCellId, requiredId));
        }
    }

    private Map<CellId, Cell> cells;
    private Map<Line, Cell> cellsByLine;
    private List<Rule> rules;
    @Getter @Setter private TaxReturn taxReturn;

    public BaseForm() {
        cells = new HashMap<>();
        cellsByLine = new HashMap<>();
        rules = new ArrayList<>();
    }

    protected void add(String id, Cell cell) {
        if(id == null) {
            throw new NullPointerException("Cannot add a cell with a null id.");
        }

        CellId cid = new CellId(id);
        cell.setId(cid);
        if(cells.containsKey(cid)) {
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
    public Cell getCell(CellId id) {
        return cells.get(id);
    }

    @Override
    public Cell getCell(Line line) {
        return cellsByLine.get(line);
    }

    private CellId[] getCells(String... cells) {
        CellId[] cellIds = new CellId[cells.length];
        for (int i = 0; i < cells.length; i++) {
            val currentId = cells[i];
            cellIds[i] = getCellId(cells[i])
                    .orElseThrow(() -> new IllegalArgumentException(format("No cell with id '%s' was found.", currentId)));
        }
        return cellIds;
    }

    @Override
    public Cell getCellById(String id) {
        val cellId = getCellId(id)
                .orElseThrow(() -> new IllegalArgumentException(format("No cell with id '%s' was found.", id)));
        return getCell(cellId);
    }

    @Override
    public Optional<CellId> getCellId(String id) {
        for(CellId cid : cells.keySet()) {
            if(cid.getId().equals(id)) {
                return Optional.of(cid);
            }
        }
        return Optional.empty();
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
        cell.setId(new CellId(id));
        cell.setLabel(label);
        cell.setLine(line);
        add(id, cell);
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

    protected void SsnCell(String id, String label) {
        fillInAndAdd(new SsnCell(), id, label);
    }

    protected void SsnCell(String id, String label, Line line) {
        fillInAndAdd(new SsnCell(), id, label, line);
    }

    protected void SubtractCell(String id, String label, Line line, String subtractFrom, String... cells) {
        CellId subtractFromCell = getCellId(subtractFrom)
                .orElseThrow(() -> new IllegalArgumentException(format("Cannot find cell with id '%s'",id)));
        fillInAndAdd(new SubtractCell(this, subtractFromCell, getCells(cells)), id, label, line);
    }

    protected SubtractCell SubtractCell(String id, String label, Line line, FormActions.SubtractFrom subtractFrom) {
        SubtractCell cell = new SubtractCell(this, subtractFrom.from, subtractFrom.lines);
        fillInAndAdd(cell, id, label, line);
        return cell;
    }

    protected void CountBoxesCell(String id, String label, Line line, Form1040 form, String... cells) {
        fillInAndAdd(new CountBoxesCell(form, getCells(cells)), id, label, line);
    }

    protected void CountFilledInCell(String id, String label, Line line, Form1040 form, String... cells) {
        fillInAndAdd(new CountFilledInCell(form, getCells(cells)), id, label, line);
    }

    protected ExemptionsCell ExemptionsCell(String id, String label, Line line) {
        ExemptionsCell cell = new ExemptionsCell(this);
        fillInAndAdd(cell, id, label, line);
        return cell;
    }

    protected void StateAbbreviationCell(String id, String label, Line line) {
        fillInAndAdd(new StateAbbreviationCell(), id, label, line);
    }

    protected FormActions.Subtract Subtract(String line) {
        CellId thisId = getCell(Line.line(line)).getId();
        return new FormActions.Subtract(this, thisId);
    }

    protected CellValueChangeListener<BigDecimal> NoLessThanZero() {
        return new NoLessThanZeroListener();
    }

    protected void add(Rule rule) {
        rules.add(rule);
    }

    protected void RequiredCellValueIfBooleanIsTrue(CellId checkBox, CellId requiredValue) {
        RequiredCellValueIfBooleanIsTrue rule =
                new RequiredCellValueIfBooleanIsTrue(this, checkBox, requiredValue);
        add(rule);
    }

    protected void RequiredValueIfCellIsFilled(CellId requiredId, CellId checkThisId) {
        RequiredCellIfValueIsFilledIn rule = new RequiredCellIfValueIsFilledIn(this, checkThisId, requiredId);
        add(rule);
    }

    protected Require Require(String requiredId) {
        CellId required = getCellId(requiredId)
                .orElseThrow(() -> new IllegalArgumentException(format("No cell with id '%s' was found.", requiredId)));;
        return new Require(required);
    }

    protected void CheckOneAndOnlyOne(String... checkboxIds) {
        Rule rule = new CheckOneAndOnlyOne(this, getCells(checkboxIds));
        add(rule);
    }

    @Override
    public List<Mistake> validate() {
        return rules.stream()
                .flatMap(rule -> rule.validate().stream())
                .collect(Collectors.toList());
    }
}
