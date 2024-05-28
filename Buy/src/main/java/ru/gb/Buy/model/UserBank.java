package ru.gb.Buy.model;

import jakarta.persistence.*;
import lombok.Data;
import ru.gb.Buy.model.enums.BankMethodPayment;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "user_in_bank")
public class UserBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_user")
    private Long idUser;
    @Column(name = "bank_method_payment")
    private BankMethodPayment bankMethodPayment;
    @Column(name = "amount")
    private BigDecimal amount;
}
