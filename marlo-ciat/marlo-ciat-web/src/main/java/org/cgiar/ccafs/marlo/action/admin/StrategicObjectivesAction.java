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

package org.cgiar.ccafs.marlo.action.admin;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchObjective;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IResearchObjectiveService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class StrategicObjectivesAction extends BaseAction {


  private static final long serialVersionUID = 4240576135659881112L;


  private IResearchObjectiveService objectiveService;


  private ICenterService centerService;


  private ResearchCenter loggedCenter;

  private List<ResearchObjective> objectives;

  @Inject
  public StrategicObjectivesAction(APConfig config, IResearchObjectiveService objectiveService,
    ICenterService centerService) {
    super(config);
    this.objectiveService = objectiveService;
    this.centerService = centerService;
  }

  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
  }

  public List<ResearchObjective> getObjectives() {
    return objectives;
  }

  @Override
  public void prepare() throws Exception {
    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());

    objectives = new ArrayList<>(objectiveService.findAll().stream()
      .filter(o -> o.isActive() && o.getResearchCenter().equals(loggedCenter)).collect(Collectors.toList()));

    String params[] = {loggedCenter.getAcronym() + ""};
    this.setBasePermission(this.getText(Permission.CENTER_ADMIN_BASE_PERMISSION, params));

    if (this.isHttpPost()) {
      if (objectives != null) {
        objectives.clear();
      }
    }
  }

  @Override
  public String save() {
    if (this.hasPermission("*")) {
      List<ResearchObjective> objectivesDB = new ArrayList<>(objectiveService.findAll().stream()
        .filter(o -> o.isActive() && o.getResearchCenter().equals(loggedCenter)).collect(Collectors.toList()));

      for (ResearchObjective researchObjective : objectivesDB) {
        if (!objectives.contains(researchObjective)) {
          objectiveService.deleteResearchObjective(researchObjective.getId());
        }
      }

      for (ResearchObjective researchObjective : objectives) {
        if (researchObjective.getId() == null || researchObjective.getId() == -1) {
          ResearchObjective researchObjectiveNew = new ResearchObjective();

          researchObjectiveNew.setActive(true);
          researchObjectiveNew.setCreatedBy(this.getCurrentUser());
          researchObjectiveNew.setModifiedBy(this.getCurrentUser());
          researchObjectiveNew.setActiveSince(new Date());
          researchObjectiveNew.setModificationJustification("");
          researchObjectiveNew.setResearchCenter(loggedCenter);
          researchObjectiveNew.setObjective(researchObjective.getObjective());

          objectiveService.saveResearchObjective(researchObjectiveNew);

        }
      }
      return SUCCESS;
    } else {
      return NOT_AUTHORIZED;
    }
  }

  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
  }

  public void setObjectives(List<ResearchObjective> objectives) {
    this.objectives = objectives;
  }
}
