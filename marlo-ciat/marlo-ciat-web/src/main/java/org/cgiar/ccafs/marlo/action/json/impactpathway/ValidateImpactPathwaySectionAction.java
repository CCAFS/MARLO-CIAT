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

package org.cgiar.ccafs.marlo.action.json.impactpathway;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.ImpactPathwaySectionsEnum;
import org.cgiar.ccafs.marlo.data.model.ResearchImpact;
import org.cgiar.ccafs.marlo.data.model.ResearchImpactObjective;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchOutputPartner;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.model.SectionStatus;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.ISectionStatusService;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.validation.impactpathway.OutcomesValidator;
import org.cgiar.ccafs.marlo.validation.impactpathway.OutputPartnersValidator;
import org.cgiar.ccafs.marlo.validation.impactpathway.OutputsValidator;
import org.cgiar.ccafs.marlo.validation.impactpathway.ProgramImpactsValidator;
import org.cgiar.ccafs.marlo.validation.impactpathway.ResearchTopicsValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ValidateImpactPathwaySectionAction extends BaseAction {


  private static final long serialVersionUID = 9053048564427813253L;
  // Logger
  private static final Logger LOG = LoggerFactory.getLogger(ValidateImpactPathwaySectionAction.class);
  // Managers
  private IProgramService programServcie;
  private ISectionStatusService sectionStatusService;
  // Parameters
  private boolean existProgram;
  private boolean validSection;
  private String sectionName;
  private long programID;
  private Map<String, Object> section;
  // Model
  private SectionStatus sectionStatus;

  // Validator
  private OutcomesValidator outcomeValidator;
  private OutputsValidator outputValidator;
  private OutputPartnersValidator outputPartnerValidator;
  private ProgramImpactsValidator impactValidator;
  private ResearchTopicsValidator topicValidator;

  @Inject
  public ValidateImpactPathwaySectionAction(APConfig config, IProgramService programServcie,
    ISectionStatusService sectionStatusService, OutcomesValidator outcomeValidator, OutputsValidator outputValidator,
    OutputPartnersValidator outputPartnerValidator, ProgramImpactsValidator impactValidator,
    ResearchTopicsValidator topicValidator) {
    super(config);
    this.programServcie = programServcie;
    this.sectionStatusService = sectionStatusService;
    this.outcomeValidator = outcomeValidator;
    this.outputValidator = outputValidator;
    this.outputPartnerValidator = outputPartnerValidator;
    this.impactValidator = impactValidator;
    this.topicValidator = topicValidator;
  }

  @Override
  public String execute() throws Exception {
    if (existProgram && validSection) {
      switch (ImpactPathwaySectionsEnum.getValue(sectionName)) {
        case PROGRAM_IMPACT:
          this.validateImpact();
          break;
        case TOPIC:
          this.validateTopic();
          break;
        case OUTCOME:
          this.validateOutcome();
          break;
        case OUTPUT:
          this.validateOutput();
          break;
      }

    }

    ResearchProgram program = programServcie.getProgramById(programID);

    switch (ImpactPathwaySectionsEnum.getValue(sectionName)) {
      case OUTCOME:
        section = new HashMap<String, Object>();
        section.put("sectionName", sectionName);
        section.put("missingFields", "");

        if (program != null) {
          List<ResearchTopic> topics = new ArrayList<>(
            program.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));
          if (topics != null && !topics.isEmpty()) {
            for (ResearchTopic researchTopic : topics) {
              List<ResearchOutcome> outcomes = new ArrayList<>(
                researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));
              if (outcomes != null && !outcomes.isEmpty()) {
                for (ResearchOutcome researchOutcome : outcomes) {
                  sectionStatus = sectionStatusService.getSectionStatusByOutcome(program.getId(),
                    researchOutcome.getId(), sectionName, this.getYear());

                  if (sectionStatus == null) {
                    sectionStatus = new SectionStatus();
                    sectionStatus.setMissingFields("No section");
                  }
                  if (sectionStatus.getMissingFields().length() > 0) {
                    section.put("missingFields", section.get("missingFields") + "-" + sectionStatus.getMissingFields());
                  }
                }
              } else {
                sectionStatus = new SectionStatus();
                sectionStatus.setMissingFields("No otucomes");
                section.put("missingFields", section.get("missingFields") + "-" + sectionStatus.getMissingFields());
              }
            }
          } else {
            sectionStatus = new SectionStatus();
            sectionStatus.setMissingFields("No research topics");
            section.put("missingFields", section.get("missingFields") + "-" + sectionStatus.getMissingFields());
          }
        }


        break;
      case OUTPUT:
        section = new HashMap<String, Object>();
        section.put("sectionName", sectionName);
        section.put("missingFields", "");
        if (program != null) {
          List<ResearchTopic> topics = new ArrayList<>(
            program.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));
          if (topics != null && !topics.isEmpty()) {
            for (ResearchTopic researchTopic : topics) {
              List<ResearchOutcome> outcomes = new ArrayList<>(
                researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));
              if (outcomes != null && !outcomes.isEmpty()) {
                for (ResearchOutcome researchOutcome : outcomes) {
                  researchOutcome.setMilestones(new ArrayList<>(researchOutcome.getResearchMilestones().stream()
                    .filter(rm -> rm.isActive()).collect(Collectors.toList())));

                  List<ResearchOutput> outputs = new ArrayList<>(researchOutcome.getResearchOutputs().stream()
                    .filter(ro -> ro.isActive()).collect(Collectors.toList()));
                  if (outputs != null && !outputs.isEmpty()) {
                    for (ResearchOutput researchOutput : outputs) {
                      sectionStatus = sectionStatusService.getSectionStatusByOutput(program.getId(),
                        researchOutput.getId(), sectionName, this.getYear());

                      if (sectionStatus == null) {
                        sectionStatus = new SectionStatus();
                        sectionStatus.setMissingFields("No section");
                      }
                      if (sectionStatus.getMissingFields().length() > 0) {
                        section.put("missingFields",
                          section.get("missingFields") + "-" + sectionStatus.getMissingFields());
                      }
                    }
                  } else {
                    sectionStatus = new SectionStatus();
                    sectionStatus.setMissingFields("No outputs");
                    section.put("missingFields", section.get("missingFields") + "-" + sectionStatus.getMissingFields());
                  }
                }
              } else {
                sectionStatus = new SectionStatus();
                sectionStatus.setMissingFields("No outcome");
                section.put("missingFields", section.get("missingFields") + "-" + sectionStatus.getMissingFields());
              }
            }
          } else {
            sectionStatus = new SectionStatus();
            sectionStatus.setMissingFields("No research topics");
            section.put("missingFields", section.get("missingFields") + "-" + sectionStatus.getMissingFields());
          }
        }
        break;
      default:
        sectionStatus = sectionStatusService.getSectionStatusByProgram(programID, sectionName, this.getYear());
        section = new HashMap<String, Object>();
        section.put("sectionName", sectionStatus.getSectionName());
        section.put("missingFields", sectionStatus.getMissingFields());
        break;

    }


    Thread.sleep(500);


    return SUCCESS;
  }


  public Map<String, Object> getSection() {
    return section;
  }

  @Override
  public void prepare() throws Exception {
    Map<String, Object> parameters = this.getParameters();
    sectionName = StringUtils.trim(((String[]) parameters.get(APConstants.SECTION_NAME))[0]);
    programID = -1;

    try {
      programID = Long.parseLong(StringUtils.trim(((String[]) parameters.get(APConstants.CENTER_PROGRAM_ID))[0]));
    } catch (Exception e) {
      LOG.error("There was an exception trying to parse the crp program id = {} ",
        StringUtils.trim(((String[]) parameters.get(APConstants.CENTER_PROGRAM_ID))[0]));
    }

    existProgram = programServcie.existProgram(programID);

    // Validate if the section exists.
    List<String> sections = new ArrayList<>();
    sections.add("programimpacts");
    sections.add("researchTopics");
    sections.add("outcomesList");
    sections.add("outputsList");

    validSection = sections.contains(sectionName);
  }


  public void setSection(Map<String, Object> section) {
    this.section = section;
  }

  public void validateImpact() {
    ResearchProgram program = programServcie.getProgramById(programID);

    if (program != null) {
      List<ResearchImpact> impacts =
        new ArrayList<>(program.getResearchImpacts().stream().filter(ri -> ri.isActive()).collect(Collectors.toList()));

      for (ResearchImpact researchImpact : impacts) {
        researchImpact.setObjectives(new ArrayList<>());
        if (researchImpact.getResearchImpactObjectives() != null) {
          for (ResearchImpactObjective impactObjective : researchImpact.getResearchImpactObjectives().stream()
            .filter(ro -> ro.isActive()).collect(Collectors.toList())) {
            researchImpact.getObjectives().add(impactObjective.getResearchObjective());
            if (researchImpact.getObjectiveValue() == null) {
              researchImpact.setObjectiveValue(impactObjective.getResearchObjective().getId().toString());
            } else {
              researchImpact.setObjectiveValue(
                researchImpact.getObjectiveValue() + "," + impactObjective.getResearchObjective().getId().toString());
            }
          }
        }

        researchImpact.setBeneficiaries(new ArrayList<>(researchImpact.getResearchImpactBeneficiaries().stream()
          .filter(rib -> rib.isActive()).collect(Collectors.toList())));
      }

      impactValidator.validate(this, impacts, program, false);
    }
  }

  public void validateOutcome() {
    ResearchProgram program = programServcie.getProgramById(programID);

    if (program != null) {
      List<ResearchTopic> topics =
        new ArrayList<>(program.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));
      if (topics != null) {
        for (ResearchTopic researchTopic : topics) {
          List<ResearchOutcome> outcomes = new ArrayList<>(
            researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));

          for (ResearchOutcome researchOutcome : outcomes) {
            researchOutcome.setMilestones(new ArrayList<>(researchOutcome.getResearchMilestones().stream()
              .filter(rm -> rm.isActive()).collect(Collectors.toList())));

            outcomeValidator.validate(this, researchOutcome, program, true);
          }
        }
      }
    }
  }

  public void validateOutput() {

    ResearchProgram program = programServcie.getProgramById(programID);

    if (program != null) {
      List<ResearchTopic> topics =
        new ArrayList<>(program.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));
      if (topics != null) {
        for (ResearchTopic researchTopic : topics) {
          List<ResearchOutcome> outcomes = new ArrayList<>(
            researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));

          for (ResearchOutcome researchOutcome : outcomes) {
            researchOutcome.setMilestones(new ArrayList<>(researchOutcome.getResearchMilestones().stream()
              .filter(rm -> rm.isActive()).collect(Collectors.toList())));

            List<ResearchOutput> outputs = new ArrayList<>(
              researchOutcome.getResearchOutputs().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));

            for (ResearchOutput researchOutput : outputs) {

              researchOutput.setNextUsers(new ArrayList<>(researchOutput.getResearchOutputsNextUsers().stream()
                .filter(nu -> nu.isActive()).collect(Collectors.toList())));

              outputValidator.validate(this, researchOutput, program, false);
            }

          }
        }
      }
    }

  }

  // TODO Possible Delete
  public void validateOutputPartner() {
    ResearchProgram program = programServcie.getProgramById(programID);

    if (program != null) {
      List<ResearchTopic> topics =
        new ArrayList<>(program.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));
      if (topics != null) {
        for (ResearchTopic researchTopic : topics) {
          List<ResearchOutcome> outcomes = new ArrayList<>(
            researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));

          for (ResearchOutcome researchOutcome : outcomes) {
            researchOutcome.setMilestones(new ArrayList<>(researchOutcome.getResearchMilestones().stream()
              .filter(rm -> rm.isActive()).collect(Collectors.toList())));

            List<ResearchOutput> outputs = new ArrayList<>(
              researchOutcome.getResearchOutputs().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));

            for (ResearchOutput researchOutput : outputs) {

              researchOutput.setPartners(new ArrayList<>(researchOutput.getResearchOutputPartners().stream()
                .filter(op -> op.isActive()).collect(Collectors.toList())));

              if (researchOutput.getPartners() != null || !researchOutput.getPartners().isEmpty()) {
                for (ResearchOutputPartner partner : researchOutput.getPartners()) {
                  partner.setUsers(new ArrayList<>(partner.getResearchOutputPartnerPersons().stream()
                    .filter(opp -> opp.isActive()).collect(Collectors.toList())));
                }
              }
              outputPartnerValidator.validate(this, researchOutput, program, false);
            }

          }
        }
      }
    }
  }

  public void validateTopic() {
    ResearchProgram program = programServcie.getProgramById(programID);

    if (program != null) {
      List<ResearchTopic> topics =
        new ArrayList<>(program.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));

      topicValidator.validate(this, topics, program, false);
    }
  }


}
