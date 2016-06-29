RUN:
To run FreeCell, start bin/freecell.jar
To run PlayingCardManager, start bin/cards.jar #
	the # argument is 1 - 5, based on the task number (5 will run FreeCell)

NOTES:
For this project, I decided to figure out how to implement drag/drop in Java Swing using movable card panels to actually display the dragging. It was odd, but I ended up finding a method, setGlassPane, for the JFrame, which allows moving of panels to be drawn in front of the entire JFrame. This happens upon drag. Upon drop, I remove the card panel from the glass pane and add it back to the stack pane if the drop is allowed.

I used a hierarchical structure for building the game GUI. Although, it took much longer, I feel this was a decent object-oriented approach. The game screen consisted of a FreeCellPanel, which was a subclass of my DragZone panel, used in parts 3 and 4 of the playing card manager tasks. The contents of this outer panel are three “group” panels which are all subclasses of CardStackPanel. These are CascadePanel, FoundationPanel, and FreeCellSpacePanel. These all contains some number (4 or 8) of CardStacks. CardStack is the abstract parent class of the Foundation, FreeCellSpace, and Cascade. The logic for determining whether a drag or drop is allowed is built unto these classes. The CardStack parent class defines these as abstract methods to be overriden. Each card stack contains some list of FreeCellCards, whose position is fixed, except for the Cascade (overriden), where the card positions are based on index.

To determine which component the dragged card was currently over, I dug around and found SwingUtilities.getDeepestComponentAt, where the starting outer-most component and mouse coordinates are given. This returns the deepest nested component the mouse is currently over. This was called on the mouseReleased method of the mouse listener for the FreeCellCard. Because the card follows the mouse on drag, I was able to add the mouse drag listener to the cards themselves instead of the outer container. I then got the deepest component and checked if it was either a card or a card stack. I then got the card stack and checked value of the last card in its list against the dragged card or first card of the dragged tableau. To move a tableau, I just called the mouse drag logic for the next card in sequence (from the dragged card) until there were no more cards above in the stack. Despite use of getDeepestComponentAt, the drop over the foundation or free cell doesn’t always work unless you are quite accurate. I’d suggest dragging from the center of a card. As for dropping over cascades, the Cascade panel is the entire height of the border surrounding it, so these drops should always work. 

Finally, I ended up adding a static class, GameController in order to save time and effort in order to retrieve the currently dragged cards from a different scope and to determine the overall state of the game and game components. This may not be the cleanest implementation choice, but it seems to work for this project.

In the end, this implementation did not use the suggested 2D list of cards, but instead maintained a similar set of data structures in a more separated manner.
I had to add some additional messy code from Stack Overflow to allow these programs to run from either a self-contained jar OR from the class files. Loading all images in a jar directory (not actual files) was particularly tricky.

UPDATE 6/28/2016: the drop logic seems to be broken now, rendering the game unplayable. A fix will come soon.
