package edu.umsl.esi.floorplan.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonProperty;


@Entity
@Table(name="FLOOR")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class FloorEntity implements Serializable{
	
	@Id
	@GeneratedValue
	@Column(name="floor_id", unique=true, nullable=false)
	private Integer floorId;
	
	@Column(name="floor_name")
	private String floorName;
	
	@Column(name="file_path")
	private String filePath;
	
	@Column(name="location")
	private String floorLocation;
	
	@Column(name="uploaded_by")
	private String uploadedBy;
	
	@Column(name="floor_desc")
	private String floorDesc;
	
	@OneToMany(mappedBy="floor")
	private Set <Cube> cubes;

	public Set<Cube> getCubes() {
		return cubes;
	}

	public void setCubes(Set<Cube> cubes) {
		this.cubes = cubes;
	}

	@JsonProperty
	public Integer getFloorId() {
		return floorId;
	}

	public void setFloorId(Integer floorId) {
		this.floorId = floorId;
	}

	@JsonProperty
	public String getFloorName() {
		return floorName;
	}

	public void setFloorName(String floorName) {
		this.floorName = floorName;
	}
	
	@JsonProperty
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@JsonProperty
	public String getFloorLocation() {
		return floorLocation;
	}

	public void setFloorLocation(String floorLocation) {
		this.floorLocation = floorLocation;
	}

	@JsonProperty
	public String getUploadedBy() {
		return uploadedBy;
	}

	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	@JsonProperty
	public String getFloorDesc() {
		return floorDesc;
	}

	public void setFloorDesc(String floorDesc) {
		this.floorDesc = floorDesc;
	}

	@Override
	public String toString() {
		return "FloorEntity [floorId=" + floorId + ", floorName=" + floorName
				+ ", filePath=" + filePath + ", floorLocation=" + floorLocation
				+ ", uploadedBy=" + uploadedBy + ", floorDesc=" + floorDesc
				+ "]";
	}
	
}
