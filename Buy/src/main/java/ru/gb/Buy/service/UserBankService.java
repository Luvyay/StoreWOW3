package ru.gb.Buy.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.Buy.model.UserBank;
import ru.gb.Buy.model.enums.BankMethodPayment;
import ru.gb.Buy.repository.UserBankRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserBankService {
    private UserBankRepository userBankRepository;
    @PersistenceContext
    private EntityManager em;

    /**
     * Метод по получение всех пользователей банка
     * @return
     */
    public List<UserBank> getAllUserBank() {
        return userBankRepository.findAll();
    }

    /**
     * Метод по получению конкретного пользователя по id (не idUser)
     * @param id
     * @return
     */
    public UserBank getUserBankById(Long id) {
        return userBankRepository.findById(id).orElseThrow();
    }

    /**
     * Метод по получению конкретного пользователя по id пользователя и по способу получению
     * @param idUser
     * @param bankMethodPayment
     * @return
     */
    public UserBank getUserBankByUserIdAndByMethodPayment(Long idUser, BankMethodPayment bankMethodPayment) {
        List<UserBank> userBanks = em.createQuery("SELECT u FROM UserBank u WHERE u.idUser = :paramIdUser AND u.bankMethodPayment = :paramMethod",
                        UserBank.class)
                .setParameter("paramIdUser", idUser)
                .setParameter("paramMethod", bankMethodPayment)
                .getResultList();

        if (userBanks.size() > 0) return userBanks.get(0);

        return null;
    }

    /**
     * Метод по созданию нового пользователя в банке
     * @param userBank
     * @return
     */
    public UserBank createUserBank(UserBank userBank) {
        return userBankRepository.save(userBank);
    }

    /**
     * Метод по обновлению пользователя в банке
     * @param userBank
     * @return
     */
    public UserBank updateUserBank(UserBank userBank) {
        return userBankRepository.save(userBank);
    }
}
