package com.black.jack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 게임내 규칙 클래스
 * 
 * @author LHS
 *
 */
public class Rule {

	Scanner scan = new Scanner(System.in);

	/**
	 * 카드를 받고 처음에 딜러의 카드를 오픈하는 메소드
	 * 
	 * @param dealer
	 */
	public void dealerInit(CardHolder deck, Player dealer) {
		setInit(deck, dealer);
		System.out.print("딜러의 카드 ");
		dealer.showFirstCard();
		System.out.println("[  ]");
	}

	public void setInit(CardHolder deck, Player player) {
		List<CardOne> hands = player.getHands();
		for (int i = 0; i < IPlayer.initCardSize; i++) {
			hands.add(deck.getCard());
		}
	}

	public void setCardOne(CardHolder deck, Player player) {
		List<CardOne> hands = player.getHands();
		hands.add(deck.getCard());
	}

	public boolean stillAlive(List<Player> players) {
		boolean isc = false;
		for (Player player : players) {
			if (player.getStatus() == 0) {
				isc = true;
			}
		}
		return isc;
	}

	/**
	 * 딜러의 카드를 오픈하고 나서 플레이어가 HIT STAND 결정하는 메소드
	 */
	public void choice(CardHolder deck, Player player) {
		while (true) {
			if (player.getStatus() != 0) {
				break;
			}
			if (score(player) > 21) {
				System.out.println("Bust!");
				player.setStatus("bust");
				break;
			}
			System.out.println("카드를 추가로 더 뽑으시겠습니까?");
			System.out.println("Y / N");
			String choice = scan.nextLine();
			if (choice.equalsIgnoreCase("y")) {
				player.setCardsOne(deck);
				player.showHand();
			} else {
				player.setStatus("stay"); // getStatus 값이 2로 반환
				break;
			}
		}
	}

	public boolean compare(Player dealer, Player player) {
		boolean isWin = false;
		if (player.getStatus() == 1) {			
			if (score(dealer) <= score(player)) {
				isWin = true;
			}
		}
		return isWin;
	}

	public int dealerRule(CardHolder deck, Player dealer) {
		List<CardOne> hands = dealer.getHands();
		System.out.print("딜러의 카드 ");
		dealer.showHand();
		while (true) {
			if (score(dealer) > 21) {
				dealer.setStatus("bust");
				break;
			} else if (score(dealer) < 17) {
				hands.add(deck.getCard());
				System.out.print("딜러의 카드 ");
				dealer.showHand();
			} else if (score(dealer) >= 17 && score(dealer) <= 21) {
				break;
			}
		}
		return score(dealer);
	}

	public int score(Player player) {
		List<CardOne> hands = player.getHands();
		int cnt = 0;
		int score = 0;
		String temp = "";
		while (cnt < hands.size()) {
			CardOne one = hands.get(cnt);
			temp = one.toString().substring(2, one.toString().lastIndexOf("]"));
			if (temp.equals("J") || temp.equals("Q") || temp.equals("K")) {
				score += 10;
				cnt++;
			} else if (temp.equals("A")) {
				score += 11;
				cnt++;
			} else {
				int i = Integer.parseInt(temp);
				score += i;
				cnt++;
			}
		}
//		System.out.println("현재 점수" + score);
		return score;
	}

	public void playerScore(Player player) {
		List<CardOne> hands = player.getHands();
		int cnt = 0;
		String temp = "";
		while (cnt < hands.size()) {
			CardOne one = hands.get(cnt);
			temp = one.toString().substring(2, one.toString().lastIndexOf("]"));
//			System.out.println("test Case: " + temp);
			if (temp.equals("J") || temp.equals("Q") || temp.equals("K")) {
				player.setScore(10);
				cnt++;
			} else if (temp.equals("A")) {
				cnt++;
			} else {
				int i = Integer.parseInt(temp);
				player.setScore(i);
				cnt++;
			}
		}
	}

	public void choiceA(Player player) {
		List<CardOne> hands = player.getHands();
		int cnt = 0;
		int score = 0;
		Scanner scan = new Scanner(System.in);
		String temp = "";
		while (cnt < hands.size()) {
			CardOne one = hands.get(cnt);
			temp = one.toString().substring(2, one.toString().lastIndexOf("]"));
			if (temp.equals("A")) {
				System.out.println("A카드를 1과 11중 무엇으로 처리하시겠습니까?");
				score = scan.nextInt();
				player.setScore(score);
				cnt++;
			} else {
				cnt++;
			}
		}
	}

	/**
	 * // *
	 */

//	/**
//	 * 플레이어의 카드의 합을 구하는 메소드
//	 */
//	public int numSum(Player player) {
//		int total = 0;
//		int size = player.getHandSize();
//				
//		if(player.toString().length() > 4){
//			for (int i = 0; i < size.; i++) {
//				player.);
//			}
//		}
//		
//		return total;
//	}

}
