# 엔티티 설계시 주의점

1. 엔티티에는 가급적으로 setter를 사용하지 말자

2. 모든 연관관계는 지연로딩으로 설정
- 즉시 로딩 ('EAGER')은 예측이 어렵고, 어떤 SQL이 실행될지 추적이 어렵다. 
- 특히 JPQL을 실행할 때 N+1 문제가 자주 발생한다. eager JPQL 에서 Select from order 을 하면 예를들어 100건이 나오면 그 100각각 100건에서 member를 100번 조회 한다 그래서 1+100 번 SELECT가 실행됨
- 실무에서 모든 연관 관계는 지연로딩('LAZY')로 설정해야 한다.
- 연관된 엔티티를 함께 DB에 조회해야 하면 fetch join 또는 엔티티 그래프 기능을 사용한다.(다 LAZY 하고 Fetch Join으로)
- @XToOne(OneToOne, ManyToOne) 관계는 기본이 즉시로딩이므로 직접 지연로딩을 설정해야 한다.

3. 컬렌션은 필드에서 초기화 하자. 컬렌션은 필드에서 초기화 하는 것이 안전한다.
- ex) private List<Order> orders = new ArrayList()<>; 이런식으로
- 'null' 문제에서 안전하다.
- 하이버네이트는 엔티티를 연속화 할 때, 컬랙션을 감싸서 하이버네이트가 제공하는 내장 컬렌션으로 변경한다. 만약 'getOrders()' 처럼 임의의 메서드에서 컬렌션을 잘못 생성하면 하이버네이트 내부 메커니즘에 문제가 발생할 수 있다. 따라서 필드레벨에서 생성하는 것이 가장 안전하고, 코드도 간결하다.

4. 테이블, 컬럼명 생성 전략
스프링 부트에서 하이버네이트 기본 매핑 전략을 변경해서 실제 테이블 필드명은 다름
https://docs.spring.io/spring-boot/docs/2.1.3.RELEASE/reference/htmlsingle/#howtoconfigure-hibernate-naming-strategy
http://docs.jboss.org/hibernate/orm/5.4/userguide/html_single/
Hibernate_User_Guide.html#naming
하이버네이트 기존 구현: 엔티티의 필드명을 그대로 테이블의 컬럼명으로 사용
( SpringPhysicalNamingStrategy )
- 논리명 생성: 명시적으로 컬럼, 테이블명을 직접 적지 않으면 ImplicitNamingStrategy 사용
spring.jpa.hibernate.naming.implicit-strategy : 테이블이나, 컬럼명을 명시하지 않을 때 논리명
적용, 
- 물리명 적용: 
spring.jpa.hibernate.naming.physical-strategy : 모든 논리명에 적용됨, 실제 테이블에 적용
(username usernm 등으로 회사 룰로 바꿀 수 있음)

5. cascade = CascadeType.ALL
orderItem 저장 order을 저장해야 하는데 cascade = CascadeType.ALL을 하면 order만 저장 하면 됨 삭제도 동일