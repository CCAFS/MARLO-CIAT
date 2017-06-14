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


package org.cgiar.ccafs.marlo.action.summaries;


import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.Project;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;

public class SummaryListAction extends BaseAction {


  /**
   * 
   */
  private static final long serialVersionUID = -4099604925246954828L;


  private List<Project> allProjects;

  private List<ResearchProgram> programs;

  private ResearchCenter loggedCenter;
  private ICenterService centerService;

  private IProgramService programService;

  @Inject
  public SummaryListAction(APConfig config, ICenterService centerService, IProgramService programService) {
    super(config);
    this.centerService = centerService;
    this.programService = programService;
  }

  public List<Project> getAllProjects() {
    return allProjects;
  }


  public List<ResearchProgram> getPrograms() {
    return programs;
  }

  @Override
  public void prepare() throws Exception {
    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());

    allProjects = new ArrayList<Project>();

    programs =
      new ArrayList<>(programService.findAll().stream().filter(p -> p.isActive()).collect(Collectors.toList()));


  }


  public void setAllProjects(List<Project> allProjects) {
    this.allProjects = allProjects;
  }

  public void setPrograms(List<ResearchProgram> programs) {
    this.programs = programs;
  }

}
