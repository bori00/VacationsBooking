package controller.util;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * Generates text formatters for different types.
 */
public class FormatterFactory {
    /**
     * Generates a text formatter for doubles.
     * @return the double text formatter.
     */
    public static TextFormatter<Double> getDoubleFormatter() {
        return getDoubleFormatter(0.0);
    }

    public static TextFormatter<Double> getDoubleFormatter(double defaultValue) {
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c;
            } else {
                return null;
            }
        };

        StringConverter<Double> converter = new StringConverter<>() {
            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return defaultValue;
                } else {
                    return Double.valueOf(s);
                }
            }

            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };
        return new TextFormatter<>(converter, defaultValue, filter);
    }

    /**
     * Generates a text formatter for integers.
     * @return the integer text formatter.
     */
    public static TextFormatter<Integer> getIntegerFormatter() {
        return new TextFormatter<>(
                new IntegerStringConverter(),
                1,
                c -> Pattern.matches("\\d*", c.getText()) ? c : null);
    }

}
