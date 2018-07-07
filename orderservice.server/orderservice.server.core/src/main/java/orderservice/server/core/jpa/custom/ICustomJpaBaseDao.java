package orderservice.server.core.jpa.custom;

import java.util.List;

public interface ICustomJpaBaseDao {

	
	List findBySql(String sql);
	
}
