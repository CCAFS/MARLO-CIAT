/*****************************************************************
 * This file is part of Managing Agricultural Research for Learning &
 * Outcomes Platform (MiLE).
 * MiLE is free software: you can redistribute it and/or modify
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

package org.cgiar.ccafs.marlo.action.capdev;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.CapacityDevelopment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.inject.Inject;

public class CapacityDevelopmentAction extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private CapacityDevelopment capdev;

  private List<CapacityDevelopment> capDevs = new ArrayList<CapacityDevelopment>();

  @Inject
  public CapacityDevelopmentAction(APConfig config) {
    super(config);

  }


  @Override
  public String add() {

    return SUCCESS;
  }


  public CapacityDevelopment getCapdev() {
    return capdev;
  }


  public List<CapacityDevelopment> getCapDevs() {
    return capDevs;
  }


  public String list() {
    return SUCCESS;
  }


  @Override
  public void prepare() throws Exception {
    final Date initiation = new Date();
    final Date termination = new Date();


    capDevs
      .add(new CapacityDevelopment(1, "Health, biodiversity and natural resource use in the western amazon lowlands",
        "thesis", initiation, termination, ""));
    capDevs.add(new CapacityDevelopment(2, "Caracterización morfológica y citogenética de las pasifloras andinas",
      "Publication", initiation, termination, ""));
    capDevs.add(new CapacityDevelopment(3, "Mathematical modeling relevant to the whitefly ipm project", "Report",
      initiation, termination, "Simone"));
    capDevs.add(new CapacityDevelopment(4, "Mathematical modeling relevant to the whitefly ipm project", "Report",
      initiation, termination, "Simone"));
  }


  @Override
  public String save() {
    System.out.println("este es el title:" + capdev.getTitle());
    return super.save();
  }


  public void setCapdev(CapacityDevelopment capdev) {
    this.capdev = capdev;
  }


  public void setCapDevs(List<CapacityDevelopment> capDevs) {
    this.capDevs = capDevs;
  }


}
