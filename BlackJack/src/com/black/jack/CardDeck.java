package com.black.jack;

import java.util.ArrayList;
import java.util.List;

public class CardDeck {
	
	private List<CardOne> cardDeck;

	public CardDeck() {
		cardDeck = new ArrayList<CardOne>();
		makeCardCase();
	}

	private void makeCardCase() {
		int cnt = 0; 
		int len = CardOne.SUIT.length * CardOne.NUMBER.length;
		
		while (true) {
			CardOne card = new CardOne();
			if (!cardDeck.contains(card)) {
				cardDeck.add(card);
				cnt++;
			}
			if (cnt == len) {
				break;
			}
		}
	}
	
	public List<CardOne> getCardCase() {
		return cardDeck;
	}

}
