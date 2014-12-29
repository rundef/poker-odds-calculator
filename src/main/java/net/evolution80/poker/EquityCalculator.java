package net.evolution80.poker;

import java.util.ArrayList;


public class EquityCalculator {
	private ArrayList<Card> mBoardCards;
	private ArrayList<Hand> mHands;

	private ArrayList<HandRanking> mRankings;
	private ArrayList<HandEquity> mEquities;


	public EquityCalculator() {
		this.reset();
	}


	public EquityCalculator reset() {
		mBoardCards = new ArrayList<Card>();
		mHands 		= new ArrayList<Hand>();

		mRankings = new ArrayList<HandRanking>();
		mEquities = new ArrayList<HandEquity>();

		return this;
	}


	public EquityCalculator addHand(Hand h) {
		mHands.add(h);
		return this;
	}


	public EquityCalculator setBoard(Card... cards) throws Exception {
		if(cards.length > 5)
			throw new Exception("Board cannot have more than 5 cards");


		mBoardCards.clear();
		for(Card c : cards)		mBoardCards.add(c);

		return this;
	}


	public EquityCalculator setBoardFromString(String str) throws Exception {
		mBoardCards.clear();

		for(int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			if (!Character.toString(c).matches("[\\s,]")) {
				mBoardCards.add(Card.fromString(str.substring(i, i + 2)));
				i += 2;
			}
		}

		return this;
	}


	public HandRanking getHandRanking(int handIndex) {
		return mRankings.get(handIndex);
	}


	public HandEquity getHandEquity(int handIndex) {
		return mEquities.get(handIndex);
	}


	public void calculate() {
		mRankings.clear();
		mEquities.clear();


		// get the remaining cards
		ArrayList<Card> remainingCards = new ArrayList<Card>();
		for(Card.Suit suit : Card.Suit.values()) {
			for(CardRank rank : CardRank.values()) {
				Card newCard 		= new Card(suit, rank);
				boolean isCardUsed 	= false;

				for(Card c : mBoardCards) {
					if(c.equals(newCard)) {
						isCardUsed = true;
						break;
					}
				}

				if(isCardUsed)		continue;

				for(Hand h : mHands) {
					if(h.getCard(0).equals(newCard) || h.getCard(1).equals(newCard)) {
						isCardUsed = true;
						break;
					}
				}

				if(isCardUsed)		continue;


				remainingCards.add(newCard);
			}
		}


		int boardCount = mBoardCards.size();
		int handsCount = mHands.size();
		if(boardCount == 0) {
			// ~200 millions possibilites
		}
		else if(boardCount == 3) {
			// ~2250 possibilites
			
			mBoardCards.add(null);
			mBoardCards.add(null);

			mBoardCards.remove(4);
			mBoardCards.remove(3);
		}

		// 1 card left
		else if(boardCount == 4) {
			for(int i = 0; i < handsCount; i++) {
				Hand h = mHands.get(i);
				HandRanking hr = HandRanking.evaluate(h.getCard(0), h.getCard(1), mBoardCards.get(0), mBoardCards.get(1), mBoardCards.get(2), mBoardCards.get(3));
				mRankings.add(hr);


				HandEquity he = new HandEquity();
				mEquities.add(he);
			}


			mBoardCards.add(null);
			for(Card c : remainingCards) {
				mBoardCards.set(4, c);

				HandRanking highestRanking = null;
				int highestRankingIndex = -1;

				for(int i = 0; i < handsCount; i++) {
					Hand h = mHands.get(i);
					HandRanking hr = HandRanking.evaluate(h.getCard(0), h.getCard(1), mBoardCards.get(0), mBoardCards.get(1), mBoardCards.get(2), mBoardCards.get(3), mBoardCards.get(4));

					if(highestRanking == null || hr.compareTo(highestRanking) >= 0) {
						highestRankingIndex = i;
						highestRanking = hr;
					}

					mRankings.add(hr);
				}

				for(int i = 0; i < handsCount; i++) {
					mEquities.get(i).addPossibleHand(i == highestRankingIndex);
				}
			}
			mBoardCards.remove(4);
		}

		// full board
		else if(boardCount == 5) {
			HandRanking highestRanking = null;
			int highestRankingIndex = -1;

			for(int i = 0; i < handsCount; i++) {
				Hand h = mHands.get(i);
				HandRanking hr = HandRanking.evaluate(h.getCard(0), h.getCard(1), mBoardCards.get(0), mBoardCards.get(1), mBoardCards.get(2), mBoardCards.get(3), mBoardCards.get(4));

				if(highestRanking == null || hr.compareTo(highestRanking) >= 0) {
					highestRankingIndex = i;
					highestRanking = hr;
				}

				mRankings.add(hr);
			}

			
			for(int i = 0; i < handsCount; i++) {
				HandEquity he = new HandEquity();
				he.addPossibleHand(i == highestRankingIndex);

				mEquities.add(he);
			}
		}
	}
}