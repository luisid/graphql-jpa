package graphqljpa.impl.extensions.authorization;

import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import graphql.GraphQLError;
import graphql.GraphQLException;
import graphql.GraphqlErrorHelper;
import graphql.introspection.Introspection;
import graphql.schema.*;
import graphqljpa.schema.GraphQLExtension;
import graphqljpa.schema.metadata.GraphQLMetaData;

import java.util.HashSet;
import java.util.Set;

public class Authorization implements GraphQLExtension {

    private boolean hasPermission(Role minimumRole, Role role) {
        if (role == Role.admin) {
            return true;
        } else if (role != null && role == Role.webmaster && minimumRole == Role.webmaster) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean next(DataFetchingEnvironment environment, GraphQLMetaData metaData) {
        GraphQLAuthorization annotation = (GraphQLAuthorization) metaData.getAnnotation(GraphQLAuthorization.class);

        if (annotation != null) {
            Role role = annotation.role();

            Object context = environment.getContext();
            if (context instanceof AuthorizationContext) {
                Role contextRole = ((AuthorizationContext) context).getRole();
                if (hasPermission(role, contextRole)) {
                    return true;
                } else {
                    throw new GraphQLException("Not allowed: " + "role '"+ contextRole + "' has not permission to access this field");
                }
            }
        }

        return true;
    }
}
