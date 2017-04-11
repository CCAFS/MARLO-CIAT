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
import org.cgiar.ccafs.marlo.data.model.MonitorignOutcomeEvidence;
import org.cgiar.ccafs.marlo.data.model.MonitoringMilestone;
import org.cgiar.ccafs.marlo.data.model.MonitoringOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchImpact;
import org.cgiar.ccafs.marlo.data.model.ResearchMilestone;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.model.TargetUnit;
import org.cgiar.ccafs.marlo.data.service.IAuditLogService;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IMonitorignOutcomeEvidenceService;
import org.cgiar.ccafs.marlo.data.service.IMonitoringMilestoneService;
import org.cgiar.ccafs.marlo.data.service.IMonitoringOutcomeService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchImpactService;
import org.cgiar.ccafs.marlo.data.service.IResearchMilestoneService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutcomeService;
import org.cgiar.ccafs.marlo.data.service.IResearchTopicService;
import org.cgiar.ccafs.marlo.data.service.ITargetUnitService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
  private IMonitoringOutcomeService monitoringOutcomeService;
  private IMonitoringMilestoneService monitoringMilestoneService;
  private IMonitorignOutcomeEvidenceService evidenceService;
  private ResearchCenter loggedCenter;
  private List<ResearchArea> researchAreas;
  private ResearchArea selectedResearchArea;
  private List<ResearchProgram> researchPrograms;

  private ResearchProgram selectedProgram;
  private ResearchOutcome outcome;
  private List<ResearchTopic> researchTopics;
  private ResearchTopic selectedResearchTopic;
  private List<ResearchImpact> researchImpacts;
  private HashMap<Long, String> targetUnitList;
  // Parameter Variables
  private long programID;
  private long areaID;
  private long topicID;
  private long outcomeID;

  @Inject
  public MonitoringOutcomeAction(APConfig config, ICenterService centerService, IResearchOutcomeService outcomeService,
    ITargetUnitService targetUnitService, IResearchTopicService researchTopicService, IProgramService programService,
    IResearchImpactService impactService, IResearchMilestoneService milestoneService, IAuditLogService auditLogService,
    IMonitoringOutcomeService monitoringOutcomeService, IMonitoringMilestoneService monitoringMilestoneService,
    IMonitorignOutcomeEvidenceService evidenceService) {
    super(config);
    this.centerService = centerService;
    this.outcomeService = outcomeService;
    this.targetUnitService = targetUnitService;
    this.researchTopicService = researchTopicService;
    this.programService = programService;
    this.impactService = impactService;
    this.milestoneService = milestoneService;
    this.auditLogService = auditLogService;
    this.evidenceService = evidenceService;
    this.monitoringMilestoneService = monitoringMilestoneService;
    this.monitoringOutcomeService = monitoringOutcomeService;
  }

  public void fillFrontValues() {
    programID = outcome.getResearchTopic().getResearchProgram().getId();
    selectedProgram = programService.getProgramById(programID);
    selectedResearchTopic = outcome.getResearchTopic();
    topicID = selectedResearchTopic.getId();
    selectedResearchArea = selectedProgram.getResearchArea();
    areaID = selectedResearchArea.getId();

    targetUnitList = new HashMap<>();
    if (targetUnitService.findAll() != null) {

      List<TargetUnit> targetUnits =
        targetUnitService.findAll().stream().filter(c -> c.isActive()).collect(Collectors.toList());

      Collections.sort(targetUnits,
        (tu1, tu2) -> tu1.getName().toLowerCase().trim().compareTo(tu2.getName().toLowerCase().trim()));

      for (TargetUnit srfTargetUnit : targetUnits) {
        targetUnitList.put(srfTargetUnit.getId(), srfTargetUnit.getName());
      }

      targetUnitList = this.sortByComparator(targetUnitList);
    }
  }

  public void fillOutcomeYear() {
    outcome.setMonitorings(new ArrayList<>());


    Calendar calendarStart = Calendar.getInstance();
    calendarStart.set(Calendar.YEAR, this.getYear());
    Calendar calendarEnd = Calendar.getInstance();
    calendarEnd.set(Calendar.YEAR, outcome.getTargetYear());

    while (calendarStart.get(Calendar.YEAR) <= calendarEnd.get(Calendar.YEAR)) {
      MonitoringOutcome monitoringOutcome = new MonitoringOutcome();

      monitoringOutcome.setActive(true);
      monitoringOutcome.setYear(calendarStart.get(Calendar.YEAR));
      monitoringOutcome.setResearchOutcome(outcome);

      monitoringOutcome.setCreatedBy(this.getCurrentUser());
      monitoringOutcome.setModifiedBy(this.getCurrentUser());
      monitoringOutcome.setActiveSince(new Date());
      monitoringOutcome.setModificationJustification("");


      long monitoringOutcomeID = monitoringOutcomeService.saveMonitoringOutcome(monitoringOutcome);
      monitoringOutcome = monitoringOutcomeService.getMonitoringOutcomeById(monitoringOutcomeID);

      List<ResearchMilestone> milestones = new ArrayList<>(outcome.getResearchMilestones().stream()
        .filter(rm -> rm.isActive() && rm.getTargetYear() >= calendarStart.get(Calendar.YEAR))
        .collect(Collectors.toList()));
      Collections.sort(milestones, (mi1, mi2) -> mi1.getId().compareTo(mi2.getId()));

      for (ResearchMilestone researchMilestone : milestones) {

        MonitoringMilestone monitoringMilestone = new MonitoringMilestone();

        monitoringMilestone.setActive(true);
        monitoringMilestone.setResearchMilestone(researchMilestone);
        monitoringMilestone.setMonitoringOutcome(monitoringOutcome);

        monitoringMilestone.setCreatedBy(this.getCurrentUser());
        monitoringMilestone.setModifiedBy(this.getCurrentUser());
        monitoringMilestone.setActiveSince(new Date());
        monitoringMilestone.setModificationJustification("");

        monitoringMilestoneService.saveMonitoringMilestone(monitoringMilestone);

      }

      monitoringOutcome = monitoringOutcomeService.getMonitoringOutcomeById(monitoringOutcomeID);
      monitoringOutcome.setMilestones(new ArrayList<>(
        monitoringOutcome.getMonitoringMilestones().stream().filter(mm -> mm.isActive()).collect(Collectors.toList())));
      monitoringOutcome.setEvidences(new ArrayList<>());


      outcome.getMonitorings().add(monitoringOutcome);

      calendarStart.add(Calendar.YEAR, 1);
    }
  }

  public long getAreaID() {
    return areaID;
  }


  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
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

    researchAreas = new ArrayList<>(
      loggedCenter.getResearchAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));

    Collections.sort(researchAreas, (ra1, ra2) -> ra1.getId().compareTo(ra2.getId()));


    if (outcome != null) {


      this.fillFrontValues();

      outcome.setMonitorings(new ArrayList<>(
        outcome.getMonitoringOutcomes().stream().filter(mo -> mo.isActive()).collect(Collectors.toList())));

      if (outcome.getMonitorings() == null || outcome.getMonitorings().isEmpty()) {
        this.fillOutcomeYear();
      } else {
        for (MonitoringOutcome monitoringOutcome : outcome.getMonitorings()) {
          monitoringOutcome.setEvidences(new ArrayList<>(monitoringOutcome.getMonitorignOutcomeEvidences().stream()
            .filter(me -> me.isActive()).collect(Collectors.toList())));

          monitoringOutcome.setMilestones(new ArrayList<>(monitoringOutcome.getMonitoringMilestones().stream()
            .filter(mm -> mm.isActive()).collect(Collectors.toList())));

          Collections.sort(monitoringOutcome.getMilestones(), (mil1, mil2) -> mil1.getResearchMilestone()
            .getTargetYear().compareTo(mil2.getResearchMilestone().getTargetYear()));

        }
      }

      Collections.sort(outcome.getMonitorings(),
        (mon1, mon2) -> (new Integer(mon1.getYear())).compareTo(new Integer(mon2.getYear())));
      for (MonitoringOutcome monitoringOutcome : outcome.getMonitorings()) {
        Collections.sort(monitoringOutcome.getMilestones(), (mil1, mil2) -> mil1.getResearchMilestone().getTargetYear()
          .compareTo(mil2.getResearchMilestone().getTargetYear()));
      }


    }


    String params[] = {loggedCenter.getAcronym(), selectedResearchArea.getId() + "", selectedProgram.getId() + ""};
    this.setBasePermission(this.getText(Permission.RESEARCH_PROGRAM_BASE_PERMISSION, params));

    if (this.isHttpPost()) {
      if (targetUnitList != null) {
        targetUnitList.clear();
      }

      if (outcome.getMonitorings() != null) {
        outcome.getMonitorings().clear();
      }
    }


  }

  @Override
  public String save() {
    if (this.hasPermission("*")) {

      this.setInvalidFields(new HashMap<>());

      ResearchOutcome outcomeDB = outcomeService.getResearchOutcomeById(outcomeID);
      outcomeDB.setBaseline(outcome.getBaseline());

      outcomeService.saveResearchOutcome(outcomeDB);

      if (outcome.getMonitorings() != null || !outcome.getMonitorings().isEmpty()) {
        for (MonitoringOutcome monitoringOutcome : outcome.getMonitorings()) {

          MonitoringOutcome monitoringOutcomeDB =
            monitoringOutcomeService.getMonitoringOutcomeById(monitoringOutcome.getId());

          List<MonitoringMilestone> monitoringMilestones = monitoringOutcome.getMilestones();

          for (MonitoringMilestone monitoringMilestone : monitoringMilestones) {

            MonitoringMilestone monitoringMilestoneDB =
              monitoringMilestoneService.getMonitoringMilestoneById(monitoringMilestone.getId());

            monitoringMilestoneDB.setAchievedValue(monitoringMilestone.getAchievedValue());
            monitoringMilestoneDB.setNarrative(monitoringMilestone.getNarrative());

            monitoringMilestoneService.saveMonitoringMilestone(monitoringMilestoneDB);

          }


          monitoringOutcomeDB.setNarrative(monitoringOutcome.getNarrative());
          monitoringOutcomeService.saveMonitoringOutcome(monitoringOutcomeDB);

          this.saveEvidences(monitoringOutcomeDB, monitoringOutcome);


        }
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

  public void saveEvidences(MonitoringOutcome monitoringOutcomeDB, MonitoringOutcome monitoringOutcome) {

    if (monitoringOutcomeDB.getEvidences() != null && monitoringOutcomeDB.getEvidences().size() > 0) {

      List<MonitorignOutcomeEvidence> evidences = new ArrayList<>(monitoringOutcomeDB.getMonitorignOutcomeEvidences()
        .stream().filter(ev -> ev.isActive()).collect(Collectors.toList()));

      for (MonitorignOutcomeEvidence monitorignOutcomeEvidence : evidences) {
        if (!monitoringOutcome.getEvidences().contains(monitorignOutcomeEvidence)) {
          evidenceService.deleteMonitorignOutcomeEvidence(monitorignOutcomeEvidence.getId());
        }
      }
    }


    if (monitoringOutcome.getEvidences() != null) {
      for (MonitorignOutcomeEvidence monitorignOutcomeEvidence : monitoringOutcome.getEvidences()) {

        if (monitorignOutcomeEvidence.getId() == null || monitorignOutcomeEvidence.getId() == -1) {


          MonitorignOutcomeEvidence evidenceSave = new MonitorignOutcomeEvidence();

          evidenceSave.setActive(true);
          evidenceSave.setCreatedBy(this.getCurrentUser());
          evidenceSave.setModifiedBy(this.getCurrentUser());
          evidenceSave.setActiveSince(new Date());
          evidenceSave.setModificationJustification("");
          evidenceSave.setEvidenceLink(monitorignOutcomeEvidence.getEvidenceLink());

          evidenceSave.setMonitoringOutcome(monitoringOutcomeDB);


          evidenceService.saveMonitorignOutcomeEvidence(evidenceSave);

        } else {
          boolean hasChanges = false;
          MonitorignOutcomeEvidence evidencePrew =
            evidenceService.getMonitorignOutcomeEvidenceById(monitorignOutcomeEvidence.getId());

          if (!evidencePrew.getEvidenceLink().equals(monitorignOutcomeEvidence.getEvidenceLink())) {
            hasChanges = true;
            evidencePrew.setEvidenceLink(monitorignOutcomeEvidence.getEvidenceLink());
          }

          if (hasChanges) {
            evidencePrew.setModifiedBy(this.getCurrentUser());
            evidencePrew.setActiveSince(new Date());
            evidenceService.saveMonitorignOutcomeEvidence(evidencePrew);
          }
        }

      }
    }
  }

  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }


  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
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

  /**
   * method that sort a map list alphabetical
   * 
   * @param unsortMap - map to sort
   * @return
   */
  private HashMap<Long, String> sortByComparator(HashMap<Long, String> unsortMap) {

    // Convert Map to List
    List<HashMap.Entry<Long, String>> list = new LinkedList<HashMap.Entry<Long, String>>(unsortMap.entrySet());

    // Sort list with comparator, to compare the Map values
    Collections.sort(list, new Comparator<HashMap.Entry<Long, String>>() {

      @Override
      public int compare(HashMap.Entry<Long, String> o1, HashMap.Entry<Long, String> o2) {
        return (o1.getValue().toLowerCase().trim()).compareTo(o2.getValue().toLowerCase().trim());
      }
    });

    // Convert sorted map back to a Map
    HashMap<Long, String> sortedMap = new LinkedHashMap<Long, String>();
    for (Iterator<HashMap.Entry<Long, String>> it = list.iterator(); it.hasNext();) {
      HashMap.Entry<Long, String> entry = it.next();
      sortedMap.put(entry.getKey(), entry.getValue());
    }
    return sortedMap;
  }

}
