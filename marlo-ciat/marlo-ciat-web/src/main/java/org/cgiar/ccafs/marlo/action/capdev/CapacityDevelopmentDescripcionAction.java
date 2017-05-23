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

package org.cgiar.ccafs.marlo.action.capdev;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.CapacityDevelopment;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

public class CapacityDevelopmentDescripcionAction extends BaseAction {


  private static final long serialVersionUID = 1L;

  private int capDevID;
  private CapacityDevelopment capdev;
  private List<String> approaches = new ArrayList<>();
  private List<String> outcomes = new ArrayList<>();
  private List<String> types = new ArrayList<>();
  private List<String> deliverables = new ArrayList<>();
  private final CapacityDevelopmentAction capDevAction = new CapacityDevelopmentAction(config);


  @Inject
  public CapacityDevelopmentDescripcionAction(APConfig config) {
    super(config);
  }


  @Override
  public String cancel() {
    System.out.println("Se cancelo la operacion");
    return super.cancel();
  }


  public List<String> getApproaches() {
    return approaches;
  }


  public CapacityDevelopment getCapdev() {
    return capdev;
  }


  public int getCapDevID() {
    return capDevID;
  }


  public List<String> getDeliverables() {
    return deliverables;
  }


  public List<String> getOutcomes() {
    return outcomes;
  }


  public List<String> getTypes() {
    return types;
  }


  @Override
  public void prepare() throws Exception {

    approaches.add("Gender");
    approaches.add("Ecosystems");
    approaches.add("Climate change");
    approaches.add("Freedom");
    approaches.add("Food security");
    approaches.add("Best Practices");

    outcomes.add("outcome 1");
    outcomes.add("outcome 2");
    outcomes.add("outcome 3");
    outcomes.add("outcome 4");
    outcomes.add("outcome 5");


    types.add("Thesis");
    types.add("Publication");
    types.add("Ph Thesis");

    try {
      capDevID = Integer.parseInt(StringUtils.trim(this.getRequest().getParameter("capDevID")));
    } catch (final Exception e) {
      capDevID = -1;
    }

    if (capDevID > -1) {
      capdev = new CapacityDevelopment(1,
        "Health, biodiversity and natural resource use in the western amazon lowlands", "thesis");
    } else {
      capdev = new CapacityDevelopment();
    }


  }


  @Override
  public String save() {

    capdev.setId(8);
    this.getRequest().setAttribute("capDevID", capdev.getId());

    System.out.println("este es el titulo -->" + capdev.getTitle());
    System.out.println("este es el tipo -->" + capdev.getType());

    capDevAction.getCapDevs().add(capdev);

    return SUCCESS;
  }


  public void setApproaches(List<String> approaches) {
    this.approaches = approaches;
  }


  public void setCapdev(CapacityDevelopment capdev) {
    this.capdev = capdev;
  }


  public void setCapDevID(int capDevID) {
    this.capDevID = capDevID;
  }


  public void setDeliverables(List<String> deliverables) {
    this.deliverables = deliverables;
  }


  public void setOutcomes(List<String> outcomes) {
    this.outcomes = outcomes;
  }


  public void setTypes(List<String> types) {
    this.types = types;
  }


}
