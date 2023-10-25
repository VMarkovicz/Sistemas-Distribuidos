package gson;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.stream.Collectors;
import java.util.List;

public class ValidationGson {
    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static final Validator validator = validatorFactory.getValidator();

    public static <T> void validate(T object) throws Error {
        List<String> violations = validator.validate(object).stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        if (!violations.isEmpty()) {
            System.out.println(violations);
            throw new Error(String.join(", ", violations));
        }
    }
}