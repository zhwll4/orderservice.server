package orderservice.server.core.jpa.baserepo;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Base64Utils;

import orderservice.server.core.basic.BasicUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.io.Serializable;
import java.util.List;

//这个没有测试成功
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)//指定自己的工厂类
public class BaseRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T,ID>
        implements IBaseRepository<T,ID> {
 
    protected EntityManager entityManager;
    protected Class<T>  domainClass;
    
    public BaseRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);
        this.domainClass = domainClass;
        this.entityManager = entityManager;
    }
    
    
}
