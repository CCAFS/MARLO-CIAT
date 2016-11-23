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
package org.cgiar.ccafs.marlo.data.service.impl;


import org.cgiar.ccafs.marlo.data.dao.INextUserTypeDAO;
import org.cgiar.ccafs.marlo.data.model.OutputNextUser;
import org.cgiar.ccafs.marlo.data.service.INextUserTypeService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class NextUserTypeService implements INextUserTypeService {


  private INextUserTypeDAO nextUserTypeDAO;

  // Managers


  @Inject
  public NextUserTypeService(INextUserTypeDAO nextUserTypeDAO) {
    this.nextUserTypeDAO = nextUserTypeDAO;


  }

  @Override
  public boolean deleteNextUserType(long nextUserTypeId) {

    return nextUserTypeDAO.deleteNextUserType(nextUserTypeId);
  }

  @Override
  public boolean existNextUserType(long nextUserTypeID) {

    return nextUserTypeDAO.existNextUserType(nextUserTypeID);
  }

  @Override
  public List<OutputNextUser> findAll() {

    return nextUserTypeDAO.findAll();

  }

  @Override
  public OutputNextUser getNextUserTypeById(long nextUserTypeID) {

    return nextUserTypeDAO.find(nextUserTypeID);
  }

  @Override
  public List<OutputNextUser> getNextUserTypesByUserId(Long userId) {
    return nextUserTypeDAO.getNextUserTypesByUserId(userId);
  }

  @Override
  public long saveNextUserType(OutputNextUser nextUserType) {

    return nextUserTypeDAO.save(nextUserType);
  }


}
