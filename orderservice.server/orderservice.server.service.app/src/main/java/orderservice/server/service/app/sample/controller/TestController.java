package orderservice.server.service.app.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import orderservice.server.core.mini.MiniException;
import orderservice.server.core.mini.MiniNotRollBackException;
import orderservice.server.core.po.Test1;
import orderservice.server.service.app.sample.service.ITestService;



@Controller
@Scope("prototype")
@RequestMapping("/nocheckLogin/test")
public class TestController {

	
	
	@Autowired
	@Qualifier("testJTAService")
	ITestService testService;
	
	
	
	@RequestMapping("/saveByRepo/{id}")
	@ResponseBody
	public Test1 testInsert(@PathVariable("id") String id){
		
		Test1 test1 =  new Test1();
		test1.setT1(id);
		test1.setT2("REPO");
		
		this.testService.saveByRepo(test1);
		
		return test1;
		
	}
	@RequestMapping("/saveByDao/{id}")
	@ResponseBody
	public Test1 testInsert2(@PathVariable("id") String id){
		
		Test1 test1 =  new Test1();
		test1.setT2("DAO");
		test1.setT1(id);
		
		try {
			this.testService.saveByDao(test1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test1.setT2("异常返回:"+e.getMessage());
		}
		
		return test1;
		
	}
	@RequestMapping("/saveTest1Get/{id}")
	@ResponseBody
	public List<Object> testInsert3(@PathVariable("id") String id){
		
		Test1 test1 =  new Test1();
		test1.setT1(id);
		try {
			List<Object> l = this.testService.testGet(test1);
			return l;
		} catch (MiniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			test1.setT2(e.getMessage());
		}
		
		return null;
		
		
	}
	@RequestMapping("/saveByBack/{id}")
	@ResponseBody
	public Test1 saveByBack(@PathVariable("id") String id){
		
		Test1 test1 =  new Test1();
		
		test1.setT1(id);
		
		try {
			this.testService.saveDataByRollBackExp(test1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			test1.setT2(e.getMessage());
		}
		
		
		return test1;
		
	}
	
	@RequestMapping("/saveByNotBack/{id}")
	@ResponseBody
	public Test1 saveByNotBack(@PathVariable("id") String id){
		
		Test1 test1 =  new Test1();
		
		test1.setT1(id);
		
		try {
			this.testService.saveDataByNotRollBackExp(test1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			test1.setT2(e.getMessage());
		}
		
		return test1;
	}
	@RequestMapping("/saveTest/{id}")
	@ResponseBody
	public Test1 saveTest(@PathVariable("id") String id){
		
		Test1 test1 =  new Test1();
		
		test1.setT1(id);
		
		try {
			this.testService.testSave(test1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			test1.setT2(e.getMessage());
		}
		
		return test1;
	}
}
