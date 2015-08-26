package com.rundef.poker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

public class HandRanking implements Comparable<HandRanking> {
	public enum Ranking {
	    HIGH_CARD, PAIR, TWO_PAIRS, TRIPS, STRAIGHT, FLUSH, FULL_HOUSE, QUADS, STRAIGHT_FLUSH
	}



	private Ranking mRank;
	private ArrayList<CardRank> mHighCardsRanks;


	public HandRanking(Ranking rank) {
		mRank = rank;
		mHighCardsRanks = new ArrayList<CardRank>();
	}


	public Ranking getRank() {
		return mRank;
	}

	public ArrayList<CardRank> getHighCardsRanks() {
		return mHighCardsRanks;
	}

	public CardRank getHighCardRank(int index) {
		return mHighCardsRanks.get(index);
	}

	public void addHighCard(CardRank rank) {
		mHighCardsRanks.add(rank);
	}


	public static HandRanking evaluate(Card... pCards) {
		HandRanking ranking;

		ArrayList<Card> cards 		= new ArrayList<Card>();
		for (Card c : pCards) {
        	cards.add(c);
        }
        Collections.sort(cards);

		ArrayList<Card> clubCards 		= new ArrayList<Card>();
		ArrayList<Card> diamondCards 	= new ArrayList<Card>();
		ArrayList<Card> heartCards 		= new ArrayList<Card>();
        ArrayList<Card> spadeCards 		= new ArrayList<Card>();

        for (Card c : cards) {
            if (c.getSuit() == Card.Suit.CLUB)
            	clubCards.add(c);
            else if (c.getSuit() == Card.Suit.DIAMOND)
            	diamondCards.add(c);
            else if (c.getSuit() == Card.Suit.HEART)
            	heartCards.add(c);
            else if (c.getSuit() == Card.Suit.SPADE)
            	spadeCards.add(c);
        }

        
        
        boolean clubFlush = false;
        boolean diamondFlush = false;
        boolean heartFlush = false;
        boolean spadeFlush = false;
        ArrayList<Card> flushCards 		= null;

        int cardsCount = pCards.length;


		if(cardsCount >= 5) {
			// Look for straight flush
			if (clubCards.size() >= 5) {
				clubFlush = true;
				flushCards = clubCards;
			}
			else if (diamondCards.size() >= 5) {
				diamondFlush = true;
				flushCards = diamondCards;
			}
			else if (heartCards.size() >= 5) {
				heartFlush = true;
				flushCards = heartCards;
			}
			else if (spadeCards.size() >= 5) {
				spadeFlush = true;
				flushCards = spadeCards;
			}

			if(flushCards != null) {
				ArrayList<CardRank> straightCards = new ArrayList<CardRank>();

				CardRank r = flushCards.get(0).getRank();
				int straightCount = 1;
				straightCards.add(r);

				for(int i = 1; i < flushCards.size(); i++) {
					int cmp = r.compareTo(flushCards.get(i).getRank());
					if(cmp != 1) {
						straightCards = new ArrayList<CardRank>();
						straightCount = 0;
					}

					r = flushCards.get(i).getRank();
					straightCount++;
					straightCards.add(r);


					if(straightCount == 5) {
						ranking = new HandRanking(Ranking.STRAIGHT_FLUSH);
						for(CardRank cr : straightCards) {
							ranking.addHighCard(cr);
						}
						return ranking;
					}
				}
			}
		}


		if(cardsCount >= 4) {
			// Look for four of a kind
			HashMap<Integer, Integer> rankCounts = new HashMap<Integer, Integer>();

			for(int i = 0; i < cardsCount; i++) {
				Integer cardRankInt = new Integer(pCards[i].getRank().value());
				Integer count = rankCounts.get(cardRankInt);

				if(count == null) 
					count = new Integer(1);
				else
					count += 1;

				rankCounts.put(cardRankInt, count);
			}

			

			for (Map.Entry<Integer, Integer> entry : rankCounts.entrySet())
			{
				if(entry.getValue() == 4) {
					CardRank quadsRank = new CardRank(entry.getKey());

					ranking = new HandRanking(Ranking.QUADS);
					ranking.addHighCard(quadsRank);
					for(Card c : cards) {
						CardRank cr = c.getRank();
						if(!cr.equals(quadsRank) && !cr.equals(quadsRank)) {
							ranking.addHighCard(cr);
							break;
						}
					}

					return ranking;
				}
			}
		}

		if(cardsCount >= 5) {
			// Look for full house
			ArrayList<CardRank> pairRanks 		= new ArrayList<CardRank>();
			ArrayList<CardRank> tripRanks 		= new ArrayList<CardRank>();


			for(int i = 0; i < cardsCount; i++) {
				for(int j = i + 1; j < cardsCount; j++) {
					boolean foundTrips = false;
					for(int k = j + 1; k < cardsCount; k++) {
						if(pCards[i].getRank().equals(pCards[j].getRank()) && 
							pCards[i].getRank().equals(pCards[k].getRank()) && 
							!tripRanks.contains(pCards[i].getRank())) {

							tripRanks.add(pCards[i].getRank());
							foundTrips = true;
						}
					}


					if(!foundTrips && pCards[i].getRank().equals(pCards[j].getRank()) && !pairRanks.contains(pCards[i].getRank()) && !tripRanks.contains(pCards[i].getRank())) {
						pairRanks.add(pCards[i].getRank());
					}
				}
			}

			if(pairRanks.size() > 0 && tripRanks.size() > 0) {
				Collections.sort(pairRanks);

				ranking = new HandRanking(Ranking.FULL_HOUSE);
				ranking.addHighCard(tripRanks.get(0));
				ranking.addHighCard(pairRanks.get(0));
				return ranking;
			}




			// Look for flush
			if (clubFlush) 			flushCards = clubCards;
			else if (diamondFlush) 	flushCards = diamondCards;
			else if (heartFlush) 	flushCards = heartCards;
			else if (spadeFlush) 	flushCards = spadeCards;

			if(flushCards != null) {
				ranking = new HandRanking(Ranking.FLUSH);
				for (int flush = 0; flush < 5; flush++)
					ranking.addHighCard(flushCards.get(flush).getRank());
				return ranking;
			}



			// Look for straight
			for(int i = 0; i < cardsCount - 5; i++) {
				CardRank r = cards.get(i).getRank();
				int straightCount = 0;

				ArrayList<CardRank> straightCards = new ArrayList<CardRank>();
				straightCards.add(r);

				for(int j = i + 1; j < cards.size(); j++) {
					int cmp = r.compareTo(cards.get(j).getRank());

					if(cmp == 0)
						continue;
					else if(cmp != 1)
						break;

					r = cards.get(j).getRank();
					straightCards.add(r);


					straightCount++;
					if(straightCount == 4) {
						ranking = new HandRanking(Ranking.STRAIGHT);
						for(CardRank cr : straightCards) {
							ranking.addHighCard(cr);
						}
						return ranking;
					}
				}
			}
		}



		if(cardsCount >= 3) {
			// Look for three of a kind
			ArrayList<CardRank> tripRanks 		= new ArrayList<CardRank>();


			for(int i = 0; i < cardsCount; i++) {
				for(int j = i + 1; j < cardsCount; j++) {
					for(int k = j + 1; k < cardsCount; k++) {
						if(pCards[i].getRank().equals(pCards[j].getRank()) && 
							pCards[i].getRank().equals(pCards[k].getRank()) && 
							!tripRanks.contains(pCards[i].getRank())) {

							tripRanks.add(pCards[i].getRank());
							i = j = k = cardsCount;
						}
					}
				}
			}

			if(tripRanks.size() > 0) {
				ranking = new HandRanking(Ranking.TRIPS);
				ranking.addHighCard(tripRanks.get(0));

				int maxCnt = 2;
				for(Card c : cards) {
					CardRank cr = c.getRank();
					if(!cr.equals(tripRanks.get(0))) {
						ranking.addHighCard(cr);
						maxCnt--;

						if(maxCnt == 0)		break;
					}
				}


				return ranking;
			}
		}



		if(cardsCount >= 4) {
			// Look for two pairs
			ArrayList<CardRank> pairRanks 		= new ArrayList<CardRank>();

			for(int i = 0; i < cardsCount; i++) {
				for(int j = i + 1; j < cardsCount; j++) {
					if(pCards[i].getRank().equals(pCards[j].getRank()) && !pairRanks.contains(pCards[i].getRank())) {
						pairRanks.add(pCards[i].getRank());
					}
				}
			}

			if(pairRanks.size() == 2) {
				Collections.sort(pairRanks);

				ranking = new HandRanking(Ranking.TWO_PAIRS);
				ranking.addHighCard(pairRanks.get(1));
				ranking.addHighCard(pairRanks.get(0));
				for(Card c : cards) {
					CardRank cr = c.getRank();
					if(!cr.equals(pairRanks.get(0)) && !cr.equals(pairRanks.get(1))) {
						ranking.addHighCard(cr);
						break;
					}
				}
				return ranking;
			}
		}

		
		// Look for a pair
		for(int i = 0; i < cardsCount; i++) {
			for(int j = i + 1; j < cardsCount; j++) {
				if(pCards[i].getRank().equals(pCards[j].getRank())) {
					ranking = new HandRanking(Ranking.PAIR);
					ranking.addHighCard(pCards[i].getRank());

					int maxCnt = 3;
					for(Card c : cards) {
						CardRank cr = c.getRank();
						if(!cr.equals(pCards[i].getRank())) {
							ranking.addHighCard(cr);
							maxCnt--;

							if(maxCnt == 0)		break;
						}
					}


					return ranking;
				}
			}
		}



		// Highcard
		ranking = new HandRanking(Ranking.HIGH_CARD);
		for(int i = 0; i < cardsCount && i < 5; i++) {
			ranking.addHighCard(cards.get(i).getRank());
		}

		return ranking;
	}












