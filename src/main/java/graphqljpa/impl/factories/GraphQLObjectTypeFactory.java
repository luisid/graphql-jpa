package graphqljpa.impl.factories;

public class GraphQLObjectTypeFactory {
    /*
        private Map<String, GraphQLObjectType> typeMap = new HashMap<>();

    @Override
    public GraphQLObjectType getObjectType(GraphQLMetaData graphQLMetaData) {
        if (typeMap.containsKey(graphQLMetaData.getName())) {
            return typeMap.get(graphQLMetaData.getName());
        }

        GraphQLObjectType objectType = buildObjectType(graphQLMetaData);

        typeMap.put(graphQLMetaData.getName(), objectType);

        return objectType;
    }

    @Override
    public GraphQLObjectType getObjectTypeByName(String name) {
        if (typeMap.containsKey(name)) {
            return typeMap.get(name);
        }
        return null;
    }

    static private GraphQLObjectType buildObjectType(GraphQLMetaData graphQLMetaData) {
        GraphQLObjectType objectType = GraphQLObjectType.newObject()
                .name(graphQLMetaData.getName())
                .description(graphQLMetaData.getDescription())
                .build();

        return objectType;
    }
     */
}
