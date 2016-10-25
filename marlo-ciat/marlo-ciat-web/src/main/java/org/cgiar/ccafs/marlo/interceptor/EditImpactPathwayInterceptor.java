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

package org.cgiar.ccafs.marlo.interceptor;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchLeader;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class EditImpactPathwayInterceptor extends AbstractInterceptor implements Serializable {

  private static final long serialVersionUID = 1217563340228252130L;
  private ICenterService centerService;
  private IUserService userService;
  private IProgramService programService;

  private BaseAction baseAction;
  private Map<String, Object> parameters;
  private Map<String, Object> session;
  private ResearchCenter researchCenter;
  private long programID = -1;
  private long areaID = -1;

  @Inject
  public EditImpactPathwayInterceptor(ICenterService centerService, IUserService userService,
    IProgramService programService) {
    this.centerService = centerService;
    this.userService = userService;
    this.programService = programService;
  }

  void getprogramId() {
    try {
      programID = Long.parseLong(((String[]) parameters.get(APConstants.CENTER_PROGRAM_ID))[0]);
    } catch (Exception e) {
      ResearchCenter loggedCenter = (ResearchCenter) session.get(APConstants.SESSION_CENTER);
      loggedCenter = centerService.getCrpById(loggedCenter.getId());
      User user = (User) session.get(APConstants.SESSION_USER);
      user = userService.getUser(user.getId());

      // TODO - Create Enum to Research Leaders Type
      List<ResearchLeader> userLeads = new ArrayList<>(user.getResearchLeaders().stream()
        .filter(rl -> rl.isActive() && rl.getType().getId() == 6).collect(Collectors.toList()));

      if (!userLeads.isEmpty()) {
        programID = userLeads.get(0).getResearchProgram().getId();
      } else {
        ResearchProgram rp =
          loggedCenter.getResearchAreas().stream().filter(ra -> ra.isActive()).collect(Collectors.toList()).get(0)
            .getResearchPrograms().stream().filter(r -> r.isActive()).collect(Collectors.toList()).get(0);
        programID = rp.getId();
        areaID = rp.getResearchArea().getId();
      }
    }
  }


  @Override
  public String intercept(ActionInvocation invocation) throws Exception {

    baseAction = (BaseAction) invocation.getAction();
    parameters = invocation.getInvocationContext().getParameters();
    session = invocation.getInvocationContext().getSession();
    researchCenter = (ResearchCenter) session.get(APConstants.SESSION_CENTER);
    this.getprogramId();

    // String params[] = {researchCenter.getAcronym(), areaID + "", programID + ""};
    // if (!baseAction.hasPermission(baseAction.generatePermission(Permission.RESEARCH_PROGRAM_FULL_PRIVILEGES,
    // params))) {
    // return BaseAction.NOT_AUTHORIZED;
    // }

    try {
      this.setPermissionParameters(invocation);
      return invocation.invoke();
    } catch (Exception e) {
      return BaseAction.NOT_FOUND;
    }
  }


  public void setPermissionParameters(ActionInvocation invocation) throws Exception {

    boolean canEdit = false;
    boolean hasPermissionToEdit = false;
    boolean editParameter = false;

    ResearchProgram researchProgram = programService.getProgramById(programID);

    if (researchProgram != null) {

      if (areaID != -1) {
        areaID = researchProgram.getResearchArea().getId();
      }
      String params[] = {researchCenter.getAcronym(), areaID + "", programID + ""};
      if (baseAction.canAccessSuperAdmin()) {
        canEdit = true;
      } else {

        if (baseAction
          .hasPermission(baseAction.generatePermission(Permission.RESEARCH_PROGRAM_FULL_PRIVILEGES, params))) {
          canEdit = true;
        }
      }

      if (parameters.get(APConstants.EDITABLE_REQUEST) != null) {
        String stringEditable = ((String[]) parameters.get(APConstants.EDITABLE_REQUEST))[0];
        editParameter = stringEditable.equals("true");
        // If the user is not asking for edition privileges we don't need to validate them.
        if (!editParameter) {
          baseAction.setEditableParameter(hasPermissionToEdit);
        }
      }

      // Check the permission if user want to edit or save the form
      if (editParameter || parameters.get("save") != null) {
        hasPermissionToEdit = (baseAction.isAdmin()) ? true : baseAction
          .hasPermission(baseAction.generatePermission(Permission.RESEARCH_PROGRAM_FULL_PRIVILEGES, params));
      }

      // Set the variable that indicates if the user can edit the section
      baseAction.setEditableParameter(hasPermissionToEdit && canEdit);
      baseAction.setCanEdit(canEdit);

    } else {
      throw new Exception();
    }
  }

}
