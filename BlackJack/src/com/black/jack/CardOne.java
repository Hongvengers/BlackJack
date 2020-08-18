package com.black.jack;

public class CardOne {
	
private String card;
	
	public static final String[] SUIT = {"◆","♠","♥","♣"};
	public static final String[] NUMBER = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
	
	public CardOne() {
		makeCard();
	}
	
	private void makeCard() {
		int s = (int)(Math.random() * SUIT.length) + 1;
		int n = (int)(Math.random() * NUMBER.length) + 1;
		card = SUIT[s - 1] + NUMBER[n - 1];
	}

	@Override
	public String toString() {
		return "[" + card + "]";
	}

	@Override
	public int hashCode() {
		return card.hashCode() + 137;
	}

	@Override
	public boolean equals(Object obj) {
		boolean isc = false;
		CardOne other = (CardOne) obj;
		if (card.equals(other.card)) {
			isc = true;
		}
		return isc;
	}

}
