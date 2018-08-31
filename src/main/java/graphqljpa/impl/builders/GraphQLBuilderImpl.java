package graphqljpa.impl.builders;

import graphql.schema.*;
import graphqljpa.impl.datafetcher.OneToManyDataFetcher;
import graphqljpa.schema.*;
import graphqljpa.schema.metadata.GraphQLAttributeMetadata;
import graphqljpa.schema.metadata.GraphQLManagedTypeMetadata;

import javax.persistence.EntityManager;
import javax.persistence.metamodel.PluralAttribute;

public class GraphQLBuilderImpl implements GraphQLBuilder {
    private EntityManager entityManager = null;
    @Override
    public GraphQLBuilder entityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        return this;
    }

    @Override
    public GraphQLObjectType buildObjectType(GraphQLManagedTypeMetadata metaData) {
        return GraphQLObjectType.newObject()
                .name(metaData.getName())
                .description(metaData.getDescription())
                //.withInterfaces()
                //.withDirectives()
                //.fields()
                .build();
    }

    @Override
    public GraphQLFieldDefinition buildField(GraphQLAttributeMetadata metaData) {
        DataFetcher dataFetcher = new PropertyDataFetcher(metaData.getOriginalName());

        if (metaData.isCollection() && (metaData.isOneToMany() || metaData.isManyToMany())) {
            dataFetcher = new OneToManyDataFetcher(entityManager, metaData, (PluralAttribute) metaData.getAttribute());
        }

        return GraphQLFieldDefinition.newFieldDefinition()
                .name(metaData.getName())
                .description(metaData.getDescription())
                .deprecate(metaData.getDeprecationReason())
                .type((GraphQLOutputType) metaData.getType())
                .dataFetcher(dataFetcher)
                .build();
        // check output type. Could be scalar, objectType/interface/union
        // directive by the builder
        // arguments - kind of complicated
    }


}
