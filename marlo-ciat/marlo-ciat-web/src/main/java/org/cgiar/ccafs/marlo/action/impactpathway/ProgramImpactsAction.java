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

/**
 * 
 */
package org.cgiar.ccafs.marlo.action.impactpathway;

import org.cgiar.ccafs.marlo.action.BaseImpactsPathwayAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.service.IAuditLogService;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.ICenterUserService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;
import org.cgiar.ccafs.marlo.data.service.IResearchLeaderService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.List;

import com.google.inject.Inject;


/**
 * The action class for handling the Program Impacts
 * Modified by @author nmatovu last on Oct 3, 2016
 */
public class ProgramImpactsAction extends BaseImpactsPathwayAction {


  private static final long serialVersionUID = -2261790056574973080L;
  private ICenterService centerService;
  private IProgramService programService;
  private IResearchAreaService researchAreaService;
  private IResearchLeaderService researchLeaderService;
  private IUserService userService;
  private ICenterUserService centerUserService;
  private IAuditLogService auditLogManager;

  private ResearchCenter loggedCenter;
  private List<ResearchArea> reseachAreas;
  private ResearchArea selectedResearchArea;
  private List<ResearchProgram> researchPrograms;
  private ResearchProgram selectedProgram;
  private Long programID;
  private Long areaID;

  /**
   * @param config
   */
  @Inject
  public ProgramImpactsAction(APConfig config) {
    super(config);
    // TODO Auto-generated constructor stub
  }

  /**
   * @param config
   * @param centerService
   * @param programService
   * @param researchAreaService
   * @param researchLeaderService
   * @param userService
   * @param centerUserService
   * @param auditLogManager
   */
  @Inject
  public ProgramImpactsAction(APConfig config, ICenterService centerService, IProgramService programService,
    IResearchAreaService researchAreaService, IResearchLeaderService researchLeaderService, IUserService userService,
    ICenterUserService centerUserService, IAuditLogService auditLogManager) {
    super(config);
    this.centerService = centerService;
    this.programService = programService;
    this.researchAreaService = researchAreaService;
    this.researchLeaderService = researchLeaderService;
    this.userService = userService;
    this.centerUserService = centerUserService;
    this.auditLogManager = auditLogManager;
  }


  /**
   * @return the loggedCenter
   */
  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
  }


  /**
   * @return the programID
   */
  public Long getProgramID() {
    return programID;
  }


  /**
   * @return the reseachAreas
   */
  public List<ResearchArea> getReseachAreas() {
    return reseachAreas;
  }


  /**
   * @return the researchPrograms
   */
  public List<ResearchProgram> getResearchPrograms() {
    return researchPrograms;
  }


  /**
   * @return the selectedProgram
   */
  public ResearchProgram getSelectedProgram() {
    return selectedProgram;
  }


  /**
   * @return the selectedResearchArea
   */
  public ResearchArea getSelectedResearchArea() {
    return selectedResearchArea;
  }


  @Override
  public void prepare() throws Exception {
    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CRP);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());
    // Load all available research areas
    reseachAreas = researchAreaService.findAll();
    selectedResearchArea = (ResearchArea) this.getSession().get(APConstants.SESSION_RESEARCH_AREA);
    // TODO: Consider user permissions
    if (selectedResearchArea != null) {
      selectedProgram = (ResearchProgram) this.getSession().get(APConstants.SESSION_RESEARCH_PROGRAM);
      if (selectedProgram != null) {
        // If the research program exists in the session
        // TODO: Handle the existing program
      } else {
        // Retrieve the research program based on the selected research area.
        researchPrograms = programService.findProgramsByResearchArea((Long) selectedResearchArea.getId());
      }
    }
  }

  /**
   * @param loggedCenter the loggedCenter to set
   */
  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
  }


  /**
   * @param programID the programID to set
   */
  public void setProgramID(Long programID) {
    this.programID = programID;
  }

  /**
   * @param reseachAreas the reseachAreas to set
   */
  public void setReseachAreas(List<ResearchArea> reseachAreas) {
    this.reseachAreas = reseachAreas;
  }


  /**
   * @param researchPrograms the researchPrograms to set
   */
  public void setResearchPrograms(List<ResearchProgram> researchPrograms) {
    this.researchPrograms = researchPrograms;
  }

  /**
   * @param selectedProgram the selectedProgram to set
   */
  public void setSelectedProgram(ResearchProgram selectedProgram) {
    this.selectedProgram = selectedProgram;
  }

  /**
   * @param selectedResearchArea the selectedResearchArea to set
   */
  public void setSelectedResearchArea(ResearchArea selectedResearchArea) {
    this.selectedResearchArea = selectedResearchArea;
  }

  /**
   * @return the areaID
   */
  public Long getAreaID() {
    return areaID;
  }

  /**
   * @param areaID the areaID to set
   */
  public void setAreaID(Long areaID) {
    this.areaID = areaID;
  }

}
