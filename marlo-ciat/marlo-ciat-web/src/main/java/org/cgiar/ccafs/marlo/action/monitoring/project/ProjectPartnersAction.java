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
import org.cgiar.ccafs.marlo.data.service.IAuditLogService;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IInstitutionService;
import org.cgiar.ccafs.marlo.data.service.IProjectPartnerPersonService;
import org.cgiar.ccafs.marlo.data.service.IProjectPartnerService;
import org.cgiar.ccafs.marlo.data.service.IProjectService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.utils.AutoSaveReader;
import org.cgiar.ccafs.marlo.validation.monitoring.project.ProjectPartnerValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
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
  private final ICenterService centerService;


  private final IProjectService projectService;
  private final IAuditLogService auditLogService;
  private final IProjectPartnerService partnerService;
  private final IProjectPartnerPersonService partnerPersonService;
  private final IInstitutionService institutionService;
  private final IUserService userService;

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
  private String transaction;

  // Validator
  private final ProjectPartnerValidator validator;

  @Inject
  public ProjectPartnersAction(APConfig config, ICenterService centerService, IProjectService projectService,
    IProjectPartnerService partnerService, IProjectPartnerPersonService partnerPersonService,
    IInstitutionService institutionService, IUserService userService, ProjectPartnerValidator validator,
    IAuditLogService auditLogService) {
    super(config);
    this.centerService = centerService;
    this.projectService = projectService;
    this.partnerService = partnerService;
    this.partnerPersonService = partnerPersonService;
    this.institutionService = institutionService;
    this.userService = userService;
    this.validator = validator;
    this.auditLogService = auditLogService;
  }

  @Override
  public String cancel() {

    final Path path = this.getAutoSaveFilePath();

    if (path.toFile().exists()) {

      final boolean fileDeleted = path.toFile().delete();
    }

    this.setDraft(false);
    Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty()) {
      final String validationMessage = messages.iterator().next();
      this.setActionMessages(null);
      this.addActionMessage("draft:" + this.getText("cancel.autoSave"));
    } else {
      this.addActionMessage("draft:" + this.getText("cancel.autoSave"));
    }
    messages = this.getActionMessages();

    return SUCCESS;
  }

  public long getAreaID() {
    return areaID;
  }

  private Path getAutoSaveFilePath() {
    final String composedClassName = project.getClass().getSimpleName();
    final String actionFile = this.getActionName().replace("/", "_");
    final String autoSaveFile = project.getId() + "_" + composedClassName + "_" + actionFile + ".json";

    return Paths.get(config.getAutoSaveFolder() + autoSaveFile);
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

  public String getTransaction() {
    return transaction;
  }

  @Override
  public void prepare() throws Exception {
    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());

    researchAreas = new ArrayList<>(
      loggedCenter.getResearchAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));

    try {
      projectID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_ID)));
    } catch (final Exception e) {
      projectID = -1;
    }

    if (this.getRequest().getParameter(APConstants.TRANSACTION_ID) != null) {

      transaction = StringUtils.trim(this.getRequest().getParameter(APConstants.TRANSACTION_ID));
      final Project history = (Project) auditLogService.getHistory(transaction);

      if (history != null) {
        project = history;
      } else {
        this.transaction = null;
        this.setTransaction("-1");
      }

    } else {
      project = projectService.getProjectById(projectID);
    }

    partnerModes = new HashMap<Boolean, String>();
    partnerModes.put(true, INTERNAL);
    partnerModes.put(false, EXTERNAL);

    if (project != null) {

      final Project ProjectDB = projectService.getProjectById(projectID);
      selectedProgram = ProjectDB.getResearchProgram();
      programID = selectedProgram.getId();
      selectedResearchArea = selectedProgram.getResearchArea();
      areaID = selectedResearchArea.getId();
      researchPrograms = new ArrayList<>(
        selectedResearchArea.getResearchPrograms().stream().filter(rp -> rp.isActive()).collect(Collectors.toList()));

      if (institutionService.findAll() != null) {
        institutions = new ArrayList<>(institutionService.findAll());
      }

      final Path path = this.getAutoSaveFilePath();

      if (path.toFile().exists() && this.getCurrentUser().isAutoSave() && this.isEditable()) {
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(path.toFile()));
        final Gson gson = new GsonBuilder().create();
        final JsonObject jReader = gson.fromJson(reader, JsonObject.class);
        final AutoSaveReader autoSaveReader = new AutoSaveReader();

        project = (Project) autoSaveReader.readFromJson(jReader);
        final Project projectDB = projectService.getProjectById(project.getId());

        if (project.getPartners() != null) {

          final List<ProjectPartner> partners = new ArrayList<>();

          for (final ProjectPartner partner : project.getPartners()) {
            final Institution institution = institutionService.getInstitutionById(partner.getInstitution().getId());
            partner.setInstitution(institution);
            if (partner.getUsers() != null) {
              for (final ProjectPartnerPerson person : partner.getUsers()) {
                final User user = userService.getUser(person.getUser().getId());
                person.setUser(user);
              }
            }

            partners.add(partner);
          }

          project.setPartners(new ArrayList<>(partners));
        }


        reader.close();
        this.setDraft(true);
      } else {

        this.setDraft(false);
        project.setPartners(new ArrayList<>(
          project.getProjectPartners().stream().filter(pp -> pp.isActive()).collect(Collectors.toList())));

        if ((project.getPartners() != null) || !project.getPartners().isEmpty()) {
          for (final ProjectPartner partner : project.getPartners()) {
            partner.setUsers(new ArrayList<>(
              partner.getProjectPartnerPersons().stream().filter(ppp -> ppp.isActive()).collect(Collectors.toList())));
          }
        }
      }

      final String params[] =
        {loggedCenter.getAcronym(), selectedResearchArea.getId() + "", selectedProgram.getId() + "", projectID + ""};
      this.setBasePermission(this.getText(Permission.PROJECT_PARTNERS_BASE_PERMISSION, params));


      if (this.isHttpPost()) {

        if (project.getPartners() != null) {
          project.getPartners().clear();
        }

        if (institutions != null) {
          institutions.clear();
        }
      }

    }
  }

  @Override
  public String save() {
    if (this.hasPermission("*")) {
      final Project projectDB = projectService.getProjectById(projectID);

      this.savePartners(projectDB);

      final List<String> relationsName = new ArrayList<>();
      relationsName.add(APConstants.PROJECT_PARTNERS_RELATION);
      project = projectService.getProjectById(projectID);
      project.setActiveSince(new Date());
      project.setModifiedBy(this.getCurrentUser());
      projectService.saveProject(project, this.getActionName(), relationsName);

      final Path path = this.getAutoSaveFilePath();

      if (path.toFile().exists()) {
        path.toFile().delete();
      }

      if (!this.getInvalidFields().isEmpty()) {
        this.setActionMessages(null);

        final List<String> keys = new ArrayList<String>(this.getInvalidFields().keySet());
        for (final String key : keys) {
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

    if ((projectSave.getProjectPartners() != null) && (projectSave.getProjectPartners().size() > 0)) {

      final List<ProjectPartner> partnersPrew =
        projectSave.getProjectPartners().stream().filter(pp -> pp.isActive()).collect(Collectors.toList());

      for (final ProjectPartner projectPartner : partnersPrew) {
        if (!project.getPartners().contains(projectPartner)) {
          for (final ProjectPartnerPerson partnerPerson : projectPartner.getProjectPartnerPersons().stream()
            .filter(ppp -> ppp.isActive()).collect(Collectors.toList())) {
            partnerPersonService.deleteProjectPartnerPerson(partnerPerson.getId());
          }
          partnerService.deleteProjectPartner(projectPartner.getId());
        } else {
          for (final ProjectPartner projectPartnerPrew : project.getPartners()) {
            if (projectPartnerPrew.equals(projectPartner)) {
              if ((projectPartner.getProjectPartnerPersons() != null)
                && (projectPartner.getProjectPartnerPersons().size() > 0)) {

                final List<ProjectPartnerPerson> personsPrew = projectPartner.getProjectPartnerPersons().stream()
                  .filter(pp -> pp.isActive()).collect(Collectors.toList());

                for (final ProjectPartnerPerson projectPartnerPerson : personsPrew) {
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
      for (final ProjectPartner projectPartner : project.getPartners()) {
        if (projectPartner.getId() == null) {

          ProjectPartner partnerNew = new ProjectPartner();
          partnerNew.setActive(true);
          partnerNew.setActiveSince(new Date());
          partnerNew.setCreatedBy(this.getCurrentUser());
          partnerNew.setModifiedBy(this.getCurrentUser());
          partnerNew.setModificationJustification("");

          partnerNew.setInternal(projectPartner.isInternal());
          partnerNew.setProject(projectSave);

          final Institution institution =
            institutionService.getInstitutionById(projectPartner.getInstitution().getId());
          partnerNew.setInstitution(institution);

          final long partnerNewId = partnerService.saveProjectPartner(partnerNew);

          partnerNew = partnerService.getProjectPartnerById(partnerNewId);

          if (projectPartner.getUsers() != null) {
            for (final ProjectPartnerPerson partnerPerson : projectPartner.getUsers()) {

              final ProjectPartnerPerson partnerPersonNew = new ProjectPartnerPerson();
              partnerPersonNew.setActive(true);
              partnerPersonNew.setActiveSince(new Date());
              partnerPersonNew.setCreatedBy(this.getCurrentUser());
              partnerPersonNew.setModifiedBy(this.getCurrentUser());
              partnerPersonNew.setModificationJustification("");

              partnerPersonNew.setProjectPartner(partnerNew);

              final User user = userService.getUser(partnerPerson.getUser().getId());
              partnerPersonNew.setUser(user);

              partnerPersonService.saveProjectPartnerPerson(partnerPersonNew);

            }
          }
        } else {

          final ProjectPartner partnerNew = partnerService.getProjectPartnerById(projectPartner.getId());

          if (partnerNew.isInternal() != projectPartner.isInternal()) {
            partnerNew.setInternal(projectPartner.isInternal());
            partnerService.saveProjectPartner(projectPartner);
          }

          if (projectPartner.getUsers() != null) {
            for (final ProjectPartnerPerson partnerPerson : projectPartner.getUsers()) {
              if (partnerPerson.getId() == null) {

                final ProjectPartnerPerson partnerPersonNew = new ProjectPartnerPerson();
                partnerPersonNew.setActive(true);
                partnerPersonNew.setActiveSince(new Date());
                partnerPersonNew.setCreatedBy(this.getCurrentUser());
                partnerPersonNew.setModifiedBy(this.getCurrentUser());
                partnerPersonNew.setModificationJustification("");


                partnerPersonNew.setProjectPartner(partnerNew);

                final User user = userService.getUser(partnerPerson.getUser().getId());
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


  public void setTransaction(String transaction) {
    this.transaction = transaction;
  }

  @Override
  public void validate() {
    if (save) {
      validator.validate(this, project, selectedProgram, true);
    }
  }

}
