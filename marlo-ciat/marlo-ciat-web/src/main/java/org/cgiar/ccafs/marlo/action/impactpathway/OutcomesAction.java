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

package org.cgiar.ccafs.marlo.action.impactpathway;

import org.cgiar.ccafs.marlo.action.BaseAction;
import org.cgiar.ccafs.marlo.config.APConfig;
import org.cgiar.ccafs.marlo.data.model.ResearchArea;
import org.cgiar.ccafs.marlo.data.model.ResearchCenter;
import org.cgiar.ccafs.marlo.data.model.ResearchOutcome;
import org.cgiar.ccafs.marlo.data.model.ResearchProgram;
import org.cgiar.ccafs.marlo.data.service.IAuditLogService;
import org.cgiar.ccafs.marlo.data.service.ICenterService;
import org.cgiar.ccafs.marlo.data.service.IProgramService;
import org.cgiar.ccafs.marlo.data.service.IResearchAreaService;
import org.cgiar.ccafs.marlo.data.service.IResearchOutcomeService;
import org.cgiar.ccafs.marlo.data.service.IUserService;
import org.cgiar.ccafs.marlo.security.Permission;
import org.cgiar.ccafs.marlo.utils.APConstants;
import org.cgiar.ccafs.marlo.utils.AutoSaveReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.ibm.icu.util.Calendar;
import org.apache.commons.lang.StringUtils;

/**
 * @author Hermes Jiménez - CIAT/CCAFS
 * @author Christian Garcia - CIAT/CCAFS
 */
@SuppressWarnings("unused")
public class OutcomesAction extends BaseAction {

  private static final long serialVersionUID = -2803122256728476290L;

