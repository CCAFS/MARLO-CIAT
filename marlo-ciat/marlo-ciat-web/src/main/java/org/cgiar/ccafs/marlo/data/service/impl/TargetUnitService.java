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


import org.cgiar.ccafs.marlo.data.dao.ITargetUnitDAO;
import org.cgiar.ccafs.marlo.data.model.TargetUnit;
import org.cgiar.ccafs.marlo.data.service.ITargetUnitService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class TargetUnitService implements ITargetUnitService {


  private ITargetUnitDAO targetUnitDAO;

  // Managers


  @Inject
  public TargetUnitService(ITargetUnitDAO targetUnitDAO) {
    this.targetUnitDAO = targetUnitDAO;


  }

  @Override
  public boolean deleteTargetUnit(long targetUnitId) {

    return targetUnitDAO.deleteTargetUnit(targetUnitId);
  }

  @Override
  public boolean existTargetUnit(long targetUnitID) {

    return targetUnitDAO.existTargetUnit(targetUnitID);
  }

  @Override
  public List<TargetUnit> findAll() {

    return targetUnitDAO.findAll();

  }

  @Override
  public TargetUnit getTargetUnitById(long targetUnitID) {

    return targetUnitDAO.find(targetUnitID);
  }

  @Override
  public List<TargetUnit> getTargetUnitsByUserId(Long userId) {
    return targetUnitDAO.getTargetUnitsByUserId(userId);
  }

  @Override
  public long saveTargetUnit(TargetUnit targetUnit) {

    return targetUnitDAO.save(targetUnit);
  }


}
