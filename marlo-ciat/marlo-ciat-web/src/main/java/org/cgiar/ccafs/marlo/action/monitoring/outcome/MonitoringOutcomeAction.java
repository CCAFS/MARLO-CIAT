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

package org.cgiar.ccafs.marlo.action.monitoring.outcome;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.MonitoringOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchImpact;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.service.IAuditLogService;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchImpactService;
import org.cgiar.ccafs.marlo.data.service.IResearchMilestoneService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutcomeService;
import org.cgiar.ccafs.marlo.data.service.IResearchTopicService;
import org.cgiar.ccafs.marlo.data.service.ITargetUnitService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class MonitoringOutcomeAction extends BaseAction {


  private static final long serialVersionUID = 8641483319512138926L;


  private ICenterService centerService;

  private IResearchOutcomeService outcomeService;


  private IAuditLogService auditLogService;

  private ITargetUnitService targetUnitService;


  private IResearchTopicService researchTopicService;
  private IProgramService programService;
  private IResearchImpactService impactService;
  private IResearchMilestoneService milestoneService;
  private ResearchCenter loggedCenter;
  private List<ResearchArea> researchAreas;
  private ResearchArea selectedResearchArea;
  private List<ResearchProgram> researchPrograms;

  private ResearchProgram selectedProgram;
  private ResearchOutcome outcome;
  private List<ResearchTopic> researchTopics;
  private ResearchTopic selectedResearchTopic;
  private List<ResearchImpact> researchImpacts;
  private List<MonitoringOutcome> monitoringOutcomes;
  private HashMap<Long, String> targetUnitList;
  // Parameter Variables
  private long programID;
  private long areaID;
  private long topicID;
  private long outcomeID;

  @Inject
  public MonitoringOutcomeAction(APConfig config, ICenterService centerService, IResearchOutcomeService outcomeService,
    ITargetUnitService targetUnitService, IResearchTopicService researchTopicService, IProgramService programService,
    IResearchImpactService impactService, IResearchMilestoneService milestoneService,
    IAuditLogService auditLogService) {
    super(config);
    this.centerService = centerService;
    this.outcomeService = outcomeService;
    this.targetUnitService = targetUnitService;
    this.researchTopicService = researchTopicService;
    this.programService = programService;
    this.impactService = impactService;
    this.milestoneService = milestoneService;
    this.auditLogService = auditLogService;
  }

  public long getAreaID() {
    return areaID;
  }

  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
  }

  public List<MonitoringOutcome> getMonitoringOutcomes() {
    return monitoringOutcomes;
  }

  public ResearchOutcome getOutcome() {
    return outcome;
  }


  public long getOutcomeID() {
    return outcomeID;
  }


  public long getProgramID() {
    return programID;
  }

  public List<ResearchArea> getResearchAreas() {
    return researchAreas;
  }

  public List<ResearchImpact> getResearchImpacts() {
    return researchImpacts;
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


  public HashMap<Long, String> getTargetUnitList() {
    return targetUnitList;
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

    try {
      outcomeID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.OUTCOME_ID)));
    } catch (Exception e) {
      e.printStackTrace();
    }

    outcome = outcomeService.getResearchOutcomeById(outcomeID);

    outcome.setMonitorings(new ArrayList<>(
      outcome.getMonitoringOutcomes().stream().filter(mo -> mo.isActive()).collect(Collectors.toList())));

    if (outcome.getMonitorings() == null || outcome.getMonitorings().isEmpty()) {

    }

  }


  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }


  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
  }


  public void setMonitoringOutcomes(List<MonitoringOutcome> monitoringOutcomes) {
    this.monitoringOutcomes = monitoringOutcomes;
  }


  public void setOutcome(ResearchOutcome outcome) {
    this.outcome = outcome;
  }

  public void setOutcomeID(long outcomeID) {
    this.outcomeID = outcomeID;
  }

  public void setProgramID(long programID) {
    this.programID = programID;
  }

  public void setResearchAreas(List<ResearchArea> researchAreas) {
    this.researchAreas = researchAreas;
  }

  public void setResearchImpacts(List<ResearchImpact> researchImpacts) {
    this.researchImpacts = researchImpacts;
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

  public void setTargetUnitList(HashMap<Long, String> targetUnitList) {
    this.targetUnitList = targetUnitList;
  }

  public void setTopicID(long topicID) {
    this.topicID = topicID;
  }

}
