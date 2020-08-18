package com.black.jack;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class GameRun {

	Rule rule = new Rule();
	private List<Player> playerList = new ArrayList<Player>();
	private final String welcome = "환영합니다. 플레이어는 몇명입니까? (딜러를 포함합니다.)";
	private final String betting = "얼마를 배팅하시겠습니까?";
	private final String bet_call = "판돈에 응하시겠습니까? Y/N";
	private final String checkCard = "플레이어님의 패입니다";
	private final String blackJack = "축하합니다. 블랙잭입니다!";
	private final String reInput = "잘못 입력하셨습니다.\n재입력 해주세요. Y / N";
	private int tableMoney;
	private int players;

	/**
	 * 각 플레이어들에게 초기 소지금을 지급해줌 기본적으로 세팅되있는 초기 소지금:5000
	 */
	public void init() {
		for (Player player : playerList) {
			player.setInitMoney();
		}
	}

	public void print() {
		for (int i = 0; i < players; i++) {
			System.out.printf("%d번째 플레이어의 소지금 : %d\n", i + 1, playerList.get(i).getMoney());
		}
	}

	/**
	 * betting 메소드, 선 배팅 후 call/die를 판단함
	 * 
	 * @param player
	 * @param int    count
	 */
	public void betting(Player player, int count) {
		System.out.println(count + "번째 플레이어님 " + bet_call);
		while (true) {
			Scanner scan = new Scanner(System.in);
			try {
				String call = scan.nextLine();
				if (call.equalsIgnoreCase("Y")) {
					player.betMoney(tableMoney);
					break;
				} else if (call.equalsIgnoreCase("N")) {
					player.setStatus("bust");
					break;
				} else {
					System.out.println(reInput);
					continue;
				}
			} catch (InputMismatchException e) {
				System.out.println(reInput);
				continue;
			}
			// scan.close();
		}
	}

	/**
	 * betting 메소드, 현 게임 Round의 판돈을 정함
	 * 
	 * @param bet
	 * @param player
	 */
	public void betting(int bet, Player player) {
		tableMoney = bet;
		player.betMoney(tableMoney);
	}

//	public int score(List<CardOne> hands) {
//		int cnt = 0;
//		int score = 0;
//		String temp = "";
//		while (cnt < hands.size()) {
//			CardOne one = hands.get(cnt);
//			temp = one.toString().substring(2, one.toString().lastIndexOf("]"));
////			System.out.println("test Case: " + temp);
//			if (temp.equals("J") || temp.equals("Q") || temp.equals("K")) {
//				score += 10;
//				cnt++;
//			}else if (temp.equals("A")) {
//				score += 1;
//				cnt++;
//			}else {
//				int i = Integer.parseInt(temp);
//				score += i;
//				cnt++;
//			}
//		}
//		System.out.println("현재 점수" + score);
//		return score;
//	}

	public void gameStart() {
		// 0. 카드를 생성한다.
		CardHolder cardDeck = CardHolder.getInstance();
		// 1. 플레이어들 불러오기. / 역할 부여하기.
		Player dealer = new Player();
		Scanner scan = new Scanner(System.in);
		System.out.println(welcome);
		players = scan.nextInt();

		for (int i = 0; i < players; i++) {
			playerList.add(new Player());
		}
		init();
		print();
		// 2. 배팅금액 받기
		System.out.println(betting);
		int bet = scan.nextInt();
		int bettingRound = 0;
		betting(bet, playerList.get(bettingRound));
		print();
		// 2-1. call/die
		int bettingCount = bettingRound + 1;
		while (true) {
			if (bettingCount >= players) {
				bettingCount = 0;
			} else if (bettingCount == bettingRound) {
				break;
			} else {
				betting(playerList.get(bettingCount++), bettingCount);
			}
		}
		print();

		// 3. 참여자들에게 카드 2장씩 분배하기
		rule.dealerInit(cardDeck, dealer);
		for (Player player : playerList) {
			if (player.getStatus() == 0) {
				rule.setInit(cardDeck, player);
			}
		}

		// 5. 플레이어에게 hit / stay 입력받기
		int count = 0;
		while (count < players) {
			Player p = playerList.get(count);
			if (p.getStatus() == 2) {
				count++;
				continue;
			}
			System.out.println(count + 1 + "번째 " + checkCard);
			p.showHand();
			if (rule.score(p) == 21) {
				System.out.println(blackJack);
				p.setStatus("blackjack");
			}
			rule.choice(cardDeck, p);
			count++;
		}
		for (Player player : playerList) {
			rule.playerScore(player);
		}
//		for (int i = 0; i < players; i++) {
//			Player p = playerList.get(i);
//			p.initCards(cardDeck);
//			System.out.print(i + 1 + "번째 플레이어의 패 : ");
//			p.showHand();
//		}
		// 6. 모든 플레이어가 stay 상태가 되면 딜러 오픈 // alive(진행중인) 상태의 플레이어가 없으면.
		// 7. 룰에 의거하여 딜러 추가카드 판별
		if (!rule.stillAlive(playerList)) { // true면 진행중인 플레이어가 존재
			rule.dealerRule(cardDeck, dealer);
		}

		for (Player player : playerList) { // 딜러와 플레이어 비교해서 이기면 플레이어 상태를 win으로 바꿔줌
			if (rule.compare(dealer, player)) {
				player.setStatus("win");
			} else if (!rule.compare(dealer, player)) {
				player.setStatus("lose");
			}
		}
		for (int i = 0; i < playerList.size(); i++) {
			Player p = playerList.get(i);
			if (p.getStatus() == 2) {
				continue;
			}
			System.out.print(i + 1 + "번째 플레이어의 패 ");
			p.showHand();
			switch (p.getStatus()) {
			case 3: System.out.println(i + 1 + "번째 플레이어님. 블랙잭 입니다! (배당금 3배)");break;
			case 4: System.out.println(i + 1 + "번째 플레이어님. 승리하셨습니다! (배당금 2배)");break;
			case 5: System.out.println(i + 1 + "번째 플레이어님. 패배하셨습니다.");break;
			}
		}
	}

}
