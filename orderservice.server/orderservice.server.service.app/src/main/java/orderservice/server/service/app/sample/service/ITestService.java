package orderservice.server.service.app.sample.service;

import java.util.List;

import orderservice.server.core.mini.MiniException;
import orderservice.server.core.mini.MiniNotRollBackException;
import orderservice.server.core.po.Test1;

public interface ITestService {

	void saveByRepo(Test1 t1);
	
	void saveByDao(Test1 t1) throws MiniException, MiniNotRollBackException;

	List<Object> testGet(Test1 t1) throws MiniException;
	
	List testBaseRepo();
	
	void saveDataByRollBackExp(Test1 t1) throws MiniException, MiniNotRollBackException;
	
	void saveDataByNotRollBackExp(Test1 t1) throws MiniNotRollBackException;
	
	void testSave(Test1 t1) throws MiniException, MiniNotRollBackException;
}
