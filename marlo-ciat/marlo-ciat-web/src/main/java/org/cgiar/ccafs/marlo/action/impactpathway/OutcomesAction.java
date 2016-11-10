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
import org.cgiar.ccafs.marlo.data.model.ResearchImpact;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.model.TargetUnit;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutcomeService;
import org.cgiar.ccafs.marlo.data.service.IResearchTopicService;
import org.cgiar.ccafs.marlo.data.service.ITargetUnitService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class OutcomesAction extends BaseAction {


  private static final long serialVersionUID = 286014380343262534L;


  // Services - Managers
  private ICenterService centerService;
  private IResearchOutcomeService outcomeService;
  private ITargetUnitService targetUnitService;
  private IResearchTopicService researchTopicService;

  // Front Variables
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
  public OutcomesAction(APConfig config, ICenterService centerService, IResearchOutcomeService outcomeService,
    ITargetUnitService targetUnitService, IResearchTopicService researchTopicService) {
    super(config);
    this.centerService = centerService;
    this.outcomeService = outcomeService;
    this.targetUnitService = targetUnitService;
    this.researchTopicService = researchTopicService;
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

    }

    outcome = outcomeService.getResearchOutcomeById(outcomeID);


    researchAreas = new ArrayList<>(
      loggedCenter.getResearchAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));

    Collections.sort(researchAreas, (ra1, ra2) -> ra1.getId().compareTo(ra2.getId()));

    if (researchAreas != null && outcome != null) {

      selectedProgram = outcome.getResearchImpact().getResearchProgram();
      programID = selectedProgram.getId();
      selectedResearchTopic = outcome.getResearchTopic();
      topicID = selectedResearchTopic.getId();
      selectedResearchArea = selectedProgram.getResearchArea();
      areaID = selectedResearchArea.getId();

      if (selectedProgram.getResearchTopics() != null) {
        researchTopics = new ArrayList<>(
          selectedProgram.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));
        try {
          topicID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.RESEARCH_TOPIC_ID)));
          selectedResearchTopic = researchTopicService.getResearchTopicById(topicID);
        } catch (Exception e) {
          if (!researchTopics.isEmpty()) {
            selectedResearchTopic = researchTopics.get(0);
          }
        }
      }

      researchPrograms = new ArrayList<>(
        selectedResearchArea.getResearchPrograms().stream().filter(rp -> rp.isActive()).collect(Collectors.toList()));

      researchImpacts = new ArrayList<>(
        selectedProgram.getResearchImpacts().stream().filter(ri -> ri.isActive()).collect(Collectors.toList()));

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

      outcome.setMilestones(new ArrayList<>(
        outcome.getResearchMilestones().stream().filter(rm -> rm.isActive()).collect(Collectors.toList())));

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
