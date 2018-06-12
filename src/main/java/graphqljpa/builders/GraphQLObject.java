package graphqljpa.builders;

import graphql.schema.GraphQLOutputType;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class GraphQLObject {
    final private String name;
    final private String description;
    final private List<GraphQLOutputType> interfaces;
    final private Boolean isRoot = false;
    final private List<GraphQLField> fieldDefinitions;
    final private List<GraphQLDirective> directives;
    final private GraphQLManagedTypeMetadata source;

    public GraphQLObject(String name, String description, List<GraphQLField> fieldDefinitions,
                             List<GraphQLOutputType> interfaces) {
        this(name, description, fieldDefinitions, interfaces, null);
    }

    public GraphQLObject(String name, String description, List<GraphQLField> fieldDefinitions,
                             List<GraphQLOutputType> interfaces, List<GraphQLDirective> directives, ObjectTypeDefinition definition) {
        this.name = name;
        this.description = description;
        this.interfaces = interfaces;
        this.definition = definition;
        this.directives = assertNotNull(directives);
        buildDefinitionMap(fieldDefinitions);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDeprecated() {
        return deprecated;
    }

    public Set<GraphQLField> getFields() {
        return new HashSet<>(fieldsBuilders);
    }

    public Set<GraphQLDirective> getDirectives() {
        return new HashSet<>(directiveBuilders);
    }

    public Boolean isRoot() {
        return isRoot;
    }

    public GraphQLObject name(String name) {
        this.name = name;
        return this;
    }

    public GraphQLObject description(String description) {
        this.description = description;
        return this;
    }

    public GraphQLObject deprecated(String deprecated) {
        this.deprecated = deprecated;
        return this;
    }

    public GraphQLObject field(GraphQLField field) {
        fieldsBuilders.add(field);
        return this;
    }

    public GraphQLObject fields(Set<GraphQLField> fields) {
        fieldsBuilders.addAll(fields);
        return this;
    }

    public GraphQLObject directive(GraphQLDirective directive) {
        directiveBuilders.add(directive);
        return this;
    }

    public GraphQLObject directives(Set<GraphQLDirective> directives) {
        directiveBuilders.addAll(directives);
        return this;
    }

    public GraphQLObject isRoot(Boolean isRoot) {
        this.isRoot = isRoot;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphQLObject that = (GraphQLObject) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
