package com.caviti.karma;

import static com.caviti.karma.Dealer.getTableDeck;
import java.util.Arrays;
import java.util.List;

/**
 * Code for the Player object.
 * 
 * @author Siku Mahayi, g19m0934@campus.ru.ac.za
 */
public class Player  {

    private String name;
    private List<Card> handCards;
    private List<Card> tableCards;
    
    public Player(String name) {
        this.name = name;
    }
    
    public List<Card> getHandCards()
    {
        return this.handCards;
    }
    
    public List<Card> geTableCards()
    {
        return this.tableCards;
    }
    
    public void pickUp()
    {
        this.handCards.add(Dealer.getCard());
    }
    
    public void pickUpPile()
    {
        List<Card> Deck = Dealer.getTableDeck();
        for (int i=0; i<Deck.size(); i++)
        {
            this.handCards.add(Deck.get(i));
        }
        Dealer.clearPile();
    } //pickUp method for Player class
    
    public int hasCards()
    {
        int handnum = this.handCards.size();
        int tablenum = this.tableCards.size();
        int numCards = handnum + tablenum;
        return numCards;
    } //hasCards method for Player cla
    
    
    @Override
    public String toString()
    {
        return String.format(this.name);
    } //toString method for Player class
    
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
     * Make a play.Remove cards from your hand, and the game overseer handles
     * what happens depending on what card(s) are played.
     * 
     */
    public void play(int i) 
    {
        Dealer.addCard(this.handCards.get(i-1));
        this.handCards.remove(i-1);
    }
    
}
