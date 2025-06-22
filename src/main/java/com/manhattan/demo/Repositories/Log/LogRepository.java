package com.manhattan.demo.Repositories.Log;

import com.manhattan.demo.Entities.Log.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository <LogEntity,Long> {
}
