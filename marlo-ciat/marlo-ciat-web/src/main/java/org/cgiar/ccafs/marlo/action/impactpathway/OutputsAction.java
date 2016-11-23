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
import org.cgiar.ccafs.marlo.data.model.OutputNextSubUser;
import org.cgiar.ccafs.marlo.data.model.OutputNextUser;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchLeader;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.service.IAuditLogService;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IOutputNextSubUserService;
import org.cgiar.ccafs.marlo.data.service.IOutputNextUserService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputService;
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
  private IOutputNextUserService outputNextUserService;
  private IOutputNextSubUserService outputNextSubUserService;

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
  private List<OutputNextUser> outputNextUsers;
  private List<OutputNextSubUser> outputNextSubUsers;

  // Parameter Variables
  private long programID;
  private long areaID;
  private long outcomeID;
  private long outputID;
  private String transaction;
  private long nextUserID;


  // Validator
  private OutputsValidator validator;


  /**
   * @param config
   */
  @Inject
  public OutputsAction(APConfig config, ICenterService centerService, IAuditLogService auditLogService,
    IProgramService programService, IResearchOutputService outputService, OutputsValidator validator,
    IOutputNextUserService outputNextUserService, IOutputNextSubUserService outputNextSubUserService) {
    super(config);
    this.centerService = centerService;
    this.auditLogService = auditLogService;
    this.programService = programService;
    this.outputService = outputService;
    this.validator = validator;
    this.outputNextUserService = outputNextUserService;
    this.outputNextSubUserService = outputNextSubUserService;
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

  public long getOutcomeID() {
    return outcomeID;
  }

  public ResearchOutput getOutput() {
    return output;
  }

  public long getOutputID() {
    return outputID;
  }

  public List<OutputNextSubUser> getOutputNextSubUsers() {
    return outputNextSubUsers;
  }


  public List<OutputNextUser> getOutputNextUsers() {
    return outputNextUsers;
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
    researchAreas =
      new ArrayList<>(loggedCenter.getResearchAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));
    Collections.sort(researchAreas, (ra1, ra2) -> ra1.getId().compareTo(ra2.getId()));

    if (researchAreas != null && output != null) {

      selectedResearchOutcome = output.getResearchOutcome();
      outcomeID = selectedResearchOutcome.getId();
      programID = selectedResearchOutcome.getResearchTopic().getResearchProgram().getId();
      selectedProgram = programService.getProgramById(programID);
      selectedResearchTopic = selectedResearchOutcome.getResearchTopic();
      selectedResearchArea = selectedProgram.getResearchArea();
      areaID = selectedResearchArea.getId();

      contacPersons =
        new ArrayList<>(selectedProgram.getResearchLeaders().stream().filter(rl -> rl.isActive())
          .collect(Collectors.toList()));


      String params[] = {loggedCenter.getAcronym(), selectedResearchArea.getId() + "", selectedProgram.getId() + ""};
      this.setBasePermission(this.getText(Permission.RESEARCH_PROGRAM_BASE_PERMISSION, params));

      if (this.isHttpPost()) {
        if (contacPersons != null) {
          contacPersons.clear();
        }
      }
    }
    // TODO Update the service method
    // Get list of next user types from the database
    outputNextUsers = outputNextUserService.findAll();
    // TODO Update the method
    outputNextSubUsers = outputNextSubUserService.findAll();

  }

  @Override
  public String save() {
    if (this.hasPermission("*")) {

      ResearchOutput outputDb = outputService.getResearchOutputById(outputID);

      outputDb.setTitle(output.getTitle());

      outputService.saveResearchOutput(outputDb);


      List<String> relationsName = new ArrayList<>();
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


  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }

  public void setContacPersons(List<ResearchLeader> contacPersons) {
    this.contacPersons = contacPersons;
  }


  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
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

  public void setOutputNextSubUsers(List<OutputNextSubUser> outputNextSubUsers) {
    this.outputNextSubUsers = outputNextSubUsers;
  }

  public void setOutputNextUsers(List<OutputNextUser> outputNextUsers) {
    this.outputNextUsers = outputNextUsers;
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


  public long getNextUserID() {
    return nextUserID;
  }


  public void setNextUserID(long nextUserID) {
    this.nextUserID = nextUserID;
  }
}
