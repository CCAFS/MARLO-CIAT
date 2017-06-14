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
import org.cgiar.ccafs.marlo.data.model.CapdevDiscipline;
import org.cgiar.ccafs.marlo.data.model.CapdevLocations;
import org.cgiar.ccafs.marlo.data.model.CapdevParticipant;
import org.cgiar.ccafs.marlo.data.model.CapdevTargetgroup;
import org.cgiar.ccafs.marlo.data.model.Crp;
import org.cgiar.ccafs.marlo.data.model.Discipline;
import org.cgiar.ccafs.marlo.data.model.LocElement;
import org.cgiar.ccafs.marlo.data.model.Participant;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.TargetGroup;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.ICapacityDevelopmentService;
import org.cgiar.ccafs.marlo.data.service.ICapdevDisciplineService;
import org.cgiar.ccafs.marlo.data.service.ICapdevLocationsService;
import org.cgiar.ccafs.marlo.data.service.ICapdevParticipantService;
import org.cgiar.ccafs.marlo.data.service.ICapdevTargetgroupService;
import org.cgiar.ccafs.marlo.data.service.ICrpService;
import org.cgiar.ccafs.marlo.data.service.IDisciplineService;
import org.cgiar.ccafs.marlo.data.service.ILocElementService;
import org.cgiar.ccafs.marlo.data.service.IParticipantService;
import org.cgiar.ccafs.marlo.data.service.IProjectService;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;
import org.cgiar.ccafs.marlo.data.service.ITargetGroupService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

public class CapacityDevelopmentDescripcionAction extends BaseAction {


  private static final long serialVersionUID = 1L;


  private int capDevID;
  private long capdevId;
  private CapacityDevelopment capdev;
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
  private List<Long> disciplinesSelected;
  private List<TargetGroup> targetGroups;
  private List<Long> targetGroupsSelected;
  private List<Long> capdevCountries;
  private List<Long> capdevRegions;
  private Participant participant;
  private final ICapacityDevelopmentService capdevService;
  private final IResearchAreaService researchAreaService;
  private final IResearchProgramDAO researchProgramSercive;
  private final IProjectService projectService;
  private final ICrpService crpService;
  private final ICapacityDevelopmentTypeDAO capdevTypeService;
  private final ILocElementService locElementService;
  private final IDisciplineService disciplineService;
  private final ITargetGroupService targetGroupService;
  private final ICapdevLocationsService capdevLocationService;
  private final ICapdevDisciplineService capdevDisciplineService;
  private final ICapdevTargetgroupService capdevTargetgroupService;
  private final IParticipantService participantService;


  private final ICapdevParticipantService capdevParicipantService;

  final Session session = SecurityUtils.getSubject().getSession();

  final User currentUser = (User) session.getAttribute(APConstants.SESSION_USER);


  @Inject
  public CapacityDevelopmentDescripcionAction(APConfig config, ICapacityDevelopmentService capdevService,
    IResearchAreaService researchAreaService, IResearchProgramDAO researchProgramSercive,
    IProjectService projectService, ICrpService crpService, ICapacityDevelopmentTypeDAO capdevTypeService,
    ILocElementService locElementService, IDisciplineService disciplineService, ITargetGroupService targetGroupService,
    ICapdevLocationsService capdevLocationService, ICapdevDisciplineService capdevDisciplineService,
    ICapdevTargetgroupService capdevTargetgroupService, IParticipantService participantService,
    ICapdevParticipantService capdevParicipantService) {
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
    this.capdevLocationService = capdevLocationService;
    this.capdevDisciplineService = capdevDisciplineService;
    this.capdevTargetgroupService = capdevTargetgroupService;
    this.participantService = participantService;
    this.capdevParicipantService = capdevParicipantService;
  }


  @Override
  public String cancel() {
    System.out.println("Se cancelo la operacion");
    return super.cancel();
  }


  public CapacityDevelopment getCapdev() {
    return capdev;
  }


  public List<Long> getCapdevCountries() {
    return capdevCountries;
  }


  public int getCapDevID() {
    return capDevID;
  }


