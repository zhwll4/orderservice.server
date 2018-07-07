package orderservice.server.core.jpa.custom;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;

public class CustomJpaBaseDaoImpl implements ICustomJpaBaseDao {

	
	private EntityManager entityManager;
	
	/**
	 * 如果不是defaultEntityManager,可以覆盖此set方法
	 * @param defaultEm
	 */
	@Autowired(required=false)
	@PersistenceContext(unitName = "defaultEntityManager")
	public void setDefaultEm(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	protected EntityManager getEm(){
		return this.entityManager;
	}
	
	@Override
	public List findBySql(String sql) {
		// TODO Auto-generated method stub
		return this.getEm().createQuery(sql).getResultList();
	}

}
