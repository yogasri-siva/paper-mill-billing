package com.example.papermill.repository;

import com.example.papermill.entity.PaperOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository abstraction for performing CRUD operations on PaperOrder
 * entities.  Extending JpaRepository supplies all the basic operations
 * automatically; no additional methods are required for this sample.
 */
public interface PaperOrderRepository extends JpaRepository<PaperOrder, Long> {
    // Additional query methods could be declared here if needed.
}