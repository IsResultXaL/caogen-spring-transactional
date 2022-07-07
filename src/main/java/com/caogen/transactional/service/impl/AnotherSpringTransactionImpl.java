package com.caogen.transactional.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author 康良玉
 * @Description 描述
 * @Create 2022-07-07 14:23
 */
@Service
public class AnotherSpringTransactionImpl {

    private final SpringTransactionImpl springTransaction;

    @Autowired
    public AnotherSpringTransactionImpl(SpringTransactionImpl springTransaction) {
        this.springTransaction = springTransaction;
    }

    /**
     * <h2>不同一个类中, 一个不标注事务的方法去调用标注 transactional 的方法, 事务生效</h2>
     */
    public void TransactionalCanRollback() {
        springTransaction.anotherOneSaveMethod();
    }
}
