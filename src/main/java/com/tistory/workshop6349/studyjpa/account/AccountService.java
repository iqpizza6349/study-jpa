package com.tistory.workshop6349.studyjpa.account;

import com.tistory.workshop6349.studyjpa.account.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service(value = "entityManagerAccountService")
public class AccountService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateAccount() {
        Account account = entityManager.find(Account.class, 2);
        account.updateName("Mr. B");
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void data() {
        Account account = Account.builder()
                .id(2)
                .name("Mr. A")
                .build();
        entityManager.persist(account);
    }
}
