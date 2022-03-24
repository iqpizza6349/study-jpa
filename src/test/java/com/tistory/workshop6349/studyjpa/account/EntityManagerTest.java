package com.tistory.workshop6349.studyjpa.account;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceUnitUtil;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntityManagerTest {

    @PersistenceContext(type = PersistenceContextType.TRANSACTION)
    private EntityManager entityManager;

    @Autowired
    private AccountService accountService;

    @BeforeEach
    public void testData() {
        Account account = Account.builder()
                .id(1)
                .name("Mr. John")
                .build();
        entityManager.persist(account);
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @Transactional
    public void 영속성_컨텍스트에_엔티티_저장되었는지_테스트() {
        Account account = entityManager.find(Account.class, 1);
        assertNotNull(account);

        PersistenceUnitUtil persistenceUnitUtil = entityManager.getEntityManagerFactory()
                .getPersistenceUnitUtil();
        boolean hasEntity = persistenceUnitUtil.isLoaded(account);
        assertThat(true, is(hasEntity));
    }

    @Test
    @Transactional
    public void 영속성_컨텍스트에_저장되어_있는_엔티티_재사용_확인() {
        Account account = entityManager.find(Account.class, 1);
        assertNotNull(account);

        Account account2 = entityManager.find(Account.class, 1);
        assertNotNull(account2);

        assertThat(account, is(sameInstance(account2)));
    }

    @Test
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void 언제_영속성_컨텍스트_flush가_이루어지나() {
        accountService.data();
        accountService.updateAccount();

        Account account = entityManager.find(Account.class, 2);
        assertThat("Mr. B", is(account.getName()));
    }

}
