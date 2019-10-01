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
     * Performs a riffle shuffle on a deck of cards. This is copied pretty much
     * line for line from my task 3 of prac 9.
     * 
     * @param times: how many times you want to shuffle the deck
     * @param cards: the deck you want to shuffle
     * @return newCards: your deck but shuffled
     */
    public static List<Card> riffleShuffle(int times, List<Card> cards) {
        List<Card> newCards = List.copyOf(cards);
        Random random = new Random();

        for (int i = 0; i < times; i++) {
            int split = 20 + random.nextInt(10);
            Card[] cards_split1 = new Card[split];
            Card[] cards_split2 = new Card[52-split];
            
            for (int idx = 0; i < cards_split1.length; i++) {
                cards_split1[idx] = newCards.get(idx);
            }
            
            for (int idx = 0; i < cards_split2.length; i++) {
                cards_split1[idx] = newCards.get(split + idx);
            }
            
            int index = 0;
            for (int j = 0; j < 52; j++) {
                if (index > split) {
                    if (cards_split1.length > cards_split2.length) {
                        for (int k = 0; k < cards_split1.length - cards_split2.length; k++) {
                            newCards.add(cards.get(index));
                            index++;
                        }
                    }
                    else {
                        for (int k = 0; k < cards_split2.length - cards_split1.length; k++) {
                            newCards.add(cards.get(index));
                            index++;
                        }
                    }
                    break;
                }

                if (random.nextBoolean()) {
                    for (int k = 0; k < random.nextInt(3); k++) {
                        if (index >= cards_split1.length) break;
                        newCards.add(cards_split1[index]);
                        index++;
                    }
                }
                else {
                    for (int k = 0; k < random.nextInt(3); k++) {
                        if (index >= cards_split2.length) break;
                        newCards.add(cards_split2[index]);
                        index++;
                    }
                }
            }
        }
        Dealer.currentDeck = newCards;
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
        Dealer.currentDeck = Arrays.asList(newCards);
        return Arrays.asList(newCards);
    }
    
    
    /**
     * Get a nicely shuffled deck of cards with a Shannon entropy of at least 5.
     * 
     * @return cards: a nicely shuffled deck of cards
     */
    public static List<Card> getDeckOfCards() {
        List<Card> cards = makeCards();
        while (getShannon(cards) < 5) {
            simpleShuffle(1, cards);
            riffleShuffle(2, cards);
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
     * @param players: your players.
     */
    public static void dealCards(Player... players) {
        List<Card> cards = getDeckOfCards();
        for (int j = 0; j < players.length; j++) {
            Card[] handCards = new Card[3];
            Card[] tableCards = new Card[6];
            for (int i = 0; i < handCards.length; i++) {
                handCards[i] = cards.get(i);
                cards.remove(0);
            }
            for (int i = 0; i < 3; i++) {
                tableCards[i] = cards.get(i);
                cards.remove(0);
            }
            for (int i = 3; i < 6; i++) {
                tableCards[i] = cards.get(i);
                tableCards[i].amIFaceUp(true);
                cards.remove(0);
            }
            players[j].setCards(Arrays.asList(handCards), Arrays.asList(tableCards));
        }
        Dealer.currentDeck = cards;
    }
    
    /**
     * Get the current deck. Ideally, you should use this after you've generated
     * a new deck and have dealt cards to players so that you can play the game
     * as normal.
     * 
     * @return 
     */
    public static List<Card> getCurrentDeck() {
        return currentDeck;
    }
}
