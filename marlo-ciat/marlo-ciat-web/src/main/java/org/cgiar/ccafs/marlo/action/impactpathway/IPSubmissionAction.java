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
import org.cgiar.ccafs.marlo.data.model.ImpactPathwayCyclesEnum;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchCycle;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.SectionStatus;
import org.cgiar.ccafs.marlo.data.model.Submission;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchCycleService;
import org.cgiar.ccafs.marlo.data.service.ISectionStatusService;
import org.cgiar.ccafs.marlo.data.service.ISubmissionService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class IPSubmissionAction extends BaseAction {


  private static final long serialVersionUID = 4882851743044518890L;


  private ISubmissionService submissionService;
  private IProgramService programService;
  private ISectionStatusService sectionStatusService;
  private IResearchCycleService cycleService;
  private ICenterService centerService;


  private ResearchProgram program;
  private ResearchCycle cycle;
  private ResearchCenter loggedCenter;

  private long programID;
  private boolean isSubmited = false;

  @Inject
  public IPSubmissionAction(APConfig config, ISubmissionService submissionService, IProgramService programService,
    ISectionStatusService sectionStatusService, IResearchCycleService cycleService, ICenterService centerService) {
    super(config);
    this.programService = programService;
    this.submissionService = submissionService;
    this.sectionStatusService = sectionStatusService;
    this.cycleService = cycleService;
    this.centerService = centerService;
  }

  @Override
  public String execute() throws Exception {
    if (this.hasPermission("*")) {
      if (this.isCompleteIP(programID)) {
        if (submissionService.findAll() != null) {
          ResearchProgram program = programService.getProgramById(programID);

          List<Submission> submissions = new ArrayList<>(program.getSubmissions().stream()
            .filter(s -> s.getResearchCycle().equals(cycle) && s.getYear() == (short) this.getYear())
            .collect(Collectors.toList()));

          if (submissions != null && submissions.size() > 0) {
            this.setSubmission(submissions.get(0));
            isSubmited = true;
          }
        }

        if (!isSubmited) {

          Submission submission = new Submission();
          submission.setResearchProgram(program);
          submission.setDateTime(new Date());
          submission.setUser(this.getCurrentUser());
          submission.setYear((short) this.getYear());
          submission.setResearchCycle(cycle);

          long submissionId = submissionService.saveSubmission(submission);

          if (submissionId > 0) {
            this.setSubmission(submissionService.getSubmissionById(submissionId));
          }

        }
      }
      return SUCCESS;
    } else {
      return NOT_AUTHORIZED;
    }

  }

  public long getProgramID() {
    return programID;
  }

  public boolean isCompleteIP(long programId) {

    if (sectionStatusService.findAll() == null) {
      return false;
    }

    ResearchProgram researchProgram = programService.getProgramById(programId);

    List<SectionStatus> sectionStatuses = new ArrayList<>(researchProgram.getSectionStatuses().stream()
      .filter(ss -> ss.getYear() == (short) this.getYear()).collect(Collectors.toList()));

    if (sectionStatuses != null && sectionStatuses.size() > 0) {
      for (SectionStatus sectionStatus : sectionStatuses) {
        if (sectionStatus.getMissingFields().length() > 0) {
          return false;
        }
      }
    } else {
      return false;
    }
    return true;
  }

  @Override
  public void prepare() throws Exception {

    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());

    try {
      programID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CENTER_PROGRAM_ID)));
    } catch (NumberFormatException e) {
      programID = -1;
      return; // Stop here and go to execute method.
    }

    program = programService.getProgramById(programID);

    String params[] = {loggedCenter.getAcronym(), program.getResearchArea().getId() + "", program.getId() + ""};
    this.setBasePermission(this.getText(Permission.RESEARCH_PROGRAM_BASE_PERMISSION, params));

    cycle = cycleService.getResearchCycleById(ImpactPathwayCyclesEnum.IMPACT_PATHWAY.getId());

  }

  public void setProgramID(long programID) {
    this.programID = programID;
  }


}
