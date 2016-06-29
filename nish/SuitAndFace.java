import java.util.Objects;

public class SuitAndFace {

	public enum Suit {diamonds, hearts, spades, clubs};
	public enum Face {King, Queen, Jack, Ten, Nine, Eight, Seven, Six, Five, Four, Three, Two, Ace}; 

	public Suit suit;
	private Face face;
	
	public SuitAndFace(Suit suit, Face face){
		this.suit = suit;
		this.face = face;
	}
	
	 public Suit getSuit(){
        return suit;
    }

	 public Face getFace(){
        return face;
    }
	
	@Override
    public String toString() {
        return face + " of " + suit;
    }

	@Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.suit);
        hash = 97 * hash + Objects.hashCode(this.face);
        return hash;
    }
	
	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SuitAndFace card = (SuitAndFace) obj;
		
        if (this.suit != card.suit) {
            return false;
        }
        if (this.face != card.face) {
            return false;
        }
        return true;
    }

	
}