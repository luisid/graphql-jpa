package model.basic;

import graphqljpa.annotation.GraphQLDescription;

import javax.persistence.*;
import java.util.List;

@Entity
abstract class Character {
    @Id
    @GraphQLDescription("Primary Key for the Character Class")
    private String id;

    @GraphQLDescription("Name of the character")
    private String name;

    @GraphQLDescription("Who are the known friends to this character")
    @ManyToMany
    @JoinTable(name="character_friends",
            joinColumns=@JoinColumn(name="source_id", referencedColumnName="id"),
            inverseJoinColumns=@JoinColumn(name="friend_id", referencedColumnName="id"))
    private List<Character> friends;

    @GraphQLDescription("What Star Wars episodes does this character appear in")
    @ElementCollection(targetClass = Episode.class)
    @Enumerated(EnumType.ORDINAL)
    private List<Episode> appearsIn;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Character> getFriends() {
        return friends;
    }

    public void setFriends(List<Character> friends) {
        this.friends = friends;
    }

    public List<Episode> getAppearsIn() {
        return appearsIn;
    }

    public void setAppearsIn(List<Episode> appearsIn) {
        this.appearsIn = appearsIn;
    }
}
