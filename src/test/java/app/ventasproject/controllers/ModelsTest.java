package app.ventasproject.controllers;

import app.ventasproject.Enum.MethodsEnum;
import app.ventasproject.Enum.StatusEnum;
import app.ventasproject.models.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public abstract class ModelsTest {
    public List<Product> productsList(){

        Product product = Product.builder()
                .name("IPHONE 13")
                .price(800.0F)
                .stock(20)
                .build();

        Product product2 = Product.builder()
                .name("IPHONE 14")
                .price(900.0F)
                .stock(25)
                .build();

        return List.of(product, product2);
    }
    
    public List<Client> clientList(){
        Client client = Client.builder()
                .name("Andres Martinez")
                .email("correo@gmail.com")
                .address("Mi casa barranquilla")
                .build();

        Client client2 = Client.builder()
                .name("Lenis Estevez")
                .email("correo2@gmail.com")
                .address("Mi casa Santa Marta")
                .build();

        return List.of(client, client2);
    }

    public List<OrderItem> orderItem(){

        OrderItem orderItem = OrderItem.builder()
                .product(productsList().get(0))
                .quantity(5)
                .unitPrice(productsList().get(0).getPrice())
                .build();

        OrderItem orderItem2 = OrderItem.builder()
                .product(productsList().get(1))
                .quantity(4)
                .unitPrice(productsList().get(1).getPrice())
                .build();
        
        OrderItem orderItem3 = OrderItem.builder()
                .product(productsList().get(1))
                .quantity(6)
                .unitPrice(productsList().get(1).getPrice())
                .build();

        OrderItem orderItem4 = OrderItem.builder()
                .product(productsList().get(0))
                .quantity(6)
                .unitPrice(productsList().get(0).getPrice())
                .build();


        return List.of(orderItem, orderItem2, orderItem3, orderItem4);
    }

    public List<Payment> paymentList(){
        Payment payment = Payment.builder()
                .datePayment(LocalDateTime.now())
                .method(MethodsEnum.NEQUI)
                .build();

        Payment payment2 = Payment.builder()
                .datePayment(LocalDateTime.now())
                .method(MethodsEnum.PSE)
                .build();

        return  List.of(payment, payment2);
    }

    public List<OrderDetail> orderDetailList(){

        OrderDetail orderDetail = OrderDetail.builder()
                .addressOrder("cra 25-123 BARRANQUILLA")
                .transporter("Interrapisimo")
                .guide("12345")
                .build();

        OrderDetail orderDetail2 = OrderDetail.builder()
                .addressOrder("calle 4 CARTAGENA")
                .transporter("Envia")
                .guide("12345")
                .build();

        return  List.of(orderDetail, orderDetail2);
    }

    public List<Order> orderList(){
        List<Client> clientList = clientList();
        Order order = Order.builder()
                .orderDate(LocalDateTime.now())
                .status(StatusEnum.ENVIADO)
                .client(clientList.get(0))
                .orderDetails(Collections.singletonList(orderDetailList().get(0)))
                .build();

        Order order2 = Order.builder()
                .orderDate(LocalDateTime.now())
                .status(StatusEnum.ENVIADO)
                .client(clientList.get(1))
                .orderDetails(Collections.singletonList(orderDetailList().get(0)))
                .build();

        return List.of(order, order2);
    }
}
