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
import org.cgiar.ccafs.marlo.data.model.NextuserType;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchLeader;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchOutputsNextUser;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.service.IAuditLogService;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.INextuserTypeService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputsNextUserService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.validation.impactpathway.OutputsValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;


/**
 * The action that is responsible for handling the outputs.
 * Modified by @author nmatovu last on Oct 3, 2016
 */
public class OutputsAction extends BaseAction {


  private static final long serialVersionUID = 4999336618747960173L;


  // Services - Managers
  private ICenterService centerService;


  private IAuditLogService auditLogService;


  private IProgramService programService;
  private IResearchOutputService outputService;
  private INextuserTypeService nextUserService;
  private IResearchOutputsNextUserService outputNextUserService;
  // Front Variables
  private ResearchCenter loggedCenter;
  private List<ResearchArea> researchAreas;

  private ResearchArea selectedResearchArea;
  private List<ResearchProgram> researchPrograms;
  private ResearchProgram selectedProgram;
  private ResearchOutcome selectedResearchOutcome;
  private ResearchOutput output;
  private ResearchTopic selectedResearchTopic;
  private List<ResearchLeader> contacPersons;
  private List<NextuserType> nextuserTypes;
  // Parameter Variables
  private long programID;
  private long areaID;
  private long outcomeID;
  private long outputID;
  private String transaction;
  private long nextUserTypeID;
  // Validator
  private OutputsValidator validator;

  /**
   * @param config
   */
  @Inject
  public OutputsAction(APConfig config, ICenterService centerService, IAuditLogService auditLogService,
    IProgramService programService, IResearchOutputService outputService, OutputsValidator validator,
    INextuserTypeService nextUserService, IResearchOutputsNextUserService outputNextUserService) {
    super(config);
    this.centerService = centerService;
    this.auditLogService = auditLogService;
    this.programService = programService;
    this.outputService = outputService;
    this.validator = validator;
    this.nextUserService = nextUserService;
    this.outputNextUserService = outputNextUserService;
  }


  public String addNextUsers() {

    return SUCCESS;
  }


  public long getAreaID() {
    return areaID;
  }


