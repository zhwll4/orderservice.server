package orderservice.server.service.app.sample.dba;


import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import orderservice.server.core.hibernate.RootDaoImpl;
import orderservice.server.core.mini.MiniException;
import orderservice.server.core.mini.MiniNotRollBackException;
import orderservice.server.core.po.Test1;


@Repository("testDbADao")
public class TestDaADaoImp implements ITestDbADao {

	
	protected EntityManager em;
	
	
	@PersistenceContext(unitName = "defaultEntityManager")
	public void setEm(EntityManager em) {
		this.em = em;
	}

	@Override
	public void saveTest1(Test1 t1) {
		// TODO Auto-generated method stub
		em.persist(t1);
	}
	

	@Override
	public Test1 getData(Test1 t1) {
		// TODO Auto-generated method stub
		return em.find(Test1.class, t1.getT1());
	}

	@Override
	public void saveTest1NotBack(Test1 t1) throws MiniNotRollBackException {
		// TODO Auto-generated method stub
		em.persist(t1);
		throw new MiniNotRollBackException("不回滚强制错误......");
	}

	@Override
	public void saveTest1RollBack(Test1 t1) throws MiniException {
		// TODO Auto-generated method stub
		em.persist(t1);
		throw new MiniException("回滚强制错误......");
	}
	
}
