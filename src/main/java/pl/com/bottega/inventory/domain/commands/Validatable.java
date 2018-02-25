package pl.com.bottega.inventory.domain.commands;

import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
public interface Validatable {

    void validate(ValidationErrors errors);
    @Component
    class ValidationErrors {

        private Map<String, Set<String>> errors = new HashMap<>();

        public void add(String fieldName, String errorMessage) {
            Set<String> fieldErrors = errors.getOrDefault(fieldName, new HashSet<>());
            fieldErrors.add(errorMessage);
            errors.putIfAbsent(fieldName, fieldErrors);
        }

        public boolean isValid() {
            return errors.isEmpty();
        }

        public Map<String, Set<String>> getErrors() {
            return new HashMap<>(errors);
        }

        public boolean any() {
            return !errors.isEmpty();
        }
    }

    default void validatePresenceOf(String value, String name, ValidationErrors errors) {
        if(value == null || value.equals("null") || value.length() == 0)
            errors.add(name, "can't be blank");
    }

    default void validatePresenceOf(Object value, String name, ValidationErrors errors) {
        if(value == null)
            errors.add(name, "can't be blank");
    }

    default void validateAmount(Long amount, String field, ValidationErrors errors) {
        if (amount < 1 || amount > 999) {
            errors.add(field,"must be between 1 and 999");
        }
    }

}
