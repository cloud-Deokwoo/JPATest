package com.adamsoft.jpa.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.adamsoft.jpa.entity.Memo;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class RepositoryTest {

	@Autowired
	MemoRepository memoRepository;
	
	@Test
	public void testDependency() {
		log.info("주입 여부:"+memoRepository.getClass().getName());
	}
	
	@Test
	public void testInsert() {
		IntStream.range(1, 100).forEach(i -> {
			Memo memo = Memo.builder().memoText("Sameple..."+i).build();
			memoRepository.save(memo);
		});
	}
	
	@Test
	public void testSelect() {
		Long mno = 99L;
		Optional<Memo> result = memoRepository.findById(mno);
		log.info("====================================");
		if(result.isPresent()) {
			Memo memo = result.get();
			log.info(memo);
		}
	}
	
	@Test
	public void testUpdate() {
		Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
		log.info(memoRepository.save(memo));
	}
	
	
	@Test
	public void testDelete() {
		Long mno = 110L;
		memoRepository.deleteById(mno);
	}
	
}
