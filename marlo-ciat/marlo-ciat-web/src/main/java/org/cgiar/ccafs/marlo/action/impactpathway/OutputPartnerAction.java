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

package org.cgiar.ccafs.marlo.action.impactpathway;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.Institution;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchOutputPartner;
import org.cgiar.ccafs.marlo.data.model.ResearchOutputPartnerPerson;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.IAuditLogService;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IInstitutionService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputPartnerPersonService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputPartnerService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.validation.impactpathway.OutputPartnersValidator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class OutputPartnerAction extends BaseAction {


  private static final long serialVersionUID = 2383816234266649382L;

  private static final String INTERNAL = "Internal";
  private static final String EXTERNAL = "External";

  // Services - Managers
  private ICenterService centerService;
  private IAuditLogService auditLogService;
  private IProgramService programService;
  private IResearchOutputService outputService;
  private IInstitutionService institutionService;
  private IResearchOutputPartnerService partnerService;
  private IResearchOutputPartnerPersonService partnerPersonService;
  private IUserService userService;

  // Front Variables
  private ResearchCenter loggedCenter;
  private List<ResearchArea> researchAreas;
  private ResearchArea selectedResearchArea;
  private List<ResearchProgram> researchPrograms;
  private ResearchProgram selectedProgram;
  private ResearchOutcome selectedResearchOutcome;
  private ResearchOutput output;
  private ResearchTopic selectedResearchTopic;
  private List<Institution> institutions;

  private List<ResearchOutputPartner> outputPartners;
  private HashMap<Boolean, String> partnerModes;

  // Parameter Variables
  private long programID;
  private long areaID;
  private long outcomeID;
  private long outputID;
  private String transaction;

  // Validator
  private OutputPartnersValidator validator;

  @Inject
  public OutputPartnerAction(APConfig config, ICenterService centerService, IAuditLogService auditLogService,
    IProgramService programService, IResearchOutputService outputService, IInstitutionService institutionService,
    IResearchOutputPartnerService partnerService, IResearchOutputPartnerPersonService partnerPersonService,
    IUserService userService, OutputPartnersValidator validator) {
    super(config);
    this.centerService = centerService;
    this.auditLogService = auditLogService;
    this.programService = programService;
    this.outputService = outputService;
    this.institutionService = institutionService;
    this.partnerService = partnerService;
    this.partnerPersonService = partnerPersonService;
    this.userService = userService;
    this.validator = validator;
  }

  public long getAreaID() {
    return areaID;
  }

  // return default gender value
  public Boolean getDefaultPartnerModeValue() {
    return false;
  }

  public List<Institution> getInstitutions() {
    return institutions;
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


  public List<ResearchOutputPartner> getOutputPartners() {
    return outputPartners;
  }


  public HashMap<Boolean, String> getPartnerModes() {
    return partnerModes;
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

    partnerModes = new HashMap<Boolean, String>();
    partnerModes.put(true, INTERNAL);
    partnerModes.put(false, EXTERNAL);

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

      if (institutionService.findAll() != null) {
        institutions = new ArrayList<>(institutionService.findAll());
      }

      output.setPartners(new ArrayList<>(
        output.getResearchOutputPartners().stream().filter(op -> op.isActive()).collect(Collectors.toList())));

      if (output.getPartners() != null || !output.getPartners().isEmpty()) {
        for (ResearchOutputPartner partner : output.getPartners()) {
          partner.setUsers(new ArrayList<>(partner.getResearchOutputPartnerPersons().stream()
            .filter(opp -> opp.isActive()).collect(Collectors.toList())));
        }
      }

      String params[] = {loggedCenter.getAcronym(), selectedResearchArea.getId() + "", selectedProgram.getId() + ""};
      this.setBasePermission(this.getText(Permission.RESEARCH_PROGRAM_BASE_PERMISSION, params));

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

      ResearchOutput outputDb = outputService.getResearchOutputById(outputID);


      this.savePartners(outputDb);

      List<String> relationsName = new ArrayList<>();
      relationsName.add(APConstants.RESEARCH_OUTPUT_PARTNER_RELATION);
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

  public void savePartners(ResearchOutput outputSave) {
    if (outputSave.getResearchOutputPartners() != null && outputSave.getResearchOutputPartners().size() > 0) {

      List<ResearchOutputPartner> partnersPrew =
        outputSave.getResearchOutputPartners().stream().filter(rop -> rop.isActive()).collect(Collectors.toList());

      for (ResearchOutputPartner outputPartner : partnersPrew) {
        if (!output.getResearchOutputPartners().contains(outputPartner)) {
          for (ResearchOutputPartnerPerson partnerPerson : outputPartner.getResearchOutputPartnerPersons().stream()
            .filter(opp -> opp.isActive()).collect(Collectors.toList())) {
            partnerPersonService.deleteResearchOutputPartnerPerson(partnerPerson.getId());
          }
          partnerService.deleteResearchOutputPartner(outputPartner.getId());
        } else {
          for (ResearchOutputPartner researchOutputPartner : output.getPartners()) {
            if (researchOutputPartner.equals(outputPartner)) {
              if (outputPartner.getResearchOutputPartnerPersons() != null
                && outputPartner.getResearchOutputPartnerPersons().size() > 0) {

                List<ResearchOutputPartnerPerson> personsPrew = outputPartner.getResearchOutputPartnerPersons().stream()
                  .filter(pp -> pp.isActive()).collect(Collectors.toList());

                for (ResearchOutputPartnerPerson researchOutputPartnerPerson : personsPrew) {
                  if (researchOutputPartner.getUsers() != null) {
                    if (!researchOutputPartner.getUsers().contains(researchOutputPartnerPerson)) {
                      partnerPersonService.deleteResearchOutputPartnerPerson(researchOutputPartnerPerson.getId());
                    }
                  }
                }
              }
            }
          }
        }
      }
    }


    if (output.getPartners() != null) {
      for (ResearchOutputPartner researchOutputPartner : output.getPartners()) {
        if (researchOutputPartner.getId() == null) {

          ResearchOutputPartner partnerNew = new ResearchOutputPartner();
          partnerNew.setActive(true);
          partnerNew.setActiveSince(new Date());
          partnerNew.setCreatedBy(this.getCurrentUser());
          partnerNew.setModifiedBy(this.getCurrentUser());
          partnerNew.setModificationJustification("");

          partnerNew.setInternal(researchOutputPartner.isInternal());
          partnerNew.setResearchOutput(outputSave);

          Institution institution =
            institutionService.getInstitutionById(researchOutputPartner.getInstitution().getId());
          partnerNew.setInstitution(institution);

          long partnerNewId = partnerService.saveResearchOutputPartner(partnerNew);

          partnerNew = partnerService.getResearchOutputPartnerById(partnerNewId);

          if (researchOutputPartner.getUsers() != null) {
            for (ResearchOutputPartnerPerson partnerPerson : researchOutputPartner.getUsers()) {

              ResearchOutputPartnerPerson partnerPersonNew = new ResearchOutputPartnerPerson();
              partnerPersonNew.setActive(true);
              partnerPersonNew.setActiveSince(new Date());
              partnerPersonNew.setCreatedBy(this.getCurrentUser());
              partnerPersonNew.setModifiedBy(this.getCurrentUser());
              // TODO partnerPersonNew.setModificationJustification("");

              partnerPersonNew.setResearchOutputPartner(partnerNew);

              User user = userService.getUser(partnerPerson.getUser().getId());
              partnerPersonNew.setUser(user);

              partnerPersonService.saveResearchOutputPartnerPerson(partnerPersonNew);

            }
          }
        } else {

          ResearchOutputPartner partnerNew = partnerService.getResearchOutputPartnerById(researchOutputPartner.getId());

          if (partnerNew.isInternal() != researchOutputPartner.isInternal()) {
            partnerNew.setInternal(researchOutputPartner.isInternal());
            partnerService.saveResearchOutputPartner(partnerNew);
          }

          if (researchOutputPartner.getUsers() != null) {
            for (ResearchOutputPartnerPerson partnerPerson : researchOutputPartner.getUsers()) {
              if (partnerPerson.getId() == null) {

                ResearchOutputPartnerPerson partnerPersonNew = new ResearchOutputPartnerPerson();
                partnerPersonNew.setActive(true);
                partnerPersonNew.setActiveSince(new Date());
                partnerPersonNew.setCreatedBy(this.getCurrentUser());
                partnerPersonNew.setModifiedBy(this.getCurrentUser());
                // TODO partnerPersonNew.setModificationJustification("");


                partnerPersonNew.setResearchOutputPartner(partnerNew);

                User user = userService.getUser(partnerPerson.getUser().getId());
                partnerPersonNew.setUser(user);

                partnerPersonService.saveResearchOutputPartnerPerson(partnerPersonNew);
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

  public void setOutcomeID(long outcomeID) {
    this.outcomeID = outcomeID;
  }

  public void setOutput(ResearchOutput output) {
    this.output = output;
  }

  public void setOutputID(long outputID) {
    this.outputID = outputID;
  }

  public void setOutputPartners(List<ResearchOutputPartner> outputPartners) {
    this.outputPartners = outputPartners;
  }

  public void setPartnerModes(HashMap<Boolean, String> partnerModes) {
    this.partnerModes = partnerModes;
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
      // validator.validate(this, output, selectedProgram, true);
    }
  }

}
