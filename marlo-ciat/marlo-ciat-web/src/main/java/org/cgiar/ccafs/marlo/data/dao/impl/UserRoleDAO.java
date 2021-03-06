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


package org.cgiar.ccafs.marlo.data.dao.impl;

import org.cgiar.ccafs.marlo.data.dao.IUserRoleDAO;
import org.cgiar.ccafs.marlo.data.model.UserRole;

import java.util.List;

import com.google.inject.Inject;

public class UserRoleDAO implements IUserRoleDAO {

  private StandardDAO dao;

  @Inject
  public UserRoleDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteUserRole(long userRoleId) {
    UserRole userRole = this.find(userRoleId);
    return dao.delete(userRole);
  }

  @Override
  public boolean existUserRole(long userRoleID) {
    UserRole userRole = this.find(userRoleID);
    if (userRole == null) {
      return false;
    }
    return true;

  }

  @Override
  public UserRole find(long id) {
    return dao.find(UserRole.class, id);

  }

  @Override
  public List<UserRole> findAll() {
    String query = "from " + UserRole.class.getName();
    List<UserRole> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<UserRole> getUserRolesByUserId(long userId) {
    String query = "from " + UserRole.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(UserRole userRole) {
    if (userRole.getId() == null) {
      dao.save(userRole);
    } else {
      dao.update(userRole);
    }
    return userRole.getId();
  }


}