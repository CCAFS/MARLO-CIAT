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

import org.cgiar.ccafs.marlo.data.dao.IMonitoringOutcomeDAO;
import org.cgiar.ccafs.marlo.data.model.MonitoringOutcome;

import java.util.List;

import com.google.inject.Inject;

public class MonitoringOutcomeDAO implements IMonitoringOutcomeDAO {

  private StandardDAO dao;

  @Inject
  public MonitoringOutcomeDAO(StandardDAO dao) {
    this.dao = dao;
  }

  @Override
  public boolean deleteMonitoringOutcome(long monitoringOutcomeId) {
    MonitoringOutcome monitoringOutcome = this.find(monitoringOutcomeId);
    monitoringOutcome.setActive(false);
    return this.save(monitoringOutcome) > 0;
  }

  @Override
  public boolean existMonitoringOutcome(long monitoringOutcomeID) {
    MonitoringOutcome monitoringOutcome = this.find(monitoringOutcomeID);
    if (monitoringOutcome == null) {
      return false;
    }
    return true;

  }

  @Override
  public MonitoringOutcome find(long id) {
    return dao.find(MonitoringOutcome.class, id);

  }

  @Override
  public List<MonitoringOutcome> findAll() {
    String query = "from " + MonitoringOutcome.class.getName();
    List<MonitoringOutcome> list = dao.findAll(query);
    if (list.size() > 0) {
      return list;
    }
    return null;

  }

  @Override
  public List<MonitoringOutcome> getMonitoringOutcomesByUserId(long userId) {
    String query = "from " + MonitoringOutcome.class.getName() + " where user_id=" + userId;
    return dao.findAll(query);
  }

  @Override
  public long save(MonitoringOutcome monitoringOutcome) {
    if (monitoringOutcome.getId() == null) {
      dao.save(monitoringOutcome);
    } else {
      dao.update(monitoringOutcome);
    }
    return monitoringOutcome.getId();
  }


}