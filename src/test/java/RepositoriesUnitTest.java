import com.tmt.pos.mypos.dao.CustomerRepository;
import com.tmt.pos.mypos.dao.OrderRepository;
import com.tmt.pos.mypos.dao.UsersRepository;
import com.tmt.pos.mypos.entities.Customer;
import com.tmt.pos.mypos.entities.Order;
import com.tmt.pos.mypos.entities.OrderItem;
import com.tmt.pos.mypos.entities.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/applicationContext.xml"})
@Transactional(readOnly = false, transactionManager = "transactionManager")
@Rollback(value = true)
@Slf4j
public class RepositoriesUnitTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UsersRepository userRepository;


    @Test
    public void findCustomer() {
        Customer c = customerRepository.getOne("niranjan");
        assertNotNull("niranjan Customer not found", c);
        log.info("Customer found ? " + c.getFirstName());
    }

    @Test
    public void createOrder() {

        LocalDateTime now = LocalDateTime.now();


        User user = userRepository.findOneByUserName("mubin");

        Order order = new Order();

        order.setCreatedBy(user.getUserName());
        order.setCreationTime(now);
        order.setModificationTime(now);


        Customer c = customerRepository.getOne("niranjan");


        order.setCustomer(c);


        OrderItem item1 = new OrderItem();
        item1.setItemCode("tp420");
        item1.setQuantity(5L);
        item1.setNetAmount(BigDecimal.valueOf(349000));
        item1.setTaxAmount(BigDecimal.valueOf(50000));
        item1.setDiscountAmount(BigDecimal.valueOf(6700));
        item1.setTotalAmount(item1.calculatedTotalAmount());


        OrderItem item2 = new OrderItem();
        item2.setItemCode("lux01");
        item2.setQuantity(2L);
        item2.setNetAmount(BigDecimal.valueOf(50));
        item2.setTaxAmount(BigDecimal.valueOf(6));
        item2.setDiscountAmount(BigDecimal.valueOf(5));
        item2.setTotalAmount(item1.calculatedTotalAmount());


        order.addOrderItem(item1);
        order.addOrderItem(item2);

        order.setPaymentStatus("UNPAID");
        order.setOrderType("CS");
        order.setNetAmount(BigDecimal.valueOf(50000));
        order.setTaxAmount(BigDecimal.valueOf(8600));
        order.setDiscountAmount(BigDecimal.valueOf(5780));
        order.setTotalAmount(BigDecimal.valueOf(50000 + 8600 - 5780));

        log.info(" Order Object - > " + order);
        orderRepository.save(order);
        log.info("Order saved successfully !!");
    }

    @Test
    @Rollback(true)
    public void createUser() {
        User user = new User();
        user.setEmail("niranjan.518@gmail.com");
        user.setFirstName("Niranjan");
        user.setLastName("Velakanti");
        user.setUserName("niranjan_v");
        user.setPassword("niranjan");
        User updateEntity = userRepository.save(user);
        assertTrue("failed to create user", updateEntity.getUserId() > 0);
    }

    @Test
    public void searchAPI(){
        String name = "jo";
        Pageable page = PageRequest.of(0, 100, Sort.by(Sort.Order.asc("firstName"))); //the default behavior

        Page<User> users = userRepository.findByLastNameLikeOrFirstNameLike(name, name, page);
        assertTrue("no users found", users.hasContent());

    }
}
