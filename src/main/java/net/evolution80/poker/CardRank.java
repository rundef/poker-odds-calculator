package net.evolution80.poker;

public class CardRank implements Comparable<CardRank>  {
	public final static int TWO 	= 2;
	public final static int THREE 	= 3;
	public final static int FOUR 	= 4;
	public final static int FIVE 	= 5;
	public final static int SIX 	= 6;
	public final static int SEVEN 	= 7;
	public final static int EIGHT 	= 8;
	public final static int NINE 	= 9;
	public final static int TEN 	= 10;
	public final static int JACK 	= 11;
	public final static int QUEEN 	= 12;
	public final static int KING 	= 13;
	public final static int ACE 	= 14;


	int mCardRank;
	public CardRank(int cardRank) {
		mCardRank = cardRank;
	}


	@Override
	public int compareTo(CardRank hr) {
		return this.ordinal() - hr.ordinal();
	}


	@Override
	public boolean equals(Object other){
		if (other == null) return false;
		if (!(other instanceof CardRank)) return false;

		CardRank otherRank = (CardRank)other;
		return (otherRank.ordinal() == this.ordinal());
	}



	public int ordinal() {
		return mCardRank;
	}


	public int value() {
		return mCardRank;
	}


	@Override
	public String toString() {
		String strRank;

		if(mCardRank == 10)				strRank = "T";
		else if(mCardRank == 11)		strRank = "J";
		else if(mCardRank == 12)		strRank = "Q";
		else if(mCardRank == 13)		strRank = "K";
		else if(mCardRank == 14)		strRank = "A";
		else							strRank = String.valueOf(mCardRank);

		return strRank;
	}


	public String toStringPlural() {
		if(mCardRank == 2)				return "twos";
		else if(mCardRank == 3)			return "threes";
		else if(mCardRank == 4)			return "fours";
		else if(mCardRank == 5)			return "fives";
		else if(mCardRank == 6)			return "sixes";
		else if(mCardRank == 7)			return "sevens";
		else if(mCardRank == 8)			return "eights";
		else if(mCardRank == 9)			return "nines";
		else if(mCardRank == 10)		return "tens";
		else if(mCardRank == 11)		return "jacks";
		else if(mCardRank == 12)		return "queens";
		else if(mCardRank == 13)		return "kings";
		else 							return "aces";
	}


	public static CardRank[] values() {
		CardRank[] allRanks = new CardRank[13];

		allRanks[0] = new CardRank(TWO);
		allRanks[1] = new CardRank(THREE);
		allRanks[2] = new CardRank(FOUR);
		allRanks[3] = new CardRank(FIVE);
		allRanks[4] = new CardRank(SIX);
		allRanks[5] = new CardRank(SEVEN);
		allRanks[6] = new CardRank(EIGHT);
		allRanks[7] = new CardRank(NINE);
		allRanks[8] = new CardRank(TEN);
		allRanks[9] = new CardRank(JACK);
		allRanks[10] = new CardRank(QUEEN);
		allRanks[11] = new CardRank(KING);
		allRanks[12] = new CardRank(ACE);

		return allRanks;
	}
}