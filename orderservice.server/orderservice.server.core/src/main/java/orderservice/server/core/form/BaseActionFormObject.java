package orderservice.server.core.form;

/**
 * @author XiongZhen
 * Create date: 2007-8-20
 */
public class BaseActionFormObject{

	private Integer page=1;
	private Integer pagesize=50;
	private String querystring;
	public String getQuerystring() {
		return querystring;
	}

	public void setQuerystring(String querystring) {
		this.querystring = querystring;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}


}

