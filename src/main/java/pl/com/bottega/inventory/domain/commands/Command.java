package pl.com.bottega.inventory.domain.commands;

public interface Command {
    void validate(Validatable.ValidationErrors validationErrors);
}
