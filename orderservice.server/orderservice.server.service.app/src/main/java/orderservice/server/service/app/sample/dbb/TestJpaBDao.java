package orderservice.server.service.app.sample.dbb;


import orderservice.server.core.jpa.baserepo.IBaseRepository;
import orderservice.server.core.po2.Test1;

//JPA主查询接口,SERVICE中使用autowire自动装配
public interface TestJpaBDao extends IBaseRepository<Test1, String>{

}
