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


import org.cgiar.ccafs.marlo.data.dao.INextuserTypeDAO;
import org.cgiar.ccafs.marlo.data.model.NextuserType;
import org.cgiar.ccafs.marlo.data.service.INextuserTypeService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class NextuserTypeService implements INextuserTypeService {


  private INextuserTypeDAO nextuserTypeDAO;

  // Managers


  @Inject
  public NextuserTypeService(INextuserTypeDAO nextuserTypeDAO) {
    this.nextuserTypeDAO = nextuserTypeDAO;


  }

  @Override
  public boolean deleteNextuserType(long nextuserTypeId) {

    return nextuserTypeDAO.deleteNextuserType(nextuserTypeId);
  }

  @Override
  public boolean existNextuserType(long nextuserTypeID) {

    return nextuserTypeDAO.existNextuserType(nextuserTypeID);
  }

  @Override
  public List<NextuserType> findAll() {

    return nextuserTypeDAO.findAll();

  }

  @Override
  public NextuserType getNextuserTypeById(long nextuserTypeID) {

    return nextuserTypeDAO.find(nextuserTypeID);
  }

  @Override
  public List<NextuserType> getNextuserTypesByUserId(Long userId) {
    return nextuserTypeDAO.getNextuserTypesByUserId(userId);
  }

  @Override
  public long saveNextuserType(NextuserType nextuserType) {

    return nextuserTypeDAO.save(nextuserType);
  }


}
