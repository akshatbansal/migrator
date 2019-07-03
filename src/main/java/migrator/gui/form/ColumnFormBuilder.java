package migrator.gui.form;

import java.util.List;

import migrator.emitter.Subscriber;
import migrator.gui.Alignment;
import migrator.gui.Form;
import migrator.gui.FormBuilder;
import migrator.gui.GuiKit;
import migrator.gui.GuiNode;
import migrator.gui.Label;
import migrator.gui.Password;
import migrator.gui.PrimaryButton;
import migrator.gui.SecondaryButton;
import migrator.gui.Section;
import migrator.gui.SelectInput;
import migrator.gui.StringInput;

public class ColumnFormBuilder implements FormBuilder {
    protected GuiKit guiKit;
    protected Form current;
    protected PrimaryButton primaryButton;
    protected SecondaryButton secondaryButton;

    public ColumnFormBuilder(GuiKit guiKit) {
        this.guiKit = guiKit;
        this.primaryButton = null;
        this.secondaryButton = null;
    }

    protected Section addRow(GuiNode inputNode, GuiNode labelNode) {
        Section labelRow = this.guiKit.createColumn(Alignment.START);
        labelRow.setWidth(100.0);
        labelRow.addChild(labelNode);
        Section row = this.guiKit.createRow(Alignment.CENTER);
        row.addChild(labelRow);
        row.addChild(inputNode);
        return row;
    }

    @Override
    public FormBuilder addSelect(String label, List<Object> options) {
        return this.addSelect(label, options, null);
    }

    @Override
    public FormBuilder addSelect(String label, List<Object> options, Object value) {
        SelectInput<Object> select = this.guiKit.createSelect(options, value);
        Label labelNode = this.guiKit.createLabel(label);
        this.current.addChild(this.addRow(select, labelNode));
        this.current.addInput(label, select);
        return this;
    }

    @Override
    public FormBuilder addString(String label) {
        return this.addString(label, "");
    }

    @Override
    public FormBuilder addString(String label, String value) {
        StringInput inputNode = this.guiKit.createStringInput(value);
        Label labelNode = this.guiKit.createLabel(label);
        this.current.addChild(this.addRow(inputNode, labelNode));
        this.current.addInput(label, inputNode);
        return this;
    }

    @Override
    public Form make() {
        Section buttonRow = this.guiKit.createRow(Alignment.END);
        buttonRow.addChild(this.primaryButton);
        buttonRow.addChild(this.secondaryButton);
        this.current.addChild(buttonRow);
        return this.current;
    }

    @Override
    public FormBuilder buildForm() {
        this.primaryButton = null;
        this.secondaryButton = null;
        this.current = this.guiKit.createForm();
        return this;
    }

    @Override
    public FormBuilder startSection(String name) {
        Label label = this.guiKit.createLabel(name);
        Section pane = this.guiKit.createRow(Alignment.START);
        pane.addChild(label);
        this.current.addChild(pane);
        return this;
    }

    @Override
    public FormBuilder endSection() {
        return this;
    }

    @Override
    public FormBuilder addPassword(String label) {
        Password inputNode = this.guiKit.createPasswordInput("");
        Label labelNode = this.guiKit.createLabel(label);
        this.current.addChild(this.addRow(inputNode, labelNode));
        this.current.addInput(label, inputNode);
        return this;
    }

    @Override
    public FormBuilder setPrimaryAction(String name, Subscriber subscriber) {
        this.primaryButton = this.guiKit.createPrimaryButton(name);
        this.primaryButton.onAction(subscriber);
        return this;
    }

    @Override
    public FormBuilder setSecondaryAction(String name, Subscriber subscriber) {
        this.secondaryButton = this.guiKit.createSecondaryButton(name);
        this.secondaryButton.onAction(subscriber);
        return this;
    }
}