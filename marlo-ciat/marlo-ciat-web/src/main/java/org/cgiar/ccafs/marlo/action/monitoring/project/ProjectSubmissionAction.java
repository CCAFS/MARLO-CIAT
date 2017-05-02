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

package org.cgiar.ccafs.marlo.action.monitoring.project;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.action.impactpathway.IPSubmissionAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ResearchCycle;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IProjectService;
import org.cgiar.ccafs.marlo.data.service.IResearchCycleService;
import org.cgiar.ccafs.marlo.data.service.ISectionStatusService;
import org.cgiar.ccafs.marlo.data.service.ISubmissionService;
import org.cgiar.ccafs.marlo.utils.SendMail;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProjectSubmissionAction extends BaseAction {

  private static final long serialVersionUID = -2125910181556052879L;

  // LOG
  private static Logger LOG = LoggerFactory.getLogger(IPSubmissionAction.class);

  // Managers
  private ISubmissionService submissionService;
  private IProjectService projectService;
  private ISectionStatusService sectionStatusService;
  private IResearchCycleService cycleService;
  private ICenterService centerService;

  // Parameters
  private SendMail sendMail;
  private Project project;
  private ResearchCycle cycle;

  @Inject
  public ProjectSubmissionAction(APConfig config, ISubmissionService submissionService, IProjectService projectService,
    ISectionStatusService sectionStatusService, IResearchCycleService cycleService, ICenterService centerService,
    SendMail sendMail) {
    super(config);
    this.submissionService = submissionService;
    this.projectService = projectService;
    this.sectionStatusService = sectionStatusService;
    this.cycleService = cycleService;
    this.centerService = centerService;
    this.sendMail = sendMail;
  }

}
