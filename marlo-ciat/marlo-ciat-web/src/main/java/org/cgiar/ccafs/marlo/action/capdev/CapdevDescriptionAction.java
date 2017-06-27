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

package org.cgiar.ccafs.marlo.action.capdev;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.dao.IResearchProgramDAO;
import org.cgiar.ccafs.marlo.data.model.CapacityDevelopment;
import org.cgiar.ccafs.marlo.data.model.Crp;
import org.cgiar.ccafs.marlo.data.model.Discipline;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.TargetGroup;
import org.cgiar.ccafs.marlo.data.service.ICrpService;
import org.cgiar.ccafs.marlo.data.service.IDisciplineService;
import org.cgiar.ccafs.marlo.data.service.IProjectService;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;
import org.cgiar.ccafs.marlo.data.service.ITargetGroupService;

import java.util.List;

import com.google.inject.Inject;

public class CapdevDescriptionAction extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private CapacityDevelopment capdev;

  private List<ResearchArea> researchAreas;
  private List<ResearchProgram> researchPrograms;
  private List<Project> projects;
  private List<Crp> crps;
  private List<Discipline> disciplines;
  private List<TargetGroup> targetGroups;
  private final IResearchAreaService researchAreaService;
  private final IResearchProgramDAO researchProgramSercive;
  private final IProjectService projectService;
  private final ICrpService crpService;
  private final IDisciplineService disciplineService;
  private final ITargetGroupService targetGroupService;


  @Inject
  public CapdevDescriptionAction(APConfig config, IResearchAreaService researchAreaService,
    IResearchProgramDAO researchProgramSercive, IProjectService projectService, ICrpService crpService,
    IDisciplineService disciplineService, ITargetGroupService targetGroupService) {
    super(config);
    this.researchAreaService = researchAreaService;
    this.researchProgramSercive = researchProgramSercive;
    this.projectService = projectService;
    this.crpService = crpService;
    this.disciplineService = disciplineService;
    this.targetGroupService = targetGroupService;
  }

  public CapacityDevelopment getCapdev() {
    return capdev;
  }


  public List<Crp> getCrps() {
    return crps;
  }


  public List<Discipline> getDisciplines() {
    return disciplines;
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


  public List<TargetGroup> getTargetGroups() {
    return targetGroups;
  }


  @Override
  public void prepare() throws Exception {
    capdev = new CapacityDevelopment();

    researchAreas = researchAreaService.findAll();
    researchPrograms = researchProgramSercive.findAll();
    projects = projectService.findAll();
    crps = crpService.findAll();

    // Disciplines List
    disciplines = disciplineService.findAll();

    // Target groups List
    targetGroups = targetGroupService.findAll();
  }


  public void setCapdev(CapacityDevelopment capdev) {
    this.capdev = capdev;
  }


  public void setCrps(List<Crp> crps) {
    this.crps = crps;
  }


  public void setDisciplines(List<Discipline> disciplines) {
    this.disciplines = disciplines;
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


  public void setTargetGroups(List<TargetGroup> targetGroups) {
    this.targetGroups = targetGroups;
  }


}
