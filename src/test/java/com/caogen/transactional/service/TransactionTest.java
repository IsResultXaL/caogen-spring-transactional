package com.caogen.transactional.service;

import com.caogen.transactional.TransactionalTestApplication;
import com.caogen.transactional.exception.CustomException;
import com.caogen.transactional.service.impl.AnotherSpringTransactionImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author 康良玉
 * @Description 描述
 * @Create 2022-07-07 12:08
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TransactionalTestApplication.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TransactionTest {

    @Autowired
    private ISpringTransaction springTransaction;

    @Autowired
    private AnotherSpringTransactionImpl anotherSpringTransaction;

    @Test
    public void testCatchExceptionCanNotRollback() {
        springTransaction.CatchExceptionCanNotRollback();
    }

    @Test
    public void testNotRuntimeExceptionCanNotRollback() throws CustomException {
        springTransaction.NotRuntimeExceptionCanNotRollback();
    }

    @Test
    public void testRuntimeExceptionCanRollback() {
        springTransaction.RuntimeExceptionCanRollback();
    }

    @Test
    public void testAssignExceptionCanRollback() throws CustomException {
        springTransaction.AssignExceptionCanRollback();
    }

    @Test
    public void testRollbackOnlyCanRollback() throws Exception {
        springTransaction.RollbackOnlyCanRollback();
    }

    @Test
    public void testNonTransactionalCanNotRollback() {
        springTransaction.NonTransactionalCanNotRollback();
    }

    @Test
    public void testTransactionalCanRollback() {
        anotherSpringTransaction.TransactionalCanRollback();
    }

}
