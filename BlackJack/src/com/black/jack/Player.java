package com.black.jack;

import java.util.ArrayList;
import java.util.List;

public class Player implements IPlayer {
	private int money = 0;
	private int bettingMoney = 0;
	private int score = 0;
	public int getBettingMoney() {
		return bettingMoney;
	}

	private List<CardOne> hands = new ArrayList<CardOne>();
	private int status = 0;
	
	
	public Player() {
		
	}
	
	public Player(CardHolder deck) {
		
	}
	
	public void setScore(int score) {
		this.score += score;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		switch (status) {
		case "alive": this.status = 0;	break;
		case "stay":  this.status = 1;	break;
		case "bust": this.status = 2;	break;
		case "blackjack": this.status = 3; break;
		case "win":	  this.status = 4; 	break;
		case "lose":	  this.status = 5; 	break;
		default:break;
		}
	}
	
	public void showFirstCard() {
		System.out.print(hands.get(0) + " ");
	}
	
	public void showHand() {
		for (int i = 0; i < hands.size(); i++) {
			System.out.print(hands.get(i) + " ");
		}
		System.out.println();
	}
	
	public List<CardOne> getHands() {
		return hands;
	}

	public void setCardsOne(CardHolder deck) {
		hands.add(deck.getCard());
	}
	
	public void setInitMoney() {
		money = initMoeny;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void betMoney(int bet) {
		bettingMoney = bet;
		money -= bet;
	}

}