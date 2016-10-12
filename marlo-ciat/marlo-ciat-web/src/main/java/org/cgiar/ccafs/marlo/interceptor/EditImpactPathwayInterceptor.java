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
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.io.Serializable;
import java.util.Map;

import com.google.inject.Inject;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
@SuppressWarnings("unused")
public class EditImpactPathwayInterceptor extends AbstractInterceptor implements Serializable {

  private static final long serialVersionUID = 1217563340228252130L;
  private ICenterService crpManager;
  private IUserService userManager;
  // private CrpProgramManager crpProgramManager;

  private BaseAction baseAction;
  private Map<String, Object> parameters;
  private Map<String, Object> session;
  private ResearchCenter crp;
  private long crpProgramID = 0;

  @Inject
  public EditImpactPathwayInterceptor(ICenterService crpManager, IUserService userManager) {
    this.crpManager = crpManager;
    this.userManager = userManager;
  }

  long getCrpProgramId() {
    try {
      return Long.parseLong(((String[]) parameters.get(APConstants.CRP_PROGRAM_ID))[0]);
    } catch (Exception e) {
      ResearchCenter loggedCrp = (ResearchCenter) session.get(APConstants.SESSION_CRP);

      loggedCrp = crpManager.getCrpById(loggedCrp.getId());

      User user = (User) session.get(APConstants.SESSION_USER);
      user = userManager.getUser(user.getId());
      // List<CrpProgramLeader> userLeads =
      // user
      // .getCrpProgramLeaders()
      // .stream()
      // .filter(
      // c -> c.isActive() && c.getCrpProgram().isActive()
      // && c.getCrpProgram().getProgramType() == ProgramType.FLAGSHIP_PROGRAM_TYPE.getValue())
      // .collect(Collectors.toList());
      // if (!userLeads.isEmpty()) {
      // return userLeads.get(0).getCrpProgram().getId();
      // } else {
      // List<CrpProgram> allPrograms =
      // loggedCrp.getCrpPrograms().stream()
      // .filter(c -> c.getProgramType() == ProgramType.FLAGSHIP_PROGRAM_TYPE.getValue() && c.isActive())
      // .collect(Collectors.toList());
      // if (!allPrograms.isEmpty()) {
      // return allPrograms.get(0).getId();
      // }
      // }
    }
    return 0;
  }


  @Override
  public String intercept(ActionInvocation invocation) throws Exception {

    baseAction = (BaseAction) invocation.getAction();
    parameters = invocation.getInvocationContext().getParameters();
    session = invocation.getInvocationContext().getSession();
    crp = (ResearchCenter) session.get(APConstants.SESSION_CRP);
    crpProgramID = this.getCrpProgramId();

    if (!baseAction.hasPermission(baseAction.generatePermission(Permission.IMPACT_PATHWAY_VISIBLE_PRIVILEGES,
      crp.getAcronym()))) {
      return BaseAction.NOT_AUTHORIZED;
    }


    try {
      this.setPermissionParameters(invocation);
      return invocation.invoke();
    } catch (Exception e) {
      return BaseAction.NOT_FOUND;
    }
  }


  public void setPermissionParameters(ActionInvocation invocation) {

    boolean canEdit = false;
    boolean hasPermissionToEdit = false;
    boolean editParameter = false;


    // CrpProgram crpProgram = crpProgramManager.getCrpProgramById(crpProgramID);

    // if (crpProgram != null) {
    //
    // if (crpProgram.getProgramType() == ProgramType.FLAGSHIP_PROGRAM_TYPE.getValue()) {
    //
    // // If user is admin, it should have privileges to edit all projects.
    // if (baseAction.isAdmin()) {
    // canEdit = true;
    // } else {
    // if (baseAction.hasPermission(baseAction.generatePermission(Permission.IMPACT_PATHWAY_EDIT_PRIVILEGES,
    // crp.getAcronym(), crpProgramID + ""))) {
    // canEdit = true;
    // }
    // }
    //
    // if (parameters.get(APConstants.EDITABLE_REQUEST) != null) {
    // String stringEditable = ((String[]) parameters.get(APConstants.EDITABLE_REQUEST))[0];
    // editParameter = stringEditable.equals("true");
    // // If the user is not asking for edition privileges we don't need to validate them.
    // if (!editParameter) {
    // baseAction.setEditableParameter(hasPermissionToEdit);
    // }
    // }
    //
    // // Check the permission if user want to edit or save the form
    // if (editParameter || parameters.get("save") != null) {
    // hasPermissionToEdit =
    // (baseAction.isAdmin()) ? true : baseAction.hasPermission(baseAction.generatePermission(
    // Permission.IMPACT_PATHWAY_EDIT_PRIVILEGES, crp.getAcronym(), crpProgramID + ""));
    // }
    //
    // // Set the variable that indicates if the user can edit the section
    // baseAction.setEditableParameter(hasPermissionToEdit && canEdit);
    // baseAction.setCanEdit(canEdit);
    // } else {
    // throw new NullPointerException();
    // }
    // }
  }

}
