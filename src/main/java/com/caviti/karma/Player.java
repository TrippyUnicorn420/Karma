package com.caviti.karma;

import java.util.Arrays;
import java.util.List;

/**
 * Code for the Player object.
 * 
 * @author Siku Mahayi, g19m0934@campus.ru.ac.za
 */
public class Player {

    private String name;
    private List<Card> handCards;
    private List<Card> tableCards;
    
    public Player(String name) {
        this.name = name;
    }
    
    /**
     * Give the cards to a player. 
     * 
     * @param handCards
     * @param tableCards 
     */
    public void setCards(List<Card> handCards, List<Card> tableCards) {
        this.handCards = handCards;
        this.tableCards = tableCards;
    }
    
    /**
     * Oh hi! Um, I used this method to see if cards were actually being dealt
     * to the players while testing the Dealer. This method is for debugging and
     * unless you're me, you shouldn't be reading this.
     * 
     */
    public void showMeYourCards() {
        System.out.println("Hand cards: " + Arrays.toString(handCards.toArray(new Card[0])));
        System.out.println("Table cards: " + Arrays.toString(tableCards.toArray(new Card[0])));

    }
    
    /**
     * Make a play. Remove cards from your hand, and the game overseer handles
     * what happens depending on what card(s) are played.
     * 
     * @param cards 
     */
    public void play(Card... cards) {
        // Waiting on how Tim is going to handle plays being made
    }
    
}
