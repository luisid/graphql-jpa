package graphqljpa.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { TYPE, FIELD })
@Retention(RUNTIME)
public @interface GraphQLBuilder {
    Class<? extends graphqljpa.schema.GraphQLBuilder>[] builders();
    Class<? extends graphqljpa.schema.GraphQLBuilder> builder();
}