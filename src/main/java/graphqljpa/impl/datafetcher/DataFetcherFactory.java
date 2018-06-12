package graphqljpa.impl.datafetcher;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphqljpa.schema.metadata.GraphQLIdentifiableTypeMetadata;

public class DataFetcherFactory {
    static public DataFetcher getDataFetcher(GraphQLIdentifiableTypeMetadata metadata) {
        return new DataFetcher() {
            @Override
            public get(DataFetchingEnvironment environment) {
                return metadata.getJavaType();
            }
        }
    }
}
