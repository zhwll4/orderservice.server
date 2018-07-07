package orderservice.server.service.app.sample.dba;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import orderservice.server.core.hibernate.IRootDao;
import orderservice.server.core.mini.MiniException;
import orderservice.server.core.mini.MiniNotRollBackException;
import orderservice.server.core.po.Test1;

public interface ITestDbADao{
	
	public void saveTest1(Test1 t1);
	public void saveTest1NotBack(Test1 t1) throws MiniNotRollBackException;
	public void saveTest1RollBack(Test1 t1) throws MiniException;
	public Test1 getData(Test1 t1);
	
	
}
