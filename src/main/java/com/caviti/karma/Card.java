package com.caviti.karma;

/**
 * Code for the Card object.
 * 
 * @author Siku Mahayi, g19m0934@campus.ru.ac.za
 */
public class Card {

    private int suit;
    private String value;
    private boolean isFaceUp;
    
    public static final int SPADES = 0;
    public static final int DIAMONDS = 1;
    public static final int HEARTS = 2;
    public static final int CLUBS = 3;

    public Card(int suit, String value, boolean isFaceUp) {
        this.suit = suit;
        this.value = value;
        this.isFaceUp = isFaceUp;
    }

    /**
     * Gets the suit of this card as a String.
     * 
     * @return suit: this card's suit
     */
    public String getSuit() {
        switch (suit) {
            case 0:
                return "Spades";
            case 1:
                return "Diamonds";
            case 2:
                return "Hearts";
            case 3:
                return "Clubs";
            default:
                return "";
        }
    }
    
    /**
     * Gets the index of the card in the deck. So an ace of spades would be 1, 
     * a queen of hearts would be 37. Unless you're trying to calculate deck
     * entropy, you probably shouldn't be using this method.
     * 
     * @return index: the index of the card in the deck.
     */
    public int getIndex() {
        return (suit * 13) + getRawValue();
    }


    /**
     * Get the number on the card for whatever reason.
     * 
     * @return value: the number on the card.
     */
    public String getValue() {
        switch (value) {
            case "A":
                return "Ace";
            case "J":
                return "Jack";
            case "Q":
                return "Queen";
            case "K":
                return "King";
            default:
                return value;
        }
    }
    
    
    /**
     * This is used for calculation of entropy by the dealer. Unless you're the
     * dealer, don't use this.
     * 
     * @return the raw value of this card
     */
    private int getRawValue() {
        switch (value) {
            case "A":
                return 0;
            case "J":
                return 10;
            case "Q":
                return 11;
            case "K":
                return 12;
            default:
                return Integer.parseInt(value) - 1;
        }
    }
    
    /**
     * Set if this card is face up or not.
     * 
     * @param wellAmI: Well, is it face up or not?
     */
    public void amIFaceUp(boolean wellAmI) {
        this.isFaceUp = wellAmI;
    }

    /**
     * Find out if this card is face up or not.
     * 
     * @return isFaceUp: if this card is face up or not.
     */
    public boolean isFaceUp() {
        return isFaceUp;
    }
    
    public boolean compareTo(Card c)
    {
        try
        {
            int x = Integer.parseInt(this.getValue());
            int y = Integer.parseInt(c.getValue());
            
            return x >= y;
        }
        catch (NumberFormatException e)
        {
            String[] Vals = {"K", "Q", "J", "A"};
            int x = 0; int y = 0;
            for (int i=11; i<Vals.length+11; i++)
            {
                if (this.getValue().equals(Vals[i-11]))
                {
                    x = i;
                }   
                if (c.getValue().equals(Vals[i-11]))
                {
                    y = i;
                }
            }
            return x >= y;
        }
    }
    
    @Override
    public String toString() {
        return getValue() + " of " + getSuit();
    }
    
}
