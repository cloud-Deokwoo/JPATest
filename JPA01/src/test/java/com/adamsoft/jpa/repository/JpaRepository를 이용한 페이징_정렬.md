### 페이징/정렬
  - JPA를 사용하지 않는 경우 페이지 처리는 데이터베이스의 종류에 따라서 사용되는 기법이 달라서 별도의 학습이 필요함
  - 오라크의 인라인 뷰(inline view)나 FETCH & OFFSET를 사용하고 MYSQL은 limit를 사용
  - JPA는 내부적으로 이런 처리를 Dialect라는 존재를 이용해서 처리하고 있는데 JDBC정보가 MYSQL의 경우에는 자동으로 MYSQL를 위한 Dialect가 설정되고 명령을 수행할 때 그 데이터베이스에 맞는 형태의 SQL로 변화해서 처리함
  - JPA가 이처럼 실제 데이터베이스에서 사용하는 SQL의 처리를 자동으로 하기 때문에 개발자들은 SQL이 아닌 API의 객체와 메서드를 사용하는 형태로 페이징 처리를 할 수 있음. 
  - Spring Data JPA에서 페이징 처리와 정렬은 findAll이라는 메서드를 사용
  - findAll메서드는 JpaRepository 인터페이스의 상위인 PagingAndSortRepository의 메서드로 파라미터로 전달되는 Pageable이라는 타입의 객체에 의해서 실행되는 쿼리를 결정하고 리턴은 Page<T> 타입
  
  - Pageable 인터페이스
    - Pageable 인터페이스는 페이지 처리에 필요한 정보를 전달하는 용도의 타입으로 인터페이스 이기 때문에 실제 객체를 생성할 때에 구현체인 org.springframework.data.domain.PageRequest 라는 Class를 사용
    - PageRequest Class의 생성자는 protected로 선언되어 new를 이용할 수 없으며, 객체를 생성하기 위해서는 static메서드인 of()를 이용해서 처리
    - PageRequest 생성자를 보면 page, size, Sort라는 정보를 이용해서 객체를 생성
    - of 메서드
      - of(int page, int size) : 0부터 시작하는 페이지 번호와 개수(size)를 이용해서 생성하는데 정렬이 지정되지 않음
      - of(int page, int size, Sort.Direction direction, String ... props) : 0부터 시작하는 페이지 번호와 개수, 정렬의 방향과 정렬 기준 필드들을 이용해서 생성
      - of(int page, int size, Sort sort) : 페이지 번호와 개수, 정렬 관련 정보를 이용해서 생성
  