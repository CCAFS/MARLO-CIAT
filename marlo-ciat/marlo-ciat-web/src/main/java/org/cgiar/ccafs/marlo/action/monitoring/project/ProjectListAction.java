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
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchLeader;
import org.cgiar.ccafs.marlo.data.model.ResearchLeaderTypeEnum;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.data.service.impl.ProjectService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProjectListAction extends BaseAction {

  private static final long serialVersionUID = -5994329141897042670L;

  private ICenterService centerService;
  private IProgramService programService;
  private ProjectService projectService;
  private IUserService userService;
  private IResearchAreaService researchAreaService;

  private ResearchArea selectedResearchArea;
  private ResearchProgram selectedProgram;
  private ResearchCenter loggedCenter;
  private List<ResearchArea> researchAreas;
  private List<ResearchProgram> researchPrograms;
  private List<Project> projects;
  private long programID;
  private long areaID;

  @Inject
  public ProjectListAction(APConfig config, ICenterService centerService, IProgramService programService,
    ProjectService projectService, IUserService userService, IResearchAreaService researchAreaService) {
    super(config);
    this.centerService = centerService;
    this.programService = programService;
    this.projectService = projectService;
    this.userService = userService;
    this.researchAreaService = researchAreaService;
  }


  public long getAreaID() {
    return areaID;
  }


  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
  }


  public long getProgramID() {
    return programID;
  }

  public List<Project> getProjects() {
    return projects;
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

  @Override
  public void prepare() throws Exception {


    areaID = -1;
    programID = -1;

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

      projects = new ArrayList<>(selectedProgram.getProjects());


    }

  }

  public void setAreaID(long areaID) {
    this.areaID = areaID;
  }

  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
  }

  public void setProgramID(long programID) {
    this.programID = programID;
  }

  public void setProjects(List<Project> projects) {
    this.projects = projects;
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

}
