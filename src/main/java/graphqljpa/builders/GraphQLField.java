package graphqljpa.builders;

import graphql.schema.GraphQLOutputType;

import java.util.HashSet;
import java.util.Set;

public final class GraphQLField {
    private String name;
    private String description;
    private String deprecated;
    private GraphQLOutputType outputType;
    private Set<GraphQLDirective> directiveBuilder = new HashSet<>();

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDeprecated() {
        return deprecated;
    }

    public Set<GraphQLDirective> getDirectives() {
        return new HashSet<>(directiveBuilder);
    }

    public GraphQLOutputType getOutputType() {
        return outputType;
    }

    public GraphQLField name(String name) {
        this.name = name;
        return this;
    }

    public GraphQLField description(String description) {
        this.description = description;
        return this;
    }

    public GraphQLField deprecated(String deprecated) {
        this.deprecated = deprecated;
        return this;
    }

    public GraphQLField outputType(GraphQLOutputType outputType) {
        this.outputType = outputType;
        return this;
    }

    public GraphQLField directive(GraphQLDirective directive) {
        this.directiveBuilder.add(directive);
        return this;
    }

    public GraphQLField directives(Set<GraphQLDirective> directives) {
        this.directiveBuilder.addAll(directives);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphQLField that = (GraphQLField) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
