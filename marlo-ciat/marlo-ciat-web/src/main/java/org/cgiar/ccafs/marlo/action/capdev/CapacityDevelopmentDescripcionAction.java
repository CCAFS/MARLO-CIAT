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
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.ICapacityDevelopmentService;
import org.cgiar.ccafs.marlo.data.service.ICrpService;
import org.cgiar.ccafs.marlo.data.service.IProjectService;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.List;

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
  private List<String> types = new ArrayList<>();
  private List<String> deliverables = new ArrayList<>();
  private List<ResearchArea> researchAreas;
  private List<ResearchProgram> researchPrograms;
  private List<Project> projects;
  private List<Crp> crps;
  private final ICapacityDevelopmentService capdevService;
  private final IResearchAreaService researchAreaService;
  private final IResearchProgramDAO researchProgramSercive;
  private final IProjectService projectService;
  private final ICrpService crpService;

  @Inject
  public CapacityDevelopmentDescripcionAction(APConfig config, ICapacityDevelopmentService capdevService,
    IResearchAreaService researchAreaService, IResearchProgramDAO researchProgramSercive,
    IProjectService projectService, ICrpService crpService) {
    super(config);
    this.capdevService = capdevService;
    this.researchAreaService = researchAreaService;
    this.researchProgramSercive = researchProgramSercive;
    this.projectService = projectService;
    this.crpService = crpService;
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


  public List<Crp> getCrps() {
    return crps;
  }


  public List<String> getDeliverables() {
    return deliverables;
  }


  public List<String> getOutcomes() {
    return outcomes;
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


  public List<String> getTypes() {
    return types;
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


    types.add("Thesis");
    types.add("Publication");
    types.add("Ph Thesis");

    researchAreas = researchAreaService.findAll();
    researchPrograms = researchProgramSercive.findAll();
    projects = projectService.findAll();
    crps = crpService.findAll();
    System.out.println("research progrmas -->" + researchPrograms.size());

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
    System.out.println("este es el tipo -->" + capdev.getCapdevType());


    final Session session = SecurityUtils.getSubject().getSession();

    final User currentUser = (User) session.getAttribute(APConstants.SESSION_USER);
    System.out.println("User actual -->" + currentUser);

    capdev.setCategory(1);
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


  public void setCrps(List<Crp> crps) {
    this.crps = crps;
  }


  public void setDeliverables(List<String> deliverables) {
    this.deliverables = deliverables;
  }


  public void setOutcomes(List<String> outcomes) {
    this.outcomes = outcomes;
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


  public void setTypes(List<String> types) {
    this.types = types;
  }


}
