package org.cgiar.ccafs.marlo.data.model;
// Generated Feb 15, 2017 8:49:31 AM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * ProjectOutput generated by hbm2java
 */
public class ProjectOutput implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = -1224397423357742951L;

  @Expose
  private Long id;


  @Expose
  private User modifiedBy;


  @Expose
  private User createdBy;


  @Expose
  private Project project;


  @Expose
  private ResearchOutput researchOutput;

  @Expose
  private boolean active;


  @Expose
  private String modificationJustification;


  @Expose
  private Date activeSince;

  public ProjectOutput() {
  }

  public ProjectOutput(boolean active) {
    this.active = active;
  }

  public ProjectOutput(User modifiedBy, User createdBy, Project project, ResearchOutput researchOutput, boolean active,
    String modificationJustification, Date activeSince) {
    this.modifiedBy = modifiedBy;
    this.createdBy = createdBy;
    this.project = project;
    this.researchOutput = researchOutput;
    this.active = active;
    this.modificationJustification = modificationJustification;
    this.activeSince = activeSince;
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
    ProjectOutput other = (ProjectOutput) obj;
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


  public ResearchOutput getResearchOutput() {
    return researchOutput;
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

  public void setModificationJustification(String modificationJustification) {
    this.modificationJustification = modificationJustification;
  }

  public void setModifiedBy(User modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  public void setProject(Project project) {
    this.project = project;
  }


  public void setResearchOutput(ResearchOutput researchOutput) {
    this.researchOutput = researchOutput;
  }


}

