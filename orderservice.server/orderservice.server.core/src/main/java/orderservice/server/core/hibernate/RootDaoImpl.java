package orderservice.server.core.hibernate;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import orderservice.server.core.basic.BasicUtils;
import orderservice.server.core.form.SplitPageForm;
import orderservice.server.core.mini.MiniException;


public class RootDaoImpl extends HibernateDaoSupport implements IRootDao{
	
	
	private SessionFactory sessionFacotry;
	
	@Autowired(required=false)
	@Qualifier("defaultSessionFactory")
	public void setSessionFacotry(SessionFactory sessionFacotry) {  
		this.sessionFacotry = sessionFacotry;
        super.setSessionFactory(sessionFacotry);  
    }
	
	protected HibernateTemplate getTp(){
		return super.getHibernateTemplate();
	}
	
	
	public <T> T save(T obj){
		getTp().save(obj);
		
		return obj;
	}
	
	
	
	public <T> T update(T obj){
		getTp().update(obj);
		return obj;
	}
	
	
	public <T> T saveOrUpdate(T obj){
		getTp().saveOrUpdate(obj);
		return obj;
	}
	public Collection saveOrUpdateAll(Collection col){
		//getTp().saveOrUpdateAll(col);
		for (Object object : col) {
			getTp().saveOrUpdate(object);
		}
		return col;
	}
	
	public int updateHql(String hql){
		
		return getTp().getSessionFactory().getCurrentSession().createQuery(hql).executeUpdate();
	}
	
	public void updateSql(String sql){
		//getTp().flush();
		getTp().getSessionFactory().getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	public SQLQuery getSQLQuery(String sql){
		
		return getTp().getSessionFactory().getCurrentSession().createSQLQuery(sql);
	}
	
	
	public List findHql(String hql){
		
		this.getTp().flush();
		//System.out.println(hql);
		
		return getTp().find(hql);
	}
	
	
	/**
	 * 不能使用缓存的查询,要是用缓存请createSQLQuery后添加 addScalar方式才能缓存
	 */
	public List findSql(String sql){
		this.getTp().flush();
		return getTp().getSessionFactory().getCurrentSession().createSQLQuery(sql).list();
	}
	
	public void deleteALL(Collection col) {
		// TODO Auto-generated method stub
		getTp().deleteAll(col);
	}
	public void deleteObj(Object obj) {
		// TODO Auto-generated method stub
		getTp().delete(obj);
	}
	
	
	public <T> List<T> getAll(Class<T> cls) {
		// TODO Auto-generated method stub
		return getTp().loadAll(cls);
	}
	
	public <T> T getObj(Class<T> cls, Serializable param) {
		// TODO Auto-generated method stub
		return getTp().get(cls, param);
	}
	
	public String createId(String curId,int length)
	{
			
		if(!curId.equals("null"))		
		{						
			String nextId = getNextId(curId,length);
			return nextId;
		}
		else
		{
			String nextId = getNextId("0",length);
			return nextId;
		}
	}
	
	public String getNextId(String curId,int length)
	{
		int nextid = Integer.parseInt(curId)+1;
		String id = Integer.toString(nextid);
		String nextId="";
		for(int i=0;i<length-(id.length());i++)
		{
			nextId = nextId+"0";
		}
		nextId = nextId+id;
		return nextId;
	}
	
	/**
	 * 
	 * @param page 当前 页数
	 * @param pagesize 每页大小
	 * @param hsql sql或hql 
	 * @param countHql 计算总署的HQL 可以为NULL
	 * @param isSql 是不是 sql
	 * @return Object[List , totalPage]
	 * @throws MiniException
	 */
	public SplitPageForm getResultByPage(int page,Integer pagesize,String hsql,String countHql,boolean isSql) throws MiniException{
		getTp().flush();
		if(countHql==null){
			String hsqlNew=hsql.split("group")[0].replaceAll("^ *", "")+"  ";
			//hsql=hsql.split("group")[0].replaceAll("^ *", "")+"  ";
			if(hsqlNew.startsWith("from")){
				countHql="select count(Id) "+hsqlNew;
			}else{
				countHql="";
				String[] hsqlNews=hsqlNew.split(" from ");
				String[] names;
				if(hsqlNews[0].indexOf(",")>0){
					names=hsqlNews[0].split(",");
				}else{
					names=hsqlNews[0].replaceAll(" *$", "").split(" ");
				}
				String name=names[names.length-1].split(" as ")[0].trim();	
				for(int i=1;i<hsqlNews.length;i++){
					countHql+="  from  "+hsqlNews[i].split(" order by ")[0];
				}
				if(hsql.indexOf(" distinct ")>0){
					name=" distinct "+name;
				}
				countHql="select count("+name.replaceAll("\\(|\\)", "")+")  "+countHql;
			}
		}	
		List countList;
		if(isSql){
			countList=this.findSql(countHql);
		}else{
			countList=getTp().find(countHql);
		}
		Integer total=Integer.parseInt(countList.get(0).toString());
		if(total<=0){
			try {
				throw new MiniException(BasicUtils.getText("resultIsNull"));
			} catch (MiniException e) {
				// TODO Auto-generated catch block
				throw new MiniException("没有找到相关数据！");
			}
		}
		else{
			page=page<1?1:page;
			if(pagesize<0){
				pagesize=total<50?total:50;
			}
			int totalPage=total/pagesize;
			if(total%pagesize>0){
				totalPage++;
			}
			page=page>totalPage?totalPage:page;
			int min=(page-1)*pagesize;
			Query query;
			if(isSql){
				query=getTp().getSessionFactory().getCurrentSession().createSQLQuery(hsql);
			}else{
				query=getTp().getSessionFactory().getCurrentSession().createQuery(hsql);
			}
			query.setFirstResult(min);
			query.setMaxResults(pagesize);
			List list=query.list();
			//Object[] listInt=new Object[]{list,totalPage,total};
			
			SplitPageForm spf = new SplitPageForm();
			spf.resultList = list;
			spf.totalCount = total;
			spf.totalPage = totalPage;
			return spf;
			//return listInt;
		}
	}
	
	@SuppressWarnings("deprecation")
	public List getListTopAny(String hsql,int ct,boolean isSql){
		Query query;
		if(isSql){
			query=getTp().getSessionFactory().getCurrentSession().createSQLQuery(hsql);
		}else{
			query=getTp().getSessionFactory().getCurrentSession().createQuery(hsql);
		}
		query.setFirstResult(0);
		query.setMaxResults(ct);
		return query.list();
	}
	
	
	
	 public void freeAnyObj(Object obj){
		getTp().getSessionFactory().getCurrentSession().evict(obj);
		
	 }
	 
	 protected void flush(){
		 this.getTp().getSessionFactory().getCurrentSession().flush();
		this.getTp().getSessionFactory().getCurrentSession().clear(); 
	 }
	 
}
