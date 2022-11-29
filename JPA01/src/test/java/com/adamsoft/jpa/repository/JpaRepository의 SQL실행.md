### SQL실행
  - JpaRepository가 제공하는 findById나 findAll 메서드를 이용한 조회
  - Query Method : 메서드의 이름 자체가 쿼리의 구분으로 처리되는 기능을 이용해서 메서드를 생성해서 조회
  - @Query : SQL과 유사하게 Entity Class정보를 이용해서 쿼리를 작성하는 기능으로 Native SQL 사용 가능
  - Querydsl : 동적 쿼리(상황에 따라 조건이 변경되는 쿼리) 처리
  - Query Methods
    - https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods
    - 쿼리 메서드는 메서드의 이름 자체가 질의(query)문이 되는 기능
    - 쿼리 메서드에서 조회하는 메서드의 이름 규칙
      find + (Entity이름)+By +변수이름
        - Entity 이름은 생략 가능
        - By 이후에 필요한 필드 조건이나 And, Or와 같은 키워드를 이용해서 메서드의 이름을 질의 조건을 이용해서 생성
        - Item Entity에서 num을 가지고 조회하는 경우 - findByNum
    - 쿼리 메서드의 관련 키워드는 SQL에서 사용되는 키워드와 동일하게 작성
    - 쿼리 메서드는 사용하는 키워드에 따라서 파라미터의 개수를 결정
    - 리턴 타입
      - select의 결과는 List타입이나 배열
      - select에서 파라미터에 Pageable타입을 넣는 경우 Page<E>
      - select 이외의 작업은 void
    - 쿼리 메서드 Smaple 및 JPQL snippet
    |keyword|sample|JPQL snippet|    
    |:---|:---|:---|
    |`Distinct`|findDistinctByLastnameAndFirstname|select distinct _ where x.lastname = ?1 and x.firstname = ?2|
    |`And`|findByLastnameAndFirstname| _ where x.lastname = ?1 and x.firstname = ?2|
    |`Or`|findByLastnameOrFirstname| _ where x.lastname = ?1 or x.firstname = ?2|
    |`Is, Equeals` |findByFirstname, findByFirstnameIs, findByFirstnameEquals| _ where x.firstname = ?1|
    |`Between`|findByStartDateBetween| _ where x.startDate between ?1 and ?2|
    |`LessThan`|findByAgeLessThan| _ where x.age < ?1| 
    |`LessThanEqual`|findByAgeLessThanEqual| _ where x.age <= ?1|
    |`GreaterThan`|findByAgeGreaterThan| _ where x.age > ?1|
    |`GreaterThanEqual`|findByAgeGreaterThanEqual| _ where x.age >= ?1|
    |`After`|findByStartDateAfter| _ where x.startDate > ?1|
    |`Before`|findByStartDateBefore| _ where x.startDate < ?1|
    |`IsNull, Null`|findByAge(Is)Null| _ where x.age is null|
    |`IsNotNull, NotNull`|findByAge(Is)NotNull| _ where x.age not null|
    |`Like`|findByFirstnameLike| _ where x.firstname like ?1|
    |`NotLike`|findByFirstnameNotLike| _ where x.firstname not like ?1|
    |`StartingWith`|findByFirstnameStartingWith| _ where x.firstname like ?1 (parameter bound with appended %)|
    |`EndingWith`|findByFirstnameEndingWith| _ where x.firstname like ?1 (parameter bound with prepended %)|
    |`Containing`|findByFirstnameContaining| _ where x.firstname like ?1 (parameter bound wrapped in %)|
    |`OrderBy`|findByAgeOrderByLastnameDesc| _ where x.age = ?1 order by x.lastname desc|
    |`Not`|findByLastnameNot| _ where x.lastname <> ?1 |
    |`In`|findByAgeIn(Collection<Age> ages)| _ where x.age in ?1 |
    |`NotIn`|findByAgeNotIn(Collection<Age> ages)| _ where x.lastname not in ?1 |
    |`True`|findByActiveTrue()| _ where x.active = true|
    |`False`|findByActiveFalse()| _ where x.active = false|
    |`IgnoreCase`|findByFirstnameIgnoreCase| _ where UPPER(x.firstname)=UPPER(?1|
    
    - Memo 객체의 mno값이 70부터 80사이의 객체를 검색하고 mno의 역순으로 정렬
      - MemoRepository 인터페이스에 메서드 추가
      ```java
        List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
      ```
      - Test Class에 테스트 메서드 추가 
      ```java
        @Test
        public void testQueryMethods(){
        	List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
        	for (Memo memo:list){
        		log.info(memo);
        	}
        }
      ```  
---
    - Spring Data JPA가 제공하는 쿼리 메서드는 검색과 같은 기능을 작서할 때 편리함을 제공하기는 하지만, 조인이나 복잡한 조건을 처리해야 하는 경우에는 And, Or 등이 사용되면 불편할 때가 많다. 
    - @Query의 경우에는 메서드의 이름과 상관없이 메서드에 추가된 annotation을 통해서 원하는 처리가 가능
    - @Query의 value는 JPQL(Java Persistence Query Langage)로 작성하는데 객체 지행 쿼리라고 함
    - @Query를 이용해서는 가능한 작업
      - 필요한 데이터만 선별적으로 추출
      - 데이터베이스에 맞는 순수한 SQL(Native SQL)
      - insert, update, delete와 같은 select가 아닌 DML처리 - @Modifying과 같이 사용
    - 작성
      - 객체 지향 쿼리는 Table 대신에 Entity Class를 이용하고 Table의 컬럼 대신에 Class에 선언된 필드를 이용해서 작성
      - JPQL은 SQL 과 상당히 유사하기 때문에 간단한 기능을 제작하는 경우에는 추가적인 학습 없이도 적용 가능
      - mno의 역순으로 정렬하라는 기능은 @Query를 이용해서 제작하면 다음과 같은 형태가 됨
      ```java
        @Query("select m from Memo m order by m.mno desc")
        List<Memo> getListDesc();
      ```
      - JPQL이 SQL과 유사하듯이 실제 SQL에서 사용되는 함수들도 JPQL에서 동일하게 사용하는데 avg(), count(), group by, order by 등 SQL에서 사용한 익순한 구문들을 사용할 수 있음.
    - 파라미터 바인딩
      - ?1, ?2와 같이 인덱스를 이용해서 파라미터의 순서를 설정하는 방식 - 인덱스는 1부터 시작
      - @Param을 이용해서 이름을 설정하고, :xxx와 같이 이름을 활용하는 방식
      - #{}과 같이 자바 빈 스타일을 이용하는 방식
      - 예제
      ```java  
      //MemoRepository 인터페이스에서 설정
      @Transactional
	@Modifying
	@Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
	int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);
	
	@Transactional
	@Modifying
	@Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}")
	int updateMemoText(@Param("param") Memo memo);
      ```
      ```java
      //테스트 메서드로 확인
      @Test
      public void testUpdateQuery(){
      	  log.info(memoRepository.updateMemoText(11L,"@Query를 이용한 수정"));
      	  log.info(memoRepository.updateMemoText(Memo.builder().mno(12L).memoText("@Query를 이용한 수정").build()));	
      }
      ```
    - 페이징 처리
      - 리턴 타입을 Page<Entity 타입>으로 지정하는 경우에는 count를 계산할 수 있는 쿼리가 필수적
      - @Query를 이용할 때는 별도의 countQuery라는 속성에 데이터 개수를 조회하는 쿼리를 작성하고 Pageable 타입의 파라미터에 전달하면 됨
      ```java
      //MemoRepository
      @Query(value="select m from Memo m where m.mno > :mno",countQuery = "select count(m) from Memo m where m.mno > :mno")
      Page<Memo> getListWithQuery(@Param("mno") Long mno, Pageable pageable);
      ```
      ```java
      @Test
      public void testSelectQuery(){
          Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
          Page<Memo> page = memoRepository.getListWithQuery(50L,pageable);
          for(Memo memo:page){
          	log.info(memo);
          }
      }
      ```
      
    - Object[] 리턴
      - 쿼리 메서드의 경우에는 Entity 타입의 데이터만을 추출하지만 @Query를 이용하는 경우에는 현재 필요한 테이터만을 Object[]의 형태로 선별적으로 추출 가능
      - JPQL을 이용해서 JOIN일나 GROUP BY등을 이용하는 경우가 있는데 이럴 때는 적당한 Entity 타입이 존재하지 않는 경우가 많기 때문에 이런 상황에서 유용함
      - mno와 memoText 그리고 현재 시간을 같이 얻어오고 싶다면 Memo Entity Class에는 시간관련된 부분의 선언이 없기 때문에 추가적인 구문이 필요
      - JPQL에서는 CURRENT_DATE, CURRENT_TIME, CURRENT_TIMESTAMP와 같은 구분을 통해서 현재 데이터베이스의 시간을 구할 수 있는데 이를 적용하면 다음과 같은 형태로 가능
      ```java
      @Query(value = "select m.mno, m.memoText, CURRENT_DATE from Memo m where m.mno > :mno", countQuery = "select count(m) from Memo m where m.mno > :mno")
      Page<Object[]> getListWithQueryObject(@Param("mno") Long mno, Pageable pageable);
      ```
      ```java
      //테스트
      @Test
      public void testSelectQueryObjectReturn(){
          Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
          Page<Object[]> page = memoRepository.getListWithQueryObject(50L, pageable);
          for(Object[] arr:page){
          	log.info(Arrays.toString(arr));
          }
      }