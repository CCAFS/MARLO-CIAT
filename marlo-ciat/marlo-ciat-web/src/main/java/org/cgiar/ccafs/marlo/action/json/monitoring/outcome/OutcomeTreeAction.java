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

package org.cgiar.ccafs.marlo.action.json.monitoring.outcome;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.service.IResearchOutcomeService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.Map;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class OutcomeTreeAction extends BaseAction {

  private static final long serialVersionUID = 4433721734538258925L;

  private long outcomeID;
  private IResearchOutcomeService outcomeService;


  @Inject
  public OutcomeTreeAction(APConfig config, IResearchOutcomeService outcomeService) {
    super(config);
    this.outcomeService = outcomeService;
  }

  @Override
  public String execute() throws Exception {
    return SUCCESS;
  }

  @Override
  public void prepare() throws Exception {
    Map<String, Object> parameters = this.getParameters();
    outcomeID = Long.parseLong(StringUtils.trim(((String[]) parameters.get(APConstants.OUTCOME_ID))[0]));
  }

}
