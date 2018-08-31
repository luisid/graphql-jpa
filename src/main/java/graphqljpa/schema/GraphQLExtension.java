package graphqljpa.schema;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLDirective;
import graphqljpa.schema.metadata.GraphQLMetaData;

import javax.xml.crypto.Data;
import java.util.Set;

public interface GraphQLExtension {
    /**
     * Executed by GraphQLSchemaBuilder to validate extensions
     * @param environment
     * @param metaData
     * @return Boolean
     */
    Boolean next(DataFetchingEnvironment environment, GraphQLMetaData metaData);
}
