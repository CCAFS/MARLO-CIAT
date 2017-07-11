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

package org.cgiar.ccafs.marlo.action.json.capdev;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.dao.IResearchProgramDAO;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.service.IProjectService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

public class FilterListsAction extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private List<Map<String, Object>> jsonResearchPrograms;
  private List<Map<String, Object>> jsonProjects;
  private final IResearchProgramDAO researchProgramSercive;
  private final IProjectService projectService;

  @Inject
  public FilterListsAction(APConfig config, IResearchProgramDAO researchProgramSercive,
    IProjectService projectService) {
    super(config);
    this.researchProgramSercive = researchProgramSercive;
    this.projectService = projectService;
  }

  @Override
  public String execute() throws Exception {
    return SUCCESS;
  }

  public String filterProject() throws Exception {
    final Map<String, Object> parameters = this.getParameters();
    jsonProjects = new ArrayList<>();
    final long researchProgramID =
      Long.parseLong(StringUtils.trim(((String[]) parameters.get(APConstants.QUERY_PARAMETER))[0]));

    System.out.println("researchProgramID --> " + researchProgramID);
    List<Project> projects = new ArrayList<>();
    if (researchProgramID > 0) {
      projects = projectService.findAll().stream()
        .filter(p -> p.isActive() && (p.getResearchProgram().getId() == researchProgramID))
        .collect(Collectors.toList());
    } else {
      projects = projectService.findAll();
    }

    if (!projects.isEmpty()) {
      for (final Project project : projects) {
        final Map<String, Object> projectMap = new HashMap<>();
        projectMap.put("projectID", project.getId());
        projectMap.put("projectName", project.getName());

        jsonProjects.add(projectMap);
      }
    }


    return SUCCESS;
  }


  public String filterResearchProgram() throws Exception {
    final Map<String, Object> parameters = this.getParameters();
    final long researchAreaID =
      Long.parseLong(StringUtils.trim(((String[]) parameters.get(APConstants.QUERY_PARAMETER))[0]));
    this.jsonResearchPrograms = new ArrayList<>();
    List<ResearchProgram> researchPrograms = new ArrayList<>();
    if (researchAreaID > 0) {
      researchPrograms = researchProgramSercive.findAll().stream()
        .filter(le -> le.isActive() && (le.getResearchArea().getId() == researchAreaID)).collect(Collectors.toList());
    } else {
      researchPrograms = researchProgramSercive.findAll();
    }

    for (final ResearchProgram researchProgram : researchPrograms) {
      final Map<String, Object> rpMap = new HashMap<String, Object>();
      rpMap.put("rpID", researchProgram.getId());
      rpMap.put("rpName", researchProgram.getName());
      rpMap.put("rpAcronym", researchProgram.getAcronym());
      rpMap.put("rpProgramType", researchProgram.getProgramType());
      this.jsonResearchPrograms.add(rpMap);
    }


    return SUCCESS;
  }


  public List<Map<String, Object>> getJsonProjects() {
    return jsonProjects;
  }


  public List<Map<String, Object>> getJsonResearchPrograms() {
    return jsonResearchPrograms;
  }


  public void setJsonProjects(List<Map<String, Object>> jsonProjects) {
    this.jsonProjects = jsonProjects;
  }


  public void setJsonResearchPrograms(List<Map<String, Object>> jsonResearchPrograms) {
    this.jsonResearchPrograms = jsonResearchPrograms;
  }


}
