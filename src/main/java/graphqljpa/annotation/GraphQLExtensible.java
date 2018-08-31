package graphqljpa.annotation;

import graphqljpa.schema.GraphQLExtension;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { TYPE, FIELD })
@Retention(RUNTIME)
public @interface GraphQLExtensible {
    boolean value() default true;
    Class<? extends GraphQLExtension>[] extensions();
}
