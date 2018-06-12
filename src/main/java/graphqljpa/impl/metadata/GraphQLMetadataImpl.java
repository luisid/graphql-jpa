package graphqljpa.impl.metadata;

import graphqljpa.schema.GraphQLBuilder;
import graphqljpa.schema.metadata.GraphQLMetaData;
import graphqljpa.impl.annotation.AnnotationUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Set;

abstract class GraphQLMetadataImpl implements GraphQLMetaData {
    @Override
    public String getDescription() {
        return AnnotationUtils.getDescription(this);
    }

    @Override
    public boolean isIgnored() {
        return AnnotationUtils.isIgnored(this);
    }

    @Override
    public GraphQLBuilder getBuilder() {
        Class<? extends GraphQLBuilder> builderClazz = AnnotationUtils.getBuilder(this);
        if (builderClazz == null) {
            try {
                return builderClazz.getConstructor().newInstance();
            } catch (NoSuchMethodException ex) {

            } catch (InstantiationException ex) {

            } catch (IllegalAccessException ex) {

            } catch (InvocationTargetException ex) {

            }
        }

        return null;
    }

    @Override
    public Set<GraphQLBuilder> getBuilders() {
        Class<? extends GraphQLBuilder>[] buildersClazz = AnnotationUtils.getBuilders(this);
        Set<GraphQLBuilder> builders = new HashSet<>();

        for (int i = 0; i < buildersClazz.length; i++) {
            try {
                builders.add(buildersClazz[i].getConstructor().newInstance());
            } catch (NoSuchMethodException ex) {

            } catch (InstantiationException ex) {

            } catch (IllegalAccessException ex) {

            } catch (InvocationTargetException ex) {

            }
        }

        return builders;
    }
}
