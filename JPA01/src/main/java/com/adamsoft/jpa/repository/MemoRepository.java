package com.adamsoft.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.adamsoft.jpa.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long> {

}
