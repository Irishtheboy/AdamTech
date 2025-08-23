/*
 * ProductRepository.java
 * ProductRepository Class
 * Author: Seymour Lawrence (230185991)
 * Date: 25 May 2025
 */
package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.admatech.domain.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}