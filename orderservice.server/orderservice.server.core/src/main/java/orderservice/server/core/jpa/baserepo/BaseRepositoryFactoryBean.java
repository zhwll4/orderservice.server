package orderservice.server.core.jpa.baserepo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
 
import javax.persistence.EntityManager;
import java.io.Serializable;
 

public class BaseRepositoryFactoryBean<R extends JpaRepository<T, I>, T,
        I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {
	
    public BaseRepositoryFactoryBean(Class<? extends R> repositoryInterface) {
		super(repositoryInterface);
		// TODO Auto-generated constructor stub
	}

	@Override
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new BaseRepositoryFactory(em);
    }
 
    private static class BaseRepositoryFactory<T, I extends Serializable>
            extends JpaRepositoryFactory {
    	
        private final EntityManager em;
 
        public BaseRepositoryFactory(EntityManager em) {
            super(em);
            this.em = em;
        }
 
        //设置具体的实现类是BaseRepositoryImpl
        @Override
        protected Object getTargetRepository(RepositoryInformation information) {
            return new BaseRepositoryImpl<T, I>((Class<T>) information.getDomainType(), em);
        }
 
        //设置具体的实现类的class
        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseRepositoryImpl.class;
        }
    }
}
