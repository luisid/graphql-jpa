package model.simple;

import graphqljpa.annotation.GraphQLIsRoot;
import graphqljpa.annotation.GraphQLName;

import javax.persistence.*;

@Entity(name = "Human")
@GraphQLIsRoot(false)
public class Human extends Character {

    @Column(name="home_planet")
    @GraphQLName("planet")
    private String homePlanet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "favorite_droid_id")
    private Droid favoriteDroid;

    public String getHomePlanet() {
        return homePlanet;
    }

    public void setHomePlanet(String homePlanet) {
        this.homePlanet = homePlanet;
    }

    public Droid getFavoriteDroid() {
        return favoriteDroid;
    }

    public void setFavoriteDroid(Droid favoriteDroid) {
        this.favoriteDroid = favoriteDroid;
    }
}