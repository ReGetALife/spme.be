package com.spme.dao;

import com.spme.Entity.ResultEntity;
import com.spme.Entity.ResultMultiKeys;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultDao extends JpaRepository<ResultEntity, ResultMultiKeys> {
}
