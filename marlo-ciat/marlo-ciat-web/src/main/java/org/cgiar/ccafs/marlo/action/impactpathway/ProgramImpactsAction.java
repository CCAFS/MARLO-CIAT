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

/**
 * 
 */
package org.cgiar.ccafs.marlo.action.impactpathway;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.Beneficiary;
import org.cgiar.ccafs.marlo.data.model.BeneficiaryType;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchImpact;
import org.cgiar.ccafs.marlo.data.model.ResearchImpactBeneficiary;
import org.cgiar.ccafs.marlo.data.model.ResearchImpactObjective;
import org.cgiar.ccafs.marlo.data.model.ResearchLeader;
import org.cgiar.ccafs.marlo.data.model.ResearchLeaderTypeEnum;
import org.cgiar.ccafs.marlo.data.model.ResearchObjective;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchRegion;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.IAuditLogService;
import org.cgiar.ccafs.marlo.data.service.IBeneficiaryService;
import org.cgiar.ccafs.marlo.data.service.IBeneficiaryTypeService;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;
import org.cgiar.ccafs.marlo.data.service.IResearchImpactBeneficiaryService;
import org.cgiar.ccafs.marlo.data.service.IResearchImpactObjectiveService;
import org.cgiar.ccafs.marlo.data.service.IResearchImpactService;
import org.cgiar.ccafs.marlo.data.service.IResearchLeaderService;
import org.cgiar.ccafs.marlo.data.service.IResearchObjectiveService;
import org.cgiar.ccafs.marlo.data.service.IResearchRegionService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.utils.AutoSaveReader;
import org.cgiar.ccafs.marlo.validation.impactpathway.ProgramImpactsValidator;

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
 * The action class for handling the Program Impacts
 * Modified by @author nmatovu last on Oct 3, 2016
 */
public class ProgramImpactsAction extends BaseAction {


  private static final long serialVersionUID = -2261790056574973080L;


  private ICenterService centerService;

  private IProgramService programService;


  private IResearchRegionService regionService;

  private IBeneficiaryTypeService beneficiaryTypeService;
  private IResearchImpactBeneficiaryService impactBeneficiaryService;
  private IResearchAreaService researchAreaService;
  private IUserService userService;
  private IResearchObjectiveService objectiveService;
  private IResearchImpactService impactService;
  private IResearchImpactObjectiveService impactObjectiveService;
  private IAuditLogService auditLogService;
  private IBeneficiaryService beneficiaryService;
  private ResearchCenter loggedCenter;
  private List<ResearchArea> researchAreas;

  private List<ResearchRegion> regions;
  private List<BeneficiaryType> beneficiaryTypes;
  private ResearchArea selectedResearchArea;
  private List<ResearchProgram> researchPrograms;
  private List<ResearchObjective> researchObjectives;
  private ResearchProgram selectedProgram;
  private List<ResearchImpact> impacts;
  private long programID;
  private long areaID;
  private String transaction;
  private ProgramImpactsValidator validator;

  @Inject
  public ProgramImpactsAction(APConfig config, ICenterService centerService, IProgramService programService,
    IResearchAreaService researchAreaService, IResearchLeaderService researchLeaderService, IUserService userService,
    IResearchObjectiveService objectiveService, IResearchImpactService impactService,
    IResearchImpactObjectiveService impactObjectiveService, ProgramImpactsValidator validator,
    IAuditLogService auditLogService, IResearchRegionService regionService,
    IBeneficiaryTypeService beneficiaryTypeService, IResearchImpactBeneficiaryService impactBeneficiaryService,
    IBeneficiaryService beneficiaryService) {
    super(config);
    this.centerService = centerService;
    this.programService = programService;
    this.researchAreaService = researchAreaService;
    this.userService = userService;
    this.objectiveService = objectiveService;
    this.impactService = impactService;
    this.impactObjectiveService = impactObjectiveService;
    this.validator = validator;
    this.auditLogService = auditLogService;
    this.regionService = regionService;
    this.beneficiaryTypeService = beneficiaryTypeService;
    this.impactBeneficiaryService = impactBeneficiaryService;
    this.beneficiaryService = beneficiaryService;
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

  /**
   * @return the areaID
   */
  public Long getAreaID() {
    return areaID;
  }

  private Path getAutoSaveFilePath() {
    String composedClassName = selectedProgram.getClass().getSimpleName();
    String actionFile = this.getActionName().replace("/", "_");
    String autoSaveFile = selectedProgram.getId() + "_" + composedClassName + "_" + actionFile + ".json";

    return Paths.get(config.getAutoSaveFolder() + autoSaveFile);
  }

  public List<BeneficiaryType> getBeneficiaryTypes() {
    return beneficiaryTypes;
  }

  public List<ResearchImpact> getImpacts() {
    return impacts;
  }

  /**
   * @return the loggedCenter
   */
  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
  }

