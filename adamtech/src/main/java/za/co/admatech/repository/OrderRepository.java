/*OrderRepository.java
  Order Repository Interface
  Author: Naqeebah Khan (219099073)
  Date: 24 May 2025
 */

package za.co.admatech.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.admatech.domain.Order;

public interface OrderRepository extends JpaRepository<Order, String> {

}
