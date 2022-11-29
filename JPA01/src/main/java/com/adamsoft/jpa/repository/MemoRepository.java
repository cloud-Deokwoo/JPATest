package com.adamsoft.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

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

}