  /**
   * @return the programID
   */
  public Long getProgramID() {
    return programID;
  }

  public List<ResearchRegion> getRegions() {
    return regions;
  }

  public List<ResearchArea> getResearchAreas() {
    return researchAreas;
  }

  public List<ResearchObjective> getResearchObjectives() {
    return researchObjectives;
  }

  /**
   * @return the researchPrograms
   */
  public List<ResearchProgram> getResearchPrograms() {
    return researchPrograms;
  }


  /**
   * @return the selectedProgram
   */
  public ResearchProgram getSelectedProgram() {
    return selectedProgram;
  }

  /**
   * @return the selectedResearchArea
   */
  public ResearchArea getSelectedResearchArea() {
    return selectedResearchArea;
  }


  public String getTransaction() {
    return transaction;
  }


  @Override
  public void prepare() throws Exception {
    areaID = -1;
    programID = -1;

    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());

    researchAreas = new ArrayList<>(
      loggedCenter.getResearchAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));

    Collections.sort(researchAreas, (ra1, ra2) -> ra1.getId().compareTo(ra2.getId()));

    if (researchAreas != null) {

      try {
        areaID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CENTER_AREA_ID)));
      } catch (Exception e) {
        try {
          programID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CENTER_PROGRAM_ID)));
        } catch (Exception ex) {
          User user = userService.getUser(this.getCurrentUser().getId());

          List<ResearchLeader> userAreaLeads = new ArrayList<>(user.getResearchLeaders().stream()
            .filter(rl -> rl.isActive()
              && rl.getType().getId() == ResearchLeaderTypeEnum.RESEARCH_AREA_LEADER_TYPE.getValue())
            .collect(Collectors.toList()));
          if (!userAreaLeads.isEmpty()) {
            areaID = userAreaLeads.get(0).getResearchArea().getId();
          } else {
            List<ResearchLeader> userProgramLeads = new ArrayList<>(user.getResearchLeaders().stream()
              .filter(rl -> rl.isActive()
                && rl.getType().getId() == ResearchLeaderTypeEnum.RESEARCH_PROGRAM_LEADER_TYPE.getValue())
              .collect(Collectors.toList()));
            if (!userProgramLeads.isEmpty()) {
              programID = userProgramLeads.get(0).getResearchProgram().getId();
            } else {
              List<ResearchProgram> rps = researchAreas.get(0).getResearchPrograms().stream().filter(r -> r.isActive())
                .collect(Collectors.toList());
              Collections.sort(rps, (rp1, rp2) -> rp1.getId().compareTo(rp2.getId()));
              ResearchProgram rp = rps.get(0);
              programID = rp.getId();
              areaID = rp.getResearchArea().getId();
            }
          }
        }
      }

      if (areaID != -1 && programID == -1) {
        selectedResearchArea = researchAreaService.find(areaID);
        researchPrograms = new ArrayList<>(
          selectedResearchArea.getResearchPrograms().stream().filter(rp -> rp.isActive()).collect(Collectors.toList()));
        Collections.sort(researchPrograms, (rp1, rp2) -> rp1.getId().compareTo(rp2.getId()));
        if (researchPrograms != null) {
          try {
            programID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CENTER_PROGRAM_ID)));
          } catch (Exception e) {
            User user = userService.getUser(this.getCurrentUser().getId());
            List<ResearchLeader> userLeads = new ArrayList<>(user.getResearchLeaders().stream()
              .filter(rl -> rl.isActive()
                && rl.getType().getId() == ResearchLeaderTypeEnum.RESEARCH_PROGRAM_LEADER_TYPE.getValue())
              .collect(Collectors.toList()));

            if (!userLeads.isEmpty()) {
              programID = userLeads.get(0).getResearchProgram().getId();
            } else {
              if (!researchPrograms.isEmpty()) {
                programID = researchPrograms.get(0).getId();
              }
            }
          }
        }

        if (this.getRequest().getParameter(APConstants.TRANSACTION_ID) != null) {

          transaction = StringUtils.trim(this.getRequest().getParameter(APConstants.TRANSACTION_ID));
          ResearchProgram history = (ResearchProgram) auditLogService.getHistory(transaction);

          if (history != null) {
            selectedProgram = history;
            areaID = selectedProgram.getResearchArea().getId();
            selectedResearchArea = researchAreaService.find(areaID);
          } else {
            this.transaction = null;
            this.setTransaction("-1");
          }

        } else {
          if (programID != -1) {
            selectedProgram = programService.getProgramById(programID);
          }
        }
      } else {

        if (this.getRequest().getParameter(APConstants.TRANSACTION_ID) != null) {

          transaction = StringUtils.trim(this.getRequest().getParameter(APConstants.TRANSACTION_ID));
          ResearchProgram history = (ResearchProgram) auditLogService.getHistory(transaction);

          if (history != null) {
            selectedProgram = history;
            areaID = selectedProgram.getResearchArea().getId();
            selectedResearchArea = researchAreaService.find(areaID);
          } else {
            this.transaction = null;
            this.setTransaction("-1");
          }

        } else {

          if (programID != -1) {
            selectedProgram = programService.getProgramById(programID);
            areaID = selectedProgram.getResearchArea().getId();
            selectedResearchArea = researchAreaService.find(areaID);
          }
        }
      }

      if (selectedProgram != null) {
        Path path = this.getAutoSaveFilePath();

        if (path.toFile().exists() && this.getCurrentUser().isAutoSave()) {
          BufferedReader reader = null;
          reader = new BufferedReader(new FileReader(path.toFile()));
          Gson gson = new GsonBuilder().create();
          JsonObject jReader = gson.fromJson(reader, JsonObject.class);
          AutoSaveReader autoSaveReader = new AutoSaveReader();

          selectedProgram = (ResearchProgram) autoSaveReader.readFromJson(jReader);

          impacts = new ArrayList<>(selectedProgram.getImpacts());

          if (impacts != null || !impacts.isEmpty()) {

            for (ResearchImpact impact : impacts) {
              if (impact.getBeneficiaries() != null) {

                List<ResearchImpactBeneficiary> impactBeneficiaries = new ArrayList<>(impact.getBeneficiaries());
                List<ResearchImpactBeneficiary> autoSaveIBeneficiaies = new ArrayList<>();
                for (ResearchImpactBeneficiary impactBeneficiary : impactBeneficiaries) {

                  ResearchRegion region =
                    regionService.getResearchRegionById(impactBeneficiary.getResearchRegion().getId());

                  Beneficiary beneficiary =
                    beneficiaryService.getBeneficiaryById(impactBeneficiary.getBeneficiary().getId());

                  ResearchImpactBeneficiary autoSaveIBeneficiay = new ResearchImpactBeneficiary();

                  autoSaveIBeneficiay.setResearchRegion(region);
                  autoSaveIBeneficiay.setBeneficiary(beneficiary);

                  if (impactBeneficiary.getId() != null) {
                    autoSaveIBeneficiay.setId(impactBeneficiary.getId());
                  }

                  autoSaveIBeneficiaies.add(autoSaveIBeneficiay);
                }

                impact.setBeneficiaries(new ArrayList<>(autoSaveIBeneficiaies));
              }

              if (impact.getObjectiveValue() != null) {
                String[] objectiveValues = impact.getObjectiveValue().split(",");
                impact.setObjectives(new ArrayList<>());

                for (int i = 0; i < objectiveValues.length; i++) {
                  ResearchObjective objective =
                    objectiveService.getResearchObjectiveById(Long.parseLong(objectiveValues[i]));
                  impact.getObjectives().add(objective);
                }
              }
            }
          }
          reader.close();
          this.setDraft(true);
        } else {
          this.setDraft(false);
          impacts =
            selectedProgram.getResearchImpacts().stream().filter(ri -> ri.isActive()).collect(Collectors.toList());

          if (impacts != null) {
            for (ResearchImpact researchImpact : impacts) {
              researchImpact.setObjectives(new ArrayList<>());
              if (researchImpact.getResearchImpactObjectives() != null) {
                for (ResearchImpactObjective impactObjective : researchImpact.getResearchImpactObjectives().stream()
                  .filter(ro -> ro.isActive()).collect(Collectors.toList())) {
                  researchImpact.getObjectives().add(impactObjective.getResearchObjective());
                }
              }
              researchImpact.setBeneficiaries(new ArrayList<>(researchImpact.getResearchImpactBeneficiaries().stream()
                .filter(rib -> rib.isActive()).collect(Collectors.toList())));
            }
          }
        }

        if (regionService.findAll() != null) {
          regions = regionService.findAll().stream().filter(r -> r.isActive()).collect(Collectors.toList());
        }

        if (beneficiaryTypeService.findAll() != null) {
          beneficiaryTypes =
            beneficiaryTypeService.findAll().stream().filter(bt -> bt.isActive()).collect(Collectors.toList());
        }

        if (objectiveService.findAll() != null) {
          researchObjectives = new ArrayList<>(
            objectiveService.findAll().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));
        }

      }
    }

    String params[] = {loggedCenter.getAcronym(), selectedResearchArea.getId() + "", selectedProgram.getId() + ""};
    this.setBasePermission(this.getText(Permission.RESEARCH_PROGRAM_BASE_PERMISSION, params));

    if (this.isHttpPost()) {
      if (researchAreas != null) {
        researchAreas.clear();
      }
      if (researchPrograms != null) {
        researchPrograms.clear();
      }
      if (researchObjectives != null) {
        researchObjectives.clear();
      }
      if (impacts != null) {
        impacts.clear();
      }
    }

  }


  @Override
  public String save() {
    if (this.hasPermission("*")) {

      ResearchProgram programDb = programService.getProgramById(selectedProgram.getId());

      for (ResearchImpact researchImpact : programDb.getResearchImpacts().stream().filter(ri -> ri.isActive())
        .collect(Collectors.toList())) {
        if (!impacts.contains(researchImpact)) {

          if (impactObjectiveService.findAll() != null) {
            for (ResearchImpactObjective impactObjective : impactObjectiveService.findAll().stream()
              .filter(io -> io.isActive() && io.getResearchImpact().getId() == researchImpact.getId())
              .collect(Collectors.toList())) {
              impactObjectiveService.deleteResearchImpactObjective(impactObjective.getId());
            }
          }

          impactService.deleteResearchImpact(researchImpact.getId());
        }
      }

      for (ResearchImpact researchImpact : impacts) {
        if (researchImpact.getId() == null || researchImpact.getId() == -1) {
          ResearchImpact researchImpactNew = new ResearchImpact();
          researchImpactNew.setActive(true);
          researchImpactNew.setActiveSince(new Date());
          researchImpactNew.setCreatedBy(this.getCurrentUser());
          researchImpactNew.setDescription(researchImpact.getDescription().trim());
          researchImpactNew.setResearchProgram(programDb);
          researchImpactNew.setColor(null);
          researchImpactNew.setShortName(researchImpact.getShortName().trim());
          researchImpactNew.setModifiedBy(this.getCurrentUser());

          long impactId = impactService.saveResearchImpact(researchImpactNew);

          researchImpactNew = impactService.getResearchImpactById(impactId);

          if (researchImpact.getObjectiveValue() != null && researchImpact.getObjectiveValue().length() > 0) {
            for (String objectiveId : researchImpact.getObjectiveValue().trim().split(",")) {
              ResearchObjective researchObjective =
                objectiveService.getResearchObjectiveById(Long.parseLong(objectiveId.trim()));
              ResearchImpactObjective impactObjectiveNew = new ResearchImpactObjective();
              impactObjectiveNew.setActive(true);
              impactObjectiveNew.setActiveSince(new Date());
              impactObjectiveNew.setCreatedBy(this.getCurrentUser());
              impactObjectiveNew.setResearchObjective(researchObjective);
              impactObjectiveNew.setResearchImpact(researchImpactNew);
              impactObjectiveNew.setModifiedBy(this.getCurrentUser());

              impactObjectiveService.saveResearchImpactObjective(impactObjectiveNew);
            }
          }

          this.saveBeneficiary(researchImpact, researchImpactNew);

        } else {
          boolean hasChanges = false;
          ResearchImpact researchImpactRew = impactService.getResearchImpactById(researchImpact.getId());

          if (!researchImpactRew.getDescription().equals(researchImpact.getDescription().trim())) {
            hasChanges = true;
            researchImpactRew.setDescription(researchImpact.getDescription().trim());
          }

          if (researchImpactRew.getShortName() == null
            || !researchImpactRew.getShortName().equals(researchImpact.getShortName().trim())) {
            hasChanges = true;
            researchImpactRew.setShortName(researchImpact.getShortName().trim());
          }

          if (hasChanges) {
            researchImpactRew.setModifiedBy(this.getCurrentUser());
            long impactId = impactService.saveResearchImpact(researchImpactRew);
            researchImpactRew = impactService.getResearchImpactById(impactId);
          }

          if (researchImpact.getObjectiveValue() != null && researchImpact.getObjectiveValue().length() > 0) {
            for (ResearchImpactObjective impactObjective : researchImpactRew.getResearchImpactObjectives().stream()
              .filter(rio -> rio.isActive()).collect(Collectors.toList())) {
              if (!researchImpact.getObjectiveValue()
                .contains(impactObjective.getResearchObjective().getId().toString())) {
                impactObjectiveService.deleteResearchImpactObjective(impactObjective.getId());
              }
            }

            for (String objectiveId : researchImpact.getObjectiveValue().trim().split(",")) {
              ResearchObjective researchObjective =
                objectiveService.getResearchObjectiveById(Long.parseLong(objectiveId.trim()));
              ResearchImpactObjective impactObjectiveNew = new ResearchImpactObjective();
              impactObjectiveNew.setResearchObjective(researchObjective);
              impactObjectiveNew.setResearchImpact(researchImpactRew);

              List<ResearchImpactObjective> impactObjectives = researchImpactRew.getResearchImpactObjectives().stream()
                .filter(rio -> rio.isActive()).collect(Collectors.toList());

              if (!impactObjectives.contains(impactObjectiveNew)) {
                impactObjectiveNew.setActive(true);
                impactObjectiveNew.setActiveSince(new Date());
                impactObjectiveNew.setCreatedBy(this.getCurrentUser());
                impactObjectiveNew.setModifiedBy(this.getCurrentUser());
                impactObjectiveService.saveResearchImpactObjective(impactObjectiveNew);
              }

            }
          } else {
            for (ResearchImpactObjective impactObjective : researchImpactRew.getResearchImpactObjectives().stream()
              .filter(rio -> rio.isActive()).collect(Collectors.toList())) {
              if (!researchImpact.getObjectiveValue()
                .contains(impactObjective.getResearchObjective().getId().toString())) {
                impactObjectiveService.deleteResearchImpactObjective(impactObjective.getId());
              }
            }
          }

          this.saveBeneficiary(researchImpact, researchImpactRew);

        }


      }

      List<String> relationsName = new ArrayList<>();
      relationsName.add(APConstants.RESEARCH_PROGRAM_IMPACT_RELATION);
      selectedProgram.setActiveSince(new Date());
      selectedProgram.setModifiedBy(this.getCurrentUser());
      programService.saveProgram(selectedProgram, this.getActionName(), relationsName);
      Collection<String> messages = this.getActionMessages();

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

      messages = this.getActionMessages();
      return SUCCESS;
    } else {
      this.setActionMessages(null);
      return NOT_AUTHORIZED;
    }

  }


  public void saveBeneficiary(ResearchImpact researchImpact, ResearchImpact researchImpactSave) {

    if (researchImpactSave.getResearchImpactBeneficiaries() != null
      && researchImpactSave.getResearchImpactBeneficiaries().size() > 0) {

      List<ResearchImpactBeneficiary> beneficiariesPrew = researchImpactSave.getResearchImpactBeneficiaries().stream()
        .filter(rb -> rb.isActive()).collect(Collectors.toList());

      if (researchImpact.getBeneficiaries() != null) {
        for (ResearchImpactBeneficiary impactBeneficiary : beneficiariesPrew) {
          if (!researchImpact.getBeneficiaries().contains(impactBeneficiary)) {
            impactBeneficiaryService.deleteResearchImpactBeneficiary(impactBeneficiary.getId());
          }
        }
      } else {
        for (ResearchImpactBeneficiary impactBeneficiary : beneficiariesPrew) {
          impactBeneficiaryService.deleteResearchImpactBeneficiary(impactBeneficiary.getId());
        }
      }
    }

    if (researchImpact.getBeneficiaries() != null) {
      for (ResearchImpactBeneficiary impactBeneficiary : researchImpact.getBeneficiaries()) {
        if (impactBeneficiary.getId() == null) {
          ResearchImpactBeneficiary impactBeneficiaryNew = new ResearchImpactBeneficiary();
          impactBeneficiaryNew.setActive(true);
          impactBeneficiaryNew.setActiveSince(new Date());
          impactBeneficiaryNew.setCreatedBy(this.getCurrentUser());
          impactBeneficiaryNew.setModifiedBy(this.getCurrentUser());
          impactBeneficiaryNew.setModificationJustification("");

          impactBeneficiaryNew.setResearchImpact(researchImpactSave);

          ResearchRegion region = regionService.getResearchRegionById(impactBeneficiary.getResearchRegion().getId());

          impactBeneficiaryNew.setResearchRegion(region);

          Beneficiary beneficiary = beneficiaryService.getBeneficiaryById(impactBeneficiary.getBeneficiary().getId());

          impactBeneficiaryNew.setBeneficiary(beneficiary);

          impactBeneficiaryService.saveResearchImpactBeneficiary(impactBeneficiaryNew);

        } else {

          boolean hasChanges = false;

          ResearchImpactBeneficiary impactBeneficiaryPrew =
            impactBeneficiaryService.getResearchImpactBeneficiaryById(impactBeneficiary.getId());

          ResearchRegion region = regionService.getResearchRegionById(impactBeneficiary.getResearchRegion().getId());
          Beneficiary beneficiary = beneficiaryService.getBeneficiaryById(impactBeneficiary.getBeneficiary().getId());

          if (impactBeneficiaryPrew.getResearchRegion() != null) {
            if (!impactBeneficiaryPrew.getResearchRegion().equals(region)) {
              impactBeneficiaryPrew.setResearchRegion(region);
              hasChanges = true;
            }
          } else {
            impactBeneficiaryPrew.setResearchRegion(region);
            hasChanges = true;
          }

          if (impactBeneficiaryPrew.getBeneficiary() != null) {

            if (!impactBeneficiaryPrew.getBeneficiary().equals(beneficiary)) {
              impactBeneficiaryPrew.setBeneficiary(beneficiary);
              hasChanges = true;
            }
          } else {
            impactBeneficiaryPrew.setBeneficiary(beneficiary);
            hasChanges = true;
          }

          if (hasChanges) {
            impactBeneficiaryPrew.setModifiedBy(this.getCurrentUser());
            impactBeneficiaryPrew.setActiveSince(new Date());
            impactBeneficiaryService.saveResearchImpactBeneficiary(impactBeneficiaryPrew);
          }


        }
      }
    }

  }


  /**
   * @param areaID the areaID to set
   */
  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }


  /**
   * @param areaID the areaID to set
   */
  public void setAreaID(Long areaID) {
    this.areaID = areaID;
  }


  public void setBeneficiaryTypes(List<BeneficiaryType> beneficiaryTypes) {
    this.beneficiaryTypes = beneficiaryTypes;
  }

  public void setImpacts(List<ResearchImpact> impacts) {
    this.impacts = impacts;
  }


  /**
   * @param loggedCenter the loggedCenter to set
   */
  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
  }

  /**
   * @param programID the programID to set
   */
  public void setProgramID(long programID) {
    this.programID = programID;
  }


  /**
   * @param programID the programID to set
   */
  public void setProgramID(Long programID) {
    this.programID = programID;
  }

  public void setRegions(List<ResearchRegion> regions) {
    this.regions = regions;
  }

  public void setResearchAreas(List<ResearchArea> researchAreas) {
    this.researchAreas = researchAreas;
  }


  public void setResearchObjectives(List<ResearchObjective> researchObjectives) {
    this.researchObjectives = researchObjectives;
  }


  /**
   * @param researchPrograms the researchPrograms to set
   */
  public void setResearchPrograms(List<ResearchProgram> researchPrograms) {
    this.researchPrograms = researchPrograms;
  }

  /**
   * @param selectedProgram the selectedProgram to set
   */
  public void setSelectedProgram(ResearchProgram selectedProgram) {
    this.selectedProgram = selectedProgram;
  }

  /**
   * @param selectedResearchArea the selectedResearchArea to set
   */
  public void setSelectedResearchArea(ResearchArea selectedResearchArea) {
    this.selectedResearchArea = selectedResearchArea;
  }

  public void setTransaction(String transaction) {
    this.transaction = transaction;
  }

  @Override
  public void validate() {
    if (save) {
      validator.validate(this, impacts, selectedProgram, true);
    }
  }


}
