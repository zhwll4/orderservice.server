package orderservice.server.service.app.sample.dba;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import orderservice.server.core.jpa.custom.CustomJpaBaseDaoImpl;

/**
 * 自定义接口的实现,CLASS 类名为JPA主查询接口类名+Impl（在配置文件中固定的，也是默认的）,这里统一继承CustomJpaBaseDaoImpl
 * 父类CustomJpaBaseDaoImpl中 默认用的是 defaultEntityManager,具体可以查看父类
 * 
 * 这个使用自定义主要是为了实现一些 特殊的 不能在service中完成 的 查询 或者 保存 等其他方法
 * 
 * @author zhwioio
 *
 */

public class TestJpaADaoImpl extends CustomJpaBaseDaoImpl implements ITestJpaADaoCustom {

	protected EntityManager em2;
	
	@PersistenceContext(unitName = "secondEntityManager")
	public void setEm2(EntityManager em) {
		this.em2 = em;
	}
	
	@Override
	public List getAllTest1() {
		// TODO Auto-generated method stub
		
		//用自定义的查询数据源1
		List l = this.findBySql("select t from Test1 t");
		
		//查询数据源2
		l.addAll(this.em2.createQuery("select t from Test1 t").getResultList());
		
		return l;
	}
	
}
