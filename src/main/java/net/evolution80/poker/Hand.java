package net.evolution80.poker;

import java.util.ArrayList;


public class Hand {
	private ArrayList<Card> mCards;
	private HandRanking mRanking;


	public Hand(Card c1, Card c2) {
		mCards = new ArrayList<Card>();
		mCards.add(c1);
		mCards.add(c2);

		mRanking = null;
	}


	public Card getCard(int index) {
		return mCards.get(index);
	}



	// assume the value variable will be trimmed
	public static Hand fromString(String value) throws Exception {
		int length = value.length();
		if(length < 4)
			throw new Exception(String.format("Invalid card value: %s", value));

		String strCard1 = value.substring(0, 2);
		String strCard2 = value.substring(length - 2);

		return new Hand(Card.fromString(strCard1), Card.fromString(strCard2));
	}


	@Override
	public String toString() {
		String output = "";
		for (Card card : mCards)
			output = String.format("%s %s", output, card.toString());
		return output.trim();
	}
}