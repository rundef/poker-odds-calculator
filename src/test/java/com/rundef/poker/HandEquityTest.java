package com.rundef.poker;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HandEquityTest {
	private EquityCalculator mCalculator = new EquityCalculator();



	@Test
	public void test1FullBoard() throws Exception {
		mCalculator.setBoardFromString("3d,4d,7d,4s,Ts")
					.addHand(Hand.fromString("5d6d"))
					.addHand(Hand.fromString("4h4c"))
					.calculate();

		assertEquals(100, mCalculator.getHandEquity(0).getEquity());
		assertEquals(0, mCalculator.getHandEquity(1).getEquity());
	}


	@Test
	public void test2OneCardLeft() throws Exception {
		mCalculator.setBoardFromString("2d,Jd,Tc,4s")
					.addHand(Hand.fromString("AsKc"))  // 4 queens / 44 cards left
					.addHand(Hand.fromString("AdAh"))
					.calculate();

		assertEquals(9, mCalculator.getHandEquity(0).getEquity());
		assertEquals(91, mCalculator.getHandEquity(1).getEquity());



		mCalculator.reset()
					.setBoardFromString("2d,Jd,Tc,4s")
					.addHand(Hand.fromString("AsKc"))  // 2 queens + 3 kings + 3 aces / 44 cards left
					.addHand(Hand.fromString("QcQh"))
					.calculate();

		assertEquals(18, mCalculator.getHandEquity(0).getEquity());
		assertEquals(82, mCalculator.getHandEquity(1).getEquity());



		mCalculator.reset()
					.setBoardFromString("2d,Jd,5c,Ts")
					.addHand(Hand.fromString("Ad5d"))  // 9 diamonds + 2 fives / 44 cards left
					.addHand(Hand.fromString("AhJh"))
					.calculate();

		assertEquals(25, mCalculator.getHandEquity(0).getEquity());
		assertEquals(75, mCalculator.getHandEquity(1).getEquity());
	}



	@Test
	public void test3OneCardLeftWithTies() throws Exception {
		/*
		mCalculator.setBoardFromString("2s,3s,4s,Js")
					.addHand(Hand.fromString("AdKd")) 
					.addHand(Hand.fromString("AcQc")) 	// 2 queens to win
														// 3 fives + 10 spades to tie
					.calculate();

		assertEquals(mCalculator.getHandEquity(0).getEquity(), 65);
		assertEquals(mCalculator.getHandEquity(1).getEquity(), 5);
		assertEquals(mCalculator.getTieEquity(0, 1).getEquity(), 30);
		*/
	}



	@Test
	public void test4TwoCardsLeft() throws Exception {
		mCalculator.setBoardFromString("2d,Kd,8c")
					.addHand(Hand.fromString("AsKc"))
					.addHand(Hand.fromString("AdAh"))
					.calculate();

		assertEquals(9, mCalculator.getHandEquity(0).getEquity());
		assertEquals(91, mCalculator.getHandEquity(1).getEquity());



		mCalculator.reset()
					.setBoardFromString("Ad,4d,5d")
					.addHand(Hand.fromString("Kd3d"))
					.addHand(Hand.fromString("5c5h"))
					.calculate();

		assertEquals(66, mCalculator.getHandEquity(0).getEquity());
		assertEquals(34, mCalculator.getHandEquity(1).getEquity());
	}


	@Test
	public void test5TwoCardsLeftWithTies() throws Exception {
	}


	@Test
	public void test6NoBoard() throws Exception {
		/*
		mCalculator.reset()
					.addHand(Hand.fromString("AcAh"))  // 4 queens / 44 cards left
					.addHand(Hand.fromString("7c7h"))
					.calculate();

		assertEquals(81, mCalculator.getHandEquity(0).getEquity());
		assertEquals(19, mCalculator.getHandEquity(1).getEquity());
		*/
	}
} 
