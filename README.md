BattleshipOnline
================
StarTrek Battleship Applet
By: Mike Cutalo

To start the game:
        1) Go to my webpage bill.kutztown.edu/~mcuta697
        2) In my menu on the left hand side you will see a section for your class.
           There will be two links here one for the StarTrek Javadoc documentation for my game.
           The other is a link to the games itself (StarTrek Battleship), simply click the link and the you
  will be taken to the GameStart page, this is where you will be able to select your game mode.
	Either Human vs Borg (computer) or Human vs Human game mode.
        3) You can also download the code and compile it youself, remember you must have a browser that has
        the java applet plugin.

Multiplayer:

	My server is called ClientServer.java running this code on bill will start the server. This server has the 
	ability to process concurrent games. Once the server has two players it will start the game and continue to listen
	for two more players to join the lobby. So far I have tested three concurrent games (9 clients) and it was working nicely. 

Placing your ships:
      Placing your ships is simple and straight forward here are the steps to place a ship. You are not able to select 
      a ship to place. The game shows you on the bottom of the applet what ship you are currently placing.

      1) Select the first point on the human board to place your ship; this acts as the anchor of the ship.
      2) Now you will see the board has displayed the possible places you may place your ship.
      3) If you wish to put your ship in a different spot, simply click back on the anchor which is the space you first clicked.
      4) Once you decide where you want to lay your ship down you may click a valid end point. Again these end points are displayed to you on the board.
      5) If the location you are trying to place your ship is invalid, the boat will be removed and you can start over placing that ship.

Game Start:
      When all ships have been places the game will start, you will be prompted that it has indeed started.
      Since you are competing against the computer, you will place your shots on the computers board. 
      Simply click on a location to fire a shot! The board will change icons dictated by the result of the shot.

Right Click:
      You can right click anywhere on the computers board to display a drop-down menu. 
      This menu will have two options, computer statics and human statics. Clicking either of them will 
      create a pop-up box that will display, number of turn, hits, misses as well as the number of ships the player has sunk.

Sunken Ships:
      As the game is progressing the sunken ships will appear as needed underneath the respected boards. 
      For example if the computer sinks a human ship, the ship that was sunk will be displayed under the humans board. 
      Likewise a human sinking a computer ship will display under the computers board. 
      Also when a ship is completely sunk the icons on the board will change to indicate it is dead. 

Interpreting the Grid:
      The grid is very straight forward I will briefly explain what each icons resembles.
        
      Space icon w/ Stars: Indicates a place has not been selected yet.
      Black: Indicates a place has been selected and it was a MISS.
      Explosion: Indicates a ship has been HIT in that location. 
      Skulls: Indicate the ship in that location has been SUNK.

      For the computers board when the human player is hovering over a spot that has not been MISSED or HIT or SUNK 
      it will change the icon to a target image, this indicates that you may fire upon this spot.
      If the human does not decide to fire in that location as soon as the mouse leave the 
      spot the image will return back to its normal Light Blue Water state.

Cheats:
      As you are playing the game the computers board will keep outputting to the console. 
      It will display ever time a human player hits a computer ship. This is simply because I 
      did not want to flood the console with the computers board. But the reason I also do this every 
      time a human hits a ship is because the console will display cache information and 
      I wanted to make sure the board is always available to view in the console.

Restarting The Game:
	When you finish a game the winner and loser are announced, a video will play and once
	the video is complete the game will ask you to play again. Both options yes and no go back to the
	GameStart page where you are able to pick Human vs Computer or Human vs Human. I do this because what
	if the user wants to play in a different game mode? But if you select the Human vs Human option you will be
	thrown back in the server queue and wait for an opponent. Like i said before my server handles concurrent games.
	

Odd Behavior:
      Some time after you place all of your ships on your grid the applet looks like it re sizes and cuts off
      the bottom of the applet. 

      Fix:
      If the applet does look like it resized and looks like it has stretched, simply click somewhere out of the applet
      then click the applet again and the applet goes back to its original size.

      I rather you be aware then not tell you at all, because if the applet does stretch from its original size. You
      can't see any of the sunken ships for either player, since I display them on the bottom of the applet. Well I hope
      this doesn't happen to you, again I think it is a macintosh thing because with a little research that seems to 
      be the case.
