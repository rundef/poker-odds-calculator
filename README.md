# Poker Equity Calculator

A pre-flop and post-flop odds calculator for Texas Holdem.

To compile the library, run the following command
```
gradle build
```

## Usage

* Preflop odds: **NOT IMPLEMENTED YET**

*example: we want to know the odds of 3 preflop all-in players holding the following hands: J♥J♤ vs T♢T♤ vs A♧K♧*
```
java -jar build/libs/poker-equity-calculator.jar JhJs TdTs AcKc
```


* Postflop odds

*example: we want to know the odds of a player holding the J♢ and the Q♢ against a player with the J♥ and the J♤ on a 7♢9♢T♤ board, with 2 cards to come*
```
java -jar build/libs/poker-equity-calculator.jar -b 7d9dTs JhJs JdQd
```
> -b denotes the board