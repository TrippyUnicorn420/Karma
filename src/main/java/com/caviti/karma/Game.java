package com.caviti.karma;

import static com.caviti.karma.Dealer.dealCards;
import java.util.*;

/**
 *
 * This is the game class, it creates the game object which initializes
 * the game and runs the game loop.
 * @author Timothy Fischer, g19F7919
 */

public class Game 
{
    private boolean endGame;
    private Player currentPlayer;
    private Player[] Players;
    
    public static void main(String[] args)
    {
        Game Karma = new Game();
        
        while (Karma.endGame == false)
        {
            Karma.gameLoop(Karma.currentPlayer);
        }
    }
    
    public Game()
    {
        try
        {
            Scanner in = new Scanner(System.in);
            System.out.println("How many players???");
            int numPlayers = in.nextInt();
            Player[] thePlayers = new Player[numPlayers];
               for (int i=0; i<numPlayers; i++)
                {
                    System.out.println("Enter player name!!");
                    String playerName = in.next();
                    Player newPlayer = new Player(playerName);
                    thePlayers[i] = newPlayer;
                }
            this.Players = thePlayers;
            dealCards(Players);
            this.currentPlayer = this.Players[0];
        }
        catch (NumberFormatException e)
        {
            System.out.println("Please enter a number!!!");
           /**
            *  how to repeat!!!!
            **/
        }
    }
    
    public void gameLoop(Player p)
    {
        Scanner in = new Scanner(System.in);
        int index = 0; int playerIndex = 0;
        playerIndex += 1;
        if(playerIndex > this.Players.length)
        {
            playerIndex -= 3;
        }

        System.out.println("");
        String command = in.nextLine();
        
        switch (command)
        {
            case (""):
                
                
            case ("x"):
               this.currentPlayer.pickUp();
                
        }
        
        if (this.currentPlayer.hasCards() == 0)
        {
            this.endGame = true;
        }
        this.currentPlayer = this.Players[playerIndex];
        this.endGame = false;
    }
    
    /*
     * The below methods belong to other classes and will be moved 
     * there at a later date. 
     */ 
    /*
    
    public String toString()
    {
        return String.format("Player: %s" , this.playerName);
    } //toString method for Player class
    
    public int hasCards()
    {
        handnum = this.handCards.size();
        tablenum = this.tableCards.size();
        numCards = handnum + tablenum;
        return numCards;
    } //hasCards method for Player class
    
    public void pickUp()
    {
        this.handCards.addAll(getTableDeck());
    } //pickUp method for Player class
    
    public static List<Card> getTableDeck() 
    {
        return tableDeck;
    } //getter for tableDeck
    
    public static List<Card> getDiscardDeck() 
    {
        return discardDeck;
    } // getter for discardDeck
    
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
    } //compareTo for the Card class
    */
}
