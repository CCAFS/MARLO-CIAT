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
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchOutput;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.model.ResearchTopic;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchObjectiveService;
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
public class GraphByAreaAction extends BaseAction {


  private static final long serialVersionUID = -5892467002878824894L;
  // logger
  private static final Logger LOG = LoggerFactory.getLogger(GraphByProgramAction.class);

  // Managers -Services
  private IProgramService programService;
  private IResearchObjectiveService objectiveService;

  // Parameters
  private long programID;

  // Return values
  private HashMap<String, Object> elements;

  @Inject
  public GraphByAreaAction(APConfig config, IProgramService programService,
    IResearchObjectiveService objectiveService) {
    super(config);
    this.programService = programService;
    this.objectiveService = objectiveService;
  }

  @Override
  public String execute() throws Exception {

    elements = new HashMap<>();
    List<HashMap<String, Object>> dataNodes = new ArrayList<HashMap<String, Object>>();
    List<HashMap<String, Object>> dataEdges = new ArrayList<HashMap<String, Object>>();


    ResearchProgram researchProgram = programService.getProgramById(programID);
    ResearchArea area = researchProgram.getResearchArea();

    // Research Area Data
    HashMap<String, Object> dataArea = new HashMap<>();
    HashMap<String, Object> dataAreaDetail = new HashMap<>();
    dataAreaDetail.put("id", "A" + area.getId());
    dataAreaDetail.put("label", area.getAcronym());
    dataAreaDetail.put("description", area.getName());
    dataAreaDetail.put("color", area.getColor());
    dataAreaDetail.put("type", "A");
    dataArea.put("data", dataAreaDetail);
    dataNodes.add(dataArea);

    // int i = 1;

    List<ResearchProgram> programs =
      new ArrayList<>(area.getResearchPrograms().stream().filter(rp -> rp.isActive()).collect(Collectors.toList()));

    for (ResearchProgram program : programs) {

      // Research Program Data
      HashMap<String, Object> dataProgram = new HashMap<>();
      HashMap<String, Object> dataProgramDetail = new HashMap<>();
      dataProgramDetail.put("id", "P" + program.getId());
      dataProgramDetail.put("parent", "A" + program.getResearchArea().getId());
      dataProgramDetail.put("label", program.getAcronym());
      dataProgramDetail.put("description", program.getName());
      dataProgramDetail.put("color", program.getColor());
      dataProgramDetail.put("type", "P");
      dataProgram.put("data", dataProgramDetail);
      dataNodes.add(dataProgram);

      List<ResearchObjective> objectives = new ArrayList<>();
      if (objectiveService.findAll() != null) {
        objectives = objectiveService.findAll().stream().filter(o -> o.isActive()).collect(Collectors.toList());
      }


      List<ResearchImpact> impacts =
        new ArrayList<>(program.getResearchImpacts().stream().filter(ri -> ri.isActive()).collect(Collectors.toList()));

      List<ResearchTopic> topics =
        new ArrayList<>(program.getResearchTopics().stream().filter(rt -> rt.isActive()).collect(Collectors.toList()));


      int z = 1;

      for (ResearchTopic topic : topics) {
        // Research Topic Data
        HashMap<String, Object> dataTopic = new HashMap<>();
        HashMap<String, Object> dataTopicDetail = new HashMap<>();
        dataTopicDetail.put("id", "T" + topic.getId());
        dataTopicDetail.put("parent", "P" + topic.getResearchProgram().getId());
        dataTopicDetail.put("label", "Research Topic " + z);
        dataTopicDetail.put("description", topic.getResearchTopic());
        dataTopicDetail.put("color", topic.getColor());
        dataTopicDetail.put("type", "T");
        dataTopic.put("data", dataTopicDetail);
        dataNodes.add(dataTopic);

        z++;
      }

      z = 1;

      for (ResearchImpact impact : impacts) {
        // Research Impact Data
        HashMap<String, Object> dataImpact = new HashMap<>();
        HashMap<String, Object> dataImpactDetail = new HashMap<>();
        dataImpactDetail.put("id", "I" + impact.getId());
        dataImpactDetail.put("parent", "P" + impact.getResearchProgram().getId());
        dataImpactDetail.put("label", "Program Impact " + z);
        dataImpactDetail.put("description", impact.getDescription());
        dataImpactDetail.put("color", impact.getColor());
        dataImpactDetail.put("type", "I");
        dataImpact.put("data", dataImpactDetail);
        dataNodes.add(dataImpact);

        List<ResearchImpactObjective> impactObjectives = new ArrayList<>(
          impact.getResearchImpactObjectives().stream().filter(io -> io.isActive()).collect(Collectors.toList()));

        for (ResearchImpactObjective researchImpactObjective : impactObjectives) {
          if (!objectives.contains(researchImpactObjective.getResearchObjective())) {
            objectives.add(researchImpactObjective.getResearchObjective());
          }
        }

        int j = 1;
        List<ResearchOutcome> outcomes = new ArrayList<>(
          impact.getResearchOutcomes().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));

        for (ResearchOutcome outcome : outcomes) {
          // Research Outcome Data
          HashMap<String, Object> dataOutcome = new HashMap<>();
          HashMap<String, Object> dataOutcomeDetail = new HashMap<>();
          dataOutcomeDetail.put("id", "OC" + outcome.getId());
          dataOutcomeDetail.put("parent", "T" + outcome.getResearchTopic().getId());
          dataOutcomeDetail.put("label", "Outcome " + j);
          dataOutcomeDetail.put("description", outcome.getDescription());
          dataOutcomeDetail.put("color", outcome.getResearchImpact().getColor());
          dataOutcomeDetail.put("type", "OC");
          dataOutcome.put("data", dataOutcomeDetail);
          dataNodes.add(dataOutcome);
          // Relation Program Impact - Outcome
          HashMap<String, Object> dataEdgeOutcome = new HashMap<>();
          HashMap<String, Object> dataEdgeOutcomeDetail = new HashMap<>();
          dataEdgeOutcomeDetail.put("source", "I" + outcome.getResearchImpact().getId());
          dataEdgeOutcomeDetail.put("target", "OC" + outcome.getId());
          dataEdgeOutcome.put("data", dataEdgeOutcomeDetail);
          dataEdges.add(dataEdgeOutcome);

          List<ResearchOutput> outputs = new ArrayList<>(
            outcome.getResearchOutputs().stream().filter(ro -> ro.isActive()).collect(Collectors.toList()));

          int k = 1;
          for (ResearchOutput output : outputs) {
            // Research Output Data
            HashMap<String, Object> dataOutput = new HashMap<>();
            HashMap<String, Object> dataOutputDetail = new HashMap<>();
            dataOutputDetail.put("id", "OP" + output.getId());
            dataOutputDetail.put("parent", "T" + output.getResearchOutcome().getResearchTopic().getId());
            dataOutputDetail.put("label", "Output " + k);
            dataOutputDetail.put("description", output.getTitle());
            dataOutputDetail.put("color", output.getResearchOutcome().getResearchImpact().getColor());
            dataOutputDetail.put("type", "OP");
            dataOutput.put("data", dataOutputDetail);
            dataNodes.add(dataOutput);
            // Relation Outcome - Output
            HashMap<String, Object> dataEdgeOutput = new HashMap<>();
            HashMap<String, Object> dataEdgeOutputDetail = new HashMap<>();
            dataEdgeOutputDetail.put("source", "OC" + output.getResearchOutcome().getId());
            dataEdgeOutputDetail.put("target", "OP" + output.getId());
            dataEdgeOutput.put("data", dataEdgeOutputDetail);
            dataEdges.add(dataEdgeOutput);

            k++;
          }

          j++;

        }

        z++;
      }

      z = 1;
      for (ResearchObjective researchObjective : objectives) {
        // Strategic Objective Data
        HashMap<String, Object> dataObjective = new HashMap<>();
        HashMap<String, Object> dataObjectiveDetail = new HashMap<>();
        dataObjectiveDetail.put("id", "SO" + researchObjective.getId());
        dataObjectiveDetail.put("label", "Strategic Objective " + z);
        dataObjectiveDetail.put("description", researchObjective.getObjective());
        dataObjectiveDetail.put("color", "#FFFFFF");
        dataObjectiveDetail.put("type", "SO");
        dataObjective.put("data", dataObjectiveDetail);
        dataNodes.add(dataObjective);

        List<ResearchImpactObjective> impactObjectives = new ArrayList<>(researchObjective.getResearchImpactObjectives()
          .stream().filter(io -> io.isActive()).collect(Collectors.toList()));

        for (ResearchImpactObjective impactObjective : impactObjectives) {
          // Relation S Objective - Program Impact
          HashMap<String, Object> dataEdgeImpact = new HashMap<>();
          HashMap<String, Object> dataEdgeImpactDetail = new HashMap<>();
          dataEdgeImpactDetail.put("source", "SO" + impactObjective.getResearchImpact().getId());
          dataEdgeImpactDetail.put("target", "I" + impactObjective.getResearchObjective().getId());
          dataEdgeImpact.put("data", dataEdgeImpactDetail);
          dataEdges.add(dataEdgeImpact);
        }


        z++;
      }


    }


    elements.put("nodes", dataNodes);
    elements.put("edges", dataEdges);

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
