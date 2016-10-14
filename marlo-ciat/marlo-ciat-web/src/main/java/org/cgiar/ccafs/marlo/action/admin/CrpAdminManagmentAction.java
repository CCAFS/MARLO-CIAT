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


package org.cgiar.ccafs.marlo.action.admin;


import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.Parameter;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.Role;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IParameterService;
import org.cgiar.ccafs.marlo.data.service.IRoleService;
import org.cgiar.ccafs.marlo.data.service.IUserRoleService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.utils.SendMail;

import java.util.List;

import com.google.inject.Inject;

public class CrpAdminManagmentAction extends BaseAction {

  private static final long serialVersionUID = -5308160641176721169L;
  // Managers
  private IRoleService roleManager;
  private IUserRoleService userRoleManager;
  // private CrpProgramManager crpProgramManager;
  private ICenterService crpManager;
  private IParameterService crpParameterManager;
  // Variables
  private ResearchCenter loggedCrp;
  private Role rolePmu;
  private long pmuRol;

  // private List<CrpProgram> flagshipsPrograms;

  private List<Parameter> parameters;
  // private CrpProgramLeaderManager crpProgramLeaderManager;
  private IUserService userManager;
  private Role fplRole;

  // Util
  private SendMail sendMail;

  @Inject
  public CrpAdminManagmentAction(APConfig config, IRoleService roleManager, IUserRoleService userRoleManager,
    ICenterService crpManager, IParameterService crpParameterManager, IUserService userManager, SendMail sendMail) {
    super(config);
    this.roleManager = roleManager;
    this.userRoleManager = userRoleManager;
    this.crpManager = crpManager;
    this.crpParameterManager = crpParameterManager;
    this.userManager = userManager;
    this.sendMail = sendMail;
  }


  public Role getFplRole() {
    return fplRole;
  }


  public ResearchCenter getLoggedCrp() {
    return loggedCrp;
  }


  public long getPmuRol() {
    return pmuRol;
  }


  public Role getRolePmu() {
    return rolePmu;
  }

  /**
   * This method notify the user that is been assigned as Program Leader for an specific Regional Program
   * 
   * @param userAssigned is the user been assigned
   * @param role is the role(Program Management)
   */
  private void notifyRoleProgramManagementAssigned(User userAssigned, Role role) {

    String managementRole = this.getText("programManagement.role");
    String managementRoleAcronym = this.getText("programManagement.role.acronym");

    userAssigned = userManager.getUser(userAssigned.getId());
    StringBuilder message = new StringBuilder();
    // Building the Email message:
    message.append(this.getText("email.dear", new String[] {userAssigned.getFirstName()}));
    message
      .append(this.getText("email.programManagement.assigned", new String[] {managementRole, loggedCrp.getName()}));
    message.append(this.getText("email.support"));
    message.append(this.getText("email.bye"));

    String toEmail = null;
    String ccEmail = null;
    if (config.isProduction()) {
      // Send email to the new user and the P&R notification email.
      // TO
      toEmail = userAssigned.getEmail();
      // CC will be the user who is making the modification.
      if (this.getCurrentUser() != null) {
        ccEmail = this.getCurrentUser().getEmail();
      }
    }
    // BBC will be our gmail notification email.
    String bbcEmails = this.config.getEmailNotification();
    sendMail.send(toEmail, ccEmail, bbcEmails, this.getText("email.programManagement.assigned.subject", new String[] {
      loggedCrp.getName(), managementRoleAcronym}), message.toString(), null, null, null, true);

  }

  /**
   * This method notify the user that is been assigned as Program Leader for an specific Regional Program
   * 
   * @param userAssigned is the user been assigned
   * @param role is the role(Program Management)
   */
  private void notifyRoleProgramManagementUnassigned(User userAssigned, Role role) {
    String managementRole = this.getText("programManagement.role");
    String managementRoleAcronym = this.getText("programManagement.role.acronym");

    userAssigned = userManager.getUser(userAssigned.getId());
    StringBuilder message = new StringBuilder();
    // Building the Email message:
    message.append(this.getText("email.dear", new String[] {userAssigned.getFirstName()}));
    message.append(this.getText("email.programManagement.unassigned",
      new String[] {managementRole, loggedCrp.getName()}));
    message.append(this.getText("email.support"));
    message.append(this.getText("email.bye"));

    String toEmail = null;
    String ccEmail = null;
    if (config.isProduction()) {
      // Send email to the new user and the P&R notification email.
      // TO
      toEmail = userAssigned.getEmail();
      // CC will be the user who is making the modification.
      if (this.getCurrentUser() != null) {
        ccEmail = this.getCurrentUser().getEmail();
      }
    }
    // BBC will be our gmail notification email.
    String bbcEmails = this.config.getEmailNotification();
    sendMail.send(
      toEmail,
      ccEmail,
      bbcEmails,
      this.getText("email.programManagement.unassigned.subject", new String[] {loggedCrp.getName(),
        managementRoleAcronym}), message.toString(), null, null, null, true);


  }


  @Override
  public void prepare() throws Exception {

    // Get the Users list that have the pmu role in this crp.
    loggedCrp = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCrp = crpManager.getCrpById(loggedCrp.getId());

    pmuRol = Long.parseLong((String) this.getSession().get(APConstants.CRP_PMU_ROLE));


    // parameters =
    // loggedCrp
    // .getCrpParameters()
    // .stream()
    // .filter(
    // c -> c.getKey().equals(APConstants.CRP_HAS_REGIONS) && c.isActive()
    // && c.getCrp().getId().equals(loggedCrp.getId())).collect(Collectors.toList());
    // if (parameters.size() == 0) {
    // loggedCrp.setHasRegions(false);
    // } else {
    // boolean param = Boolean.parseBoolean(parameters.get(0).getValue());
    // loggedCrp.setHasRegions(param);
    // }


  }


  public void setFplRole(Role fplRole) {
    this.fplRole = fplRole;
  }

  public void setLoggedCrp(ResearchCenter loggedCrp) {
    this.loggedCrp = loggedCrp;
  }

  public void setPmuRol(long pmuRol) {
    this.pmuRol = pmuRol;
  }


  public void setRolePmu(Role rolePmu) {
    this.rolePmu = rolePmu;
  }


}
