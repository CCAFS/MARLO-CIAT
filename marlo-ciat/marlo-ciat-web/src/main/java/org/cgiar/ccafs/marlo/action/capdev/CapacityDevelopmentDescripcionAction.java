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
import org.cgiar.ccafs.marlo.data.dao.ICapacityDevelopmentTypeDAO;
import org.cgiar.ccafs.marlo.data.dao.IResearchProgramDAO;
import org.cgiar.ccafs.marlo.data.model.CapacityDevelopment;
import org.cgiar.ccafs.marlo.data.model.CapacityDevelopmentType;
import org.cgiar.ccafs.marlo.data.model.Crp;
import org.cgiar.ccafs.marlo.data.model.Discipline;
import org.cgiar.ccafs.marlo.data.model.LocElement;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.TargetGroup;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.ICapacityDevelopmentService;
import org.cgiar.ccafs.marlo.data.service.ICrpService;
import org.cgiar.ccafs.marlo.data.service.IDisciplineService;
import org.cgiar.ccafs.marlo.data.service.ILocElementService;
import org.cgiar.ccafs.marlo.data.service.IProjectService;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;
import org.cgiar.ccafs.marlo.data.service.ITargetGroupService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

public class CapacityDevelopmentDescripcionAction extends BaseAction {


  private static final long serialVersionUID = 1L;

  private int capDevID;
  private CapacityDevelopment capdev;
  private List<String> approaches = new ArrayList<>();
  private List<String> outcomes = new ArrayList<>();
  private List<String> deliverables = new ArrayList<>();
  private List<ResearchArea> researchAreas;
  private List<ResearchProgram> researchPrograms;
  private List<Project> projects;
  private List<Crp> crps;
  private List<LocElement> regionsList;
  private List<LocElement> countryList;
  private List<CapacityDevelopmentType> capdevTypes;
  private List<Discipline> disciplines;
  private List<TargetGroup> targetGroups;
  private final ICapacityDevelopmentService capdevService;
  private final IResearchAreaService researchAreaService;
  private final IResearchProgramDAO researchProgramSercive;
  private final IProjectService projectService;
  private final ICrpService crpService;
  private final ICapacityDevelopmentTypeDAO capdevTypeService;
  private final ILocElementService locElementService;
  private final IDisciplineService disciplineService;
  private final ITargetGroupService targetGroupService;

  @Inject
  public CapacityDevelopmentDescripcionAction(APConfig config, ICapacityDevelopmentService capdevService,
    IResearchAreaService researchAreaService, IResearchProgramDAO researchProgramSercive,
    IProjectService projectService, ICrpService crpService, ICapacityDevelopmentTypeDAO capdevTypeService,
    ILocElementService locElementService, IDisciplineService disciplineService,
    ITargetGroupService targetGroupService) {
    super(config);
    this.capdevService = capdevService;
    this.researchAreaService = researchAreaService;
    this.researchProgramSercive = researchProgramSercive;
    this.projectService = projectService;
    this.crpService = crpService;
    this.capdevTypeService = capdevTypeService;
    this.locElementService = locElementService;
    this.disciplineService = disciplineService;
    this.targetGroupService = targetGroupService;
  }


  @Override
  public String cancel() {
    System.out.println("Se cancelo la operacion");
    return super.cancel();
  }


  public List<String> getApproaches() {
    return approaches;
  }


  public CapacityDevelopment getCapdev() {
    return capdev;
  }


  public int getCapDevID() {
    return capDevID;
  }


  public List<CapacityDevelopmentType> getCapdevTypes() {
    return capdevTypes;
  }


  public List<LocElement> getCountryList() {
    return countryList;
  }


  public List<Crp> getCrps() {
    return crps;
  }


  public List<String> getDeliverables() {
    return deliverables;
  }


  public List<Discipline> getDisciplines() {
    return disciplines;
  }


  public List<String> getOutcomes() {
    return outcomes;
  }


