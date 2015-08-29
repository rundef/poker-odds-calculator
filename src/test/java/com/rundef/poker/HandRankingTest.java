package com.rundef.poker;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HandRankingTest {
	private EquityCalculator mCalculator = new EquityCalculator();


	@Test
	public void test1StraightFlush() throws Exception {
		mCalculator.setBoardFromString("3d,4d,7d,4s,Ts")
					.addHand(Hand.fromString("5d6d"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.STRAIGHT_FLUSH, hr1.getRank());
		assertEquals(5, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.SEVEN,   hr1.getHighCardRank(0).value());
		assertEquals(CardRank.SIX,     hr1.getHighCardRank(1).value());
		assertEquals(CardRank.FIVE,    hr1.getHighCardRank(2).value());
		assertEquals(CardRank.FOUR,    hr1.getHighCardRank(3).value());
		assertEquals(CardRank.THREE,   hr1.getHighCardRank(4).value());





		mCalculator.reset();
		mCalculator.setBoardFromString("3d4dTcAs5d")
					.addHand(Hand.fromString("6d2d"))
					.calculate();

		hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.STRAIGHT_FLUSH, hr1.getRank());
		assertEquals(5, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.SIX,   hr1.getHighCardRank(0).value());
		assertEquals(CardRank.FIVE,  hr1.getHighCardRank(1).value());
		assertEquals(CardRank.FOUR,  hr1.getHighCardRank(2).value());
		assertEquals(CardRank.THREE, hr1.getHighCardRank(3).value());
		assertEquals(CardRank.TWO,   hr1.getHighCardRank(4).value());




		mCalculator.reset();
		mCalculator.setBoardFromString("3d,4d,7s,4s,2d")
					.addHand(Hand.fromString("Ad5d"))
					.calculate();

		hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.STRAIGHT_FLUSH, hr1.getRank());
		assertEquals(CardRank.FIVE,  hr1.getHighCardRank(0).value());
		assertEquals(CardRank.FOUR,  hr1.getHighCardRank(1).value());
		assertEquals(CardRank.THREE, hr1.getHighCardRank(2).value());
		assertEquals(CardRank.TWO,   hr1.getHighCardRank(3).value());
		assertEquals(CardRank.ACE,   hr1.getHighCardRank(4).value());
	}



	@Test
	public void test2Quads() throws Exception {
		mCalculator.setBoardFromString("2s,4d,7d,4s,Ts")
					.addHand(Hand.fromString("4h4c"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.QUADS, hr1.getRank());
		assertEquals(2, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.FOUR, hr1.getHighCardRank(0).value());
		assertEquals(CardRank.TEN, hr1.getHighCardRank(1).value());
	}



	@Test
	public void test3Fullhouse() throws Exception {
		mCalculator.setBoardFromString("2s,4d,7d,4s,Ts")
					.addHand(Hand.fromString("ThTd"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.FULL_HOUSE, hr1.getRank());
	}


	@Test
	public void test4Flush() throws Exception {
		mCalculator.setBoardFromString("2d,7d,Ts,9d,Th")
					.addHand(Hand.fromString("JdQd"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.FLUSH, hr1.getRank());
		assertEquals(5, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.QUEEN, hr1.getHighCardRank(0).value());
		assertEquals(CardRank.JACK, hr1.getHighCardRank(1).value());
		assertEquals(CardRank.NINE, hr1.getHighCardRank(2).value());
		assertEquals(CardRank.SEVEN, hr1.getHighCardRank(3).value());
		assertEquals(CardRank.TWO, hr1.getHighCardRank(4).value());
	}



	@Test
	public void test5Straight() throws Exception {
		mCalculator.setBoardFromString("2d,7d,Ts,9d,Th")
					.addHand(Hand.fromString("8s6h"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.STRAIGHT, hr1.getRank());
		assertEquals(5, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.TEN, hr1.getHighCardRank(0).value());
		assertEquals(CardRank.NINE, hr1.getHighCardRank(1).value());
		assertEquals(CardRank.EIGHT, hr1.getHighCardRank(2).value());
		assertEquals(CardRank.SEVEN, hr1.getHighCardRank(3).value());
		assertEquals(CardRank.SIX, hr1.getHighCardRank(4).value());



		mCalculator.reset();
		mCalculator.setBoardFromString("3d4sTcAs5s")
					.addHand(Hand.fromString("6s2d"))
					.calculate();

		hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.STRAIGHT, hr1.getRank());
		assertEquals(5, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.SIX,   hr1.getHighCardRank(0).value());
		assertEquals(CardRank.FIVE,  hr1.getHighCardRank(1).value());
		assertEquals(CardRank.FOUR,  hr1.getHighCardRank(2).value());
		assertEquals(CardRank.THREE, hr1.getHighCardRank(3).value());
		assertEquals(CardRank.TWO,   hr1.getHighCardRank(4).value());


		mCalculator.reset();
		mCalculator.setBoardFromString("3d4s9s7s5s")
					.addHand(Hand.fromString("Ad2d"))
					.calculate();

		hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.STRAIGHT, hr1.getRank());
		assertEquals(5, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.FIVE,  hr1.getHighCardRank(0).value());
		assertEquals(CardRank.FOUR,  hr1.getHighCardRank(1).value());
		assertEquals(CardRank.THREE, hr1.getHighCardRank(2).value());
		assertEquals(CardRank.TWO,   hr1.getHighCardRank(3).value());
		assertEquals(CardRank.ACE,   hr1.getHighCardRank(4).value());
	}



	@Test
	public void test6Trips() throws Exception {
		mCalculator.setBoardFromString("2d,7d,Ts,9d,Th")
					.addHand(Hand.fromString("TcJc"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.TRIPS, hr1.getRank());
		assertEquals(3, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.TEN, hr1.getHighCardRank(0).value());
		assertEquals(CardRank.JACK, hr1.getHighCardRank(1).value());
		assertEquals(CardRank.NINE, hr1.getHighCardRank(2).value());
	}



	@Test
	public void test7Twopairs() throws Exception {
		mCalculator.setBoardFromString("2d,7d,Ts,9d,Jh")
					.addHand(Hand.fromString("TcJc"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.TWO_PAIRS, hr1.getRank());
		assertEquals(3, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.JACK, hr1.getHighCardRank(0).value());
		assertEquals(CardRank.TEN,  hr1.getHighCardRank(1).value());
		assertEquals(CardRank.NINE, hr1.getHighCardRank(2).value());



		mCalculator.reset();
		mCalculator.setBoardFromString("2d,7d,7s,Jh")
					.addHand(Hand.fromString("AcJc"))
					.calculate();

		hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.TWO_PAIRS, hr1.getRank());
		assertEquals(3, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.JACK, 	hr1.getHighCardRank(0).value());
		assertEquals(CardRank.SEVEN,  	hr1.getHighCardRank(1).value());
		assertEquals(CardRank.ACE, 		hr1.getHighCardRank(2).value());
	}



	@Test
	public void test8Pair() throws Exception {
		mCalculator.setBoardFromString("2d,7d,Ts,9d,6s")
					.addHand(Hand.fromString("TcJc"))
					.addHand(Hand.fromString("JdJh"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);
		HandRanking hr2 = mCalculator.getHandRanking(1);

		assertEquals(HandRanking.Ranking.PAIR, hr1.getRank());
		assertEquals(4, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.TEN, hr1.getHighCardRank(0).value());
		assertEquals(CardRank.JACK, hr1.getHighCardRank(1).value());
		assertEquals(CardRank.NINE, hr1.getHighCardRank(2).value());
		assertEquals(CardRank.SEVEN, hr1.getHighCardRank(3).value());

		assertEquals(HandRanking.Ranking.PAIR, hr2.getRank());
		assertEquals(4, hr2.getHighCardsRanks().size());
		assertEquals(CardRank.JACK, hr2.getHighCardRank(0).value());
		assertEquals(CardRank.TEN, hr2.getHighCardRank(1).value());
		assertEquals(CardRank.NINE, hr2.getHighCardRank(2).value());
		assertEquals(CardRank.SEVEN, hr2.getHighCardRank(3).value());
	}



	@Test
	public void test9Highcard() throws Exception {
		mCalculator.setBoardFromString("2c,4c,Jd,9d,6d")
					.addHand(Hand.fromString("KcQc"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.HIGH_CARD, hr1.getRank());
		assertEquals(5, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.KING,  hr1.getHighCardRank(0).value());
		assertEquals(CardRank.QUEEN, hr1.getHighCardRank(1).value());
		assertEquals(CardRank.JACK,  hr1.getHighCardRank(2).value());
		assertEquals(CardRank.NINE,  hr1.getHighCardRank(3).value());
		assertEquals(CardRank.SIX,   hr1.getHighCardRank(4).value());
	}


	@Test
	public void test10IncompleteBoard() throws Exception {
		mCalculator.setBoardFromString("2c,4c,Jd")
					.addHand(Hand.fromString("KcQc"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.HIGH_CARD, hr1.getRank());
		assertEquals(5, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.KING,  hr1.getHighCardRank(0).value());
		assertEquals(CardRank.QUEEN, hr1.getHighCardRank(1).value());
		assertEquals(CardRank.JACK,  hr1.getHighCardRank(2).value());
		assertEquals(CardRank.FOUR,  hr1.getHighCardRank(3).value());
		assertEquals(CardRank.TWO,   hr1.getHighCardRank(4).value());




		mCalculator.reset();
		mCalculator.setBoardFromString("2c,4c,Jd,9s")
					.addHand(Hand.fromString("KcQc"))
					.calculate();

		hr1 = mCalculator.getHandRanking(0);

		assertEquals(HandRanking.Ranking.HIGH_CARD, hr1.getRank());
		assertEquals(5, hr1.getHighCardsRanks().size());
		assertEquals(CardRank.KING,  hr1.getHighCardRank(0).value());
		assertEquals(CardRank.QUEEN, hr1.getHighCardRank(1).value());
		assertEquals(CardRank.JACK,  hr1.getHighCardRank(2).value());
		assertEquals(CardRank.NINE,  hr1.getHighCardRank(3).value());
		assertEquals(CardRank.FOUR,   hr1.getHighCardRank(4).value());
	}
} 
