package com.adamsoft.jpa.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.adamsoft.jpa.entity.Memo;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml","file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"})
@Log4j
public class RepositoryTest {

	@Autowired
	MemoRepository memoRepository;  //MemoRepository 주입
	
	@Test   //주입 확인 테스트... 
	public void testDependency() {
		log.info("주입 여주:"+memoRepository.getClass().getName());
	}

	//삽입 확인
	@Test
	public void testInsert() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Memo memo = Memo.builder().memoText("Sample..."+i).build();
			memoRepository.save(memo);
		});
	}
	
	//데이터 조회 테스트
	@Test
	public void testSelect() {
		//데이터베이스에 존재하는 mno
		Long mno = 100L;
		Optional<Memo> result = memoRepository.findById(mno);
		log.info("================================================");
		if(result.isPresent()) {
			Memo memo = result.get();
			log.info(memo);
		}
	}

	//데이터 수정
	@Test
	public void testUpdate() {
		Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
		log.info(memoRepository.save(memo));
	}
	
	//데이터 삭제
	@Test
	public void testDelete() {
		Long mno = 100L;
		memoRepository.deleteById(mno);
	}
	
	//페이징 테스트 
	// 페이징
	@Test
	public void testPageDefault() {
		//1페이지 10개
		Pageable pageable = PageRequest.of(0,10);
		Page<Memo> result = memoRepository.findAll(pageable);
		log.info(result);
		log.info("--------------------------------------------------");
		log.info("Total Pages: "+result.getTotalPages());  //전체 페이지 개수
		log.info("Total Count: "+result.getTotalElements());  //전체 데이터 개수
		log.info("Page Number: "+result.getNumber());  //현재 페이지 번호 0부터 시작
		log.info("Page Size: "+result.getSize());  //페이지당 데이터 개수
		log.info("Has next page?: "+result.hasNext());  //다음 페이지 존재 여부
		log.info("First Page?: "+result.isFirst());  //시작 페이지(0) 여부
		log.info("--------------------------------------------------");
		//데이터 순회
		for(Memo memo:result.getContent()) {
			log.info(memo);
		}
				
	}
	
	//정렬 테스트 - mno의 내림차순 정렬  //initialzation Error발생
	public void testSort2() {
		Sort sort1 = Sort.by("mno").descending();
		Pageable pageable = PageRequest.of(0, 10,sort1);
		Page<Memo> result = memoRepository.findAll(pageable);
		result.get().forEach(memo -> {
			log.info(memo);
		});
	}
	
	//정렬 테스트 - 결합된 조건
	@Test
	public void testSortConcate() {
		Sort sort1 = Sort.by("mno").descending();
		Sort sort2 = Sort.by("memoText").ascending();
		Sort sortAll = sort1.and(sort2); // and를 이용한 연결
		Pageable pageable = PageRequest.of(0, 10,sortAll); //결합된 정렬 조건
		Page<Memo> result = memoRepository.findAll(pageable);
		result.get().forEach(memo -> {
			log.info(memo);
		});
	}
	
	@Test
	public void testQueryMethods() {
		List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L, 80L);
		for (Memo memo:list) {
			log.info(memo);
		}
	}
	
	@Test
	public void testQueryMethodsPaging() {
		Pageable pageable = PageRequest.of(0, 10,Sort.by("mno").descending());
		Page<Memo> result = memoRepository.findByMnoBetween(10L, 50L, pageable);
		result.get().forEach(memo -> log.info(memo));
	}
	
	@Commit //작업을 완료하기 위해서 설정
	@Transactional  //설정하지 않으면 에러
	@Test
	public void testDeleteQueryMethods() {
		memoRepository.deleteMemoByMnoLessThan(10L);
	}
	
	//파라미터 바인딩 
	@Test
	public void testUpdateQuery(){
	 	log.info(memoRepository.updateMemoText(11L,"@Query를 이용한 수정"));
	  	log.info(memoRepository.updateMemoText(Memo.builder().mno(12L).memoText("@Query를 이용한 수정").build()));	
	}
	
	//페이징 처리
	@Test
	public void testSelectQuery(){
	    Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
	    Page<Memo> page = memoRepository.getListWithQuery(50L,pageable);
	    for(Memo memo:page){
	      	log.info(memo);
	    }
	}
	
	//Object[] 리턴 테스트 
	@Test
    public void testSelectQueryObjectReturn(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
        Page<Object[]> page = memoRepository.getListWithQueryObject(50L, pageable);
        for(Object[] arr:page){
        	log.info(Arrays.toString(arr));
        }
    }
}
