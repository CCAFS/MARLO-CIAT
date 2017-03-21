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


import org.cgiar.ccafs.marlo.data.dao.IMonitoringOutcomeDAO;
import org.cgiar.ccafs.marlo.data.model.MonitoringOutcome;
import org.cgiar.ccafs.marlo.data.service.IMonitoringOutcomeService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class MonitoringOutcomeService implements IMonitoringOutcomeService {


  private IMonitoringOutcomeDAO monitoringOutcomeDAO;

  // Managers


  @Inject
  public MonitoringOutcomeService(IMonitoringOutcomeDAO monitoringOutcomeDAO) {
    this.monitoringOutcomeDAO = monitoringOutcomeDAO;


  }

  @Override
  public boolean deleteMonitoringOutcome(long monitoringOutcomeId) {

    return monitoringOutcomeDAO.deleteMonitoringOutcome(monitoringOutcomeId);
  }

  @Override
  public boolean existMonitoringOutcome(long monitoringOutcomeID) {

    return monitoringOutcomeDAO.existMonitoringOutcome(monitoringOutcomeID);
  }

  @Override
  public List<MonitoringOutcome> findAll() {

    return monitoringOutcomeDAO.findAll();

  }

  @Override
  public MonitoringOutcome getMonitoringOutcomeById(long monitoringOutcomeID) {

    return monitoringOutcomeDAO.find(monitoringOutcomeID);
  }

  @Override
  public List<MonitoringOutcome> getMonitoringOutcomesByUserId(Long userId) {
    return monitoringOutcomeDAO.getMonitoringOutcomesByUserId(userId);
  }

  @Override
  public long saveMonitoringOutcome(MonitoringOutcome monitoringOutcome) {

    return monitoringOutcomeDAO.save(monitoringOutcome);
  }


}
