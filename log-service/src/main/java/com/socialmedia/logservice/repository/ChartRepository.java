package com.socialmedia.logservice.repository;

import com.socialmedia.logservice.model.Chart;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface ChartRepository extends CrudRepository<Chart, UUID> {
}
