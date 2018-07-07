package orderservice.server.core.po2;
// Generated 2018-7-2 14:30:12 by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Test2 generated by hbm2java
 */
@Entity
@Table(name = "test2", catalog = "hospwx2")
public class Test2 implements java.io.Serializable {

	private String t1;
	private String t2;

	public Test2() {
	}

	public Test2(String t1) {
		this.t1 = t1;
	}

	public Test2(String t1, String t2) {
		this.t1 = t1;
		this.t2 = t2;
	}

	@Id

	@Column(name = "t1", unique = true, nullable = false, length = 10)
	public String getT1() {
		return this.t1;
	}

	public void setT1(String t1) {
		this.t1 = t1;
	}

	@Column(name = "t2", length = 30)
	public String getT2() {
		return this.t2;
	}

	public void setT2(String t2) {
		this.t2 = t2;
	}

}
