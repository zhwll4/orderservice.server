package orderservice.server.service.app.sample.dbb;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import orderservice.server.core.hibernate.IRootDao;
import orderservice.server.core.mini.MiniException;
import orderservice.server.core.mini.MiniNotRollBackException;
import orderservice.server.core.po2.Test1;

public interface ITestDbBDao  extends IRootDao{
	public void saveTest1(Test1 t1);
	public void saveTest1Exp(Test1 t1) throws MiniException, MiniNotRollBackException;
	
	public Test1 getData(Test1 t1);
	
	
}