  public List<Long> getCapdevRegions() {
    return capdevRegions;
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


  public List<Long> getDisciplinesSelected() {
    return disciplinesSelected;
  }


  public List<String> getOutcomes() {
    return outcomes;
  }


  public Participant getParticipant() {
    return participant;
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


  public List<Long> getTargetGroupsSelected() {
    return targetGroupsSelected;
  }


  @Override
  public void prepare() throws Exception {


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
      capdevCountries = new ArrayList<Long>();
      capdevRegions = new ArrayList<Long>();
      disciplinesSelected = new ArrayList<Long>();
    }


  }


  @Override
  public String save() {

    System.out.println("category -->" + capdev.getCategory());


    if (capdev.getCategory() == 1) {
      capdev.setTitle(participant.getName() + " " + participant.getLastName());
    }

    if (capdev.getCategory() == 2) {


    }
    System.out.println("title -->" + capdev.getTitle());

    capdev.setActive(true);
    capdev.setUsersByCreatedBy(currentUser);
    capdevService.saveCapacityDevelopment(capdev);

    this.saveParticipant(participant);
    this.saveCapDevParticipan(participant, capdev);

    // this.saveCapDevCountries(capdevCountries, capdev);
    // this.saveCapDevRegions(capdevRegions, capdev);
    // this.saveCapDevDisciplines(disciplinesSelected, capdev);
    // this.saveCapdevTargetGroups(targetGroupsSelected, capdev);


    return SUCCESS;
  }


  public void saveCapDevCountries(List<Long> capdevCountries, CapacityDevelopment capdev) {
    CapdevLocations capdevLocations = null;
    if (!capdevCountries.isEmpty()) {
      for (final Long iterator : capdevCountries) {
        final LocElement country = locElementService.getLocElementById(iterator);
        capdevLocations = new CapdevLocations();
        capdevLocations.setCapacityDevelopment(capdev);
        capdevLocations.setLocElement(country);
        capdevLocations.setActive(true);
        capdevLocations.setActiveSince(new Date());
        capdevLocations.setUsersByCreatedBy(currentUser);
        capdevLocationService.saveCapdevLocations(capdevLocations);
      }
    }
  }


  public void saveCapDevDisciplines(List<Long> disciplines, CapacityDevelopment capdev) {
    CapdevDiscipline capdevDiscipline = null;
    if (!disciplines.isEmpty()) {
      for (final Long iterator : disciplines) {
        final Discipline discipline = disciplineService.getDisciplineById(iterator);
        capdevDiscipline = new CapdevDiscipline();
        capdevDiscipline.setCapacityDevelopment(capdev);
        capdevDiscipline.setDisciplines(discipline);
        capdevDiscipline.setActive(true);
        capdevDiscipline.setActiveSince(new Date());
        capdevDiscipline.setUsersByCreatedBy(currentUser);
        capdevDisciplineService.saveCapdevDiscipline(capdevDiscipline);
      }
    }
  }


  public void saveCapDevParticipan(Participant participant, CapacityDevelopment capdev) {
    final CapdevParticipant capdevParticipant = new CapdevParticipant();
    capdevParticipant.setCapacityDevelopment(capdev);
    capdevParticipant.setParticipant(participant);
    capdevParticipant.setActive(true);
    capdevParticipant.setActiveSince(new Date());
    capdevParticipant.setUsersByCreatedBy(currentUser);

    capdevParicipantService.saveCapdevParticipant(capdevParticipant);
  }


  public void saveCapDevRegions(List<Long> capdevRegions, CapacityDevelopment capdev) {

    CapdevLocations capdevLocations = null;
    if (!capdevRegions.isEmpty()) {
      for (final Long iterator : capdevCountries) {
        final LocElement country = locElementService.getLocElementById(iterator);
        capdevLocations = new CapdevLocations();
        capdevLocations.setCapacityDevelopment(capdev);
        capdevLocations.setLocElement(country);
        capdevLocations.setActive(true);
        capdevLocations.setActiveSince(new Date());
        capdevLocations.setUsersByCreatedBy(currentUser);
        capdevLocationService.saveCapdevLocations(capdevLocations);
      }
    }
  }


  public void saveCapdevTargetGroups(List<Long> targetGroups, CapacityDevelopment capdev) {
    CapdevTargetgroup capdevTargetgroup = null;
    if (!targetGroups.isEmpty()) {
      for (final Long iterator : targetGroups) {
        final TargetGroup targetGroup = targetGroupService.getTargetGroupById(iterator);
        capdevTargetgroup = new CapdevTargetgroup();
        capdevTargetgroup.setCapacityDevelopment(capdev);
        capdevTargetgroup.setTargetGroups(targetGroup);
        capdevTargetgroup.setActive(true);
        capdevTargetgroup.setActiveSince(new Date());
        capdevTargetgroup.setUsersByCreatedBy(currentUser);
        capdevTargetgroupService.saveCapdevTargetgroup(capdevTargetgroup);
      }
    }
  }


  public void saveParticipant(Participant participant) {
    participant.setActive(true);
    participant.setAciveSince(new Date());
    participant.setUsersByCreatedBy(currentUser);
    participantService.saveParticipant(participant);
  }


  public void setCapdev(CapacityDevelopment capdev) {
    this.capdev = capdev;
  }


  public void setCapdevCountries(List<Long> capdevCountries) {
    this.capdevCountries = capdevCountries;
  }


  public void setCapDevID(int capDevID) {
    this.capDevID = capDevID;
  }


  public void setCapdevRegions(List<Long> capdevRegions) {
    this.capdevRegions = capdevRegions;
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


  public void setDisciplinesSelected(List<Long> disciplinesSelected) {
    this.disciplinesSelected = disciplinesSelected;
  }


  public void setOutcomes(List<String> outcomes) {
    this.outcomes = outcomes;
  }

  public void setParticipant(Participant participant) {
    this.participant = participant;
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


  public void setTargetGroupsSelected(List<Long> targetGroupsSelected) {
    this.targetGroupsSelected = targetGroupsSelected;
  }


}

