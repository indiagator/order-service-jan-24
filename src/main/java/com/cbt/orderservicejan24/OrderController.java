package com.cbt.orderservicejan24;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController
{
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderstatusRepository orderstatusRepository;

    Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("save/order")
    public ResponseEntity<Order> saveOrder(@RequestBody Order order)
    {
        order.setOrderid(String.valueOf((int) (Math.random() * 10000)));
        orderRepository.save(order);

        Orderstatus orderstatus = new Orderstatus();
        orderstatus.setId(String.valueOf((int) (Math.random() * 10000)));
        orderstatus.setOrderid(order.getOrderid());
        orderstatus.setStatus("OPEN");

        orderstatusRepository.save(orderstatus);

        logger.info("order placed successfully");

        return ResponseEntity.ok(order);
    }

    @PostMapping("accept/order/{orderid}")
    public ResponseEntity<String> updateOrderStatusUponAccept(@PathVariable String orderid)
    {

        String offerid = orderRepository.findById(orderid).get().getOfferid();

        List<Order> orders = orderRepository.findByOfferid(offerid);
        orders.stream().
                forEach(tempOrder ->
                {
                    if(!(tempOrder.getOrderid().equals(orderid)))
                    {
                        orderstatusRepository.updateStatusByOrderid("REJECTED",tempOrder.getOrderid());
                    }
                    else {orderstatusRepository.updateStatusByOrderid("ACCEPTED",tempOrder.getOrderid());}
                });

        logger.info("order accepted successfully");

        return ResponseEntity.ok().body("Order Accepted");

    }

}
