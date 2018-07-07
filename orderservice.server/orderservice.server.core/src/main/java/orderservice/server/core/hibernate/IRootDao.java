package orderservice.server.core.hibernate;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface IRootDao {

	
	public <T> T save(T obj);
	
	
	
	public <T> T update(T obj);
	
	public <T> T saveOrUpdate(T obj);
	public  Collection saveOrUpdateAll(Collection col);
	
	public int updateHql(String hql);
	
	public void updateSql(String sql);

	public List findHql(String hql);
	
	
	public List findSql(String sql);
	
	public <T> T getObj(Class<T> cls,Serializable param);
	
	public <T> List<T> getAll(Class<T> cls );
	
	public void deleteObj(Object obj);
	
	public void deleteALL(Collection col);
	
}
