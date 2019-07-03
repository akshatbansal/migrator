package migrator.gui;

import java.util.List;

import migrator.emitter.Subscriber;

public interface FormBuilder {
    public FormBuilder buildForm();
    public FormBuilder addString(String label);
    public FormBuilder addString(String label, String value);
    public FormBuilder addSelect(String label, List<Object> options);
    public FormBuilder addSelect(String label, List<Object> options, Object value);
    public FormBuilder startSection(String name);
    public FormBuilder endSection();
    public FormBuilder addPassword(String label);
    public FormBuilder setPrimaryAction(String name, Subscriber subscriber);
    public FormBuilder setSecondaryAction(String name, Subscriber subscriber);
    public Form make();
}