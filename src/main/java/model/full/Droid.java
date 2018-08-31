package model.full;

import graphqljpa.annotation.GraphQLDescription;
import graphqljpa.annotation.GraphQLIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@GraphQLDescription("Represents an electromechanical robot in the Star Wars Universe")
public class Droid extends Character {
    @GraphQLIgnore
    @Column(name = "primary_function")
    @GraphQLDescription("Documents the primary purpose this droid serves")
    private String primaryFunction;

    public String getPrimaryFunction() {
        return primaryFunction;
    }

    public void setPrimaryFunction(String primaryFunction) {
        this.primaryFunction = primaryFunction;
    }
}