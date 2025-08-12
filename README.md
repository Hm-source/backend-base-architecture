## branch

- architecture
- jpa-extended
- jpa

## GOAL

- architecture
    - 계층형 아키텍쳐와 헥사고날 아키텍쳐가 적용되어있는 프로젝트의 구성을 보고 이해한다
    - 인터페이스와 클래스가 어떻게 연결되어있는지 DIP + OCP 장점을 직접본다
    - 외부로부터 요청이 들어오는 두 종류의 Primary Adapter 를 보고 이해한다
        - Controller / Message Consumer
    - 외부로 데이터를 주고받는 세 종류의 Secondary Adapter 를 보고 이해한다
        - Persistance(DB) / Message Consumer / External Open API
    - DDD 의 기반이되는 어그리거트를 엔티티 클래스들의 집합을 통해 보고 이해한다
- jpa-extended
    - Controller - Service - Repository 가 어떻게 연결되어 구현되는지에 익숙해지기
    - 엔티티 간 연관관계 3요소인 1. 다중성 2. 방향 3. 연관관계의 주인에 익숙해지기
    - FetchType.EAGER / LAZY 직접 디버깅으로 확인하면서 배운것을 이해하기
    - CascadeType.REMOVE 와 orphanRemoval 의 동작흐름을 직접 작성해보면서 이해하기
    - 연관관계에 있는 엔티티 객체들을 Service 서 어떻게 다루는지 직접 작성해보면서 이해하기
- jpa
    - Spring Data JPA 의 Query Method 가 어떻게 동작되는지
    - JPA 를 위한 Entity 클래스 구성이 어떻게 되는것인지

### 반복 횟수(branch마다)

- architecture
    - [X] 1회
    - [X] 2회
    - [ ] 3회
    - [ ] 4회
    - [ ] 5회
- jpa-extended
    - [ ] 1회
    - [ ] 2회
    - [ ] 3회
    - [ ] 4회
    - [ ] 5회
- jpa
    - [ ] 1회
    - [ ] 2회
    - [ ] 3회
    - [ ] 4회
    - [ ] 5회