/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MARLO).
 * MARLO is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option) any later version.
 * MARLO is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with MARLO. If not, see <http://www.gnu.org/licenses/>.
 *****************************************************************/

package org.cgiar.ccafs.marlo.action.monitoring.project;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.Institution;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ProjectPartner;
import org.cgiar.ccafs.marlo.data.model.ProjectPartnerPerson;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IInstitutionService;
import org.cgiar.ccafs.marlo.data.service.IProjectPartnerPersonService;
import org.cgiar.ccafs.marlo.data.service.IProjectPartnerService;
import org.cgiar.ccafs.marlo.data.service.IProjectService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.validation.monitoring.project.ProjectPartnerValidator;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProjectPartnersAction extends BaseAction {


  private static final long serialVersionUID = -6355291584207636077L;


  private static final String INTERNAL = "Internal";


  private static final String EXTERNAL = "External";
  // Services - Managers
  private ICenterService centerService;
  private IProjectService projectService;

  private IProjectPartnerService partnerService;
  private IProjectPartnerPersonService partnerPersonService;
  private IInstitutionService institutionService;
  private IUserService userService;

  // Front Variables
  private ResearchCenter loggedCenter;
  private ResearchArea selectedResearchArea;
  private ResearchProgram selectedProgram;
  private List<ResearchArea> researchAreas;
  private List<ResearchProgram> researchPrograms;
  private List<Institution> institutions;
  private List<ProjectPartner> projectPartners;
  private HashMap<Boolean, String> partnerModes;
  private Project project;

  // Parameter Variables
  private long programID;
  private long areaID;
  private long projectID;

  // Validator
  private ProjectPartnerValidator validator;

  @Inject
  public ProjectPartnersAction(APConfig config, ICenterService centerService, IProjectService projectService,
    IProjectPartnerService partnerService, IProjectPartnerPersonService partnerPersonService,
    IInstitutionService institutionService, IUserService userService, ProjectPartnerValidator validator) {
    super(config);
    this.centerService = centerService;
    this.projectService = projectService;
    this.partnerService = partnerService;
    this.partnerPersonService = partnerPersonService;
    this.institutionService = institutionService;
    this.userService = userService;
    this.validator = validator;
  }

  public long getAreaID() {
    return areaID;
  }


  public List<Institution> getInstitutions() {
    return institutions;
  }

  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
  }

  public HashMap<Boolean, String> getPartnerModes() {
    return partnerModes;
  }


  public long getProgramID() {
    return programID;
  }

  public Project getProject() {
    return project;
  }

  public long getProjectID() {
    return projectID;
  }

  public List<ProjectPartner> getProjectPartners() {
    return projectPartners;
  }

  public List<ResearchArea> getResearchAreas() {
    return researchAreas;
  }

  public List<ResearchProgram> getResearchPrograms() {
    return researchPrograms;
  }

  public ResearchProgram getSelectedProgram() {
    return selectedProgram;
  }

  public ResearchArea getSelectedResearchArea() {
    return selectedResearchArea;
  }

  @Override
  public void prepare() throws Exception {
    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());

    researchAreas = new ArrayList<>(
      loggedCenter.getResearchAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));

    try {
      projectID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_ID)));
    } catch (Exception e) {
      projectID = -1;
    }

    project = projectService.getProjectById(projectID);

    partnerModes = new HashMap<Boolean, String>();
    partnerModes.put(true, INTERNAL);
    partnerModes.put(false, EXTERNAL);

    if (project != null) {

      selectedProgram = project.getResearchProgram();
      programID = selectedProgram.getId();
      selectedResearchArea = selectedProgram.getResearchArea();
      areaID = selectedResearchArea.getId();
      researchPrograms = new ArrayList<>(
        selectedResearchArea.getResearchPrograms().stream().filter(rp -> rp.isActive()).collect(Collectors.toList()));

      if (institutionService.findAll() != null) {
        institutions = new ArrayList<>(institutionService.findAll());
      }

      project.setPartners(new ArrayList<>(
        project.getProjectPartners().stream().filter(pp -> pp.isActive()).collect(Collectors.toList())));

      if (project.getPartners() != null || !project.getPartners().isEmpty()) {
        for (ProjectPartner partner : project.getPartners()) {
          partner.setUsers(new ArrayList<>(
            partner.getProjectPartnerPersons().stream().filter(ppp -> ppp.isActive()).collect(Collectors.toList())));
        }
      }

      String params[] =
        {loggedCenter.getAcronym(), selectedResearchArea.getId() + "", selectedProgram.getId() + "", projectID + ""};
      this.setBasePermission(this.getText(Permission.PROJECT_PARTNERS_BASE_PERMISSION, params));


      if (this.isHttpPost()) {
        if (institutions != null) {
          institutions.clear();
        }
      }

    }
  }

  @Override
  public String save() {
    if (this.hasPermission("*")) {
      Project projectDB = projectService.getProjectById(projectID);

      this.savePartners(projectDB);

      if (!this.getInvalidFields().isEmpty()) {
        this.setActionMessages(null);

        List<String> keys = new ArrayList<String>(this.getInvalidFields().keySet());
        for (String key : keys) {
          this.addActionMessage(key + ": " + this.getInvalidFields().get(key));
        }

      } else {
        this.addActionMessage("message:" + this.getText("saving.saved"));
      }


      return SUCCESS;
    } else {


      return NOT_AUTHORIZED;
    }
  }


  public void savePartners(Project projectSave) {

    if (projectSave.getProjectPartners() != null && projectSave.getProjectPartners().size() > 0) {

      List<ProjectPartner> partnersPrew =
        projectSave.getProjectPartners().stream().filter(pp -> pp.isActive()).collect(Collectors.toList());

      for (ProjectPartner projectPartner : partnersPrew) {
        if (!project.getPartners().contains(projectPartner)) {
          for (ProjectPartnerPerson partnerPerson : projectPartner.getProjectPartnerPersons().stream()
            .filter(ppp -> ppp.isActive()).collect(Collectors.toList())) {
            partnerPersonService.deleteProjectPartnerPerson(partnerPerson.getId());
          }
          partnerService.deleteProjectPartner(projectPartner.getId());
        } else {
          for (ProjectPartner projectPartnerPrew : project.getPartners()) {
            if (projectPartnerPrew.equals(projectPartner)) {
              if (projectPartner.getProjectPartnerPersons() != null
                && projectPartner.getProjectPartnerPersons().size() > 0) {

                List<ProjectPartnerPerson> personsPrew = projectPartner.getProjectPartnerPersons().stream()
                  .filter(pp -> pp.isActive()).collect(Collectors.toList());

                for (ProjectPartnerPerson projectPartnerPerson : personsPrew) {
                  if (projectPartnerPrew.getUsers() != null) {
                    if (!projectPartnerPrew.getUsers().contains(projectPartnerPerson)) {
                      partnerPersonService.deleteProjectPartnerPerson(projectPartnerPerson.getId());
                    }
                  }
                }
              }
            }
          }
        }
      }
    }


    if (project.getPartners() != null) {
      for (ProjectPartner projectPartner : project.getPartners()) {
        if (projectPartner.getId() == null) {

          ProjectPartner partnerNew = new ProjectPartner();
          partnerNew.setActive(true);
          partnerNew.setActiveSince(new Date());
          partnerNew.setCreatedBy(this.getCurrentUser());
          partnerNew.setModifiedBy(this.getCurrentUser());
          partnerNew.setModificationJustification("");

          partnerNew.setInternal(projectPartner.isInternal());
          partnerNew.setProject(projectSave);

          Institution institution = institutionService.getInstitutionById(projectPartner.getInstitution().getId());
          partnerNew.setInstitution(institution);

          long partnerNewId = partnerService.saveProjectPartner(partnerNew);

          partnerNew = partnerService.getProjectPartnerById(partnerNewId);

          if (projectPartner.getUsers() != null) {
            for (ProjectPartnerPerson partnerPerson : projectPartner.getUsers()) {

              ProjectPartnerPerson partnerPersonNew = new ProjectPartnerPerson();
              partnerPersonNew.setActive(true);
              partnerPersonNew.setActiveSince(new Date());
              partnerPersonNew.setCreatedBy(this.getCurrentUser());
              partnerPersonNew.setModifiedBy(this.getCurrentUser());
              partnerPersonNew.setModificationJustification("");

              partnerPersonNew.setProjectPartner(partnerNew);

              User user = userService.getUser(partnerPerson.getUser().getId());
              partnerPersonNew.setUser(user);

              partnerPersonService.saveProjectPartnerPerson(partnerPersonNew);

            }
          }
        } else {

          ProjectPartner partnerNew = partnerService.getProjectPartnerById(projectPartner.getId());

          if (partnerNew.isInternal() != projectPartner.isInternal()) {
            partnerNew.setInternal(projectPartner.isInternal());
            partnerService.saveProjectPartner(projectPartner);
          }

          if (projectPartner.getUsers() != null) {
            for (ProjectPartnerPerson partnerPerson : projectPartner.getUsers()) {
              if (partnerPerson.getId() == null) {

                ProjectPartnerPerson partnerPersonNew = new ProjectPartnerPerson();
                partnerPersonNew.setActive(true);
                partnerPersonNew.setActiveSince(new Date());
                partnerPersonNew.setCreatedBy(this.getCurrentUser());
                partnerPersonNew.setModifiedBy(this.getCurrentUser());
                partnerPersonNew.setModificationJustification("");


                partnerPersonNew.setProjectPartner(partnerNew);

                User user = userService.getUser(partnerPerson.getUser().getId());
                partnerPersonNew.setUser(user);

                partnerPersonService.saveProjectPartnerPerson(partnerPersonNew);
              }
            }
          }

        }
      }
    }

  }

  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }

  public void setInstitutions(List<Institution> institutions) {
    this.institutions = institutions;
  }

  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
  }

  public void setPartnerModes(HashMap<Boolean, String> partnerModes) {
    this.partnerModes = partnerModes;
  }

  public void setProgramID(long programID) {
    this.programID = programID;
  }

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(long projectID) {
    this.projectID = projectID;
  }

  public void setProjectPartners(List<ProjectPartner> projectPartners) {
    this.projectPartners = projectPartners;
  }

  public void setResearchAreas(List<ResearchArea> researchAreas) {
    this.researchAreas = researchAreas;
  }

  public void setResearchPrograms(List<ResearchProgram> researchPrograms) {
    this.researchPrograms = researchPrograms;
  }

  public void setSelectedProgram(ResearchProgram selectedProgram) {
    this.selectedProgram = selectedProgram;
  }


  public void setSelectedResearchArea(ResearchArea selectedResearchArea) {
    this.selectedResearchArea = selectedResearchArea;
  }

  @Override
  public void validate() {
    if (save) {
      validator.validate(this, project, selectedProgram, true);
    }
  }

}
