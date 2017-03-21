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

import org.cgiar.ccafs.marlo.data.dao.IMonitoringMilestoneDAO;
import org.cgiar.ccafs.marlo.data.model.MonitoringMilestone;

import java.util.List;

import com.google.inject.Inject;

public class MonitoringMilestoneDAO implements IMonitoringMilestoneDAO {

  private StandardDAO dao;

  @Inject
  public MonitoringMilestoneDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteMonitoringMilestone(long monitoringMilestoneId) {
    MonitoringMilestone monitoringMilestone = this.find(monitoringMilestoneId);
    monitoringMilestone.setActive(false);
    return this.save(monitoringMilestone) > 0;
  }

  @Override
  public boolean existMonitoringMilestone(long monitoringMilestoneID) {
    MonitoringMilestone monitoringMilestone = this.find(monitoringMilestoneID);
    if (monitoringMilestone == null) {
      return false;
    }
    return true;

  }

  @Override
  public MonitoringMilestone find(long id) {
    return dao.find(MonitoringMilestone.class, id);

  }

  @Override
  public List<MonitoringMilestone> findAll() {
    String query = "from " + MonitoringMilestone.class.getName();
    List<MonitoringMilestone> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<MonitoringMilestone> getMonitoringMilestonesByUserId(long userId) {
    String query = "from " + MonitoringMilestone.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(MonitoringMilestone monitoringMilestone) {
    if (monitoringMilestone.getId() == null) {
      dao.save(monitoringMilestone);
    } else {
      dao.update(monitoringMilestone);
    }
    return monitoringMilestone.getId();
  }


}