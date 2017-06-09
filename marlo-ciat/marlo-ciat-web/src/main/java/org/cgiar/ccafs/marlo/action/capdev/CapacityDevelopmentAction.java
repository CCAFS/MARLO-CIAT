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
import org.cgiar.ccafs.marlo.data.service.ICapacityDevelopmentService;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

public class CapacityDevelopmentAction extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private CapacityDevelopment capdev;

  private List<CapacityDevelopment> capDevs = new ArrayList<CapacityDevelopment>();

  private final ICapacityDevelopmentService capdevService;

  private int capDevID;
  private String capdevCategory;

  @Inject
  public CapacityDevelopmentAction(APConfig config, ICapacityDevelopmentService capdevService) {
    super(config);
    this.capdevService = capdevService;

  }


  @Override
  public String add() {

    System.out.println("esta es la categoria del CapacityDevelopmentAction-->" + capdevCategory);
    return SUCCESS;
  }


  public CapacityDevelopment getCapdev() {
    return capdev;
  }


  public String getCapdevCategory() {
    return capdevCategory;
  }


  public int getCapDevID() {
    return capDevID;
  }


  public List<CapacityDevelopment> getCapDevs() {
    return capDevs;
  }


  public String list() {
    return SUCCESS;
  }


  @Override
  public void prepare() throws Exception {
    capDevs = capdevService.findAll();

  }


  @Override
  public String save() {
    return super.save();
  }


  public void setCapdev(CapacityDevelopment capdev) {
    this.capdev = capdev;
  }


  public void setCapdevCategory(String capdevCategory) {
    this.capdevCategory = capdevCategory;
  }


  public void setCapDevID(int capDevID) {
    this.capDevID = capDevID;
  }


  public void setCapDevs(List<CapacityDevelopment> capDevs) {
    this.capDevs = capDevs;
  }


}
