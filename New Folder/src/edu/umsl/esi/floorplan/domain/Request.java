package edu.umsl.esi.floorplan.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="REQUEST")
public class Request implements Serializable{
    
	@Id
	@Column(name="REQUEST_ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int request_id;
	
	@Column(name="EMPLOYEE_NAME")
	private String employee_name;
	
	@Column(name="EMPLOYEE_PHONE")
	private String employee_phone;
	
	@Column(name="EMPLOYEE_EMAIL")
	private String employee_email;
	
	@Column(name="REQUEST_CUBE")
	private String request_cube;
	
	@Column(name="REQUEST_TEAM")
	private String request_team;
	
	@Column(name="OTHER_REQUEST")
	private String other_request;

	public int getRequest_id() {
		return request_id;
	}

	public void setRequest_id(int request_id) {
		this.request_id = request_id;
	}

	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	public String getEmployee_phone() {
		return employee_phone;
	}

	public void setEmployee_phone(String employee_phone) {
		this.employee_phone = employee_phone;
	}

	public String getEmployee_email() {
		return employee_email;
	}

	public void setEmployee_email(String employee_email) {
		this.employee_email = employee_email;
	}

	public String getRequest_cube() {
		return request_cube;
	}

	public void setRequest_cube(String request_cube) {
		this.request_cube = request_cube;
	}

	public String getRequest_team() {
		return request_team;
	}

	public void setRequest_team(String request_team) {
		this.request_team = request_team;
	}

	public String getOther_request() {
		return other_request;
	}

	public void setOther_request(String other_request) {
		this.other_request = other_request;
	}

	@Override
	public String toString() {
		return "Request [request_id=" + request_id + ", employee_name="
				+ employee_name + ", employee_phone=" + employee_phone
				+ ", employee_email=" + employee_email + ", request_cube="
				+ request_cube + ", request_team=" + request_team
				+ ", other_request=" + other_request + "]";
	}
	
}
