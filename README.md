# Poker Equity Calculator

A pre-flop and post-flop odds calculator for Texas Holdem.

To compile the library, run the following command
```
gradle build
```

## Usage

Calculate preflop odds: **NOT IMPLEMENTED YET**

Calculate postflop odds:
example: we want to knows the odds of a player holding the J♢ and the Q♢ against a player with the J♥ and the J♤ on a 7♢9♢T♤ board
```
java -jar build/libs/poker-equity-calculator.jar -b 7d9dTs JhJs JdQd
```