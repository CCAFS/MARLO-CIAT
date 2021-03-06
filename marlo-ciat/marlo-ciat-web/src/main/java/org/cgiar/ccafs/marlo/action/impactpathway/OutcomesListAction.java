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
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchLeader;
import org.cgiar.ccafs.marlo.data.model.ResearchLeaderTypeEnum;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.model.SectionStatus;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutcomeService;
import org.cgiar.ccafs.marlo.data.service.IResearchTopicService;
import org.cgiar.ccafs.marlo.data.service.ISectionStatusService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class OutcomesListAction extends BaseAction {

  private static final long serialVersionUID = 2639447995874299013L;

  private ICenterService centerService;
  private ResearchCenter loggedCenter;
  private List<ResearchOutcome> outcomes;

  private IProgramService programService;
  private List<ResearchArea> researchAreas;
  private IResearchAreaService researchAreaService;
  private List<ResearchProgram> researchPrograms;

  private List<ResearchTopic> researchTopics;
  private IResearchTopicService researchTopicService;
  private IResearchOutcomeService outcomeService;
  private ResearchProgram selectedProgram;
  private ResearchArea selectedResearchArea;
  private ResearchTopic selectedResearchTopic;
  private IUserService userService;
  private ISectionStatusService sectionStatusService;

  private long topicID;
  private long programID;
  private long outcomeID;
  private long areaID;
  private String justification;

  @Inject
  public OutcomesListAction(APConfig config, ICenterService centerService, IProgramService programService,
    IResearchAreaService researchAreaService, IUserService userService, IResearchTopicService researchTopicService,
    IResearchOutcomeService outcomeService, ISectionStatusService sectionStatusService) {
    super(config);
    this.centerService = centerService;
    this.programService = programService;
    this.researchAreaService = researchAreaService;
    this.userService = userService;
    this.researchTopicService = researchTopicService;
    this.outcomeService = outcomeService;
    this.sectionStatusService = sectionStatusService;
  }

  @Override
  public String add() {

    ResearchOutcome outcome = new ResearchOutcome();
    outcome.setActive(true);
    outcome.setActiveSince(new Date());
    outcome.setCreatedBy(this.getCurrentUser());
    outcome.setModifiedBy(this.getCurrentUser());
    outcome.setResearchTopic(selectedResearchTopic);
    outcome.setTargetYear(-1);
    outcome.setImpactPathway(true);
    outcomeID = outcomeService.saveResearchOutcome(outcome);

    if (outcomeID > 0) {
      return SUCCESS;
    } else {
      return INPUT;
    }


  }

  public List<ResearchOutcome> allProgramOutcomes() {

    List<ResearchOutcome> ouList = new ArrayList<>();

    List<ResearchTopic> researchTopics = new ArrayList<>(
      selectedProgram.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));

    for (ResearchTopic researchTopic : researchTopics) {
      List<ResearchOutcome> researchOutcomes = new ArrayList<>(
        researchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));

      for (ResearchOutcome researchOutcome : researchOutcomes) {
        ouList.add(researchOutcome);
      }
    }

    return ouList;

  }

  @Override
  public String delete() {
    Map<String, Object> parameters = this.getParameters();
    outcomeID = Long.parseLong(StringUtils.trim(((String[]) parameters.get(APConstants.OUTCOME_ID))[0]));

    ResearchOutcome outcome = outcomeService.getResearchOutcomeById(outcomeID);

    if (outcome != null) {
      programID = outcome.getResearchTopic().getResearchProgram().getId();
      topicID = outcome.getResearchTopic().getId();
      outcome
        .setModificationJustification(this.getJustification() == null ? "Outcome deleted" : this.getJustification());
      outcome.setModifiedBy(this.getCurrentUser());

      outcomeService.saveResearchOutcome(outcome);

      SectionStatus status =
        sectionStatusService.getSectionStatusByOutcome(programID, outcome.getId(), "outcomesList", this.getYear());

      if (status != null) {
        sectionStatusService.deleteSectionStatus(status.getId());
      }


      outcomeService.deleteResearchOutcome(outcome.getId());
      this.addActionMessage("message:" + this.getText("deleting.success"));
    }

    return SUCCESS;
  }

  public long getAreaID() {
    return areaID;
  }

  public String getJustification() {
    return justification;
  }

  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
  }

  public long getOutcomeID() {
    return outcomeID;
  }

  public List<ResearchOutcome> getOutcomes() {
    return outcomes;
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


  public List<ResearchTopic> getResearchTopics() {
    return researchTopics;
  }

  public ResearchProgram getSelectedProgram() {
    return selectedProgram;
  }

  public ResearchArea getSelectedResearchArea() {
    return selectedResearchArea;
  }

  public ResearchTopic getSelectedResearchTopic() {
    return selectedResearchTopic;
  }

  public long getTopicID() {
    return topicID;
  }

  @Override
  public void prepare() throws Exception {
    areaID = -1;
    programID = -1;
    topicID = -1;

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

        if (programID != -1) {
          selectedProgram = programService.getProgramById(programID);
        }
      } else {
        if (programID != -1) {
          selectedProgram = programService.getProgramById(programID);
          areaID = selectedProgram.getResearchArea().getId();
          selectedResearchArea = researchAreaService.find(areaID);
        }
      }

      if (selectedProgram.getResearchTopics() != null) {
        researchTopics = new ArrayList<>(selectedProgram.getResearchTopics().stream()
          .filter(rt -> rt.isActive() && rt.getResearchTopic().trim().length() > 0).collect(Collectors.toList()));
        try {
          topicID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.RESEARCH_TOPIC_ID)));
          if (topicID == -1) {
            outcomes = this.allProgramOutcomes();
          } else {
            selectedResearchTopic = researchTopicService.getResearchTopicById(topicID);
          }
          selectedResearchTopic = researchTopicService.getResearchTopicById(topicID);
        } catch (Exception e) {
          outcomes = this.allProgramOutcomes();
        }
        if (selectedResearchTopic != null) {
          if (selectedResearchTopic.getResearchOutcomes() != null) {
            outcomes = selectedResearchTopic.getResearchOutcomes().stream().filter(ro -> ro.isActive())
              .collect(Collectors.toList());
          }
        }

      }

    }
  }

  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }

  @Override
  public void setJustification(String justification) {
    this.justification = justification;
  }

  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
  }

  public void setOutcomeID(long outcomeID) {
    this.outcomeID = outcomeID;
  }

  public void setOutcomes(List<ResearchOutcome> outcomes) {
    this.outcomes = outcomes;
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

  public void setResearchTopics(List<ResearchTopic> researchTopics) {
    this.researchTopics = researchTopics;
  }

  public void setSelectedProgram(ResearchProgram selectedProgram) {
    this.selectedProgram = selectedProgram;
  }

  public void setSelectedResearchArea(ResearchArea selectedResearchArea) {
    this.selectedResearchArea = selectedResearchArea;
  }

  public void setSelectedResearchTopic(ResearchTopic selectedResearchTopic) {
    this.selectedResearchTopic = selectedResearchTopic;
  }

  public void setTopicID(long topicID) {
    this.topicID = topicID;
  }

}
