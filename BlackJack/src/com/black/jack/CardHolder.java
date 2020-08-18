package com.black.jack;

import java.util.Iterator;
import java.util.List;

public class CardHolder {
	
	private static CardHolder holder;
	private Iterator<CardOne> deck;
	
	private CardHolder() {
		getCardDeck();
	}
	
	public static CardHolder getInstance() {
		if (holder == null) {
			holder = new CardHolder();
		}
		return holder;
	}
	
	private void getCardDeck() {
		List<CardOne> cards = new CardDeck().getCardCase();
		deck = cards.iterator();
	}
	
	public CardOne getCard() {
		CardOne card = deck.next();
		deck.remove(); // 필요없음 지알아서 해줌 멍청아
		return card;
	}

}