  public List<ResearchLeader> getContacPersons() {
    return contacPersons;
  }


  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
  }

  public long getNextUserID() {
    return nextUserTypeID;
  }

  public List<NextuserType> getNextuserTypes() {
    return nextuserTypes;
  }

  public long getOutcomeID() {
    return outcomeID;
  }

  public ResearchOutput getOutput() {
    return output;
  }

  public long getOutputID() {
    return outputID;
  }

  public long getProgramID() {
    return programID;
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

  public ResearchOutcome getSelectedResearchOutcome() {
    return selectedResearchOutcome;
  }

  public ResearchTopic getSelectedResearchTopic() {
    return selectedResearchTopic;
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

    try {
      outputID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.OUTPUT_ID)));
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (this.getRequest().getParameter(APConstants.TRANSACTION_ID) != null) {

      transaction = StringUtils.trim(this.getRequest().getParameter(APConstants.TRANSACTION_ID));
      ResearchOutput history = (ResearchOutput) auditLogService.getHistory(transaction);

      if (history != null) {
        output = history;
      } else {
        this.transaction = null;
        this.setTransaction("-1");
      }

    } else {
      output = outputService.getResearchOutputById(outputID);
    }
    researchAreas = new ArrayList<>(
      loggedCenter.getResearchAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));
    Collections.sort(researchAreas, (ra1, ra2) -> ra1.getId().compareTo(ra2.getId()));

    if (researchAreas != null && output != null) {

      selectedResearchOutcome = output.getResearchOutcome();
      outcomeID = selectedResearchOutcome.getId();
      programID = selectedResearchOutcome.getResearchTopic().getResearchProgram().getId();
      selectedProgram = programService.getProgramById(programID);
      selectedResearchTopic = selectedResearchOutcome.getResearchTopic();
      selectedResearchArea = selectedProgram.getResearchArea();
      areaID = selectedResearchArea.getId();

      contacPersons = new ArrayList<>(
        selectedProgram.getResearchLeaders().stream().filter(rl -> rl.isActive()).collect(Collectors.toList()));

      if (nextUserService.findAll() != null) {
        nextuserTypes = new ArrayList<>(nextUserService.findAll().stream()
          .filter(nu -> nu.isActive() && nu.getNextuserType() == null).collect(Collectors.toList()));
      }

      output.setNextUsers(new ArrayList<>(
        output.getResearchOutputsNextUsers().stream().filter(nu -> nu.isActive()).collect(Collectors.toList())));

      String params[] = {loggedCenter.getAcronym(), selectedResearchArea.getId() + "", selectedProgram.getId() + ""};
      this.setBasePermission(this.getText(Permission.RESEARCH_PROGRAM_BASE_PERMISSION, params));

      if (this.isHttpPost()) {
        if (contacPersons != null) {
          contacPersons.clear();
        }

        if (nextuserTypes != null) {
          nextuserTypes.clear();
        }
      }
    }

  }

  @Override
  public String save() {
    if (this.hasPermission("*")) {

      ResearchOutput outputDb = outputService.getResearchOutputById(outputID);

      outputDb.setTitle(output.getTitle());

      long outputSaveId = outputService.saveResearchOutput(outputDb);

      ResearchOutput outputSave = outputService.getResearchOutputById(outputSaveId);

      this.saveNextUser(outputSave);

      List<String> relationsName = new ArrayList<>();
      relationsName.add(APConstants.RESEARCH_OUTPUT_NEXTUSER_RELATION);
      output = outputService.getResearchOutputById(outputID);
      output.setActiveSince(new Date());
      output.setModifiedBy(this.getCurrentUser());
      outputService.saveResearchOutput(output, this.getActionName(), relationsName);

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

  public void saveNextUser(ResearchOutput outputSave) {

    if (outputSave.getResearchOutputsNextUsers() != null && outputSave.getResearchOutputsNextUsers().size() > 0) {

      List<ResearchOutputsNextUser> nextUsersPrev = new ArrayList<>(
        outputSave.getResearchOutputsNextUsers().stream().filter(nu -> nu.isActive()).collect(Collectors.toList()));

      for (ResearchOutputsNextUser researchOutputsNextUser : nextUsersPrev) {
        if (!output.getNextUsers().contains(researchOutputsNextUser)) {
          outputNextUserService.deleteResearchOutputsNextUser(researchOutputsNextUser.getId());
        }
      }
    }

    if (output.getNextUsers() != null) {
      for (ResearchOutputsNextUser outputNextUser : output.getNextUsers()) {
        if (outputNextUser.getId() == null) {
          ResearchOutputsNextUser nextUserNew = new ResearchOutputsNextUser();
          nextUserNew.setActive(true);
          nextUserNew.setActiveSince(new Date());
          nextUserNew.setCreatedBy(this.getCurrentUser());
          nextUserNew.setModifiedBy(this.getCurrentUser());
          nextUserNew.setModificationJustification("");

          nextUserNew.setResearchOutput(outputSave);
          NextuserType nextuserType = nextUserService.getNextuserTypeById(outputNextUser.getNextuserType().getId());
          nextUserNew.setNextuserType(nextuserType);
          outputNextUserService.saveResearchOutputsNextUser(nextUserNew);

        } else {
          boolean hasChanges = false;

          ResearchOutputsNextUser nextUserPrev =
            outputNextUserService.getResearchOutputsNextUserById(outputNextUser.getId());

          NextuserType nextuserType = nextUserService.getNextuserTypeById(outputNextUser.getNextuserType().getId());

          if (nextUserPrev.getNextuserType() != null) {
            if (!nextUserPrev.getNextuserType().equals(nextuserType)) {
              nextUserPrev.setNextuserType(nextuserType);
              hasChanges = true;
            }
          } else {
            nextUserPrev.setNextuserType(nextuserType);
            hasChanges = true;
          }

          if (hasChanges) {
            nextUserPrev.setModifiedBy(this.getCurrentUser());
            nextUserPrev.setActiveSince(new Date());
            outputNextUserService.saveResearchOutputsNextUser(nextUserPrev);
          }

        }

      }
    }

  }


  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }

  public void setContacPersons(List<ResearchLeader> contacPersons) {
    this.contacPersons = contacPersons;
  }


  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
  }


  public void setNextUserID(long nextUserID) {
    this.nextUserTypeID = nextUserID;
  }

  public void setNextuserTypes(List<NextuserType> nextuserTypes) {
    this.nextuserTypes = nextuserTypes;
  }


  public void setOutcomeID(long outcomeID) {
    this.outcomeID = outcomeID;
  }

  public void setOutput(ResearchOutput output) {
    this.output = output;
  }

  public void setOutputID(long outputID) {
    this.outputID = outputID;
  }

  public void setProgramID(long programID) {
    this.programID = programID;
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


  public void setSelectedResearchOutcome(ResearchOutcome selectedResearchOutcome) {
    this.selectedResearchOutcome = selectedResearchOutcome;
  }


  public void setSelectedResearchTopic(ResearchTopic selectedResearchTopic) {
    this.selectedResearchTopic = selectedResearchTopic;
  }


  public void setTransaction(String transaction) {
    this.transaction = transaction;
  }


  @Override
  public void validate() {
    if (save) {
      validator.validate(this, output, selectedProgram, true);
    }
  }
}
