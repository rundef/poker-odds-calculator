# Poker Equity Calculator

A pre-flop and post-flop odds calculator for Texas Holdem.

To compile the library, run the following command
```
gradle build
```

## Usage

* Pre-flop odds

*example: we want to know the odds of 3 pre-flop all-in players holding the following hands: J♥J♤ vs T♢T♤ vs A♧K♧*
```
java -jar build/libs/poker-equity-calculator.jar JhJs TdTs AcKc
```

*Board : no cards*

*Player 1: Jh Js - Pair (Jacks) --- ~42 %*
*Player 2: Td Ts - Pair (Tens) --- ~17 %*
*Player 3: Ac Kc - High card (A,K) --- ~41 %*

*Simulated 200000 random boards in 3.1 second*



* Post-flop odds

*example: we want to know the odds of a player holding the J♢ and the Q♢ against a player with the J♥ and the J♤ on a 7♢9♢T♤ board, with 2 cards to come*
```
java -jar build/libs/poker-equity-calculator.jar -b 7d9dTs JhJs JdQd
java -jar build/libs/poker-equity-calculator.jar -b 7d9dTs7s JhJs JdQd
```
> -b denotes the board