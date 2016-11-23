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


import org.cgiar.ccafs.marlo.data.dao.IOutputNextSubUserDAO;
import org.cgiar.ccafs.marlo.data.model.OutputNextSubUser;
import org.cgiar.ccafs.marlo.data.service.IOutputNextSubUserService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class OutputNextSubUserService implements IOutputNextSubUserService {


  private IOutputNextSubUserDAO outputNextSubUserDAO;

  // Managers


  @Inject
  public OutputNextSubUserService(IOutputNextSubUserDAO outputNextSubUserDAO) {
    this.outputNextSubUserDAO = outputNextSubUserDAO;


  }

  @Override
  public boolean deleteOutputNextSubUser(long outputNextSubUserId) {

    return outputNextSubUserDAO.deleteOutputNextSubUser(outputNextSubUserId);
  }

  @Override
  public boolean existOutputNextSubUser(long outputNextSubUserID) {

    return outputNextSubUserDAO.existOutputNextSubUser(outputNextSubUserID);
  }

  @Override
  public List<OutputNextSubUser> findAll() {

    return outputNextSubUserDAO.findAll();

  }

  @Override
  public OutputNextSubUser getOutputNextSubUserById(long outputNextSubUserID) {

    return outputNextSubUserDAO.find(outputNextSubUserID);
  }

  @Override
  public List<OutputNextSubUser> getOutputNextSubUsersByUserId(Long userId) {
    return outputNextSubUserDAO.getOutputNextSubUsersByUserId(userId);
  }

  @Override
  public long saveOutputNextSubUser(OutputNextSubUser outputNextSubUser) {

    return outputNextSubUserDAO.save(outputNextSubUser);
  }


}
