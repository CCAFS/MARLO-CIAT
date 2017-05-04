package org.cgiar.ccafs.marlo.data.model;
// Generated Feb 15, 2017 8:49:31 AM by Hibernate Tools 3.4.0.CR1


import org.cgiar.ccafs.marlo.data.IAuditLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.annotations.Expose;

/**
 * Project generated by hbm2java
 */
public class Project implements java.io.Serializable, IAuditLog {


  private static final long serialVersionUID = -4480927754617355650L;


  @Expose
  private Long id;


  @Expose
  private User modifiedBy;


  @Expose
  private User contactPerson;


  @Expose
  private User projectLeader;


  @Expose
  private User createdBy;


  @Expose
  private ProjectStatus projectStatus;


  @Expose
  private String name;


  @Expose
  private String shortName;

  @Expose
  private Date startDate;


  @Expose
  private Date endDate;


  @Expose
  private boolean active;


  @Expose
  private Date activeSince;


  @Expose
  private Date dateCreated;


  @Expose
  private String modificationJustification;


  @Expose
  private ResearchProgram researchProgram;

  @Expose
  private ProjectCrosscutingTheme projectCrosscutingTheme;

  private Set<SectionStatus> sectionStatuses = new HashSet<SectionStatus>(0);

  private Set<ProjectOutput> projectOutputs = new HashSet<ProjectOutput>(0);

  private Set<ProjectFundingSource> projectFundingSources = new HashSet<ProjectFundingSource>(0);

  private Set<Deliverable> deliverables = new HashSet<Deliverable>(0);

  private Set<Submission> submissions = new HashSet<Submission>(0);

  private List<ProjectOutput> outputs;

  private List<ProjectFundingSource> fundingSources;

  private Set<ProjectPartner> projectPartners = new HashSet<ProjectPartner>(0);

  private List<ProjectPartner> partners;

  public Project() {
  }


  public Project(boolean active) {
    this.active = active;
  }


  public Project(User modifiedBy, User contactPerson, User projectLeader, User createdBy, ProjectStatus projectStatus,
    String name, String shortName, Date startDate, Date endDate, boolean active, Date activeSince,
    String modificationJustification, Set<ProjectOutput> projectOutputs) {
    this.modifiedBy = modifiedBy;
    this.contactPerson = contactPerson;
    this.projectLeader = projectLeader;
    this.createdBy = createdBy;
    this.projectStatus = projectStatus;
    this.name = name;
    this.shortName = shortName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.active = active;
    this.activeSince = activeSince;
    this.modificationJustification = modificationJustification;
    this.projectOutputs = projectOutputs;
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
    Project other = (Project) obj;
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

  public User getContactPerson() {
    return contactPerson;
  }

  public User getCreatedBy() {
    return createdBy;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public Set<Deliverable> getDeliverables() {
    return deliverables;
  }

  public Date getEndDate() {
    return endDate;
  }

  public String getEndDateFormat() {
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = "";
    if (this.startDate != null) {
      date = simpleDateFormat.format(this.startDate);
    }
    return date;
  }

  public List<ProjectFundingSource> getFundingSources() {
    return fundingSources;
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

  public String getName() {
    return name;
  }

  public List<ProjectOutput> getOutputs() {
    return outputs;
  }

  public List<ProjectPartner> getPartners() {
    return partners;
  }

  public ProjectCrosscutingTheme getProjectCrosscutingTheme() {
    return projectCrosscutingTheme;
  }

  public Set<ProjectFundingSource> getProjectFundingSources() {
    return projectFundingSources;
  }

  public User getProjectLeader() {
    return projectLeader;
  }

  public Set<ProjectOutput> getProjectOutputs() {
    return projectOutputs;
  }

  public Set<ProjectPartner> getProjectPartners() {
    return projectPartners;
  }

  public ProjectStatus getProjectStatus() {
    return projectStatus;
  }


  public ResearchProgram getResearchProgram() {
    return researchProgram;
  }


  public Set<SectionStatus> getSectionStatuses() {
    return sectionStatuses;
  }


  public String getShortName() {
    return shortName;
  }

  public String getStarDateFormat() {
    String pattern = "yyyy-MM-dd";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String date = "";
    if (this.startDate != null) {
      date = simpleDateFormat.format(this.startDate);
    }
    return date;
  }


  public Date getStartDate() {
    return startDate;
  }


  public Set<Submission> getSubmissions() {
    return submissions;
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


  public void setContactPerson(User contactPerson) {
    this.contactPerson = contactPerson;
  }


  public void setCreatedBy(User createdBy) {
    this.createdBy = createdBy;
  }


  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }


  public void setDeliverables(Set<Deliverable> deliverables) {
    this.deliverables = deliverables;
  }


  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public void setFundingSources(List<ProjectFundingSource> fundingSources) {
    this.fundingSources = fundingSources;
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


  public void setName(String name) {
    this.name = name;
  }

  public void setOutputs(List<ProjectOutput> outputs) {
    this.outputs = outputs;
  }

  public void setPartners(List<ProjectPartner> partners) {
    this.partners = partners;
  }

  public void setProjectCrosscutingTheme(ProjectCrosscutingTheme projectCrosscutingTheme) {
    this.projectCrosscutingTheme = projectCrosscutingTheme;
  }

  public void setProjectFundingSources(Set<ProjectFundingSource> projectFundingSources) {
    this.projectFundingSources = projectFundingSources;
  }

  public void setProjectLeader(User projectLeader) {
    this.projectLeader = projectLeader;
  }

  public void setProjectOutputs(Set<ProjectOutput> projectOutputs) {
    this.projectOutputs = projectOutputs;
  }

  public void setProjectPartners(Set<ProjectPartner> projectPartners) {
    this.projectPartners = projectPartners;
  }

  public void setProjectStatus(ProjectStatus projectStatus) {
    this.projectStatus = projectStatus;
  }

  public void setResearchProgram(ResearchProgram researchProgram) {
    this.researchProgram = researchProgram;
  }


  public void setSectionStatuses(Set<SectionStatus> sectionStatuses) {
    this.sectionStatuses = sectionStatuses;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }


  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public void setSubmissions(Set<Submission> submissions) {
    this.submissions = submissions;
  }


}

