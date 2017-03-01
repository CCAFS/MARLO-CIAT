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
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.data.service.impl.ProjectService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProjectDescriptionAction extends BaseAction {


  private static final long serialVersionUID = 3034101967516313023L;


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

  private long programID;
  private long areaID;
  private long projectID;
  private Project project;

  @Inject
  public ProjectDescriptionAction(APConfig config, ICenterService centerService, IProgramService programService,
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

  public Project getProject() {
    return project;
  }


  public long getProjectID() {
    return projectID;
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
    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());

    researchAreas = new ArrayList<>(
      loggedCenter.getResearchAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()));

    try {
      projectID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CENTER_AREA_ID)));
    } catch (Exception e) {
      projectID = -1;
    }

    project = projectService.getProjectById(projectID);

    if (project != null) {

      selectedProgram = project.getResearchProgram();
      programID = selectedProgram.getId();
      selectedResearchArea = selectedProgram.getResearchArea();
      areaID = selectedResearchArea.getId();
      researchPrograms = new ArrayList<>(
        selectedResearchArea.getResearchPrograms().stream().filter(rp -> rp.isActive()).collect(Collectors.toList()));

      project.setOutputs(
        new ArrayList<>(project.getProjectOutputs().stream().filter(po -> po.isActive()).collect(Collectors.toList())));

      project.setFundingSources(new ArrayList<>(
        project.getProjectFundingSources().stream().filter(fs -> fs.isActive()).collect(Collectors.toList())));


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

  public void setProject(Project project) {
    this.project = project;
  }

  public void setProjectID(long projectID) {
    this.projectID = projectID;
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
