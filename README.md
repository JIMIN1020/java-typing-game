# Java를 자바먹자!
2023-1 숙명여자대학교 객체지향프로그래밍 텀프로젝트 과제
<img width="712" alt="스크린샷 2023-07-07 오후 8 26 47" src="https://github.com/JIMIN1020/java-typing-game/assets/121474189/1552ecfd-e6c4-4a5b-942a-f753e6f2de6e">
<img width="1312" alt="스크린샷 2023-07-07 오후 8 39 49" src="https://github.com/JIMIN1020/java-typing-game/assets/121474189/e8281a2e-f3db-467b-b0ac-6aea22bd47b8">

<br />

## 프로그램 개요
`Java를 자바먹자!`는 하늘에서 떨어지는 객체지향프로그래밍 수업에서 배울 수 있었던 다양한 Java 관련 용어, 키워드들을 입력하여 제거해야 하는 **Java Swing GUI** 기반의 타자 연습게임입니다.

- 게임은 객체지향프로그래밍 수업을 그대로 반영하여 총 **1주차~10주차**로 구성되어 있습니다.

- 단어를 입력하면 **score가 10씩 증가**하고, 입력된 단어는 사라집니다.

- 매 주차마다 **3개의 Life**가 제공되며 단어가 바닥에 닿게되면 Life가 하나씩 줄어들게 되고, Life가 모두 없어지면 게임은 종료됩니다.

<br />

## 프로그램 특징
이 타자게임은 크게 2가지의 특징을 가지고 있습니다.

### 1. 교수님의 Quiz!
<img width="202" alt="스크린샷 2023-07-07 오후 8 13 14" src="https://github.com/JIMIN1020/java-typing-game/assets/121474189/0bfe9eb1-4ee9-4cc0-8aa3-838c4d1e5eb3">

매 주차 마지막에는 교수님이 등장하여 해당 주차의 주제와 관련된 **퀴즈**를 출제합니다.

이 퀴즈를 맞춰야만 다음 주차로 넘어갈 수 있으며, 만약 교수님이 바닥에 닿을 때까지 퀴즈를 맞추지 못했을 경우에는 Life가 남아있더라도 그대로 게임이 종료됩니다.



### 2. 위험한 빨간색 단어!
<img width="190" alt="스크린샷 2023-07-07 오후 8 13 27" src="https://github.com/JIMIN1020/java-typing-game/assets/121474189/2b0a97a2-41bf-484d-9f44-88607c7dc04b">

게임에 등장하는 단어들은 **30% 확률**로 빨간색 단어가 됩니다.

이 빨간색 단어는 일반 단어들과 달리 2번 입력하여 제거해야하는 특징을 가지고 있습니다.


<br />

## 나의 개발 파트
- 주요 thread 구현 (GameManager, WordGenerator, WordRain)
- 주요 method 구현 (gameOver, nextLevel, restart, quiz 등)
- 게임 운영에 필요한 클래스 구현 (WordStorage, GameInfo 등))
- File I/O 처리

<br />
