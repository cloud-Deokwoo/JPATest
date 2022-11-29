package com.adamsoft.jpa.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.adamsoft.jpa.entity.Memo;

/*
 * JpaRepository 인터페이스 
 *  - Spring Data JPA는 JPA의 구현체인 Hibernate를 이용하기 위한 여러 API를 제공함
 *  - Spring Data JPA에는 여러 종류의 인터페이스의 기능을 통해서 JPA관련 작업을 별도의 코드 없이 처리할 수 있게 지원
 *  - JpaRepository를 이용하는 경우가 많음(해당 인터페이스를 상속하여 사용)
 *  
 *  JpaRepository<Entity Class, Entity의 Id자료형>을 상속받아서 생성
 *  
 *  CRUD 작업
 *   - insert, update 작업 : save(Entity 객체)
 *   - select 작업 : findById(키 타입), findAll()
 *   - 데이터 개수 확인 : count()
 *   - delete 작업 : deleteById(키타입), delete(Entity 객체)
 *   * save()의 경우 구현체가 메모리(Entity Manager라는 존재가 Entity들을 관리하는 방식)에서 객체를 비교하고 없다면 insert를 존재하면 update동작을 수행
 */
public interface MemoRepository extends JpaRepository<Memo, Long> {

	
	//Query 메서드의 이용 : mno값이 70에서 80사이의 객체를 검색하고, 역순으로 정렬
	List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
	
	//Memo 객체의 Mno값이 10부터 50사이의 객체를 내림차순 정렬해서 검색하고 페이징
	Page<Memo> findByMnoBetween(Long from,Long to, Pageable pageable); 
	
	//deleteBy로 시작하는 메서드를 이용한 mno가 10보다 작은 데이터 삭제
	void deleteMemoByMnoLessThan(Long num);
	
	
	//파라미터 바인딩 
	@Transactional
	@Modifying
	@Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
	int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);
	
	@Transactional
	@Modifying
	@Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}")
	int updateMemoText(@Param("param") Memo memo);
	
	//페이징 처리
	@Query(value="select m from Memo m where m.mno > :mno",countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Memo> getListWithQuery(@Param("mno") Long mno, Pageable pageable);
	
	//Object[] 리턴을 사용
	@Query(value = "select m.mno, m.memoText, CURRENT_DATE from Memo m where m.mno > :mno", countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Object[]> getListWithQueryObject(@Param("mno") Long mno, Pageable pageable);
	
}
