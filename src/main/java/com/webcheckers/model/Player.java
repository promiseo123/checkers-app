package com.webcheckers.model;

/**
 * Player represents a single Player with a unique name
 *
 * @author Promise Omiponle
 */
public class Player {
    /** Unique name representing the player. */
    private String name;

    /**
     * This instantiates a single Player object.
     *
     * @param name the unique player name
     */
    public Player(String name) {
        this.name=name;
    }

    /**
     * Get the unique name of a Player
     *
     * @return the player's unique name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return string representation of this Player
     *
     * @return the player's unique name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Check if this Player is the same as another.
     *
     * @param other the Player object being compared to
     * @return boolean indicating if they are the same or not
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Player) {

            // Two players are equal if they have the same name
            return ((Player) other).name.equals(this.name);
        }
        return false;
    }
}
