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
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.Role;
import org.cgiar.ccafs.marlo.data.model.User;
import org.cgiar.ccafs.marlo.data.model.UserRole;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IRoleService;
import org.cgiar.ccafs.marlo.data.service.IUserRoleService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class ProgramCoordinatorAction extends BaseAction {

  private static final long serialVersionUID = 1843943617459402461L;

  private IUserService userService;
  private IUserRoleService userRoleService;
  private IRoleService roleService;
  private ICenterService centerService;
  private ResearchCenter loggedCenter;
  private List<UserRole> userRoles;

  @Inject
  public ProgramCoordinatorAction(APConfig config, IUserService userService, ICenterService centerService,
    IRoleService roleService, IUserRoleService userRoleService) {
    super(config);
    this.userService = userService;
    this.roleService = roleService;
    this.centerService = centerService;
  }

  public ResearchCenter getLoggedCenter() {
    return loggedCenter;
  }

  public List<UserRole> getUserRoles() {
    return userRoles;
  }

  @Override
  public void prepare() throws Exception {

    loggedCenter = (ResearchCenter) this.getSession().get(APConstants.SESSION_CENTER);
    loggedCenter = centerService.getCrpById(loggedCenter.getId());

    System.out.println("ROLE ----------- " + this.getSession().get(APConstants.CENTER_COORD_ROLE).toString());
    long coorRoleId = Long.parseLong(this.getSession().get(APConstants.CENTER_COORD_ROLE).toString());

    Role role = roleService.getRoleById(coorRoleId);

    userRoles = new ArrayList<>(role.getUserRoles());

    String params[] = {loggedCenter.getAcronym() + ""};
    this.setBasePermission(this.getText(Permission.CENTER_ADMIN_BASE_PERMISSION, params));

    if (this.isHttpPost()) {
      if (userRoles != null) {
        userRoles.clear();
      }
    }


  }

  @Override
  public String save() {
    if (this.hasPermission("*")) {

      long coorRoleId = Long.parseLong(this.getParameterValue(APConstants.CENTER_COORD_ROLE));
      Role role = roleService.getRoleById(coorRoleId);
      List<UserRole> userRolesDB = new ArrayList<>(
        userRoleService.findAll().stream().filter(ur -> ur.getRole().equals(role)).collect(Collectors.toList()));

      for (UserRole userRole : userRolesDB) {
        if (!userRoles.contains(userRole)) {
          userRoleService.deleteUserRole(userRole.getId());
        }
      }

      for (UserRole userRole : userRoles) {
        if (userRole.getId() == null || userRole.getId() == -1) {
          UserRole userRolenew = new UserRole();

          Role roles = roleService.getRoleById(userRole.getRole().getId());
          userRole.setRole(roles);

          User user = userService.getUser(userRole.getUser().getId());
          userRole.setUser(user);

          userRoleService.saveUserRole(userRolenew);
        }
      }

      return SUCCESS;
    } else {
      return NOT_AUTHORIZED;
    }
  }

  public void setLoggedCenter(ResearchCenter loggedCenter) {
    this.loggedCenter = loggedCenter;
  }

  public void setUserRoles(List<UserRole> userRoles) {
    this.userRoles = userRoles;
  }


}