  public List<Project> getProjects() {
    return projects;
  }


  public List<LocElement> getRegionsList() {
    return regionsList;
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

    approaches.add("Gender");
    approaches.add("Ecosystems");
    approaches.add("Climate change");
    approaches.add("Freedom");
    approaches.add("Food security");
    approaches.add("Best Practices");

    outcomes.add("outcome 1");
    outcomes.add("outcome 2");
    outcomes.add("outcome 3");
    outcomes.add("outcome 4");
    outcomes.add("outcome 5");


    researchAreas = researchAreaService.findAll();
    researchPrograms = researchProgramSercive.findAll();
    projects = projectService.findAll();
    crps = crpService.findAll();
    capdevTypes = capdevTypeService.findAll();

    // Regions List
    regionsList = new ArrayList<>(locElementService.findAll().stream()
      .filter(le -> le.isActive() && (le.getLocElementType() != null) && (le.getLocElementType().getId() == 1))
      .collect(Collectors.toList()));
    Collections.sort(regionsList, (r1, r2) -> r1.getName().compareTo(r2.getName()));

    // Country List
    countryList = new ArrayList<>(locElementService.findAll().stream()
      .filter(le -> le.isActive() && (le.getLocElementType() != null) && (le.getLocElementType().getId() == 2))
      .collect(Collectors.toList()));
    Collections.sort(countryList, (c1, c2) -> c1.getName().compareTo(c2.getName()));

    // Disciplines List
    disciplines = disciplineService.findAll();

    // Target groups List
    targetGroups = targetGroupService.findAll();

    try {
      capDevID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("capDevID")));
    } catch (final Exception e) {
      capDevID = -1;
    }

    if (capDevID > -1) {

    } else {
      capdev = new CapacityDevelopment();
    }


  }


  @Override
  public String save() {


    System.out.println("este es el titulo -->" + capdev.getTitle());
    System.out.println("este es el tipo -->" + capdev.getCapdevType().getId());
    System.out.println("este es el research area -->" + capdev.getResearchArea().getId());
    System.out.println("esta es la categoria -->" + capdev.getCategory());

    System.out.println("Este es el strartDate -->" + capdev.getStartDate());
    System.out.println("Este es el endDate -->" + capdev.getEndDate());


    final Session session = SecurityUtils.getSubject().getSession();

    final User currentUser = (User) session.getAttribute(APConstants.SESSION_USER);
    System.out.println("User actual -->" + currentUser);

    capdev.setCtFirstName("Luis");
    capdev.setCtLastName("Gonzalez");
    capdev.setCtEmail("l.o.gonzalez@cgiar.org");
    capdev.setActive(true);
    capdev.setUsersByCreatedBy(currentUser);
    capdevService.saveCapacityDevelopment(capdev);


    return SUCCESS;
  }


  public void setApproaches(List<String> approaches) {
    this.approaches = approaches;
  }


  public void setCapdev(CapacityDevelopment capdev) {
    this.capdev = capdev;
  }


  public void setCapDevID(int capDevID) {
    this.capDevID = capDevID;
  }


  public void setCapdevTypes(List<CapacityDevelopmentType> capdevTypes) {
    this.capdevTypes = capdevTypes;
  }


  public void setCountryList(List<LocElement> countryList) {
    this.countryList = countryList;
  }


  public void setCrps(List<Crp> crps) {
    this.crps = crps;
  }


  public void setDeliverables(List<String> deliverables) {
    this.deliverables = deliverables;
  }


  public void setDisciplines(List<Discipline> disciplines) {
    this.disciplines = disciplines;
  }


  public void setOutcomes(List<String> outcomes) {
    this.outcomes = outcomes;
  }


  public void setProjects(List<Project> projects) {
    this.projects = projects;
  }


  public void setRegionsList(List<LocElement> regionsList) {
    this.regionsList = regionsList;
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
