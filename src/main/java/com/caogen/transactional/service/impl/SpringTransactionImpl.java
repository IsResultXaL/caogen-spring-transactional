package com.caogen.transactional.service.impl;

import com.caogen.transactional.dao.ExtraAdDao;
import com.caogen.transactional.entity.ExtraAd;
import com.caogen.transactional.exception.CustomException;
import com.caogen.transactional.service.ISpringTransaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author 康良玉
 * @Description 描述
 * @Create 2022-07-07 12:00
 */
@Slf4j
@Service
public class SpringTransactionImpl implements ISpringTransaction {

    /**
     * ExtraAd Dao
     */
    private final ExtraAdDao extraAdDao;

    @Autowired
    public SpringTransactionImpl(ExtraAdDao extraAdDao) {
        this.extraAdDao = extraAdDao;
    }

    /**
     * <h2>捕捉异常, 导致不能回滚</h2>
     */
    @Override
    @Transactional
    public void CatchExceptionCanNotRollback() {
        try {
            extraAdDao.save(new ExtraAd("caogen"));
            throw new RuntimeException();
        } catch (Exception ex) {
            ex.printStackTrace();
            // 手动标记回滚
            // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    /**
     * <h2>捕捉异常并转换异常, 导致不能回滚</h2>
     */
    @Override
    @Transactional
    public void NotRuntimeExceptionCanNotRollback() throws CustomException {
        try {
            extraAdDao.save(new ExtraAd("caogen"));
            throw new RuntimeException();
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    /**
     * <h2>RuntimeException 异常可以回滚</h2>
     * */
    @Override
    @Transactional
    public void RuntimeExceptionCanRollback() {
        extraAdDao.save(new ExtraAd("caogen"));
        throw new RuntimeException();
    }

    /**
     * <h2>指定异常, 可以回滚</h2>
     * */
    @Override
    @Transactional(rollbackFor = {CustomException.class})
    public void AssignExceptionCanRollback() throws CustomException {
        try {
            extraAdDao.save(new ExtraAd("caogen"));
            throw new RuntimeException();
        } catch (Exception ex) {
            throw new CustomException(ex.getMessage());
        }
    }

    /**
     * <h2>在 private 方法上标注 transactional, 事务无效</h2>
     * */
    @Transactional
    public void oneSaveMethod() {
        extraAdDao.save(new ExtraAd("caogen"));
    }

    /**
     * <h2>Rollback Only, 事务可以回滚</h2>
     * A方法有事务，B方法有事务，A方法调用了B方法，Spring会把两个事务合并，并且事务类型是 Rollback Only
     * 只有两个事务都成功了才会提交，一个失败就会回滚
     * @throws Exception
     */
    @Override
    @Transactional
    public void RollbackOnlyCanRollback() throws Exception {
        oneSaveMethod();
        try {
            extraAdDao.save(new ExtraAd());
        } catch (Exception e) {
            e.printStackTrace();
            // 需要把异常抛出，要不然会打印 org.springframework.transaction.UnexpectedRollbackException: Transaction silently rolled back because it has been marked as rollback-only
            // 表示事务处理异常，抛出异常之后才会进行回滚, 虽然事务异常和事务回滚都不会更改数据库数据，性质不一样
            throw e;
        }
    }

    /**
     * <h2>在private方法上标注transactional, 事务无效</h2>
     * */
    @Transactional
    public void anotherOneSaveMethod() {
        extraAdDao.save(new ExtraAd("caogen"));
        throw new RuntimeException();
    }

    /**
     * <h2>同一个类中, 一个不标注事务的方法去调用 transactional 的方法, 事务会失效</h2>
     */
    @Override
    public void NonTransactionalCanNotRollback() {
        anotherOneSaveMethod();
    }
}
