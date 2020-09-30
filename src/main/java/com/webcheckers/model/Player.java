package com.webcheckers.model;

public class Player {
    private String name;

    public Player(String name) {
        this.name=name;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Player) {

            // Two players are equal if they have the same name
            return ((Player) other).name.equals(this.name);
        }
        return false;
    }
}
