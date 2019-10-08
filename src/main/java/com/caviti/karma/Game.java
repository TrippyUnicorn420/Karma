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
            dealCards(this.Players);
            this.currentPlayer = this.Players[0];
            Dealer.initializeTableDeck();
        }
        catch (Exception e)
        {
            System.out.println("I don't understand you!!");
           /**
            *  how to repeat!!!!
            **/
        }
    }
    
    public void gameLoop(Player p)
    {
        Scanner in = new Scanner(System.in);
        int index = 0; int playerIndex = 0;
        
        if(playerIndex > this.Players.length-1)
        {
            playerIndex -= this.Players.length;
        }
        System.out.println("It is "+ this.currentPlayer + "'s turn!");
        System.out.print("Your hand: ");
        System.out.println(this.currentPlayer.getHandCards());
        System.out.print("Top card of tableDeck: ");
        System.out.println(Dealer.showTopCard());
        System.out.println("Would you like to play a card or bust?");
        String command = in.nextLine();
        try 
        {
            String[] commands = command.split(" ");
            if ("play".equals(commands[0]))
            {
                
                int num = Integer.parseInt(commands[1]);
                if (Dealer.showTopCard().compareTo(this.currentPlayer.getHandCards().get(num)))
                {
                    this.currentPlayer.play(num);
                    
                   /////TO DO! 
                }
                else
                {
                    System.out.println("You cannot play that card!!"); 
                }
            }
                
            if (this.currentPlayer.getHandCards().size() < 3)
            {
                this.currentPlayer.pickUp();
            }
            
        }
        catch(Exception e)
        {
            if (command.equals("bust"))
            {
                this.currentPlayer.pickUpPile();
                Dealer.initializeTableDeck();
            }
            else
            {
                System.out.println("I don't understand you!!");
            }
        }
        
        List<Card> Deck = Dealer.getTableDeck();
        System.out.println(Deck.get(Deck.size()-1));
        
        if (this.currentPlayer.hasCards() == 0)
        {
            this.endGame = true;
        }
        this.currentPlayer = this.Players[playerIndex];
        this.endGame = false;
    }
    
}
