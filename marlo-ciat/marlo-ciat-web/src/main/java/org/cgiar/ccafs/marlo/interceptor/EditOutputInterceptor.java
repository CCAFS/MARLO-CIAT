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
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutputService;
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
public class EditOutputInterceptor extends AbstractInterceptor implements Serializable {

  private static final long serialVersionUID = 8386352290491092445L;

  private IResearchOutputService outputService;
  private IProgramService programService;

  private BaseAction baseAction;
  private Map<String, Object> parameters;
  private Map<String, Object> session;
  private ResearchCenter researchCenter;

  private long outputID = -1;
  private long areaID = -1;
  private long programID = -1;

  @Inject
  public EditOutputInterceptor(IProgramService programService, IResearchOutputService outputService) {
    this.programService = programService;
    this.outputService = outputService;
  }

  @Override
  public String intercept(ActionInvocation invocation) throws Exception {
    baseAction = (BaseAction) invocation.getAction();
    parameters = invocation.getInvocationContext().getParameters();
    session = invocation.getInvocationContext().getSession();
    researchCenter = (ResearchCenter) session.get(APConstants.SESSION_CENTER);

    try {
      outputID = Long.parseLong(((String[]) parameters.get(APConstants.OUTPUT_ID))[0]);
    } catch (Exception e) {
      return BaseAction.NOT_FOUND;
    }

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

    ResearchOutput output = outputService.getResearchOutputById(outputID);

    if (output != null) {

      ResearchOutcome outcome = output.getResearchOutcome();

      if (outcome != null) {
        programID = outcome.getResearchTopic().getResearchProgram().getId();

        ResearchProgram program = programService.getProgramById(programID);

        if (program != null) {

          areaID = program.getResearchArea().getId();

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
      } else {
        throw new Exception();
      }
    } else {
      throw new Exception();
    }

  }

}
