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


import org.cgiar.ccafs.marlo.data.dao.IMonitoringMilestoneDAO;
import org.cgiar.ccafs.marlo.data.model.MonitoringMilestone;
import org.cgiar.ccafs.marlo.data.service.IMonitoringMilestoneService;

import java.util.List;

import com.google.inject.Inject;

/**
 * @author Christian Garcia
 */
public class MonitoringMilestoneService implements IMonitoringMilestoneService {


  private IMonitoringMilestoneDAO monitoringMilestoneDAO;

  // Managers


  @Inject
  public MonitoringMilestoneService(IMonitoringMilestoneDAO monitoringMilestoneDAO) {
    this.monitoringMilestoneDAO = monitoringMilestoneDAO;


  }

  @Override
  public boolean deleteMonitoringMilestone(long monitoringMilestoneId) {

    return monitoringMilestoneDAO.deleteMonitoringMilestone(monitoringMilestoneId);
  }

  @Override
  public boolean existMonitoringMilestone(long monitoringMilestoneID) {

    return monitoringMilestoneDAO.existMonitoringMilestone(monitoringMilestoneID);
  }

  @Override
  public List<MonitoringMilestone> findAll() {

    return monitoringMilestoneDAO.findAll();

  }

  @Override
  public MonitoringMilestone getMonitoringMilestoneById(long monitoringMilestoneID) {

    return monitoringMilestoneDAO.find(monitoringMilestoneID);
  }

  @Override
  public List<MonitoringMilestone> getMonitoringMilestonesByUserId(Long userId) {
    return monitoringMilestoneDAO.getMonitoringMilestonesByUserId(userId);
  }

  @Override
  public long saveMonitoringMilestone(MonitoringMilestone monitoringMilestone) {

    return monitoringMilestoneDAO.save(monitoringMilestone);
  }


}
