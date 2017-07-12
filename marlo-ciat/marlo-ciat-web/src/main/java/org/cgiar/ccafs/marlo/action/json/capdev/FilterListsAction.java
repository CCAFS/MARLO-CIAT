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
import org.cgiar.ccafs.marlo.data.model.Institution;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ProjectOutput;
import org.cgiar.ccafs.marlo.data.model.ProjectPartner;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.service.IInstitutionService;
import org.cgiar.ccafs.marlo.data.service.IProjectOutputService;
import org.cgiar.ccafs.marlo.data.service.IProjectPartnerService;
import org.cgiar.ccafs.marlo.data.service.IProjectService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputService;
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
  private List<Map<String, Object>> jsonPartners_output;
  private final IResearchProgramDAO researchProgramSercive;
  private final IProjectService projectService;
  private final IProjectPartnerService projectPartnerService;
  private final IProjectOutputService projectOutputService;
  private final IInstitutionService institutionService;
  private final IResearchOutputService researchOutputService;

  @Inject
  public FilterListsAction(APConfig config, IResearchProgramDAO researchProgramSercive, IProjectService projectService,
    IProjectPartnerService projectPartnerService, IProjectOutputService projectOutputService,
    IInstitutionService institutionService, IResearchOutputService researchOutputService) {
    super(config);
    this.researchProgramSercive = researchProgramSercive;
    this.projectService = projectService;
    this.projectPartnerService = projectPartnerService;
    this.projectOutputService = projectOutputService;
    this.institutionService = institutionService;
    this.researchOutputService = researchOutputService;
  }

  @Override
  public String execute() throws Exception {
    return SUCCESS;
  }

  public String filterPartners_Outputs() throws Exception {
    final Map<String, Object> parameters = this.getParameters();
    jsonPartners_output = new ArrayList<>();
    final long projectID =
      Long.parseLong(StringUtils.trim(((String[]) parameters.get(APConstants.QUERY_PARAMETER))[0]));
    System.out.println("projectID --> " + projectID);

    List<ProjectPartner> projectPartners = new ArrayList<>();
    List<ProjectOutput> projectOutputs = new ArrayList<>();
    List<Institution> partners = new ArrayList<>();
    List<ResearchOutput> outputs = new ArrayList<>();

    if (projectID > 0) {
      projectPartners = projectPartnerService.findAll().stream()
        .filter(le -> le.isActive() && (le.getProject().getId() == projectID)).collect(Collectors.toList());
      projectOutputs = projectOutputService.findAll().stream()
        .filter(po -> po.isActive() && (po.getProject().getId() == projectID)).collect(Collectors.toList());

    }
    if (projectID <= 0) {
      partners = institutionService.findAll();
      outputs = researchOutputService.findAll();
    }
    if (projectPartners.isEmpty()) {
      System.out.println("projectPartners.isEmpty() ");
      partners = institutionService.findAll();
      System.out.println("partners.size() " + partners.size());
      final List<Map<String, Object>> listpartnertMap = new ArrayList<>();
      for (final Institution partner : partners) {
        final Map<String, Object> partnertMap = new HashMap<>();
        partnertMap.put("idPartner", partner.getId());
        partnertMap.put("partnerName", partner.getName());
        partnertMap.put("partnerAcronym", partner.getAcronym());
        listpartnertMap.add(partnertMap);
      }
      final Map<String, Object> map = new HashMap<>();
      map.put("partners", listpartnertMap);
      jsonPartners_output.add(map);
    }
    if (!projectPartners.isEmpty()) {
      System.out.println("!projectPartners.isEmpty()");
      final List<Map<String, Object>> listpartnertMap = new ArrayList<>();
      for (final ProjectPartner projectPartner : projectPartners) {
        final Map<String, Object> projectPartnermap = new HashMap<>();
        projectPartnermap.put("idPartner", projectPartner.getInstitution().getId());
        projectPartnermap.put("partnerName", projectPartner.getInstitution().getName());
        projectPartnermap.put("partnerAcronym", projectPartner.getInstitution().getAcronym());
        listpartnertMap.add(projectPartnermap);
      }
      final Map<String, Object> map = new HashMap<>();
      map.put("partners", listpartnertMap);
      jsonPartners_output.add(map);
    }

    if (projectOutputs.isEmpty()) {
      System.out.println("projectOutputs.isEmpty() ");
      outputs = researchOutputService.findAll();
      System.out.println("outputs.size() " + outputs.size());
      final List<Map<String, Object>> listpartnertMap = new ArrayList<>();
      for (final ResearchOutput researchOutput : outputs) {
        final Map<String, Object> researchOutputMap = new HashMap<>();
        researchOutputMap.put("idOutput", researchOutput.getId());
        researchOutputMap.put("outputTitle", researchOutput.getTitle());
        researchOutputMap.put("outputShortName", researchOutput.getShortName());
        listpartnertMap.add(researchOutputMap);
      }
      final Map<String, Object> map = new HashMap<>();
      map.put("outputs", listpartnertMap);
      jsonPartners_output.add(map);
    }

    if (!projectOutputs.isEmpty()) {
      System.out.println("!projectOutputs.isEmpty()");
      final List<Map<String, Object>> listpartnertMap = new ArrayList<>();
      for (final ProjectOutput projectOutput : projectOutputs) {
        final Map<String, Object> projectOutputMap = new HashMap<>();
        projectOutputMap.put("idOutput", projectOutput.getResearchOutput().getId());
        projectOutputMap.put("outputTitle", projectOutput.getResearchOutput().getTitle());
        projectOutputMap.put("outputShortName", projectOutput.getResearchOutput().getShortName());
        listpartnertMap.add(projectOutputMap);
      }
      final Map<String, Object> map = new HashMap<>();
      map.put("outputs", listpartnertMap);
      jsonPartners_output.add(map);
    }

    return SUCCESS;
  }

  public String filterProject() throws Exception {
    final Map<String, Object> parameters = this.getParameters();
    jsonProjects = new ArrayList<>();
    final long researchProgramID =
      Long.parseLong(StringUtils.trim(((String[]) parameters.get(APConstants.QUERY_PARAMETER))[0]));

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


  public List<Map<String, Object>> getJsonPartners_output() {
    return jsonPartners_output;
  }

  public List<Map<String, Object>> getJsonProjects() {
    return jsonProjects;
  }


  public List<Map<String, Object>> getJsonResearchPrograms() {
    return jsonResearchPrograms;
  }


  public void setJsonPartners_output(List<Map<String, Object>> jsonPartners_output) {
    this.jsonPartners_output = jsonPartners_output;
  }

  public void setJsonProjects(List<Map<String, Object>> jsonProjects) {
    this.jsonProjects = jsonProjects;
  }


  public void setJsonResearchPrograms(List<Map<String, Object>> jsonResearchPrograms) {
    this.jsonResearchPrograms = jsonResearchPrograms;
  }


}
