package orderservice.server.service.app.sample.dbb;


import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import orderservice.server.core.hibernate.RootDaoImpl;
import orderservice.server.core.mini.MiniException;
import orderservice.server.core.po2.Test1;

/**
 * 这里使用 hibernate实现的
 * @author zhwioio
 *
 */

@Repository("testDbBDao")
public class TestDbbDaoImp extends RootDaoImpl   implements ITestDbBDao {
	
	
	@Autowired
	@Qualifier("secondSessionFactory")
	@Override
	public void setSessionFacotry(SessionFactory sessionFacotry) {  
        super.setSessionFactory(sessionFacotry);  
    }
	
	@Override
	public void saveTest1(Test1 t1) {
		// TODO Auto-generated method stub
		this.saveOrUpdate(t1);
		System.out.println(" TESTB dao  执行完成。。。。。。。。"+t1.getT2());
	}

	@Override
	public void saveTest1Exp(Test1 t1) throws MiniException {
		// TODO Auto-generated method stub
		this.saveOrUpdate(t1);
		throw new MiniException("强制错误......");
	}

	@Override
	public Test1 getData(Test1 t1) {
		// TODO Auto-generated method stub
		return (Test1)this.getObj(Test1.class, t1.getT1());
	}

}