	@Override
	public int compareTo(HandRanking hr) {
		int cmp = this.mRank.compareTo(hr.getRank());
		if(cmp == 0) {
			ArrayList<CardRank> highCards = hr.getHighCardsRanks();
			int size1 = this.mHighCardsRanks.size();
			int size2 = highCards.size();

			if(size1 == size2) {
				for(int i = 0; i < size1; i++) {
					int cmpHighCards = this.mHighCardsRanks.get(i).compareTo(highCards.get(i));
					if(cmpHighCards != 0) return cmpHighCards;
				}
			}
			else {
				return size1 - size2;
			}
		}
		return cmp;
	}


	@Override
	public String toString() {
		String str = "";

		if(mRank == Ranking.HIGH_CARD) 				str = "High card";
		else if(mRank == Ranking.PAIR) 				str = "Pair";
		else if(mRank == Ranking.TWO_PAIRS) 		str = "Two pairs";
		else if(mRank == Ranking.TRIPS) 			str = "Three of a kind";
		else if(mRank == Ranking.STRAIGHT) 			str = "Straight";
		else if(mRank == Ranking.FLUSH) 			str = "Flush";
		else if(mRank == Ranking.FULL_HOUSE) 		str = "Full house";
		else if(mRank == Ranking.QUADS) 			str = "Four of a kind";
		else if(mRank == Ranking.STRAIGHT_FLUSH) 	str = "Straight flush";


		String highCards = "";
		if(mRank == Ranking.HIGH_CARD || mRank == Ranking.STRAIGHT || mRank == Ranking.FLUSH || mRank == Ranking.STRAIGHT_FLUSH) {
			for(CardRank cr : mHighCardsRanks) {
				if(!highCards.isEmpty())
					highCards += ",";

				highCards += cr.toString();
			}
		}
		else if(mRank == Ranking.PAIR) {
			highCards += mHighCardsRanks.get(0).toStringPlural();

			String strHighSingleCards = "";
			for(int i = 1; i < mHighCardsRanks.size(); i++) {
				if(!strHighSingleCards.isEmpty())
					strHighSingleCards += ",";

				strHighSingleCards += mHighCardsRanks.get(i);
			}

			if(!strHighSingleCards.isEmpty())
				highCards += " - " + strHighSingleCards;
		

			highCards = Character.toUpperCase(highCards.charAt(0)) + highCards.substring(1);
		}
		else if(mRank == Ranking.TWO_PAIRS) {
			highCards += mHighCardsRanks.get(0).toStringPlural() + " and " + mHighCardsRanks.get(1).toStringPlural();
			if(mHighCardsRanks.size() > 2)		highCards += " - " + mHighCardsRanks.get(2);

			highCards = Character.toUpperCase(highCards.charAt(0)) + highCards.substring(1);
		}
		else if(mRank == Ranking.TRIPS || mRank == Ranking.QUADS) {
			highCards += mHighCardsRanks.get(0).toStringPlural();
			highCards = Character.toUpperCase(highCards.charAt(0)) + highCards.substring(1);

			if(mRank == Ranking.QUADS) {
				if(mHighCardsRanks.size() > 1)		highCards +=  " - " + mHighCardsRanks.get(1);
			}
			else if(mRank == Ranking.TRIPS) {
				if(mHighCardsRanks.size() == 2) {
					highCards =  " - " + mHighCardsRanks.get(1);
				}
				else if(mHighCardsRanks.size() > 2) {
					highCards +=  " - " + mHighCardsRanks.get(1) + "," + mHighCardsRanks.get(2);
				}
			}
		}
		else if(mRank == Ranking.FULL_HOUSE) {
			if(mHighCardsRanks.size() == 2) {
				highCards += mHighCardsRanks.get(0).toStringPlural() + " full of " + mHighCardsRanks.get(1).toStringPlural();
				highCards = Character.toUpperCase(highCards.charAt(0)) + highCards.substring(1);
			}
		}


		if(!highCards.isEmpty())
			str = String.format("%s (%s)", str, highCards);

		return str;
	}
}