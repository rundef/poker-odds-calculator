package com.rundef.poker;

import java.util.ArrayList;

public class Console
{
	public static void main(String[] args) throws Exception
	{
		boolean isBoard = false;
		String board = "";
		ArrayList<String> handsStr = new ArrayList<>();
		ArrayList<Hand> hands = new ArrayList<>();

		for (String s: args) {
			if(s.equals("-b")) {
				isBoard = true;
				continue;
			}


			if(isBoard) {
				board = s;
				isBoard = false;
			}
			else {
				handsStr.add(s);
			}
        }

        if(board.isEmpty()) {
        	throw new Exception("Preflop odds calculator: NOT IMPLEMENTED YET.");
        }

        if(handsStr.size() < 2) {
        	throw new Exception("You must enter at least 2 hands");
        }





        EquityCalculator calculator = new EquityCalculator();
        calculator.setBoardFromString(board);
        
        for(int i = 0; i < handsStr.size(); i++) {
        	Hand h = Hand.fromString(handsStr.get(i));
        	hands.add(h);
        	calculator.addHand(h);
        }

        calculator.calculate();

        for(int i = 0; i < hands.size(); i++) {
        	HandRanking hr = calculator.getHandRanking(i);
        	HandEquity he = calculator.getHandEquity(i);

        	System.out.println(String.format("Player %d: %s - %s --- %s", 1+i, hands.get(i), hr, he));
        }


		//Console.board5();
		//Console.board5_2();
		//Console.board5_3();
	}


	public static void board4() throws Exception {
		Card board1 = Card.fromString("2d");
		Card board2 = Card.fromString("7d");
		Card board3 = Card.fromString("Ts");
		Card board4 = Card.fromString("9d");

		Hand h1 = Hand.fromString("JhJs");
		Hand h2 = Hand.fromString("Jd Qd");

		EquityCalculator calculator = new EquityCalculator();
		calculator.setBoard(board1, board2, board3, board4);
		calculator.addHand(h1);
		calculator.addHand(h2);



		calculator.calculate();

		HandRanking hr1 = calculator.getHandRanking(0);
		HandRanking hr2 = calculator.getHandRanking(1);

		HandEquity he1 = calculator.getHandEquity(0);
		HandEquity he2 = calculator.getHandEquity(1);

		System.out.println(String.format("Board: %s - %s - %s - %s", board1, board2, board3, board4));
		System.out.println(String.format("Player 1: %s - %s --- %s", h1, hr1, he1));
		System.out.println(String.format("Player 2: %s - %s --- %s", h2, hr2, he2));
	}





	// two pairs, flush, straight
	public static void board5() throws Exception {
		Card board1 = Card.fromString("2d");
		Card board2 = Card.fromString("7d");
		Card board3 = Card.fromString("Ts");
		Card board4 = Card.fromString("9d");
		Card board5 = Card.fromString("Td");

		Hand h1 = Hand.fromString("JhJs");
		Hand h2 = Hand.fromString("Jd Qd");
		Hand h3 = Hand.fromString("8s6h");

		EquityCalculator calculator = new EquityCalculator();
		calculator.setBoard(board1, board2, board3, board4, board5);
		calculator.addHand(h1);
		calculator.addHand(h2);
		calculator.addHand(h3);



		calculator.calculate();

		HandRanking hr1 = calculator.getHandRanking(0);
		HandRanking hr2 = calculator.getHandRanking(1);
		HandRanking hr3 = calculator.getHandRanking(2);

		HandEquity he1 = calculator.getHandEquity(0);
		HandEquity he2 = calculator.getHandEquity(1);
		HandEquity he3 = calculator.getHandEquity(2);

		System.out.println(String.format("Board: %s - %s - %s - %s - %s", board1, board2, board3, board4, board5));
		System.out.println(String.format("Player 1: %s - %s --- %s", h1, hr1, he1));
		System.out.println(String.format("Player 2: %s - %s --- %s", h2, hr2, he2));
		System.out.println(String.format("Player 3: %s - %s --- %s", h3, hr3, he3));
	}


	// straight flush, quads, full house
	public static void board5_2() throws Exception {
		Card board1 = Card.fromString("3d");
		Card board2 = Card.fromString("4d");
		Card board3 = Card.fromString("7d");
		Card board4 = Card.fromString("4s");
		Card board5 = Card.fromString("Ts");

		Hand h1 = Hand.fromString("5d6d");
		Hand h2 = Hand.fromString("4h,4c");
		Hand h3 = Hand.fromString("7s7h");

		EquityCalculator calculator = new EquityCalculator();
		calculator.setBoard(board1, board2, board3, board4, board5);
		calculator.addHand(h1);
		calculator.addHand(h2);
		calculator.addHand(h3);



		calculator.calculate();

		HandRanking hr1 = calculator.getHandRanking(0);
		HandRanking hr2 = calculator.getHandRanking(1);
		HandRanking hr3 = calculator.getHandRanking(2);

		HandEquity he1 = calculator.getHandEquity(0);
		HandEquity he2 = calculator.getHandEquity(1);
		HandEquity he3 = calculator.getHandEquity(2);

		System.out.println(String.format("Board: %s - %s - %s - %s - %s", board1, board2, board3, board4, board5));
		System.out.println(String.format("Player 1: %s - %s --- %s", h1, hr1, he1));
		System.out.println(String.format("Player 2: %s - %s --- %s", h2, hr2, he2));
		System.out.println(String.format("Player 3: %s - %s --- %s", h3, hr3, he3));
	}


	// trips, pair, high card
	public static void board5_3() throws Exception {
		Card board1 = Card.fromString("2c");
		Card board2 = Card.fromString("4c");
		Card board3 = Card.fromString("Jd");
		Card board4 = Card.fromString("9d");
		Card board5 = Card.fromString("6d");

		Hand h1 = Hand.fromString("6h6d");
		Hand h2 = Hand.fromString("9dTs");
		Hand h3 = Hand.fromString("KcQc");

		EquityCalculator calculator = new EquityCalculator();
		calculator.setBoard(board1, board2, board3, board4, board5);
		calculator.addHand(h1);
		calculator.addHand(h2);
		calculator.addHand(h3);



		calculator.calculate();

		HandRanking hr1 = calculator.getHandRanking(0);
		HandRanking hr2 = calculator.getHandRanking(1);
		HandRanking hr3 = calculator.getHandRanking(2);

		HandEquity he1 = calculator.getHandEquity(0);
		HandEquity he2 = calculator.getHandEquity(1);
		HandEquity he3 = calculator.getHandEquity(2);

		System.out.println(String.format("Board: %s - %s - %s - %s - %s", board1, board2, board3, board4, board5));
		System.out.println(String.format("Player 1: %s - %s --- %s", h1, hr1, he1));
		System.out.println(String.format("Player 2: %s - %s --- %s", h2, hr2, he2));
		System.out.println(String.format("Player 3: %s - %s --- %s", h3, hr3, he3));
	}
}