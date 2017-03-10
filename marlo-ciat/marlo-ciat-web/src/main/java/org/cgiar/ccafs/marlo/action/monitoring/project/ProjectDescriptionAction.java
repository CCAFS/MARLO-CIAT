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
import org.cgiar.ccafs.marlo.data.model.FundingSourceType;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ProjectCrosscutingTheme;
import org.cgiar.ccafs.marlo.data.model.ProjectFundingSource;
import org.cgiar.ccafs.marlo.data.model.ProjectOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IFundingSourceTypeService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IProjectCrosscutingThemeService;
import org.cgiar.ccafs.marlo.data.service.IProjectFundingSourceService;
import org.cgiar.ccafs.marlo.data.service.IProjectOutputService;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.data.service.impl.ProjectService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.validation.monitoring.project.ProjectDescriptionValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProjectDescriptionAction extends BaseAction {


  private static final long serialVersionUID = 3034101967516313023L;


  private ICenterService centerService;
  private IProgramService programService;
  private ProjectService projectService;
  private IUserService userService;
  private IResearchAreaService researchAreaService;
  private IResearchOutputService outputService;
  private IFundingSourceTypeService fundingSourceService;
  private IProjectOutputService projectOutputService;
  private IProjectFundingSourceService projectFundingSourceService;
  private IProjectCrosscutingThemeService projectCrosscutingThemeService;
  private ProjectDescriptionValidator validator;

  private ResearchArea selectedResearchArea;
  private ResearchProgram selectedProgram;

  private ResearchCenter loggedCenter;
  private List<ResearchArea> researchAreas;
  private List<ResearchProgram> researchPrograms;
  private List<FundingSourceType> fundingSourceTypes;
  private List<ResearchOutput> outputs;
  private long programID;
  private long areaID;
  private long projectID;
  private Project project;

  @Inject
  public ProjectDescriptionAction(APConfig config, ICenterService centerService, IProgramService programService,
    ProjectService projectService, IUserService userService, IResearchAreaService researchAreaService,
    IFundingSourceTypeService fundingSourceService, ProjectDescriptionValidator validator,
    IResearchOutputService outputService, IProjectOutputService projectOutputService,
    IProjectFundingSourceService projectFundingSourceService,
    IProjectCrosscutingThemeService projectCrosscutingThemeService) {
    super(config);
    this.centerService = centerService;
    this.programService = programService;
    this.projectService = projectService;
    this.userService = userService;
    this.researchAreaService = researchAreaService;
    this.fundingSourceService = fundingSourceService;
    this.validator = validator;
    this.outputService = outputService;
    this.projectFundingSourceService = projectFundingSourceService;
    this.projectOutputService = projectOutputService;
    this.projectCrosscutingThemeService = projectCrosscutingThemeService;
  }

  public long getAreaID() {
    return areaID;
  }

  public List<FundingSourceType> getFundingSourceTypes() {
    return fundingSourceTypes;
  }

  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
  }

  public List<ResearchOutput> getOutputs() {
    return outputs;
  }

  public long getProgramID() {
    return programID;
  }


  public void getProgramOutputs() {

    outputs = new ArrayList<>();

    List<ResearchTopic> researchTopics = new ArrayList<>(
      selectedProgram.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));

    for (ResearchTopic researchTopic : researchTopics) {
      List<ResearchOutcome> researchOutcomes = new ArrayList<>(
        researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));
      for (ResearchOutcome researchOutcome : researchOutcomes) {
        List<ResearchOutput> researchOutputs = new ArrayList<>(
          researchOutcome.getResearchOutputs().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));
        for (ResearchOutput researchOutput : researchOutputs) {
          outputs.add(researchOutput);
        }
      }
    }
  }


  public Project getProject() {
    return project;
  }

  public long getProjectID() {
    return projectID;
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

    if (project != null) {

      selectedProgram = project.getResearchProgram();
      programID = selectedProgram.getId();
      selectedResearchArea = selectedProgram.getResearchArea();
      areaID = selectedResearchArea.getId();
      researchPrograms = new ArrayList<>(
        selectedResearchArea.getResearchPrograms().stream().filter(rp -> rp.isActive()).collect(Collectors.toList()));

      ProjectCrosscutingTheme crosscutingTheme =
        projectCrosscutingThemeService.getProjectCrosscutingThemeById(project.getId());

      project.setProjectCrosscutingTheme(crosscutingTheme);

      project.setOutputs(
        new ArrayList<>(project.getProjectOutputs().stream().filter(po -> po.isActive()).collect(Collectors.toList())));

      project.setFundingSources(new ArrayList<>(
        project.getProjectFundingSources().stream().filter(fs -> fs.isActive()).collect(Collectors.toList())));

      fundingSourceTypes = new ArrayList<>(
        fundingSourceService.findAll().stream().filter(fst -> fst.isActive()).collect(Collectors.toList()));

      this.getProgramOutputs();

    }

    String params[] = {loggedCenter.getAcronym(), selectedResearchArea.getId() + "", selectedProgram.getId() + ""};
    this.setBasePermission(this.getText(Permission.RESEARCH_PROGRAM_BASE_PERMISSION, params));

    if (this.isHttpPost()) {
      if (outputs != null) {
        outputs.clear();
      }

      if (fundingSourceTypes != null) {
        fundingSourceTypes.clear();
      }

      if (project.getProjectCrosscutingTheme() != null) {
        project.getProjectCrosscutingTheme().setPoliciesInstitutions(null);
        project.getProjectCrosscutingTheme().setGenderYouth(null);
        project.getProjectCrosscutingTheme().setClimateChange(null);
        project.getProjectCrosscutingTheme().setCapacityDevelopment(null);
        project.getProjectCrosscutingTheme().setNa(null);
        project.getProjectCrosscutingTheme().setBigData(null);
      }

      if (project.getFundingSources() != null) {
        project.getFundingSources().clear();
      }

      if (project.getOutputs() != null) {
        project.getOutputs().clear();
      }
    }


  }

  @Override
  public String save() {
    if (this.hasPermission("*")) {

      Project projectDB = projectService.getProjectById(projectID);

      projectDB.setName(project.getName());
      projectDB.setShortName(project.getShortName());

      projectDB.setStartDate(project.getStartDate());
      projectDB.setEndDate(project.getEndDate());

      if (project.getProjectLeader().getId() != null) {
        User projectLeader = userService.getUser(project.getProjectLeader().getId());
        projectDB.setProjectLeader(projectLeader);
      }

      long projectSaveID = projectService.saveProject(projectDB);

      projectDB = projectService.getProjectById(projectSaveID);

      if (project.getProjectCrosscutingTheme() != null) {
        this.saveCrossCuting(projectDB);
      }

      this.saveFundingSources(projectDB);
      this.saveOutputs(projectDB);

      Collection<String> messages = this.getActionMessages();
      if (!this.getInvalidFields().isEmpty()) {
        this.setActionMessages(null);
        List<String> keys = new ArrayList<String>(this.getInvalidFields().keySet());
        for (String key : keys) {
          this.addActionMessage(key + ": " + this.getInvalidFields().get(key));
        }
      } else {
        this.addActionMessage("message:" + this.getText("saving.saved"));
      }
      messages = this.getActionMessages();

      return SUCCESS;
    } else {
      return NOT_AUTHORIZED;
    }
  }

  public void saveCrossCuting(Project projectDB) {
    ProjectCrosscutingTheme crosscutingTheme = project.getProjectCrosscutingTheme();

    ProjectCrosscutingTheme crosscutingThemeSave =
      projectCrosscutingThemeService.getProjectCrosscutingThemeById(project.getProjectCrosscutingTheme().getId());

    crosscutingThemeSave
      .setClimateChange(crosscutingTheme.getClimateChange() != null ? crosscutingTheme.getClimateChange() : false);
    crosscutingThemeSave
      .setGenderYouth(crosscutingTheme.getGenderYouth() != null ? crosscutingTheme.getGenderYouth() : false);
    crosscutingThemeSave.setPoliciesInstitutions(
      crosscutingTheme.getPoliciesInstitutions() != null ? crosscutingTheme.getPoliciesInstitutions() : false);
    crosscutingThemeSave.setCapacityDevelopment(
      crosscutingTheme.getCapacityDevelopment() != null ? crosscutingTheme.getCapacityDevelopment() : false);
    crosscutingThemeSave.setBigData(crosscutingTheme.getBigData() != null ? crosscutingTheme.getBigData() : false);
    crosscutingThemeSave.setNa(crosscutingTheme.getNa() != null ? crosscutingTheme.getNa() : false);

    crosscutingThemeSave.setProject(projectDB);

    projectCrosscutingThemeService.saveProjectCrosscutingTheme(crosscutingThemeSave);


  }

  public void saveFundingSources(Project projectDB) {

    if (projectDB.getProjectFundingSources() != null && projectDB.getProjectFundingSources().size() > 0) {
      List<ProjectFundingSource> fundingSourcesPrew = new ArrayList<>(
        projectDB.getProjectFundingSources().stream().filter(pfs -> pfs.isActive()).collect(Collectors.toList()));

      for (ProjectFundingSource projectFundingSource : fundingSourcesPrew) {
        if (!project.getFundingSources().contains(projectFundingSource)) {
          projectFundingSourceService.deleteProjectFundingSource(projectFundingSource.getId());
        }
      }
    }

    if (project.getFundingSources() != null) {

      for (ProjectFundingSource projectFundingSource : project.getFundingSources()) {
        if (projectFundingSource.getId() == null || projectFundingSource.getId() == -1) {

          ProjectFundingSource fundingSourceSave = new ProjectFundingSource();

          FundingSourceType fundingSourceType =
            fundingSourceService.getFundingSourceTypeById(projectFundingSource.getFundingSourceType().getId());
          Project project = projectService.getProjectById(projectID);

          fundingSourceSave.setProject(project);
          fundingSourceSave.setFundingSourceType(fundingSourceType);
          fundingSourceSave.setDonor(projectFundingSource.getDonor());
          fundingSourceSave.setActive(true);
          fundingSourceSave.setActiveSince(new Date());
          fundingSourceSave.setCreatedBy(this.getCurrentUser());
          fundingSourceSave.setModifiedBy(this.getCurrentUser());
          fundingSourceSave.setModificationJustification("");

          projectFundingSourceService.saveProjectFundingSource(fundingSourceSave);

        } else {
          boolean hasChanges = false;
          ProjectFundingSource fundingSourcePrew =
            projectFundingSourceService.getProjectFundingSourceById(projectFundingSource.getId());

          if (!fundingSourcePrew.getFundingSourceType().equals(projectFundingSource.getFundingSourceType())) {
            hasChanges = true;
            FundingSourceType fundingSourceType =
              fundingSourceService.getFundingSourceTypeById(projectFundingSource.getFundingSourceType().getId());
            fundingSourcePrew.setFundingSourceType(fundingSourceType);
          }

          if (!fundingSourcePrew.getDonor().equals(projectFundingSource.getDonor())) {
            hasChanges = true;
            fundingSourcePrew.setDonor(projectFundingSource.getDonor());
          }

          if (hasChanges) {
            fundingSourcePrew.setModifiedBy(this.getCurrentUser());
            projectFundingSourceService.saveProjectFundingSource(fundingSourcePrew);
          }

        }
      }


    }

  }

  public void saveOutputs(Project projectDB) {

    if (projectDB.getProjectOutputs() != null && projectDB.getProjectOutputs().size() > 0) {
      List<ProjectOutput> outputsPrew = new ArrayList<>(
        projectDB.getProjectOutputs().stream().filter(po -> po.isActive()).collect(Collectors.toList()));

      for (ProjectOutput output : outputsPrew) {
        if (!project.getOutputs().contains(output)) {
          projectOutputService.deleteProjectOutput(output.getId());
        }
      }
    }

    if (project.getOutputs() != null) {
      for (ProjectOutput output : project.getOutputs()) {
        if (output.getId() == null || output.getId() == -1) {
          ProjectOutput outputSave = new ProjectOutput();

          ResearchOutput researchOutput = outputService.getResearchOutputById(output.getResearchOutput().getId());
          Project project = projectService.getProjectById(projectID);

          outputSave.setProject(project);
          outputSave.setResearchOutput(researchOutput);
          outputSave.setActive(true);
          outputSave.setCreatedBy(this.getCurrentUser());
          outputSave.setModifiedBy(this.getCurrentUser());
          outputSave.setActiveSince(new Date());
          outputSave.setModificationJustification("");

          projectOutputService.saveProjectOutput(outputSave);

        }
      }
    }


  }

  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }

  public void setFundingSourceTypes(List<FundingSourceType> fundingSourceTypes) {
    this.fundingSourceTypes = fundingSourceTypes;
  }

  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
  }

  public void setOutputs(List<ResearchOutput> outputs) {
    this.outputs = outputs;
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
