package orderservice.server.service.app.sample.dba;


import orderservice.server.core.jpa.baserepo.IBaseRepository;
import orderservice.server.core.po.Test1;

//JPA主查询接口,SERVICE中使用autowire自动装配,这个添加了 自定义方法的
public interface TestJpaADao extends IBaseRepository<Test1, String>, ITestJpaADaoCustom {

}
