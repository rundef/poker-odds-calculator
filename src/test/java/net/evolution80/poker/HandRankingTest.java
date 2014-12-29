package net.evolution80.poker;

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

		assertEquals(mCalculator.getHandRanking(0).getRank(), HandRanking.Ranking.STRAIGHT_FLUSH);
	}



	@Test
	public void test2Quads() throws Exception {
		mCalculator.setBoardFromString("2s,4d,7d,4s,Ts")
					.addHand(Hand.fromString("4h4c"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(hr1.getRank(), HandRanking.Ranking.QUADS);
		assertEquals(hr1.getHighCardsRanks().size(), 2);
		assertEquals(hr1.getHighCardRank(0).value(), CardRank.FOUR);
		assertEquals(hr1.getHighCardRank(1).value(), CardRank.TEN);
	}



	@Test
	public void test3Fullhouse() throws Exception {
		mCalculator.setBoardFromString("2s,4d,7d,4s,Ts")
					.addHand(Hand.fromString("ThTd"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(hr1.getRank(), HandRanking.Ranking.FULL_HOUSE);
	}


	@Test
	public void test4Flush() throws Exception {
		mCalculator.setBoardFromString("2d,7d,Ts,9d,Th")
					.addHand(Hand.fromString("JdQd"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(hr1.getRank(), HandRanking.Ranking.FLUSH);
		assertEquals(hr1.getHighCardsRanks().size(), 5);
		assertEquals(hr1.getHighCardRank(0).value(), CardRank.QUEEN);
		assertEquals(hr1.getHighCardRank(1).value(), CardRank.JACK);
		assertEquals(hr1.getHighCardRank(2).value(), CardRank.NINE);
		assertEquals(hr1.getHighCardRank(3).value(), CardRank.SEVEN);
		assertEquals(hr1.getHighCardRank(4).value(), CardRank.TWO);
	}



	@Test
	public void test5Straight() throws Exception {
		mCalculator.setBoardFromString("2d,7d,Ts,9d,Th")
					.addHand(Hand.fromString("8s6h"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(hr1.getRank(), HandRanking.Ranking.STRAIGHT);
		assertEquals(hr1.getHighCardsRanks().size(), 5);
		assertEquals(hr1.getHighCardRank(0).value(), CardRank.TEN);
		assertEquals(hr1.getHighCardRank(1).value(), CardRank.NINE);
		assertEquals(hr1.getHighCardRank(2).value(), CardRank.EIGHT);
		assertEquals(hr1.getHighCardRank(3).value(), CardRank.SEVEN);
		assertEquals(hr1.getHighCardRank(4).value(), CardRank.SIX);
	}



	@Test
	public void test6Trips() throws Exception {
		mCalculator.setBoardFromString("2d,7d,Ts,9d,Th")
					.addHand(Hand.fromString("TcJc"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(hr1.getRank(), HandRanking.Ranking.TRIPS);
		assertEquals(hr1.getHighCardsRanks().size(), 3);
		assertEquals(hr1.getHighCardRank(0).value(), CardRank.TEN);
		assertEquals(hr1.getHighCardRank(1).value(), CardRank.JACK);
		assertEquals(hr1.getHighCardRank(2).value(), CardRank.NINE);
	}



	@Test
	public void test7Twopairs() throws Exception {
		mCalculator.setBoardFromString("2d,7d,Ts,9d,Jh")
					.addHand(Hand.fromString("TcJc"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(hr1.getRank(), HandRanking.Ranking.TWO_PAIRS);
		assertEquals(hr1.getHighCardsRanks().size(), 3);
		assertEquals(hr1.getHighCardRank(0).value(), CardRank.JACK);
		assertEquals(hr1.getHighCardRank(1).value(), CardRank.TEN);
		assertEquals(hr1.getHighCardRank(2).value(), CardRank.NINE);
	}



	@Test
	public void test8Pair() throws Exception {
		mCalculator.setBoardFromString("2d,7d,Ts,9d,6s")
					.addHand(Hand.fromString("TcJc"))
					.addHand(Hand.fromString("JdJh"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);
		HandRanking hr2 = mCalculator.getHandRanking(1);

		assertEquals(hr1.getRank(), HandRanking.Ranking.PAIR);
		assertEquals(hr1.getHighCardsRanks().size(), 4);
		assertEquals(hr1.getHighCardRank(0).value(), CardRank.TEN);
		assertEquals(hr1.getHighCardRank(1).value(), CardRank.JACK);
		assertEquals(hr1.getHighCardRank(2).value(), CardRank.NINE);
		assertEquals(hr1.getHighCardRank(3).value(), CardRank.SEVEN);

		assertEquals(hr2.getRank(), HandRanking.Ranking.PAIR);
		assertEquals(hr2.getHighCardsRanks().size(), 4);
		assertEquals(hr2.getHighCardRank(0).value(), CardRank.JACK);
		assertEquals(hr2.getHighCardRank(1).value(), CardRank.TEN);
		assertEquals(hr2.getHighCardRank(2).value(), CardRank.NINE);
		assertEquals(hr2.getHighCardRank(3).value(), CardRank.SEVEN);
	}



	@Test
	public void test9Highcard() throws Exception {
		mCalculator.setBoardFromString("2c,4c,Jd,9d,6d")
					.addHand(Hand.fromString("KcQc"))
					.calculate();

		HandRanking hr1 = mCalculator.getHandRanking(0);

		assertEquals(hr1.getRank(), HandRanking.Ranking.HIGH_CARD);
		assertEquals(hr1.getHighCardsRanks().size(), 5);
		assertEquals(hr1.getHighCardRank(0).value(), CardRank.KING);
		assertEquals(hr1.getHighCardRank(1).value(), CardRank.QUEEN);
		assertEquals(hr1.getHighCardRank(2).value(), CardRank.JACK);
		assertEquals(hr1.getHighCardRank(3).value(), CardRank.NINE);
		assertEquals(hr1.getHighCardRank(4).value(), CardRank.SIX);
	}
} 
