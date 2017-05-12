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
import org.cgiar.ccafs.marlo.data.model.LocElement;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ProjectCrosscutingTheme;
import org.cgiar.ccafs.marlo.data.model.ProjectFundingSource;
import org.cgiar.ccafs.marlo.data.model.ProjectLocation;
import org.cgiar.ccafs.marlo.data.model.ProjectOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchLeader;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.IAuditLogService;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IFundingSourceTypeService;
import org.cgiar.ccafs.marlo.data.service.ILocElementService;
import org.cgiar.ccafs.marlo.data.service.IProjectCrosscutingThemeService;
import org.cgiar.ccafs.marlo.data.service.IProjectFundingSourceService;
import org.cgiar.ccafs.marlo.data.service.IProjectLocationService;
import org.cgiar.ccafs.marlo.data.service.IProjectOutputService;
import org.cgiar.ccafs.marlo.data.service.IProjectService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.utils.AutoSaveReader;
import org.cgiar.ccafs.marlo.validation.monitoring.project.ProjectDescriptionValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
public class ProjectDescriptionAction extends BaseAction {


  private static final long serialVersionUID = 3034101967516313023L;


  private ICenterService centerService;

  private IProjectService projectService;


  private IUserService userService;


  private IResearchOutputService outputService;


  private IFundingSourceTypeService fundingSourceService;

  private IProjectOutputService projectOutputService;


  private IProjectLocationService projectLocationService;

  private ILocElementService locElementService;

  private IProjectFundingSourceService projectFundingSourceService;

  private IProjectCrosscutingThemeService projectCrosscutingThemeService;


  private IAuditLogService auditLogService;
  private ProjectDescriptionValidator validator;
  private ResearchArea selectedResearchArea;
  private ResearchProgram selectedProgram;
  private ResearchCenter loggedCenter;
  private List<ResearchArea> researchAreas;
  private List<ResearchProgram> researchPrograms;
  private List<FundingSourceType> fundingSourceTypes;
  private List<ResearchOutput> outputs;
  private List<LocElement> regionLists;
  private List<LocElement> countryLists;
  private boolean region;
  private long programID;
  private long areaID;
  private long projectID;
  private Project project;
  private String principalInvestigator;
  private String transaction;

  @Inject
  public ProjectDescriptionAction(APConfig config, ICenterService centerService, IProjectService projectService,
    IUserService userService, IFundingSourceTypeService fundingSourceService, ProjectDescriptionValidator validator,
    IResearchOutputService outputService, IProjectOutputService projectOutputService,
    IProjectFundingSourceService projectFundingSourceService,
    IProjectCrosscutingThemeService projectCrosscutingThemeService, IProjectLocationService projectLocationService,
    ILocElementService locElementService, IAuditLogService auditLogService) {
    super(config);
    this.centerService = centerService;
    this.projectService = projectService;
    this.userService = userService;
    this.fundingSourceService = fundingSourceService;
    this.validator = validator;
    this.outputService = outputService;
    this.projectFundingSourceService = projectFundingSourceService;
    this.projectOutputService = projectOutputService;
    this.projectCrosscutingThemeService = projectCrosscutingThemeService;
    this.projectLocationService = projectLocationService;
    this.locElementService = locElementService;
    this.auditLogService = auditLogService;
  }

  @Override
  public String cancel() {

    Path path = this.getAutoSaveFilePath();

    if (path.toFile().exists()) {

      boolean fileDeleted = path.toFile().delete();
    }

    this.setDraft(false);
    Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty()) {
      String validationMessage = messages.iterator().next();
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
    String composedClassName = project.getClass().getSimpleName();
    String actionFile = this.getActionName().replace("/", "_");
    String autoSaveFile = project.getId() + "_" + composedClassName + "_" + actionFile + ".json";

    return Paths.get(config.getAutoSaveFolder() + autoSaveFile);
  }

