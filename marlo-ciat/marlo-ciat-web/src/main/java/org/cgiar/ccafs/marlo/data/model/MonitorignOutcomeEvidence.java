package org.cgiar.ccafs.marlo.data.model;
// Generated Mar 27, 2017 9:38:19 AM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.util.Date;

import com.google.gson.annotations.Expose;

/**
 * MonitorignOutcomeEvidence generated by hbm2java
 */
public class MonitorignOutcomeEvidence implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = -5850591332279864292L;

  @Expose
  private Long id;

  @Expose
  private MonitoringOutcome monitoringOutcome;

  @Expose
  private User modifiedBy;

  @Expose
  private User createdBy;

  @Expose
  private String evidenceLink;

  @Expose
  private boolean active;

  @Expose
  private Date activeSince;

  @Expose
  private String modificationJustification;


  public MonitorignOutcomeEvidence() {
  }


  public MonitorignOutcomeEvidence(MonitoringOutcome monitoringOutcome, String evidenceLink, boolean active) {
    this.monitoringOutcome = monitoringOutcome;
    this.evidenceLink = evidenceLink;
    this.active = active;
  }


  public MonitorignOutcomeEvidence(MonitoringOutcome monitoringOutcome, User modifiedBy, User createdBy,
    String evidenceLink, boolean active, Date activeSince, String modificationJustification) {
    this.monitoringOutcome = monitoringOutcome;
    this.modifiedBy = modifiedBy;
    this.createdBy = createdBy;
    this.evidenceLink = evidenceLink;
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
    MonitorignOutcomeEvidence other = (MonitorignOutcomeEvidence) obj;
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


  public String getEvidenceLink() {
    return evidenceLink;
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

  public MonitoringOutcome getMonitoringOutcome() {
    return monitoringOutcome;
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

  public void setEvidenceLink(String evidenceLink) {
    this.evidenceLink = evidenceLink;
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


  public void setMonitoringOutcome(MonitoringOutcome monitoringOutcome) {
    this.monitoringOutcome = monitoringOutcome;
  }


}

