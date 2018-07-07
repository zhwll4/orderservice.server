package orderservice.server.service.app.sample.dba;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import orderservice.server.core.jpa.custom.ICustomJpaBaseDao;
import orderservice.server.core.po.Test1;

//需要自定义方法的接口 ，统一继承 ICustomJpaBaseDao ，方便扩展
public interface ITestJpaADaoCustom extends ICustomJpaBaseDao {
	
	
	List getAllTest1();
	
}
