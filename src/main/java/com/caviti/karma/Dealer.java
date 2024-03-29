package com.caviti.karma;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * This is the dealer class. Handles creating decks, shuffling cards, and 
 * dealing cards to players.
 * 
 * @author Siku Mahayi, g19m0934@campus.ru.ac.za
 */
public class Dealer {
    
    private static List<Card> currentDeck;
    private static List<Card> tableDeck;
    private static List<Card> discardDeck;
    
    /**
     * Calculates the Shannon entropy for a given deck of cards. This method is
     * <b>for internal use only</b> and should not be used outside this class.
     * 
     * @param cards: your deck of cards
     * @return Shannon: the entropy for this deck
     */
    private static double getShannon(List<Card> cards) {
        int[] cardIndices = new int[52];
        for (int i = 0; i < cards.size(); i++) {
            cardIndices[i] = cards.get(i).getIndex();
        }
        int[] bins = new int[52];
        double shannon = 0.0;
        for (int i = 0; i < 52; i++) {
            int diff = cardIndices[i] - cardIndices[(i + 1) % 52];
            if (diff < 0)
                diff += 52;
            bins[diff]++;
        }
        for (int i = 0; i < 52; i++) {
            if (bins[i] > 0) {
                double p = bins[i] / 52.0;
                shannon -= p * Math.log(p)/Math.log(2.0);
            }
        }
        return shannon;
    }
    
    /**
     * Generates a deck of cards in the following order:
     * - Spades, A - 2 - ... - J - Q - K
     * - Diamonds, A - 2 - ... - J - Q - K
     * - Hearts, A - 2 - ... - J - Q - K
     * - Clubs, A - 2 - ... - J - Q - K
     * 
     * The cards are ordered and are therefore not shuffled. Use
     * getDeckOfCards() if you want a shuffled deck of cards.
     * 
     * @return cards: List of cards, not shuffled or anything
     */
    private static List<Card> makeCards() {
        List<Card> cards = new LinkedList<>();
        for (int i: new int[]{Card.SPADES, Card.DIAMONDS, Card.HEARTS, Card.CLUBS}) {
            for (String s: new String[]{"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"}) {
                cards.add(new Card(i, s, false));
            }
        }
        Dealer.currentDeck = cards;
        return cards;
    }
    
    /**
     * Performs a riffle shuffle on a deck of cards.
     * 
     * @param unshuffled: the deck you want to shuffle
     * @return newCards: your deck but shuffled
     */
    public static List<Card> riffleShuffle(List<Card> unshuffled) {
        Card[] cards = unshuffled.toArray(new Card[0]);
        List<Card> newCards = new LinkedList<>();
        Random random = new Random();
        
        int split = 20 + random.nextInt(10);
        Card[] firstHalf = new Card[split];
        Card[] secondHalf = new Card[52 - split];
        System.arraycopy(cards, 0, firstHalf, 0, firstHalf.length);
        System.arraycopy(cards, split, secondHalf, 0, secondHalf.length);
        
        int leftIndex = 0, rightIndex = 0, mainIndex = 0;
        while (newCards.size() < 52) {
            if (random.nextBoolean() && leftIndex < firstHalf.length) {
                newCards.add(firstHalf[leftIndex]);
                leftIndex++;
            }
            else if (rightIndex < secondHalf.length) {
                newCards.add(secondHalf[rightIndex]);
                rightIndex++;
            }
        }
        return newCards;
    }
    
    /**
     * This is a simple shuffling algorithm that goes through the deck and swaps
     * the current card with a random card. Copied almost line for line from my
     * task 5 of prac 9
     * 
     * @param times: how many times you want to shuffle the deck
     * @param cards: the deck you want to shuffle
     * @return newCards: your deck but shuffled
     */
    public static List<Card> simpleShuffle(int times, List<Card> cards) {
        Card[] newCards = cards.toArray(new Card[0]);
        Random random = new Random();
        for (int j = 0; j < times; j++) {
            for (int i = 0; i < newCards.length; i++) {
                int randomCard = random.nextInt(cards.size());
                Card temp = newCards[i];
                newCards[i] = newCards[randomCard];
                newCards[randomCard] = temp;
            }
        }
        Dealer.currentDeck = new LinkedList<>(Arrays.asList(newCards));
        return new LinkedList<>(Arrays.asList(newCards));
    }
    
    
    /**
     * Get a nicely shuffled deck of cards with a Shannon entropy of at least 5.
     * 
     * @return cards: a nicely shuffled deck of cards
     */
    public static List<Card> getDeckOfCards() {
        List<Card> cards = makeCards();
        while (getShannon(cards) < 5) {
            cards = riffleShuffle(cards);
            cards = simpleShuffle(1, cards);
        }
        return cards;
    }
    
    /**
     * <p>This deals cards to any players passed as arguments. Pass your players 
     * like this:</p>
     * 
     *      <code>dealCards(player1, player2, ..., playern);</code>
     * 
     * <p>Basically what I'm trying to say is that there aren't a fixed number of
     * arguments for this method. Pass as many arguments as you have players.</p>
     * 
     * The above was changed to just passing an array of Player objects.
     * 
     * @param players: your players.
     */
 
    
    public static void dealCards(Player[] players) {
        List<Card> cards = getDeckOfCards();
        for (Player player : players) {
            Card[] handCards = new Card[3];
            Card[] tableCards = new Card[6];
            for (int i = 0; i < handCards.length; i++) {
                handCards[i] = cards.get(cards.size()-1);
                cards.remove(cards.size()-1);
            }
            for (int i = 0; i < 3; i++) {
                tableCards[i] = cards.get(i);
                cards.remove(0);
            }
            for (int i = 3; i < 6; i++) {
                tableCards[i] = cards.get(cards.size()-1);
                tableCards[i].amIFaceUp(true);
                cards.remove(cards.size()-1);
            }
            player.setCards(Arrays.asList(handCards), Arrays.asList(tableCards));
        }
        Dealer.currentDeck.addAll(cards);
    }
    
    
    /**
     * Get the current deck.Ideally, you should use this after you've generated
     * a new deck and have dealt cards to players so that you can play the game
     * as normal.
     * 
     * @param c
     * @return the current deck
     */
    public static void addCard(Card c)
    {
        Dealer.tableDeck.add(c);
    }
    
    public static Card getCard()
    {
        Card c = Dealer.currentDeck.get(Dealer.currentDeck.size()-1);
        Dealer.currentDeck.remove(Dealer.currentDeck.size()-1);
        return c;
    }
    
    public static Card showTopCard()
    {
        Card c = Dealer.tableDeck.get(Dealer.tableDeck.size()-1);
        return c;
    }
    
    public static void initializeTableDeck()
    {
        List<Card> cards = Dealer.getCurrentDeck();
        List<Card> Deck = new LinkedList<>();
        Deck.add(cards.get(cards.size()-1));
        cards.remove(cards.size()-1);
       Dealer.tableDeck = Deck;
    }
    
    public static void clearPile()
    {
        Dealer.tableDeck.clear();
    }
    
    public static List<Card> getCurrentDeck() {
        return currentDeck;
    }
    
    public static List<Card> getTableDeck() 
    {
        return tableDeck;
    } //getter for tableDeck
    
    public static List<Card> getDiscardDeck() 
    {
        return discardDeck;
    } // getter for discardDeck
}
