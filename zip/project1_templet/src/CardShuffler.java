import java.util.*;

public class CardShuffler {
	
	public static List<FaceAndSuit> shuffle() {
		Face[] faces = Face.values();
		Suit[] suits = Suit.values();
		List<FaceAndSuit> keys = generateKeyList(faces, suits);
		return shuffleKeyList(keys);
	}

	private static List<FaceAndSuit> generateKeyList(Face[] faces, Suit[] suits) {
		List<FaceAndSuit> keyList = new ArrayList<>();
		for (Suit suit : suits) {
			for (Face face : faces) {
				keyList.add(new FaceAndSuit(face, suit));
			}
		}
		return keyList;
	}

	//note: this removes all items from the input list!
	private static List<FaceAndSuit> shuffleKeyList(List<FaceAndSuit> input) {
		List<FaceAndSuit> output = new ArrayList<>();
		Random random =  new Random();

		while(!input.isEmpty()) {
			int index = random.nextInt(input.size());
			output.add(input.remove(index));
		}
		return output;
	}

}