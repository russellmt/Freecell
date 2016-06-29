public class FaceAndSuit {

	private Face face;
	private Suit suit;

	public FaceAndSuit(Face face, Suit suit) {
		this.face = face;
		this.suit = suit;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof FaceAndSuit) {
			FaceAndSuit other = (FaceAndSuit) o;
			return other.getFace() == face && other.getSuit() == suit;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return face.hashCode() * suit.hashCode();
	}

	@Override
	public String toString() {
		return face.toString() + " of " + suit.toString();
	}

	public Face getFace() {
		return face;
	}

	public Suit getSuit() {
		return suit;
	}

	public static FaceAndSuit parseFaceAndSuit(String imageName) {
		String name = imageName.toLowerCase();
		Suit suit = null;
		Face face = null;

        name = cleanCardImageName(name);

		if (name.startsWith("ace")) {
			face = Face.Ace;
		} else if (name.startsWith("2")) {
			face = Face.Two;
		} else if (name.startsWith("3")) {
			face = Face.Three;
		} else if (name.startsWith("4")) {
			face = Face.Four;
		} else if (name.startsWith("5")) {
			face = Face.Five;
		} else if (name.startsWith("6")) {
			face = Face.Six;
		} else if (name.startsWith("7")) {
			face = Face.Seven;
		} else if (name.startsWith("8")) {
			face = Face.Eight;
		} else if (name.startsWith("9")) {
			face = Face.Nine;
		} else if (name.startsWith("10")) {
			face = Face.Ten;
		} else if (name.startsWith("jack")) {
			face = Face.Jack;
		} else if (name.startsWith("queen")) {
			face = Face.Queen;
		} else if (name.startsWith("king")) {
			face = Face.King;
		}

		if (name.contains("hearts")) {
			suit = Suit.Hearts;
		} else if (name.contains("diamonds")) {
			suit = Suit.Diamonds;
		} else if (name.contains("spades")) {
			suit = Suit.Spades;
		} else if (name.contains("clubs")) {
			suit = Suit.Clubs;
		}

		return new FaceAndSuit(face, suit);
	}

    private static String cleanCardImageName(String name) {
        if (name.startsWith(CardLoader.directoryPath) || name.startsWith("/" + CardLoader.directoryPath)) {
            return name.replace("res", "").replace("/", "");
        }
        return name;
    }
}