  public static void printMap(Map<String, Integer> map) {
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      System.out.println("[Key] : " + entry.getKey() + " [Value] : " + entry.getValue());
    }
  }

  private long researchProgramId;
  private String transaction;
  private IProgramService crpProgramManager;
  private IResearchOutcomeService crpProgramOutcomeManager;
  private HashMap<Long, String> idoList;
  private ResearchCenter loggedCrp;
  private List<ResearchOutcome> outcomes;
  private List<ResearchProgram> programs;
  private ResearchProgram selectedProgram;
  private HashMap<Long, String> targetUnitList;
  private ICenterService crpManager;
  private IUserService userManager;
  private List<Integer> milestoneYears;
  private IAuditLogService auditLogManager;
  private List<ResearchArea> reseachAreas;
  private IResearchAreaService researchAreaService;
  private ResearchArea selectedResearchArea;


  @Inject
  public OutcomesAction(APConfig config, ICenterService crpManager, IUserService userManager,
    IAuditLogService auditLogManager, IResearchOutcomeService crpProgramOutcomeManager,
    IProgramService crpProgramManager, IResearchAreaService researchAreaService) {
    super(config);
    this.crpProgramOutcomeManager = crpProgramOutcomeManager;
    this.crpProgramManager = crpProgramManager;
    this.crpManager = crpManager;
    this.userManager = userManager;
    this.auditLogManager = auditLogManager;
    this.researchAreaService = researchAreaService;
  }


  @Override
  public String cancel() {

    Path path = this.getAutoSaveFilePath();

    if (path.toFile().exists()) {
      path.toFile().delete();
    }

    this.setDraft(false);
    Collection<String> messages = this.getActionMessages();
    if (!messages.isEmpty()) {
      String validationMessage = messages.iterator().next();
      this.setActionMessages(null);
      this.addActionWarning(this.getText("cancel.autoSave") + validationMessage);
    } else {
      this.addActionMessage(this.getText("cancel.autoSave"));
    }
    messages = this.getActionMessages();

    return SUCCESS;
  }


  private Path getAutoSaveFilePath() {
    String composedClassName = selectedProgram.getClass().getSimpleName();
    String actionFile = this.getActionName().replace("/", "_");
    String autoSaveFile = selectedProgram.getId() + "_" + composedClassName + "_" + actionFile + ".json";
    return Paths.get(config.getAutoSaveFolder());
  }


  public HashMap<Long, String> getIdoList() {
    return idoList;
  }


  public ResearchCenter getLoggedCrp() {
    return loggedCrp;
  }


  public List<Integer> getMilestoneYears() {
    return milestoneYears;
  }


  /**
   * @return the outcomes
   */
  public List<ResearchOutcome> getOutcomes() {
    return outcomes;
  }


  /**
   * @return the programs
   */
  public List<ResearchProgram> getPrograms() {
    return programs;
  }


  /**
   * @return the reseachAreas
   */
  public List<ResearchArea> getReseachAreas() {
    return reseachAreas;
  }

  public long getResearchProgramId() {
    return researchProgramId;
  }

  /**
   * @return the selectedProgram
   */
  public ResearchProgram getSelectedProgram() {
    return selectedProgram;
  }

  /**
   * @return the selectedResearchArea
   */
  public ResearchArea getSelectedResearchArea() {
    return selectedResearchArea;
  }


  public HashMap<Long, String> getTargetUnitList() {
    return targetUnitList;
  }


  public List<Integer> getTargetYears() {
    List<Integer> targetYears = new ArrayList<>();

    Date date = new Date();
    Calendar calendarStart = Calendar.getInstance();
    calendarStart.setTime(date);

    Calendar calendarEnd = Calendar.getInstance();
    calendarEnd.set(Calendar.YEAR, APConstants.END_YEAR);

    while (calendarStart.get(Calendar.YEAR) <= calendarEnd.get(Calendar.YEAR)) {
      // Adding the year to the list.
      targetYears.add(calendarStart.get(Calendar.YEAR));
      // Adding a year (365 days) to the start date.
      calendarStart.add(Calendar.YEAR, 1);
    }

    return targetYears;
  }


  public String getTransaction() {
    return transaction;
  }


  public void loadInfo() {

  }

  @Override
  public void prepare() throws Exception {


    // IAuditLog ia = auditLogManager.getHistory(4);
    loggedCrp = (ResearchCenter) this.getSession().get(APConstants.SESSION_CRP);
    outcomes = new ArrayList<ResearchOutcome>();
    loggedCrp = crpManager.getCrpById(loggedCrp.getId());
    targetUnitList = new HashMap<>();

    // TODO: Move this to base action. Retrieve research areas from the base action
    reseachAreas = researchAreaService.findAll();
    // TODO: Consider the transaction
    selectedResearchArea = (ResearchArea) this.getSession().get(APConstants.SESSION_RESEARCH_AREA);
    if (this.getRequest().getParameter(APConstants.TRANSACTION_ID) != null) {


      transaction = StringUtils.trim(this.getRequest().getParameter(APConstants.TRANSACTION_ID));
      ResearchProgram history = (ResearchProgram) auditLogManager.getHistory(transaction);
      if (history != null) {
        researchProgramId = history.getId();
        selectedProgram = history;
        // outcomes.addAll(history.getCrpProgramOutcomes());

        this.setEditable(false);
        this.setCanEdit(false);
        programs = new ArrayList<>();
        this.loadInfo();
        programs.add(history);
      } else {
        programs = new ArrayList<>();
        this.transaction = null;

        this.setTransaction("-1");
      }

      Collections.sort(outcomes, (lc1, lc2) -> lc1.getId().compareTo(lc2.getId()));
    } else {
      // TODO: Select programs based on the selected research area.
      // List<CrpProgram> allPrograms =
      // selectedResearchArea.getResearchPrograms().stream()
      // .filter(c -> c.getProgramType() == ProgramType.FLAGSHIP_PROGRAM_TYPE.getValue() && c.isActive())
      // .collect(Collectors.toList());

      // List<CrpProgram> allPrograms =
      // loggedCrp.getCrpPrograms().stream()
      // .filter(c -> c.getProgramType() == ProgramType.FLAGSHIP_PROGRAM_TYPE.getValue() && c.isActive())
      // .collect(Collectors.toList());
      researchProgramId = -1;
      // if (allPrograms != null) {
      //
      // this.programs = allPrograms;
      // try {
      // crpProgramID = Long.parseLong(StringUtils.trim(this.getRequest().getParameter(APConstants.CRP_PROGRAM_ID)));
      // } catch (Exception e) {
      //
      // User user = userManager.getUser(this.getCurrentUser().getId());
      // List<CrpProgramLeader> userLeads =
      // user
      // .getCrpProgramLeaders()
      // .stream()
      // .filter(
      // c -> c.isActive() && c.getCrpProgram().isActive()
      // && c.getCrpProgram().getProgramType() == ProgramType.FLAGSHIP_PROGRAM_TYPE.getValue())
      // .collect(Collectors.toList());
      // if (!userLeads.isEmpty()) {
      // crpProgramID = userLeads.get(0).getCrpProgram().getId();
      // } else {
      // if (!this.programs.isEmpty()) {
      // crpProgramID = this.programs.get(0).getId();
      // }
      // }
      //
      // }
      // } else {
      programs = new ArrayList<>();
      // }

      if (researchProgramId != -1) {
        selectedProgram = crpProgramManager.getCrpProgramById(researchProgramId);
        // outcomes.addAll(selectedProgram.getCrpProgramOutcomes().stream().filter(c -> c.isActive())
        // .collect(Collectors.toList()));

      }

      if (selectedProgram != null) {

        milestoneYears = this.getTargetYears();

        Path path = this.getAutoSaveFilePath();

        if (path.toFile().exists() && this.getCurrentUser().isAutoSave()) {

          BufferedReader reader = null;

          reader = new BufferedReader(new FileReader(path.toFile()));

          Gson gson = new GsonBuilder().create();


          JsonObject jReader = gson.fromJson(reader, JsonObject.class);

          AutoSaveReader autoSaveReader = new AutoSaveReader();

          selectedProgram = (ResearchProgram) autoSaveReader.readFromJson(jReader);
          // outcomes = selectedProgram.getOutcomes();
          selectedProgram.setAcronym(crpProgramManager.getCrpProgramById(selectedProgram.getId()).getAcronym());
          // selectedProgram.setModifiedBy(userManager.getUser(selectedProgram.getModifiedBy().getId()));
          // selectedProgram.set.setCrp(loggedCrp);
          if (outcomes == null) {
            outcomes = new ArrayList<>();
          }
          // for (CrpProgramOutcome outcome : outcomes) {
          //
          // if (outcome.getSubIdos() != null) {
          // for (CrpOutcomeSubIdo subIdo : outcome.getSubIdos()) {
          // if (subIdo.getSrfSubIdo() != null && subIdo.getSrfSubIdo().getId() != null) {
          // subIdo.setSrfSubIdo(srfSubIdoManager.getSrfSubIdoById(subIdo.getSrfSubIdo().getId()));
          // }
          // }
          // }
          // }

          reader.close();
          this.setDraft(true);
        } else {
          this.loadInfo();
          this.setDraft(false);
        }

        String params[] = {loggedCrp.getAcronym(), selectedProgram.getId().toString()};
        this.setBasePermission(this.getText(Permission.IMPACT_PATHWAY_BASE_PERMISSION, params));
        // if (!selectedProgram.getSubmissions().isEmpty()) {
        // this.setCanEdit(false);
        // this.setEditable(false);
        // // this.setSubmission(selectedProgram.getSubmissions().stream().collect(Collectors.toList()).get(0));
        // }

      }

      if (this.isHttpPost()) {
        outcomes.clear();
      }
    }


    idoList = new HashMap<>();
    // srfIdos = new ArrayList<>();
    // for (SrfIdo srfIdo : srfIdoManager.findAll().stream().filter(c -> c.isActive()).collect(Collectors.toList())) {
    // idoList.put(srfIdo.getId(), srfIdo.getDescription());
    //
    // srfIdo.setSubIdos(srfIdo.getSrfSubIdos().stream().filter(c -> c.isActive()).collect(Collectors.toList()));
    // srfIdos.add(srfIdo);
    // }


  }

  @Override
  public String save() {


    return SUCCESS;


  }


  public void setIdoList(HashMap<Long, String> idoList) {
    this.idoList = idoList;
  }

  public void setLoggedCrp(ResearchCenter loggedCrp) {
    this.loggedCrp = loggedCrp;
  }


  public void setMilestoneYears(List<Integer> milestoneYears) {
    this.milestoneYears = milestoneYears;
  }


  /**
   * @param outcomes the outcomes to set
   */
  public void setOutcomes(List<ResearchOutcome> outcomes) {
    this.outcomes = outcomes;
  }


  /**
   * @param programs the programs to set
   */
  public void setPrograms(List<ResearchProgram> programs) {
    this.programs = programs;
  }


  /**
   * @param reseachAreas the reseachAreas to set
   */
  public void setReseachAreas(List<ResearchArea> reseachAreas) {
    this.reseachAreas = reseachAreas;
  }


  public void setResearchProgramId(long crpProgramID) {
    this.researchProgramId = crpProgramID;
  }


  /**
   * @param selectedProgram the selectedProgram to set
   */
  public void setSelectedProgram(ResearchProgram selectedProgram) {
    this.selectedProgram = selectedProgram;
  }

  /**
   * @param selectedResearchArea the selectedResearchArea to set
   */
  public void setSelectedResearchArea(ResearchArea selectedResearchArea) {
    this.selectedResearchArea = selectedResearchArea;
  }


  public void setTargetUnitList(HashMap<Long, String> targetUnitList) {
    this.targetUnitList = targetUnitList;
  }


  public void setTransaction(String transactionId) {
    this.transaction = transactionId;
  }


  private HashMap<Long, String> sortByComparator(HashMap<Long, String> unsortMap) {

    // Convert Map to List
    List<HashMap.Entry<Long, String>> list = new LinkedList<HashMap.Entry<Long, String>>(unsortMap.entrySet());

    // Sort list with comparator, to compare the Map values
    Collections.sort(list, new Comparator<HashMap.Entry<Long, String>>() {

      @Override
      public int compare(HashMap.Entry<Long, String> o1, HashMap.Entry<Long, String> o2) {
        return (o1.getValue().toLowerCase().trim()).compareTo(o2.getValue().toLowerCase().trim());
      }
    });

    // Convert sorted map back to a Map
    HashMap<Long, String> sortedMap = new LinkedHashMap<Long, String>();
    for (Iterator<HashMap.Entry<Long, String>> it = list.iterator(); it.hasNext();) {
      HashMap.Entry<Long, String> entry = it.next();
      sortedMap.put(entry.getKey(), entry.getValue());
    }
    return sortedMap;
  }

  @Override
  public void validate() {
    // if (save) {
    // validator.validate(this, outcomes, selectedProgram, true);
    // }
  }

}