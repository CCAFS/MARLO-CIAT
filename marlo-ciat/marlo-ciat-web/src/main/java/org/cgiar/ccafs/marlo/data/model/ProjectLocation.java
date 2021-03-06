package org.cgiar.ccafs.marlo.data.model;
// Generated May 11, 2017 11:25:11 AM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * ProjectLocation generated by hbm2java
 */
public class ProjectLocation implements java.io.Serializable, IAuditLog {

  private static final long serialVersionUID = 1286006751155139286L;


  @Expose
  private Long id;


  @Expose
  private LocElement locElement;

  @Expose
  private User modifiedBy;

  @Expose
  private User createdBy;

  @Expose
  private Project project;

  @Expose
  private boolean active;

  @Expose
  private Date activeSince;

  @Expose
  private String modificationJustification;

  public ProjectLocation() {
  }

  public ProjectLocation(boolean active, Date activeSince) {
    this.active = active;
    this.activeSince = activeSince;
  }


  public ProjectLocation(LocElement locElement, User modifiedBy, User createdBy, Project project, boolean active,
    Date activeSince, String modificationJustification) {
    this.locElement = locElement;
    this.modifiedBy = modifiedBy;
    this.createdBy = createdBy;
    this.project = project;
    this.active = active;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (this.getClass() != obj.getClass()) {
      return false;
    }
    ProjectLocation other = (ProjectLocation) obj;
    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }
    return true;
  }


  public Date getActiveSince() {
    return activeSince;
  }


  public User getCreatedBy() {
    return createdBy;
  }


  @Override
  public Long getId() {
    return id;
  }


  public LocElement getLocElement() {
    return locElement;
  }


  @Override
  public String getLogDeatil() {
    StringBuilder sb = new StringBuilder();
    sb.append("Id : ").append(this.getId());
    return sb.toString();
  }


  public String getModificationJustification() {
    return modificationJustification;
  }

  @Override
  public User getModifiedBy() {
    return modifiedBy;
  }

  public Project getProject() {
    return project;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    return result;
  }

  @Override
  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public void setActiveSince(Date activeSince) {
    this.activeSince = activeSince;
  }

  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setLocElement(LocElement locElement) {
    this.locElement = locElement;
  }


  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  public void setProject(Project project) {
    this.project = project;
  }

}

