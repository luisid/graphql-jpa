package graphqljpa.annotation;

public @interface GraphQLFilterable {
    boolean value() default true;
}
