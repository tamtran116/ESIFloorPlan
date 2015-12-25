package edu.umsl.esi.floorplan.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;

@Entity
@Table(name="CUBE")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class CubeDO implements Serializable{
	
    private static final String DEFAULT_NAME = "OPEN";  
    private static final String DEFAULT_TEAM_LEADER = "NOT ASSIGNED";  
	private static final boolean DEFAULT_ONSHORE = false;
	private static final boolean DEFAULT_OFFSHORE = false;
	private static final boolean DEFAULT_BADGE = false;
	private static final boolean DEFAULT_PARKING = false;
	private static final boolean DEFAULT_LAPTOP = false;
	private static final boolean DEFAULT_VPN = false;

	@Id
	@Column(name="CUBE_ID")
	private String cube_id;
	
	@Column(name="X1")
	private double x1;
	
	@Column(name="Y1")
	private double y1;
	
	@Column(name="X2")
	private double x2;
	
	@Column(name="Y2")
	private double y2;
	
	@Column(name="WIDTH")
	private double width;
	
	@Column(name="HEIGHT")
	private double height;
	
	@Column(name="EMPLOYEE_NAME")
	private String employee_name = DEFAULT_NAME;
	
	@Column(name="OCCUPIED")
	private Boolean occupied;
	
	@Column(name="TEAM_LEADER")
	private String teamLeader = DEFAULT_TEAM_LEADER;
	
	@Column(name="CX")
	private double cx;
	
	@Column(name="CY")
	private double cy;
	
	@Column(name="CURRENT_DISTANCE")
	private double current_distance = 0;
	
	@Column(name="PHONE")
	private String phone;
	
	@Column(name="SOFTWARE")
	private String software;
	
	@Column(name="ONSHORE")
	private Boolean onshore = DEFAULT_ONSHORE;
	
	@Column(name="OFFSHORE")
	private Boolean offshore = DEFAULT_OFFSHORE;
	
	@Column(name="BADGE")
	private Boolean badge = DEFAULT_BADGE;
	
	@Column(name="PARKING")
	private Boolean parking = DEFAULT_PARKING;
	
	@Column(name="LAPTOP")
	private Boolean laptop = DEFAULT_LAPTOP;
	
	@Column(name="VPN")
	private Boolean vpn = DEFAULT_VPN;
	
	@ManyToOne
	@JoinColumn(name="floor_id")
	private FloorDO floor;
	
	public FloorDO getFloor() {
		return floor;
	}

	public void setFloor(FloorDO floor) {
		this.floor = floor;
	}

	@JsonProperty
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonProperty
	public String getSoftware() {
		return software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	@JsonProperty
	public Boolean getOnshore() {
		return onshore;
	}

	public void setOnshore(Boolean onshore) {
		this.onshore = onshore;
	}

	@JsonProperty
	public Boolean getOffshore() {
		return offshore;
	}

	public void setOffshore(Boolean offshore) {
		this.offshore = offshore;
	}

	@JsonProperty
	public Boolean getBadge() {
		return badge;
	}

	
	public void setBadge(Boolean badge) {
		this.badge = badge;
	}

	@JsonProperty
	public Boolean getParking() {
		return parking;
	}

	public void setParking(Boolean parking) {
		this.parking = parking;
	}

	@JsonProperty
	public Boolean getLaptop() {
		return laptop;
	}

	public void setLaptop(Boolean laptop) {
		this.laptop = laptop;
	}

	@JsonProperty
	public Boolean getVpn() {
		return vpn;
	}

	public void setVpn(Boolean vpn) {
		this.vpn = vpn;
	}

	@JsonProperty
	public double getCurrent_distance() {
		return current_distance;
	}

	public void setCurrent_distance(double current_distance) {
		this.current_distance = current_distance;
	}

	public double getCx() {
		return cx;
	}

	public double getCy() {
		return cy;
	}
	@JsonProperty
	public String getCube_id() {
		return cube_id;
	}

	public void setCube_id(String cube_id) {
		this.cube_id = cube_id;
	}

	

	@JsonProperty
	public String getEmployee_name() {
		return employee_name;
	}

	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}

	@JsonProperty
	public Boolean getOccupied() {
		return occupied;
	}

	public void setOccupied(Boolean occupied) {
		this.occupied = occupied;
	}

	@JsonProperty
	public String getTeamLeader() {
		return teamLeader;
	}

	public void setTeamLeader(String teamLeader) {
		this.teamLeader = teamLeader;
	}

	public double getX1() {
		return x1;
	}

	public void setX1(double x1) {
		this.x1 = x1;
	}

	public double getY1() {
		return y1;
	}

	public void setY1(double y1) {
		this.y1 = y1;
	}

	public double getX2() {
		return x2;
	}

	public void setX2(double x2) {
		this.x2 = x2;
	}

	public double getY2() {
		return y2;
	}

	public void setY2(double y2) {
		this.y2 = y2;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setCx(double cx) {
		this.cx = cx;
	}

	public void setCy(double cy) {
		this.cy = cy;
	}

	@Override
	public String toString() {
		return "CubeDO [cube_id=" + cube_id + ", x1=" + x1 + ", y1=" + y1
				+ ", x2=" + x2 + ", y2=" + y2 + ", width=" + width
				+ ", height=" + height + ", employee_name=" + employee_name
				+ ", occupied=" + occupied + ", teamLeader=" + teamLeader
				+ ", cx=" + cx + ", cy=" + cy + ", current_distance="
				+ current_distance + ", phone=" + phone + ", software="
				+ software + ", onshore=" + onshore + ", offshore=" + offshore
				+ ", badge=" + badge + ", parking=" + parking + ", laptop="
				+ laptop + ", vpn=" + vpn + "]";
	}
	
	
}
