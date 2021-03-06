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

package org.cgiar.ccafs.marlo.action.json.monitoring.outcome;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.MonitoringMilestone;
import org.cgiar.ccafs.marlo.data.model.MonitoringOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchMilestone;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.TargetUnit;
import org.cgiar.ccafs.marlo.data.service.IMonitoringMilestoneService;
import org.cgiar.ccafs.marlo.data.service.IResearchMilestoneService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutcomeService;
import org.cgiar.ccafs.marlo.data.service.ITargetUnitService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionContext;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class MilestoneAddAction extends BaseAction {


  private static final long serialVersionUID = -469429669727688493L;

  private static String TITLE = "title";
  private static String TARGET_UNIT = "targetUnit";
  private static String YEAR = "year";
  private static String VALUE = "value";
  private static String OUTCOME_ID = "outcomeID";

  private long outcomeID;
  private IMonitoringMilestoneService monitoringMilestoneService;

  private IResearchMilestoneService milestoneService;
  private IResearchOutcomeService outcomeService;
  private ITargetUnitService targetUnitService;
  private List<Map<String, Object>> newMilestone;

  @Inject
  public MilestoneAddAction(APConfig config, IMonitoringMilestoneService monitoringMilestoneService,
    IResearchMilestoneService milestoneService, ITargetUnitService targetUnitService,
    IResearchOutcomeService outcomeService) {
    super(config);
    this.monitoringMilestoneService = monitoringMilestoneService;
    this.milestoneService = milestoneService;
    this.targetUnitService = targetUnitService;
    this.outcomeService = outcomeService;
  }

  @Override
  public String execute() throws Exception {

    Map<String, Object> parameters = ActionContext.getContext().getParameters();

    outcomeID = Long.parseLong(StringUtils.trim(((String[]) parameters.get(OUTCOME_ID))[0]));

    ResearchOutcome outcome = outcomeService.getResearchOutcomeById(outcomeID);

    Map<String, Object> milestoneData = new HashMap<>();
    ResearchMilestone milestone = new ResearchMilestone();
    milestone.setResearchOutcome(outcome);
    milestone.setActive(true);
    milestone.setActiveSince(new Date());
    milestone.setCreatedBy(this.getCurrentUser());
    milestone.setModifiedBy(this.getCurrentUser());
    milestone.setImpactPathway(false);

    TargetUnit targetUnit = targetUnitService
      .getTargetUnitById(Long.parseLong(StringUtils.trim(((String[]) parameters.get(TARGET_UNIT))[0])));
    milestone.setTargetUnit(targetUnit);
    milestoneData.put("targetUnitId", targetUnit.getId());
    if (targetUnit.getId() != -1) {
      milestone
        .setValue(BigDecimal.valueOf(Double.parseDouble(StringUtils.trim(((String[]) parameters.get(VALUE))[0]))));
      milestoneData.put("value", milestone.getValue());
    } else {
      milestone.setValue(null);
    }

    milestone.setTargetYear(Integer.parseInt(StringUtils.trim(((String[]) parameters.get(YEAR))[0])));
    milestoneData.put("targetYear", milestone.getTargetYear());
    milestone.setTitle(StringUtils.trim(((String[]) parameters.get(TITLE))[0]));
    milestoneData.put("title", milestone.getTitle());

    long milestoneID = milestoneService.saveResearchMilestone(milestone);
    milestoneData.put("id", milestoneID);

    milestone = milestoneService.getResearchMilestoneById(milestoneID);


    List<MonitoringOutcome> monitoringOutcomes = new ArrayList<>(
      outcome.getMonitoringOutcomes().stream().filter(mo -> mo.isActive()).collect(Collectors.toList()));
    Collections.sort(monitoringOutcomes,
      (mon1, mon2) -> (new Integer(mon1.getYear())).compareTo(new Integer(mon2.getYear())));

    List<Map<String, Object>> monitoringDatas = new ArrayList<>();

    int i = 0;
    for (MonitoringOutcome monitoringOutcome : monitoringOutcomes) {

      Map<String, Object> monitoringData = new HashMap<>();
      if (milestone.getTargetYear() >= monitoringOutcome.getYear()) {
        monitoringData.put("index", i);

        MonitoringMilestone monitoringMilestone = new MonitoringMilestone();
        monitoringMilestone.setActive(true);
        monitoringMilestone.setActiveSince(new Date());
        monitoringMilestone.setCreatedBy(this.getCurrentUser());
        monitoringMilestone.setModifiedBy(this.getCurrentUser());
        monitoringMilestone.setResearchMilestone(milestone);
        monitoringMilestone.setMonitoringOutcome(monitoringOutcome);
        monitoringMilestone.setModificationJustification("Added in Monitoring " + this.getYear());

        long monitoringMilestoneID = monitoringMilestoneService.saveMonitoringMilestone(monitoringMilestone);
        monitoringData.put("Elementid", monitoringMilestoneID);

        monitoringDatas.add(monitoringData);
      }
      i++;
    }

    newMilestone = new ArrayList<>();
    newMilestone.add(milestoneData);
    newMilestone.addAll(monitoringDatas);


    return SUCCESS;
  }

  public List<Map<String, Object>> getNewMilestone() {
    return newMilestone;
  }

  public long getOutcomeID() {
    return outcomeID;
  }

  public void setNewMilestone(List<Map<String, Object>> newMilestone) {
    this.newMilestone = newMilestone;
  }

  public void setOutcomeID(long outcomeID) {
    this.outcomeID = outcomeID;
  }


}
