package com.example.usermanagement.config;

import java.util.regex.Pattern;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Custom Naming Strategy tương tự NestJS TypeORM NamingStrategy - Table names: camelCase ->
 * snake_case + pluralize - Column names: camelCase -> snake_case
 */
public class CustomNamingStrategy implements PhysicalNamingStrategy {

    private static final Pattern CAMEL_CASE_PATTERN = Pattern.compile("([a-z])([A-Z])");

    @Override
    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        if (name == null) return null;

        String tableName = toSnakeCase(name.getText());
        String pluralizedName = pluralize(tableName);

        return Identifier.toIdentifier(pluralizedName);
    }

    @Override
    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        return name;
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment jdbcEnvironment) {
        if (name == null) return null;
        return Identifier.toIdentifier(toSnakeCase(name.getText()));
    }

    /** Convert camelCase to snake_case */
    private String toSnakeCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return CAMEL_CASE_PATTERN.matcher(input).replaceAll("$1_$2").toLowerCase();
    }

    /** Simple pluralization logic (tương tự pluralize library của NestJS) */
    private String pluralize(String word) {
        if (word == null || word.isEmpty()) {
            return word;
        }

        // Special cases
        if (word.equals("person")) return "people";
        if (word.equals("child")) return "children";
        if (word.equals("foot")) return "feet";
        if (word.equals("tooth")) return "teeth";
        if (word.equals("mouse")) return "mice";
        if (word.equals("man")) return "men";
        if (word.equals("woman")) return "women";

        // Words ending in 'y' preceded by consonant
        if (word.endsWith("y") && word.length() > 1 && !isVowel(word.charAt(word.length() - 2))) {
            return word.substring(0, word.length() - 1) + "ies";
        }

        // Words ending in 's', 'ss', 'sh', 'ch', 'x', 'z'
        if (word.endsWith("s")
                || word.endsWith("ss")
                || word.endsWith("sh")
                || word.endsWith("ch")
                || word.endsWith("x")
                || word.endsWith("z")) {
            return word + "es";
        }

        // Words ending in 'f' or 'fe'
        if (word.endsWith("f")) {
            return word.substring(0, word.length() - 1) + "ves";
        }
        if (word.endsWith("fe")) {
            return word.substring(0, word.length() - 2) + "ves";
        }

        // Default: add 's'
        return word + "s";
    }

    private boolean isVowel(char c) {
        return "aeiouAEIOU".indexOf(c) != -1;
    }
}
