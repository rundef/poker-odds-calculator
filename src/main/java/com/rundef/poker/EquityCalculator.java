package com.rundef.poker;

import java.util.ArrayList;
import java.lang.System;
import java.util.Random;


public class EquityCalculator {
	private ArrayList<Card> mBoardCards;
	private ArrayList<Hand> mHands;

	private ArrayList<HandRanking> mRankings;
	private ArrayList<HandEquity> mEquities;
	private long mSeed;
	private long mMaxIterations;


	public EquityCalculator() {
		this.reset();

		mMaxIterations = 200000;
	}


	public EquityCalculator reset() {
		mBoardCards = new ArrayList<Card>();
		mHands 		= new ArrayList<Hand>();

		mRankings = new ArrayList<HandRanking>();
		mEquities = new ArrayList<HandEquity>();

		mSeed 	= System.currentTimeMillis();

		return this;
	}

	public void setSeed(long seed) {
		mSeed = seed;
	}

	public void setMaxIterations(long iterations) {
		mMaxIterations = iterations;
	}

	public long getMaxIterations() {
		return mMaxIterations;
	}


	public EquityCalculator addHand(Hand h) {
		mHands.add(h);
		return this;
	}


	public EquityCalculator setBoard(Card... cards) throws Exception {
		if(cards.length != 3 && cards.length != 4 && cards.length != 5)
			throw new Exception("Board must contain 3, 4 or 5 cards");


		mBoardCards.clear();
		for(Card c : cards)		mBoardCards.add(c);

		return this;
	}


	public EquityCalculator setBoardFromString(String str) throws Exception {
		mBoardCards.clear();

		str = str.replaceAll("\\s","").replaceAll(",","");
		for(int i = 0; i+1 < str.length(); i += 2) {
			mBoardCards.add(Card.fromString(str.substring(i, i + 2)));
		}

		if(mBoardCards.size() != 3 && mBoardCards.size() != 4 && mBoardCards.size() != 5)
			throw new Exception("Board must contain 3, 4 or 5 cards");

		return this;
	}


	public boolean boardIsEmpty() {
		return mBoardCards.isEmpty();
	}


	public void printBoard() {
		if(boardIsEmpty()) {
			System.out.println("Board : no cards");
		}
		else {
			String boardStr = "";
			for(Card c : mBoardCards) {
				if(!boardStr.isEmpty()) {
					boardStr += " - ";
				}
				boardStr += String.format("%s", c);
			}
			System.out.println(String.format("Board: %s", boardStr));
		}
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
		int remainingCount = remainingCards.size();
		if(boardCount == 0) {
			// ~200 millions possibilities
			for(int i = 0; i < handsCount; i++) {
				HandEquity he = new HandEquity();
				mEquities.add(he);

				Hand h = mHands.get(i);
				HandRanking hr = HandRanking.evaluate(h.getCard(0), h.getCard(1));
				mRankings.add(hr);
			}


			mBoardCards.add(null);
			mBoardCards.add(null);
			mBoardCards.add(null);
			mBoardCards.add(null);
			mBoardCards.add(null);


			Random generator = new Random(mSeed);
			for(long iterations = mMaxIterations; iterations > 0; iterations--) {
				int card1Index = generator.nextInt(remainingCount);
				int card2Index, card3Index, card4Index, card5Index;

				do {
					card2Index = generator.nextInt(remainingCount);
				} while(card2Index == card1Index);

				do {
					card3Index = generator.nextInt(remainingCount);
				} while(card3Index == card2Index || card3Index == card1Index);

				do {
					card4Index = generator.nextInt(remainingCount);
				} while(card4Index == card3Index || card4Index == card2Index || card4Index == card1Index);

				do {
					card5Index = generator.nextInt(remainingCount);
				} while(card5Index == card4Index || card5Index == card3Index || card5Index == card2Index || card5Index == card1Index);
				

				mBoardCards.set(0, remainingCards.get(card1Index));
				mBoardCards.set(1, remainingCards.get(card2Index));
				mBoardCards.set(2, remainingCards.get(card3Index));
				mBoardCards.set(3, remainingCards.get(card4Index));
				mBoardCards.set(4, remainingCards.get(card5Index));

				HandRanking highestRanking = null;
				int highestRankingIndex = -1;

				for(int z = 0; z < handsCount; z++) {
					Hand h = mHands.get(z);
					HandRanking hr = HandRanking.evaluate(h.getCard(0), h.getCard(1), mBoardCards.get(0), mBoardCards.get(1), mBoardCards.get(2), mBoardCards.get(3), mBoardCards.get(4));

					if(highestRanking == null || hr.compareTo(highestRanking) >= 0) {
						highestRankingIndex = z;
						highestRanking = hr;
					}
				}

				for(int z = 0; z < handsCount; z++) {
					mEquities.get(z).addPossibleHand(z == highestRankingIndex);
				}
			}

			mBoardCards.clear();
		}
		else if(boardCount == 3) {
			// ~2250 possibilities
			for(int i = 0; i < handsCount; i++) {
				HandEquity he = new HandEquity();
				mEquities.add(he);

				Hand h = mHands.get(i);
				HandRanking hr = HandRanking.evaluate(h.getCard(0), h.getCard(1), mBoardCards.get(0), mBoardCards.get(1), mBoardCards.get(2));
				mRankings.add(hr);
			}


			mBoardCards.add(null);
			mBoardCards.add(null);

			for(int i = 0; i < remainingCount; i++) {
				Card c1 = remainingCards.get(i);

				for(int j = i + 1; j < remainingCount; j++) {
					Card c2 = remainingCards.get(j);

					mBoardCards.set(3, c1);
					mBoardCards.set(4, c2);

					HandRanking highestRanking = null;
					int highestRankingIndex = -1;

					for(int k = 0; k < handsCount; k++) {
						Hand h = mHands.get(k);
						HandRanking hr = HandRanking.evaluate(h.getCard(0), h.getCard(1), mBoardCards.get(0), mBoardCards.get(1), mBoardCards.get(2), mBoardCards.get(3), mBoardCards.get(4));

						if(highestRanking == null || hr.compareTo(highestRanking) >= 0) {
							highestRankingIndex = k;
							highestRanking = hr;
						}
					}

					for(int k = 0; k < handsCount; k++) {
						mEquities.get(k).addPossibleHand(k == highestRankingIndex);
					}
				}
			}

			mBoardCards.remove(4);
			mBoardCards.remove(3);
		}

		// 1 card left
		else if(boardCount == 4) {
			for(int i = 0; i < handsCount; i++) {
				HandEquity he = new HandEquity();
				mEquities.add(he);

				Hand h = mHands.get(i);
				HandRanking hr = HandRanking.evaluate(h.getCard(0), h.getCard(1), mBoardCards.get(0), mBoardCards.get(1), mBoardCards.get(2), mBoardCards.get(3));
				mRankings.add(hr);
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