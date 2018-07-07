package orderservice.server.core.jpa.baserepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.NoRepositoryBean;
 
import java.io.Serializable;
import java.util.List;
 
/**
 * 
 * 这个本来是想要做一些给所有jpa 都增加 固定方法的 类，但是没有 测试成功，暂且所有的 JPA 接口 都继承它吧
 * 
 * @author zhwioio
 *
 * @param <T>
 * @param <ID>
 */
public interface 	IBaseRepository<T,ID extends Serializable> extends JpaRepository<T,ID> {
   
}
