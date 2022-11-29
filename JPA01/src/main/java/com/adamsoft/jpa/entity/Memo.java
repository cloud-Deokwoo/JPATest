package com.adamsoft.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
/*
 * JPA를 통해서 관리하게 되는 객체 - Entity Class와 Entity Object
 * Entity 객체들을 처리하는 기능을 가진 Repository
 *  - Repository는 Spring Data JPA에서 제공하는 인터페이스로 설계하는데 스프링 내부에서
 *  자동으로 객체를 생성하고, 실행하는 구조라 개발자 입장에서는 단순히 인테페이스를 하나 정의하는 작업으로 충분
 *  - 기존 방식에서는 모든 코드를 직접 구현하고 트랜잭션 처리가 필요했으나, Spring Data JPA에서는 자동으로 생성되는 코드를 이용
 *  하므로 단순 CRUD나 페이지 처리 등을 개발할 때에 직접 코드를 작성하지 않아도 됨.
 *  
 * # Entity관련 annotation
 *  - @Entity : 클래스를 Entity로 선언
 *  - @Table : Entity와 매핑할 테이블 지정 - 생략시 클래스 이름과 동일한 테이블 매핑
 *  - @Id : 테이블의 기본키로 사용할 속성을 지정
 *  - @GeneratedValue : 키 값을 생성하는 전략 명시
 *  - @Column : 필드와 컬럼 매핑
 *  - @Lob : BLOB, CLOB 타입 매핑
 *  - @CreationTimestamp : insert시 시간 자동 저장
 *  - @UpdateTimestamp : update시 시간 자동 저장
 *  - @Enumerated : enum 타입 매핑
 *  - @Transient : 해당 필드는 데이터베이스 매핑을 하지 않음
 *  - @Temporal : 날짜 타입 매핑
 *  - @CreateDate : Entity가 생성되어 저장될 때 시간 자동 저장
 *  - @LastModifiedDate : Entity의 값을 변경할 때 시간 자동 저장
 *  
 *  
 */
@Entity      //Entity 클래스에 반드시 추가해야 함. 해당 Class는 Entity를 위한 클래스이며, 객체는 JPA로 관리되는 객체임을 의미
@Table(name="tbl_memo")  //Entity와 같이 사용할 수 있는 어노테이션으로 어떤 테이블 생성할지 위한 정보를 담는 어노테이션(이름, 인덱스 생성설정도 가능). 주)MySQL에서는 중간 대문자 있는 경우 앞에 _를 붙이고 소문자로 변경함
@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memo {
	
	@Id     // PK에 해당하는 필드를 지정. 사용자가 직접 입력하지 않는한 @GeneratedValue를 사용해서 자동 생성함. 
	//auto_increament 사용
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	//Sequence 생성
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SequenceGeneratorName")
	//@SequenceGenerator(sequenceName = "SequenceName", name = "SequenceGeneratorName", allocationSize = 1)
	
	//생성방법을 Hibernate가 결정 (AUTO)
	@GeneratedValue(strategy = GenerationType.AUTO)  
	private Long mno;
	
	
	//@Column 테이블블의 컬럼과 매핑하기 위한 annotation
	//생략하면 동일한 이름의 컬럼 매핑
	// 속성 : name - 매핑할 컬럼 이름, 
	//	unique - 유일함, 
	//	insertable - 삽입 가능 여부, 
	//	updatable - 수정가능여부,
	//	length - 문자열의 길이
	//	nullable - null 가능 여부
	//	columnDefinition - 자료형과 제약 조건을 직접 기재
	//   @Column(columnDefinition = "varchar(255) default 'Yes'")
	//	precision - 소수를 포함한 전체 자릿수로 BigDecimal에서 사용가능
	//	scale - 소수 자릿수의 자릿수로 BigDecimal에서 사용가능
	@Column(length = 200, nullable = false)
	private String memoText;

}
