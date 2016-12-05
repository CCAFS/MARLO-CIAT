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

package org.cgiar.ccafs.marlo.action.json.impactpathway.graph;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchImpact;
import org.cgiar.ccafs.marlo.data.model.ResearchImpactObjective;
import org.cgiar.ccafs.marlo.data.model.ResearchObjective;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.utils.APConstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.inject.Inject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 */
public class GraphByProgramAction extends BaseAction {


  private static final long serialVersionUID = -5892467002878824894L;
  // logger
  private static final Logger LOG = LoggerFactory.getLogger(GraphByProgramAction.class);

  // Managers -Services
  private IProgramService programService;

  // Parameters
  private long programID;

  // Return values
  private HashMap<String, Object> elements;

  @Inject
  public GraphByProgramAction(APConfig config, IProgramService programService) {
    super(config);
    this.programService = programService;
  }

  @Override
  public String execute() throws Exception {

    elements = new HashMap<>();
    List<HashMap<String, Object>> dataNodes = new ArrayList<HashMap<String, Object>>();
    List<HashMap<String, Object>> dataEdges = new ArrayList<HashMap<String, Object>>();


    ResearchProgram program = programService.getProgramById(programID);

    HashMap<String, Object> dataProgram = new HashMap<>();
    HashMap<String, Object> dataProgramDetail = new HashMap<>();
    dataProgramDetail.put("id", "P" + program.getId());
    dataProgramDetail.put("label", program.getAcronym());
    dataProgramDetail.put("description", program.getName());
    dataProgramDetail.put("color", program.getColor());
    dataProgramDetail.put("type", "P");
    dataProgram.put("data", dataProgram);

    dataNodes.add(dataProgram);


    ResearchArea area = program.getResearchArea();

    HashMap<String, Object> dataArea = new HashMap<>();
    HashMap<String, Object> dataAreaDetail = new HashMap<>();
    dataAreaDetail.put("id", "P" + area.getId());
    dataAreaDetail.put("label", area.getAcronym());
    dataAreaDetail.put("description", area.getName());
    // TODO dataAreaDetail.put("color", area.getColor());
    dataAreaDetail.put("type", "A");
    dataArea.put("data", dataProgram);


    List<ResearchImpact> impacts =
      new ArrayList<>(program.getResearchImpacts().stream().filter(ri -> ri.isActive()).collect(Collectors.toList()));

    List<ResearchTopic> topics =
      new ArrayList<>(program.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));

    List<ResearchObjective> objectives = new ArrayList<>();

    for (ResearchImpact impact : impacts) {

      List<ResearchImpactObjective> impactObjectives = new ArrayList<>(
        impact.getResearchImpactObjectives().stream().filter(io -> io.isActive()).collect(Collectors.toList()));

      for (ResearchImpactObjective researchImpactObjective : impactObjectives) {
        if (!objectives.contains(researchImpactObjective.getResearchObjective())) {
          objectives.add(researchImpactObjective.getResearchObjective());
        }
      }


    }


    return SUCCESS;
  }

  public HashMap<String, Object> getElements() {
    return elements;
  }

  @Override
  public void prepare() throws Exception {
    Map<String, Object> parameters = this.getParameters();

    try {
      programID = Long.parseLong(StringUtils.trim(((String[]) parameters.get(APConstants.CENTER_PROGRAM_ID))[0]));
    } catch (Exception e) {
      LOG.error("There was an exception trying to parse the crp program id = {} ",
        StringUtils.trim(((String[]) parameters.get(APConstants.CENTER_PROGRAM_ID))[0]));
    }
  }

  public void setElements(HashMap<String, Object> elements) {
    this.elements = elements;
  }

}
