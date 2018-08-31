package graphqljpa.schema;

import org.atteo.evo.inflector.English;

public interface NamingStrategy {
    default String singularize(String word) {
        return English.plural(word, 1);
    };

    default String pluralize(String word) {
        return English.plural(word);
    };

}