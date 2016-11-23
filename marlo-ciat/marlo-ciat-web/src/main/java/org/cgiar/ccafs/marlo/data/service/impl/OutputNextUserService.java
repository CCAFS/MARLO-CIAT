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


import org.cgiar.ccafs.marlo.data.dao.IOutputNextUserDAO;
import org.cgiar.ccafs.marlo.data.model.OutputNextUser;
import org.cgiar.ccafs.marlo.data.service.IOutputNextUserService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class OutputNextUserService implements IOutputNextUserService {


  private IOutputNextUserDAO outputNextUserDAO;

  // Managers


  @Inject
  public OutputNextUserService(IOutputNextUserDAO outputNextUserDAO) {
    this.outputNextUserDAO = outputNextUserDAO;


  }

  @Override
  public boolean deleteOutputNextUser(long outputNextUserId) {

    return outputNextUserDAO.deleteOutputNextUser(outputNextUserId);
  }

  @Override
  public boolean existOutputNextUser(long outputNextUserID) {

    return outputNextUserDAO.existOutputNextUser(outputNextUserID);
  }

  @Override
  public List<OutputNextUser> findAll() {

    return outputNextUserDAO.findAll();

  }

  @Override
  public OutputNextUser getOutputNextUserById(long outputNextUserID) {

    return outputNextUserDAO.find(outputNextUserID);
  }

  @Override
  public List<OutputNextUser> getOutputNextUsersByUserId(Long userId) {
    return outputNextUserDAO.getOutputNextUsersByUserId(userId);
  }

  @Override
  public long saveOutputNextUser(OutputNextUser outputNextUser) {

    return outputNextUserDAO.save(outputNextUser);
  }


}