  public List<LocElement> getCountryLists() {
    return countryLists;
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

  private String getPI() {
    List<ResearchLeader> leaders = new ArrayList<>(
      selectedProgram.getResearchLeaders().stream().filter(rl -> rl.isActive()).collect(Collectors.toList()));
    return leaders.get(0).getUser().getComposedCompleteName();
  }

  public String getPrincipalInvestigator() {
    return principalInvestigator;
  }

  public long getProgramID() {
    return programID;
  }

  public void getProgramOutputs() {

    outputs = new ArrayList<>();

    List<ResearchTopic> researchTopics = new ArrayList<>(
      selectedProgram.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));
    principalInvestigator = this.getPI();
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

  public List<LocElement> getRegionLists() {
    return regionLists;
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

  public boolean isRegion() {
    return region;
  }


  @Override
  public void prepare() throws Exception {
    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());

    researchAreas = new ArrayList<>(
      loggedCenter.getResearchAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));
    region = false;
    // Regions List
    regionLists = new ArrayList<>(locElementService.findAll().stream()
      .filter(le -> le.isActive() && le.getLocElementType() != null && le.getLocElementType().getId() == 1)
      .collect(Collectors.toList()));
    Collections.sort(regionLists, (r1, r2) -> r1.getName().compareTo(r2.getName()));

    // Country List
    countryLists = new ArrayList<>(locElementService.findAll().stream()
      .filter(le -> le.isActive() && le.getLocElementType() != null && le.getLocElementType().getId() == 2)
      .collect(Collectors.toList()));
    Collections.sort(countryLists, (c1, c2) -> c1.getName().compareTo(c2.getName()));

    try {
      projectID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.PROJECT_ID)));
    } catch (Exception e) {
      projectID = -1;
    }

    if (this.getRequest().getParameter(APConstants.TRANSACTION_ID) != null) {

      transaction = StringUtils.trim(this.getRequest().getParameter(APConstants.TRANSACTION_ID));
      Project history = (Project) auditLogService.getHistory(transaction);

      if (history != null) {
        project = history;
      } else {
        this.transaction = null;
        this.setTransaction("-1");
      }

    } else {
      project = projectService.getProjectById(projectID);
    }


    if (project != null) {

      Project ProjectDB = projectService.getProjectById(projectID);
      selectedProgram = ProjectDB.getResearchProgram();
      programID = selectedProgram.getId();
      selectedResearchArea = selectedProgram.getResearchArea();
      areaID = selectedResearchArea.getId();
      researchPrograms = new ArrayList<>(
        selectedResearchArea.getResearchPrograms().stream().filter(rp -> rp.isActive()).collect(Collectors.toList()));

      Path path = this.getAutoSaveFilePath();

      if (path.toFile().exists() && this.getCurrentUser().isAutoSave() && this.isEditable()) {
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(path.toFile()));
        Gson gson = new GsonBuilder().create();
        JsonObject jReader = gson.fromJson(reader, JsonObject.class);
        AutoSaveReader autoSaveReader = new AutoSaveReader();

        project = (Project) autoSaveReader.readFromJson(jReader);
        Project projectDB = projectService.getProjectById(project.getId());

        if (project.getProjectLeader() != null) {
          if (project.getProjectLeader().getId() != null) {
            if (project.getProjectLeader().getId() != null || project.getProjectLeader().getId() != -1) {
              User user = userService.getUser(project.getProjectLeader().getId());
              project.setProjectLeader(user);
            }
          }
        }

        if (project.getOutputs() != null) {
          List<ProjectOutput> outputs = new ArrayList<>();
          for (ProjectOutput output : project.getOutputs()) {

            if (output.getId() != null) {
              ProjectOutput projectOutput = projectOutputService.getProjectOutputById(output.getId());
              outputs.add(projectOutput);


            } else {
              ResearchOutput researchOutput = outputService.getResearchOutputById(output.getResearchOutput().getId());
              ProjectOutput projectOutput = new ProjectOutput();
              projectOutput.setResearchOutput(researchOutput);
              projectOutput.setProject(projectDB);
              outputs.add(projectOutput);
            }


          }

          project.setOutputs(new ArrayList<>(outputs));
        }

        if (project.getProjectCountries() != null) {
          for (ProjectLocation projectLocation : project.getProjectCountries()) {
            if (projectLocation != null) {
              projectLocation.setLocElement(
                locElementService.getLocElementByISOCode(projectLocation.getLocElement().getIsoAlpha2()));
            }
          }
        }

        if (project.getProjectRegions() != null) {
          for (ProjectLocation projectLocation : project.getProjectRegions()) {
            region = true;
            if (projectLocation != null) {
              projectLocation
                .setLocElement(locElementService.getLocElementById(projectLocation.getLocElement().getId()));
            }
          }
        }

        reader.close();
        this.setDraft(true);
      } else {
        this.setDraft(false);

        ProjectCrosscutingTheme crosscutingTheme;
        if (this.isEditable()) {
          crosscutingTheme = projectCrosscutingThemeService.getProjectCrosscutingThemeById(project.getId());
        } else {
          crosscutingTheme = project.getProjectCrosscutingTheme();
        }
        project.setProjectCrosscutingTheme(crosscutingTheme);

        project.setOutputs(new ArrayList<>(
          project.getProjectOutputs().stream().filter(po -> po.isActive()).collect(Collectors.toList())));

        project.setFundingSources(new ArrayList<>(
          project.getProjectFundingSources().stream().filter(fs -> fs.isActive()).collect(Collectors.toList())));


        if (project.getProjectLocations() != null) {

          List<ProjectLocation> countries = new ArrayList<>(project.getProjectLocations().stream()
            .filter(fl -> fl.isActive() && fl.getLocElement().getLocElementType().getId() == 2)
            .collect(Collectors.toList()));

          project.setProjectCountries(new ArrayList<>(countries));

          List<ProjectLocation> regions = new ArrayList<>(project.getProjectLocations().stream()
            .filter(fl -> fl.isActive() && fl.getLocElement().getLocElementType().getId() == 1)
            .collect(Collectors.toList()));


          if (regions.size() > 0) {
            region = true;
          }

          project.setProjectRegions(regions);

        }


      }

      fundingSourceTypes = new ArrayList<>(
        fundingSourceService.findAll().stream().filter(fst -> fst.isActive()).collect(Collectors.toList()));

      this.getProgramOutputs();

    }

    String params[] =
      {loggedCenter.getAcronym(), selectedResearchArea.getId() + "", selectedProgram.getId() + "", projectID + ""};
    this.setBasePermission(this.getText(Permission.PROJECT_DESCRIPTION_BASE_PERMISSION, params));

    if (this.isHttpPost()) {
      if (outputs != null) {
        outputs.clear();
      }

      if (fundingSourceTypes != null) {
        fundingSourceTypes.clear();
      }

      if (project.getProjectCrosscutingTheme() != null) {
        project.getProjectCrosscutingTheme().setPoliciesInstitutions(null);
        project.getProjectCrosscutingTheme().setGender(null);
        project.getProjectCrosscutingTheme().setYouth(null);
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

      projectDB.setGlobal(project.isGlobal());

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
      this.saveLocations(projectDB);

      List<String> relationsName = new ArrayList<>();
      relationsName.add(APConstants.PROJECT_FUNDING_SOURCE_RELATION);
      relationsName.add(APConstants.PROJECT_OUTPUT_RELATION);
      relationsName.add(APConstants.PROJECT_LOCATION_RELATION);
      project = projectService.getProjectById(projectID);
      project.setActiveSince(new Date());
      project.setModifiedBy(this.getCurrentUser());
      projectService.saveProject(project, this.getActionName(), relationsName);

      Path path = this.getAutoSaveFilePath();

      if (path.toFile().exists()) {
        path.toFile().delete();
      }


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

  public void saveCrossCuting(Project projectDB) {
    ProjectCrosscutingTheme crosscutingTheme = project.getProjectCrosscutingTheme();

    ProjectCrosscutingTheme crosscutingThemeSave =
      projectCrosscutingThemeService.getProjectCrosscutingThemeById(projectDB.getProjectCrosscutingTheme().getId());

    crosscutingThemeSave
      .setClimateChange(crosscutingTheme.getClimateChange() != null ? crosscutingTheme.getClimateChange() : false);
    crosscutingThemeSave.setGender(crosscutingTheme.getGender() != null ? crosscutingTheme.getGender() : false);
    crosscutingThemeSave.setYouth(crosscutingTheme.getYouth() != null ? crosscutingTheme.getYouth() : false);
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
          fundingSourceSave.setTitle(projectFundingSource.getTitle());
          fundingSourceSave.setOcsCode(projectFundingSource.getOcsCode());
          fundingSourceSave.setActive(true);
          fundingSourceSave.setActiveSince(new Date());
          fundingSourceSave.setCreatedBy(this.getCurrentUser());
          fundingSourceSave.setModifiedBy(this.getCurrentUser());
          fundingSourceSave.setModificationJustification("");

          // TODO when be to implement the OCS service
          fundingSourceSave.setSync(false);

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
            fundingSourcePrew.setActiveSince(new Date());
            projectFundingSourceService.saveProjectFundingSource(fundingSourcePrew);
          }

        }
      }


    }

  }

  public void saveLocations(Project projectDB) {

    if (project.getProjectRegions() != null) {

      List<ProjectLocation> regions = new ArrayList<>(projectDB.getProjectLocations().stream()
        .filter(fl -> fl.isActive() && fl.getLocElement().getLocElementType().getId() == 1)
        .collect(Collectors.toList()));

      if (regions != null && regions.size() > 0) {
        for (ProjectLocation projectLocation : regions) {
          if (!project.getProjectRegions().contains(projectLocation)) {
            projectLocationService.deleteProjectLocation(projectLocation.getId());
          }
        }
      }


      for (ProjectLocation projectLocation : project.getProjectRegions()) {


        if (projectLocation.getId() == null || projectLocation.getId() == -1) {

          ProjectLocation projectLocationSave = new ProjectLocation();
          projectLocationSave.setActive(true);
          projectLocationSave.setActiveSince(new Date());
          projectLocationSave.setCreatedBy(this.getCurrentUser());
          projectLocationSave.setModifiedBy(this.getCurrentUser());
          projectLocationSave.setModificationJustification("");
          projectLocationSave.setProject(projectDB);

          LocElement element = locElementService.getLocElementById(projectLocation.getLocElement().getId());
          projectLocationSave.setLocElement(element);

          projectLocationService.saveProjectLocation(projectLocationSave);
        }
      }


    }

    if (project.getProjectCountries() != null) {

      List<ProjectLocation> countries = new ArrayList<>(projectDB.getProjectLocations().stream()
        .filter(fl -> fl.isActive() && fl.getLocElement().getLocElementType().getId() == 2)
        .collect(Collectors.toList()));

      if (countries != null && countries.size() > 0) {
        for (ProjectLocation projectLocation : countries) {
          if (!project.getProjectCountries().contains(projectLocation)) {
            projectLocationService.deleteProjectLocation(projectLocation.getId());
          }
        }
      }

      for (ProjectLocation projectLocation : project.getProjectCountries()) {


        if (projectLocation.getId() == null || projectLocation.getId() == -1) {

          ProjectLocation projectLocationSave = new ProjectLocation();
          projectLocationSave.setActive(true);
          projectLocationSave.setActiveSince(new Date());
          projectLocationSave.setCreatedBy(this.getCurrentUser());
          projectLocationSave.setModifiedBy(this.getCurrentUser());
          projectLocationSave.setModificationJustification("");
          projectLocationSave.setProject(projectDB);

          LocElement element = locElementService.getLocElementByISOCode(projectLocation.getLocElement().getIsoAlpha2());
          projectLocationSave.setLocElement(element);

          projectLocationService.saveProjectLocation(projectLocationSave);
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

  public void setCountryLists(List<LocElement> countryLists) {
    this.countryLists = countryLists;
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

  public void setPrincipalInvestigator(String principalInvestigator) {
    this.principalInvestigator = principalInvestigator;
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

  public void setRegion(boolean region) {
    this.region = region;
  }


  public void setRegionLists(List<LocElement> regionLists) {
    this.regionLists = regionLists;
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
