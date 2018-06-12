package graphqljpa.impl;

import graphql.schema.GraphQLArgument;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;

import javax.persistence.Index;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

public class ArgumentBuilder {
    static List<GraphQLArgument> getArguments(GraphQLManagedTypeMetadata metadata) {
        Table table = (Table) metadata.getAnnotation(Table.class);
        Index[] indices = table.indexes();

        return new ArrayList<>();
    }

    static List<GraphQLArgument> getArguments(GraphQLAttributeMetadata metadata) {
        Table table = (Table) metadata.getAnnotation(Table.class);
        Index[] indices = table.indexes();

        return new ArrayList<>();
    }
}
