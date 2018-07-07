package orderservice.server.service.app.sample.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import orderservice.server.core.basic.BasicUtils;
import orderservice.server.core.mini.MiniException;
import orderservice.server.core.mini.MiniNotRollBackException;
import orderservice.server.core.po.Test1;
import orderservice.server.service.app.sample.dba.ITestDbADao;
import orderservice.server.service.app.sample.dba.TestJpaADao;
import orderservice.server.service.app.sample.dbb.ITestDbBDao;
import orderservice.server.service.app.sample.dbb.TestJpaBDao;


@Service("testJTAService")
@Transactional
public class TestServiceImp implements ITestService{

	/**
	 * 这是一个单独的JPA EntityManager 实现
	 */
	@Autowired
	@Qualifier("testDbADao")
	ITestDbADao testADao;
	
	/**
	 * 这个DAO是 hibernate SessionFactory 实现
	 */
	@Autowired
	@Qualifier("testDbBDao")
	ITestDbBDao testBDao;
	
	/**
	 * 这是一个customDao,虽然是DBA数据源，但是里面注入了 DBB 的数据源 EM，所以是个混合的REPO
	 */
	@Autowired
	TestJpaADao testJpaADao;
	
	/**
	 * 这是一个 没有任何 自定义方法的 JPA 实现
	 */
	@Autowired
	TestJpaBDao testJpaBDao;
	
	
	@Override
	public void saveByRepo(Test1 t1) {
		// TODO Auto-generated method stub
		
		t1.setT2("TEST db1----"+BasicUtils.getStringDateNow());
		this.testJpaADao.save(t1);
		
		orderservice.server.core.po2.Test1 t2 = new orderservice.server.core.po2.Test1();
		t2.setT1(t1.getT1());
		t2.setT2("TEST db2----"+BasicUtils.getStringDateNow());
		this.testJpaBDao.save(t2);
		
		
		
	}
	@Transactional
	@Override
	public void saveByDao(Test1 t1) throws MiniException, MiniNotRollBackException {
		// TODO Auto-generated method stub
		
//		
//		t1.setT2("TEST Hibernate db1----"+BasicUtils.getStringDateNow());
//		this.testADao.saveTest1(t1);
//		
		orderservice.server.core.po2.Test1 t2 = new orderservice.server.core.po2.Test1();
		t2.setT1(t1.getT1());
		t2.setT2("TEST Hibernate db2----"+BasicUtils.getStringDateNow());
		this.testBDao.saveTest1(t2);
		
	}
	
	@Override
	public List<Object> testGet(Test1 t1) throws MiniException {
		// TODO Auto-generated method stub
		List<Object> l = new ArrayList<Object>();
		t1 = this.testADao.getData(t1);
		l.add(t1);
		orderservice.server.core.po2.Test1 t2 = new orderservice.server.core.po2.Test1(t1.getT1());
		
		l.add(this.testBDao.getData(t2));
		return l;
	}

	@Override
	public List testBaseRepo() {
		// TODO Auto-generated method stub
		List l = testJpaADao.getAllTest1();
		
		//再附加一次默认的数据源1
		l.addAll(testJpaADao.findAll());
		
		
		//再附加一次默认的数据源2
		l.addAll(testJpaBDao.findAll());
		
		return l;
	}
	
	/**
	 * 测试事务传播性 是否起作用
	 */
	@Override
	public void testSave(Test1 t1) throws MiniException, MiniNotRollBackException {
		// TODO Auto-generated method stub
		
		t1.setT2("测试是否可以保存");
		
		
		this.testJpaADao.save(t1);
		
		this.testBDao.save(t1);
		
		
	}
	@Override
	public void saveDataByRollBackExp(Test1 t1) throws MiniException {
		// TODO Auto-generated method stub
		
		orderservice.server.core.po2.Test1 t2 = new orderservice.server.core.po2.Test1();
		t2.setT1(t1.getT1());
		t2.setT2("TEST RollBack DB2----"+BasicUtils.getStringDateNow());
		this.testJpaBDao.save(t2);
		
		t1.setT2("TEST RollBack DB1----"+BasicUtils.getStringDateNow());
		this.testADao.saveTest1RollBack(t1);
		
		
	}
	@Override
	public void saveDataByNotRollBackExp(Test1 t1) throws MiniNotRollBackException {
		// TODO Auto-generated method stub
		orderservice.server.core.po2.Test1 t2 = new orderservice.server.core.po2.Test1();
		t2.setT1(t1.getT1());
		t2.setT2("TEST NotRollBack DB2----"+BasicUtils.getStringDateNow());
		this.testJpaBDao.save(t2);
		
		t1.setT2("TEST NotRollBack DB1----"+BasicUtils.getStringDateNow());
		this.testADao.saveTest1NotBack(t1);
	}

}
