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
import java.util.List;

import com.google.inject.Inject;

public class CapacityDevelopmentAction extends BaseAction {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String test;
  private List<CapacityDevelopment> capDevs = new ArrayList<CapacityDevelopment>();
  private List<String> lista = new ArrayList<>();

  @Inject
  public CapacityDevelopmentAction(APConfig config) {
    super(config);
    capDevs.add(new CapacityDevelopment(1, "capDev1", "thesis"));
    capDevs.add(new CapacityDevelopment(2, "capDev2", "Publication"));
    capDevs.add(new CapacityDevelopment(3, "capDev3", "Report"));

  }


  @Override
  public String execute() throws Exception {
    return SUCCESS;
  }


  public List<CapacityDevelopment> getCapDevs() {
    return capDevs;
  }


  public List<String> getLista() {
    return lista;
  }


  public String getTest() {
    return test;
  }


  public String list() {
    return SUCCESS;
  }


  public void setCapDevs(List<CapacityDevelopment> capDevs) {
    this.capDevs = capDevs;
  }


  public void setLista(List<String> lista) {
    this.lista = lista;
  }


  public void setTest(String test) {
    this.test = test;
  }


}
